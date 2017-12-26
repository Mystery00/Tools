/*
 * Created by Mystery0 on 17-12-26 下午11:29.
 * Copyright (c) 2017. All Rights reserved.
 *
 * Last modified 17-10-12 下午1:20
 */

package vip.mystery0.tools.crashHandler

/**
 * Created by mystery0.
 */
interface AutoCleanListener
{
	fun done()
	fun error(message: String?)
}