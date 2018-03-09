/*
 * Created by Mystery0 on 18-3-10 上午12:52.
 * Copyright (c) 2018. All Rights reserved.
 *
 * Last modified 18-3-10 上午12:52
 */

package vip.mystery0.tools.utils

import android.content.Context

object Mystery0DensityUtil {
	fun dip2px(context: Context,
			   dpValue: Float): Int = (dpValue * context.resources.displayMetrics.density + 0.5F).toInt()

	fun px2dip(context: Context,
			   pxValue: Float): Int = (pxValue / context.resources.displayMetrics.density + 0.5F).toInt()

	fun getScreenWidth(context: Context): Int {
		return context.resources.displayMetrics.widthPixels
	}

	fun getWidth(context: Context, leftMarginDip: Float, rightMarginDip: Float): Int {
		return getScreenWidth(context) - dip2px(context, leftMarginDip) - dip2px(context, rightMarginDip)
	}
}