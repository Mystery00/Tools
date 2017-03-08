package com.mystery0.tools.Logs;

import android.util.Log;

public class Logs
{
    private static final int VERBOSE = 2;
    private static final int DEBUG = 3;
    private static final int INFO = 4;
    private static final int WARN = 5;
    private static final int ERROR = 6;

    private static int SET;

    public enum LogLevel
    {
        Debug, Release
    }

    public static void setLevel(LogLevel level)
    {
        switch (level)
        {
            case Debug:
                SET = 0;
                break;
            case Release:
                SET = 5;
                break;
        }
    }

    public static void v(String tag, String message)
    {
        if (SET < VERBOSE)
        {
            Log.v(tag, message);
        }
    }

    public static void i(String tag, String message)
    {
        if (SET < INFO)
        {
            Log.i(tag, message);
        }
    }

    public static void d(String tag, String message)
    {
        if (SET < DEBUG)
        {
            Log.d(tag, message);
        }
    }

    public static void w(String tag, String message)
    {
        if (SET < WARN)
        {
            Log.w(tag, message);
        }
    }

    public static void e(String tag, String message)
    {
        if (SET < ERROR)
        {
            Log.e(tag, message);
        }
    }
}
