package com.mystery0.tools.Logs

import android.util.Log

object Logs
{
	private val VERBOSE = 2
	private val DEBUG = 3
	private val INFO = 4
	private val WARN = 5
	private val ERROR = 6

	private var SET: Int = 0

	enum class LogLevel
	{
		Debug, Release
	}

	@JvmStatic fun setLevel(level: LogLevel)
	{
		when (level)
		{
			Logs.LogLevel.Debug -> SET = 0
			Logs.LogLevel.Release -> SET = 5
		}
	}

	@JvmStatic fun v(tag: String, message: String)
	{
		if (SET < VERBOSE)
		{
			Log.v(tag, message)
		}
	}

	@JvmStatic fun i(tag: String, message: String)
	{
		if (SET < INFO)
		{
			Log.i(tag, message)
		}
	}

	@JvmStatic fun d(tag: String, message: String)
	{
		if (SET < DEBUG)
		{
			Log.d(tag, message)
		}
	}

	@JvmStatic fun w(tag: String, message: String)
	{
		if (SET < WARN)
		{
			Log.w(tag, message)
		}
	}

	@JvmStatic fun e(tag: String, message: String)
	{
		if (SET < ERROR)
		{
			Log.e(tag, message)
		}
	}
}
