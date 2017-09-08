package vip.mystery0.tools.ImageLoader

import android.content.Context
import android.graphics.Bitmap
import android.util.LruCache
import com.android.volley.toolbox.ImageLoader

class ImageCache(context: Context) : ImageLoader.ImageCache
{
	private val lruCache: LruCache<String, Bitmap>
	private val diskCache: DiskCache

	init
	{
		lruCache = object : LruCache<String, Bitmap>((Runtime.getRuntime().maxMemory() / 1024).toInt() / 4)
		{
			override fun sizeOf(key: String, value: Bitmap): Int
			{
				return value.rowBytes * value.height / 1024
			}
		}
		diskCache = DiskCache(context)
	}

	override fun getBitmap(url: String): Bitmap?
	{
		var bitmap = lruCache.get(url)
		if (bitmap == null)
		{
			bitmap = diskCache.getBitmap(url)
		}
		return bitmap
	}

	override fun putBitmap(url: String, bitmap: Bitmap?)
	{
		lruCache.put(url, bitmap)
		diskCache.putBitmap(url, bitmap)
	}
}