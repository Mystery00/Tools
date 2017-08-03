package com.mystery0.tools.SnackBar

import android.content.Context
import android.support.design.widget.BaseTransientBottomBar
import android.support.design.widget.Snackbar
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.accessibility.AccessibilityManager
import com.mystery0.tools.Logs.Logs
import com.mystery0.tools.R

object ASnackBar
{
	private val TAG = "ASnackBar"

	@JvmStatic fun disableAccessibility(context: Context)
	{
		val contextThemeWrapper = ContextThemeWrapper(context, R.style.Theme_AppCompat)
		val view = LayoutInflater.from(contextThemeWrapper).inflate(R.layout.mystery0_snack_bar_coordinator_layout, null)
		Snackbar.make(view, "SnackBar", Snackbar.LENGTH_SHORT)
				.apply {
					try
					{
						val mAccessibilityManagerField = BaseTransientBottomBar::class.java.getDeclaredField("mAccessibilityManager")
						mAccessibilityManagerField.isAccessible = true
						val accessibilityManager = mAccessibilityManagerField.get(this)
						val mIsEnabledField = AccessibilityManager::class.java.getDeclaredField("mIsEnabled")
						mIsEnabledField.isAccessible = true
						mIsEnabledField.setBoolean(accessibilityManager, false)
						mAccessibilityManagerField.set(this, accessibilityManager)
					}
					catch (e: Exception)
					{
						Logs.e(TAG, "disableAccessibility: $e")
					}
				}
	}
}