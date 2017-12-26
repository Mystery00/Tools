/*
 * Created by Mystery0 on 17-12-26 下午11:29.
 * Copyright (c) 2017. All Rights reserved.
 *
 * Last modified 17-10-12 下午1:20
 */

package vip.mystery0.tools.logs

import android.util.Log

object Logs {
    private val VERBOSE = 2
    private val DEBUG = 3
    private val INFO = 4
    private val WARN = 5
    private val ERROR = 6

    val Debug = 0
    val Release = 5

    private var SET: Int = 0

    @JvmStatic
    fun setLevel(level: Int) {
        SET = level
    }

    @JvmStatic
    fun v(tag: String, message: String) {
        if (SET < VERBOSE) {
            Log.v(tag, message)
        }
    }

    @JvmStatic
    fun i(tag: String, message: String) {
        if (SET < INFO) {
            Log.i(tag, message)
        }
    }

    @JvmStatic
    fun d(tag: String, message: String) {
        if (SET < DEBUG) {
            Log.d(tag, message)
        }
    }

    @JvmStatic
    fun w(tag: String, message: String) {
        if (SET < WARN) {
            Log.w(tag, message)
        }
    }

    @JvmStatic
    fun e(tag: String, message: String) {
        if (SET < ERROR) {
            Log.e(tag, message)
        }
    }

    @JvmStatic
    fun wtf(tag: String, message: String, throwable: Throwable) {
        Log.wtf(tag, message, throwable)
    }
}
