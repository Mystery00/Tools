package com.mystery0.tools.ImageLoader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.android.volley.toolbox.ImageLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DiskCache implements ImageLoader.ImageCache
{
    private String CacheDir;
    private String fileName;

    DiskCache(Context context, String fileName)
    {
        //noinspection ConstantConditions
        CacheDir = context.getExternalCacheDir().getAbsolutePath() + "/";
        this.fileName = fileName;
    }

    @Override
    public Bitmap getBitmap(String url)
    {
        return BitmapFactory.decodeFile(CacheDir + (fileName == null || fileName.equals("") ? getFileName(url) : fileName) + ".png");
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap)
    {
        File fileDir = new File(CacheDir);
        if (fileDir.exists() || fileDir.mkdirs())
        {
            FileOutputStream fileOutputStream = null;
            try
            {
                File file = new File(CacheDir + (fileName == null || fileName.equals("") ? getFileName(url) : fileName) + ".png");
                //noinspection ResultOfMethodCallIgnored
                file.createNewFile();
                fileOutputStream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            } catch (IOException e)
            {
                e.printStackTrace();
            } finally
            {
                if (fileOutputStream != null)
                {
                    try
                    {
                        fileOutputStream.close();
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 通过url获取文件名
     *
     * @param url 地址
     * @return 文件名
     */
    private static String getFileName(String url)
    {
        int start = url.lastIndexOf("/");
        int end = url.lastIndexOf(".");
        if (start != -1 && end != -1)
        {
            return url.substring(start + 1, end);
        } else
        {
            return null;
        }
    }
}
