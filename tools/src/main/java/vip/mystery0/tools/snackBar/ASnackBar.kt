/*
 * Created by Mystery0 on 17-12-26 下午11:29.
 * Copyright (c) 2017. All Rights reserved.
 *
 * Last modified 17-10-12 下午1:20
 */

package vip.mystery0.tools.snackBar

import android.content.Context
import android.support.design.widget.BaseTransientBottomBar
import android.support.design.widget.Snackbar
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.accessibility.AccessibilityManager
import vip.mystery0.tools.logs.Logs
import vip.mystery0.tools.R

object ASnackBar {
    private val TAG = "ASnackBar"

    @JvmStatic
    fun disableAccessibility(context: Context) {
        try {
            val contextThemeWrapper = ContextThemeWrapper(context, R.style.Theme_AppCompat)
            val view = LayoutInflater.from(contextThemeWrapper).inflate(R.layout.mystery0_snack_bar_coordinator_layout, null)
            Snackbar.make(view, "SnackBar", Snackbar.LENGTH_SHORT)
                    .apply {
                        val mAccessibilityManagerField = BaseTransientBottomBar::class.java.getDeclaredField("mAccessibilityManager")
                        mAccessibilityManagerField.isAccessible = true
                        val accessibilityManager = mAccessibilityManagerField.get(this)
                        val mIsEnabledField = AccessibilityManager::class.java.getDeclaredField("mIsEnabled")
                        mIsEnabledField.isAccessible = true
                        mIsEnabledField.setBoolean(accessibilityManager, false)
                        mAccessibilityManagerField.set(this, accessibilityManager)
                    }
        } catch (e: Exception) {
            Logs.e(TAG, "disableAccessibility: 禁用无障碍服务失败")
            e.printStackTrace()
        }
    }
}