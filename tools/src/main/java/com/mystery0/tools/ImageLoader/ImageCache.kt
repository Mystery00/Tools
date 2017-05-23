package com.mystery0.tools.ImageLoader

import android.content.Context
import android.graphics.Bitmap
import android.util.LruCache
import com.android.volley.toolbox.ImageLoader

class ImageCache internal constructor(context: Context,
									  private var fileName: String?) : ImageLoader.ImageCache
{
	private val lruCache: LruCache<String, Bitmap>
	private val diskCache: DiskCache

	init
	{
		this.fileName = fileName
		lruCache = object : LruCache<String, Bitmap>((Runtime.getRuntime().maxMemory() / 1024).toInt() / 4)
		{
			override fun sizeOf(key: String, value: Bitmap): Int
			{
				return value.rowBytes * value.height / 1024
			}
		}
		diskCache = DiskCache(context, fileName)
	}

	override fun getBitmap(url: String?): Bitmap
	{
		var bitmap = lruCache.get(if (fileName == "") url else fileName)
		if (bitmap == null)
		{
			bitmap = diskCache.getBitmap(if (fileName == "") url else fileName)
		}
		return bitmap
	}

	override fun putBitmap(url: String?, bitmap: Bitmap?)
	{
		lruCache.put(if (fileName == "") url else fileName, bitmap)
		diskCache.putBitmap(if (fileName == "") url else fileName, bitmap)
	}
}