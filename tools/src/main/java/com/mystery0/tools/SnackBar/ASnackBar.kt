package com.mystery0.tools.SnackBar

import android.content.res.ColorStateList
import android.support.annotation.ColorInt
import android.support.annotation.NonNull
import android.support.annotation.StringRes
import android.support.design.widget.BaseTransientBottomBar
import android.support.design.widget.Snackbar
import android.view.View
import android.view.accessibility.AccessibilityManager
import com.mystery0.tools.Logs.Logs

object ASnackBar
{
	private val TAG = "ASnackBar"
	private lateinit var snackBar: Snackbar

	init
	{
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
			Logs.d(TAG, "Reflection error: $e")
		}
	}

	@JvmStatic fun make(@NonNull view: View, @NonNull text: CharSequence,
						@BaseTransientBottomBar.Duration duration: Int): ASnackBar
	{
		snackBar = Snackbar.make(view, text, duration)
		return this
	}

	@JvmStatic fun make(@NonNull view: View, @StringRes resId: Int,
						@BaseTransientBottomBar.Duration duration: Int): ASnackBar
	{
		return make(view, view.resources.getText(resId), duration)
	}

	@JvmStatic fun setText(@NonNull message: CharSequence): ASnackBar
	{
		snackBar.setText(message)
		return this
	}

	@JvmStatic fun setText(@StringRes resId: Int): ASnackBar
	{
		snackBar.setText(resId)
		return this
	}

	@JvmStatic fun setAction(text: CharSequence, listener: View.OnClickListener): ASnackBar
	{
		snackBar.setAction(text, listener)
		return this
	}

	@JvmStatic fun setAction(@StringRes resId: Int, listener: View.OnClickListener): ASnackBar
	{
		snackBar.setAction(resId, listener)
		return this
	}

	@JvmStatic fun setActionTextColor(colors: ColorStateList): ASnackBar
	{
		snackBar.setActionTextColor(colors)
		return this
	}

	@JvmStatic fun setActionTextColor(@ColorInt color: Int): ASnackBar
	{
		snackBar.setActionTextColor(color)
		return this
	}

	@JvmStatic fun addCallBack(callback: BaseTransientBottomBar.BaseCallback<Snackbar>): ASnackBar
	{
		snackBar.addCallback(callback)
		return this
	}

	@JvmStatic fun show()
	{
		snackBar.show()
	}
}