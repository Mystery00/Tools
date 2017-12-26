/*
 * Created by Mystery0 on 17-12-26 下午11:29.
 * Copyright (c) 2017. All Rights reserved.
 *
 * Last modified 17-10-12 下午1:20
 */

package vip.mystery0.tools.crashHandler

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