package com.mystery0.tools.ImageLoader;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

public class ImageCache implements ImageLoader.ImageCache
{
	private LruCache<String, Bitmap> lruCache;
	private DiskCache diskCache;
	private String fileName;

	public ImageCache(Context context)
	{
		this(context, null);
	}

	public ImageCache(Context context, String fileName)
	{
		this.fileName = fileName;
		lruCache = new LruCache<String, Bitmap>((int) (Runtime.getRuntime().maxMemory() / 1024) / 4)
		{
			@Override
			protected int sizeOf(String key, Bitmap value)
			{
				return value.getRowBytes() * value.getHeight() / 1024;
			}
		};
		diskCache = new DiskCache(context, fileName);
	}

	@Override
	public Bitmap getBitmap(String url)
	{
		Bitmap bitmap = lruCache.get((fileName == null || fileName.equals("") ? url : fileName));
		if (bitmap == null)
		{
			bitmap = diskCache.getBitmap((fileName == null || fileName.equals("") ? url : fileName));
		}
		return bitmap;
	}

	@Override
	public void putBitmap(String url, Bitmap bitmap)
	{
		lruCache.put((fileName == null || fileName.equals("") ? url : fileName), bitmap);
		diskCache.putBitmap((fileName == null || fileName.equals("") ? url : fileName), bitmap);
	}
}
