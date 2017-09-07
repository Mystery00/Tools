package com.mystery0.tools.CrashHandler

/**
 * Created by mystery0.
 */
interface AutoCleanListener
{
	fun done()
	fun error(message: String?)
}