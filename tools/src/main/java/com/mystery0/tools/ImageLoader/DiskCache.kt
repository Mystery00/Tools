package com.mystery0.tools.ImageLoader

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory

import com.android.volley.toolbox.ImageLoader

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.security.MessageDigest

class DiskCache(context: Context) : ImageLoader.ImageCache
{
	private val CacheDir: String = context.externalCacheDir!!.absolutePath + File.separator

	override fun getBitmap(url: String): Bitmap?
	{
		return BitmapFactory.decodeFile(CacheDir + MD5(url))
	}

	override fun putBitmap(url: String, bitmap: Bitmap?)
	{
		val fileDir = File(CacheDir)
		if (fileDir.exists() || fileDir.mkdirs())
		{
			var fileOutputStream: FileOutputStream? = null
			try
			{
				val file = File(CacheDir + MD5(url))
				file.createNewFile()
				fileOutputStream = FileOutputStream(file)
				bitmap!!.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
			}
			catch (e: IOException)
			{
				e.printStackTrace()
			}
			finally
			{
				if (fileOutputStream != null)
				{
					try
					{
						fileOutputStream.close()
					}
					catch (e: IOException)
					{
						e.printStackTrace()
					}

				}
			}
		}
	}

	private fun MD5(source: String): String
	{
		val messageDigest = MessageDigest.getInstance("MD5")
		val buff = messageDigest.digest(source.toByteArray())
		return bytesToHex(buff)
	}

	private fun bytesToHex(bytes: ByteArray): String
	{
		val md5str = StringBuffer()
		var digital: Int
		for (i in bytes.indices)
		{
			digital = bytes[i].toInt()

			if (digital < 0)
			{
				digital += 256
			}
			if (digital < 16)
			{
				md5str.append("0")
			}
			md5str.append(Integer.toHexString(digital))
		}
		return md5str.toString().toUpperCase()
	}
}
