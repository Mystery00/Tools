package com.mystery0.tools.ImageLoader

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory

import com.android.volley.toolbox.ImageLoader

import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class DiskCache internal constructor(context: Context,
									 private val fileName: String?) : ImageLoader.ImageCache
{
	private val CacheDir: String = context.externalCacheDir!!.absolutePath + "/"

	override fun getBitmap(url: String?): Bitmap
	{
		return BitmapFactory.decodeFile(CacheDir + (if (fileName == null || fileName == "") File(url).name else fileName) + ".png")
	}

	override fun putBitmap(url: String?, bitmap: Bitmap?)
	{
		val fileDir = File(CacheDir)
		if (fileDir.exists() || fileDir.mkdirs())
		{
			var fileOutputStream: FileOutputStream? = null
			try
			{
				val file = File(CacheDir + (if (fileName == null || fileName == "") File(url).name else fileName) + ".png")

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
}
