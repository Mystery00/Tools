package com.mystery0.tools.CrashHandler

import java.io.File

/**
 * Created by mystery0.
 */
interface CatchExceptionListener
{
	fun onException(date: String, file: File, appVersionName: String, appVersionCode: Int,
					AndroidVersion: String,
					sdk: Int, vendor: String, model: String, ex: Throwable)
}