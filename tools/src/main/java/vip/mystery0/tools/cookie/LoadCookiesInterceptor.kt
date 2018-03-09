/*
 * Created by Mystery0 on 18-3-10 上午12:59.
 * Copyright (c) 2018. All Rights reserved.
 *
 * Last modified 18-3-4 上午9:26
 */

package vip.mystery0.tools.cookie

import android.text.TextUtils
import okhttp3.Interceptor
import okhttp3.Response

class LoadCookiesInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        val cookie = CookieManager.getCookie(request.url().host())
        if (!TextUtils.isEmpty(cookie))
            builder.addHeader("Cookie", cookie!!)
        return chain.proceed(builder.build())
    }
}