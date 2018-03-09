/*
 * Created by Mystery0 on 18-3-10 上午12:54.
 * Copyright (c) 2018. All Rights reserved.
 *
 * Last modified 18-3-10 上午12:54
 */

package vip.mystery0.tools.cookie

import android.content.SharedPreferences

object CookieManager {
	private const val COOKIE_PREFS = "cookies"
	private var cookiePreferences:SharedPreferences?=null

	fun putCookie(domain: String, cookie: String?) {
		if (cookiePreferences==null)
			return
		cookiePreferences!!.edit()
				.putString(domain, cookie)
				.apply()
	}

	fun getCookie(domain: String): String? {
		return cookiePreferences?.getString(domain, null)
	}
}