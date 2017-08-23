package com.mystery0.tools.CrashHandler

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.nfc.FormatException
import android.os.Build
import android.os.Environment
import com.mystery0.tools.Logs.Logs
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("StaticFieldLeak")
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
	private lateinit var mDefaultCrashHandler: Thread.UncaughtExceptionHandler

	private lateinit var mContext: Context

	private lateinit var sharedPreferences: SharedPreferences

	private var isSendException = false

	private lateinit var catchExceptionListener: CatchExceptionListener

	interface AutoCleanListener
	{
		fun done()
		fun error(message: String?)
	}

	@JvmStatic
	fun getInstance(context: Context): CrashHandler
	{
		mContext = context
		sharedPreferences = context.getSharedPreferences("CrashHandlerConfiguration", Context.MODE_PRIVATE)
		if (mCrashHandler == null)
		{
			mCrashHandler = CrashHandler
		}
		Logs.setLevel(Logs.LogLevel.Release)
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

	fun isAutoClean(): CrashHandler
	{
		return isAutoClean(3)
	}

	fun isAutoClean(cleanTime: Int): CrashHandler
	{
		if (cleanTime < 0)
		{
			throw FormatException("clean cleanTime cannot be less than 0")
		}
		sharedPreferences.edit().putBoolean("isAutoClean", true).putLong("cleanTime", (cleanTime * 86400000).toLong()).apply()
		return this
	}

	fun debug(): CrashHandler
	{
		Logs.setLevel(Logs.LogLevel.Debug)
		return this
	}

	fun sendException(catchExceptionListener: CatchExceptionListener): CrashHandler
	{
		this.catchExceptionListener = catchExceptionListener
		isSendException = true
		return this
	}

	fun clean(autoCleanListener: AutoCleanListener)
	{
		try
		{
			if (sharedPreferences.getBoolean("isAutoClean", false))
			{
				val dir = File(PATH + File.separator + this.dir + File.separator)
				val time = sharedPreferences.getLong("cleanTime", 3 * 86400000)
				if (dir.exists() || dir.mkdirs())
				{
					for (file: File in dir.listFiles())
					{
						if (file.name.contains(fileNamePrefix) && file.name.contains(fileNameSuffix))
						{
							val now = Calendar.getInstance().timeInMillis
							val modified = file.lastModified()
							Logs.d(TAG, "fileName: " + file.name)
							Logs.d(TAG, "fileTime: " + (now - modified) / 86400000)
							if (now - modified >= time)
								file.delete()
						}
					}
				}
				autoCleanListener.done()
			}
			else
			{
				autoCleanListener.error("auto clean disabled!")
			}
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
		}
		catch (e: IOException)
		{
			e.printStackTrace()
		}

		//打印出当前调用栈信息
		ex.printStackTrace()
		//如果系统提供了默认的异常处理器，则交给系统去结束我们的程序
		mDefaultCrashHandler.uncaughtException(thread, ex)
	}

	private fun dumpExceptionToSDCard(ex: Throwable)
	{
		if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED)
		{
			Logs.w(TAG, "sdcard unmounted,skip dump exception")
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
			val printWriter = PrintWriter(BufferedWriter(FileWriter(file)))
			//导出异常发生时间
			printWriter.println(time)

			//导出手机信息
			//应用的版本名称和版本号
			val packageManager = mContext.packageManager
			val packageInfo = packageManager.getPackageInfo(mContext.packageName, PackageManager.GET_ACTIVITIES)
			printWriter.print("App Version: ")
			printWriter.print(packageInfo.versionName)
			printWriter.print('_')
			printWriter.println(packageInfo.versionCode)

			//android版本号
			printWriter.print("OS Version: ")
			printWriter.print(Build.VERSION.RELEASE)
			printWriter.print("_")
			printWriter.println(Build.VERSION.SDK_INT)

			//手机制造商
			printWriter.print("Vendor: ")
			printWriter.println(Build.MANUFACTURER)

			//手机型号
			printWriter.print("Model: ")
			printWriter.println(Build.MODEL)

			printWriter.println()
			//导出异常的调用栈信息
			ex.printStackTrace(printWriter)
			printWriter.close()

			//这里可以通过网络上传异常信息到服务器，便于开发人员分析日志从而解决bug
			if (isSendException)
			{
				catchExceptionListener.onException(time, file, packageInfo.versionName, packageInfo.versionCode, Build.VERSION.RELEASE, Build.VERSION.SDK_INT, Build.MANUFACTURER, Build.MODEL, ex)
			}
		}
		catch (e: Exception)
		{
			Logs.wtf(TAG, "dumpExceptionToSDCard: dump crash info failed", e)
		}
	}

	interface CatchExceptionListener
	{
		fun onException(date: String, file: File, appVersionName: String, appVersionCode: Int,
						AndroidVersion: String,
						sdk: Int, vendor: String, model: String, ex: Throwable)
	}

}