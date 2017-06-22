package com.mystery0.tools.CrashHandler

import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.nfc.FormatException
import android.os.Build
import android.os.Environment
import android.os.Process
import android.util.Log
import com.mystery0.tools.Logs.Logs
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

object CrashHandler : Thread.UncaughtExceptionHandler
{
	private var mCrashHandler: CrashHandler? = null

	private val TAG = "CrashHandler"

	private val PATH = Environment.getExternalStorageDirectory().path

	//log文件存储目录
	private var dir = "log"

	//log文件前缀名
	private var fileNamePrefix = "crash"

	//log文件的扩展名
	private var fileNameSuffix = "txt"

	//系统默认的异常处理（默认情况下，系统会终止当前的异常程序）
	private var mDefaultCrashHandler: Thread.UncaughtExceptionHandler? = null

	private var mContext: Context? = null

	private var sharedPreferences: SharedPreferences? = null

	interface AutoCleanListener
	{
		fun done()
		fun error(message: String?)
	}

	@JvmStatic fun getInstance(context: Context): CrashHandler
	{
		mContext = context
		sharedPreferences = mContext!!.getSharedPreferences("CrashHandlerConfiguration", Context.MODE_PRIVATE)
		if (mCrashHandler == null)
		{
			mCrashHandler = CrashHandler
		}
		return mCrashHandler as CrashHandler
	}

	fun setDirectory(name: String): CrashHandler
	{
		dir = name
		return this
	}

	fun setPrefixName(fileName: String): CrashHandler
	{
		fileNamePrefix = fileName
		return this
	}

	fun setExtensionName(fileName: String): CrashHandler
	{
		fileNameSuffix = fileName
		return this
	}

	fun isAutoClean(isAutoClean: Boolean): CrashHandler
	{
		return isAutoClean(isAutoClean, 3)
	}

	fun isAutoClean(isAutoClean: Boolean, cleanTime: Int): CrashHandler
	{
		if (cleanTime < 0)
		{
			throw FormatException("clean cleanTime cannot be less than 0")
		}
		sharedPreferences!!.edit().putBoolean("isAutoClean", isAutoClean).putLong("cleanTime", (cleanTime * 86400000).toLong()).apply()
		return this
	}

	fun clean(autoCleanListener: AutoCleanListener)
	{
		try
		{
			if (sharedPreferences!!.getBoolean("isAutoClean", false))
			{
				val dir = File(PATH + File.separator + this.dir + File.separator)
				val time = sharedPreferences!!.getLong("cleanTime", 3 * 86400000)
				if (dir.exists() || dir.mkdirs())
				{
					val files = dir.listFiles()
					for (file: File in files)
					{
						if (file.name.contains(fileNamePrefix) && file.name.contains(fileNameSuffix))
						{
							val now = Calendar.getInstance().timeInMillis
							val modified = file.lastModified()
							if (now - modified >= time * 86400000)
								file.delete()
						}
					}
				}
			}
			autoCleanListener.done()
		}
		catch (e: Exception)
		{
			autoCleanListener.error(e.message)
		}
	}

	//这里主要完成初始化工作
	fun init()
	{
		mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler()
		Thread.setDefaultUncaughtExceptionHandler(this)
	}

	/**
	 * 这个是最关键的函数，当程序中有未被捕获的异常，系统将会自动调用#uncaughtException方法
	 * thread为出现未捕获异常的线程，ex为未捕获的异常，有了这个ex，我们就可以得到异常信息。
	 */
	override fun uncaughtException(thread: Thread?, ex: Throwable)
	{
		try
		{
			//导出异常信息到SD卡中
			dumpExceptionToSDCard(ex)
			//这里可以通过网络上传异常信息到服务器，便于开发人员分析日志从而解决bug
			//sendException();
		}
		catch (e: IOException)
		{
			e.printStackTrace()
		}

		//打印出当前调用栈信息
		ex.printStackTrace()

		//如果系统提供了默认的异常处理器，则交给系统去结束我们的程序
		if (mDefaultCrashHandler != null)
		{
			mDefaultCrashHandler!!.uncaughtException(thread, ex)
		}
		else
		{
			Process.killProcess(Process.myPid())
		}
	}

	private fun dumpExceptionToSDCard(ex: Throwable)
	{
		if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED)
		{
			Log.w(TAG, "sdcard unmounted,skip dump exception")
			return
		}

		val dir = File(PATH + File.separator + this.dir + File.separator)
		if (!dir.exists())
		{
			dir.mkdirs()
		}
		val time: String = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(Calendar.getInstance().time)
		//以当前时间创建文件
		val file = File(PATH + File.separator + this.dir + File.separator + fileNamePrefix + time + "." + fileNameSuffix)

		try
		{
			val pw = PrintWriter(BufferedWriter(FileWriter(file)))
			//导出异常发生时间
			pw.println(time)

			//导出手机信息
			dumpPhoneInfo(pw)

			pw.println()
			//导出异常的调用栈信息
			ex.printStackTrace(pw)

			pw.close()
		}
		catch (e: Exception)
		{
			Log.w(TAG, "dump crash info failed")
		}
	}

	private fun dumpPhoneInfo(pw: PrintWriter)
	{
		//应用的版本名称和版本号
		val pm = mContext!!.packageManager
		val pi = pm.getPackageInfo(mContext!!.packageName, PackageManager.GET_ACTIVITIES)
		pw.print("App Version: ")
		pw.print(pi.versionName)
		pw.print('_')
		pw.println(pi.versionCode)

		//android版本号
		pw.print("OS Version: ")
		pw.print(Build.VERSION.RELEASE)
		pw.print("_")
		pw.println(Build.VERSION.SDK_INT)

		//手机制造商
		pw.print("Vendor: ")
		pw.println(Build.MANUFACTURER)

		//手机型号
		pw.print("Model: ")
		pw.println(Build.MODEL)
	}

}