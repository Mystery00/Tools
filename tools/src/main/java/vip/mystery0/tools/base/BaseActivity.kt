/*
 * Created by Mystery0 on 18-3-10 上午1:02.
 * Copyright (c) 2018. All Rights reserved.
 *
 * Last modified 18-3-10 上午1:02
 */

package vip.mystery0.tools.base

import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

abstract class BaseActivity : AppCompatActivity() {
	lateinit var TAG: String

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		TAG = javaClass.simpleName
		initView()
		initData()
		loadDataToView()
		requestData()
		monitor()
	}

	open fun initView() {}
	open fun initData() {}
	open fun loadDataToView() {}
	open fun requestData() {}
	open fun monitor() {}

	fun toastMessage(@StringRes id: Int, duration: Int = Toast.LENGTH_LONG) {
		toastMessage(getString(id), duration)
	}

	fun toastMessage(message: String?, duration: Int = Toast.LENGTH_LONG) {
		Toast.makeText(this, message, duration).show()
	}
}