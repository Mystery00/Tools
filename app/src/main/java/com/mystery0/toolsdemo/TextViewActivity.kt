/*
 * Created by Mystery0 on 18-1-23 下午3:23.
 * Copyright (c) 2018. All Rights reserved.
 *
 * Last modified 18-1-23 下午3:23
 */

package com.mystery0.toolsdemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_text_view.*
import vip.mystery0.tools.logs.Logs

class TextViewActivity : AppCompatActivity() {
    private val TAG = "TextViewActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_view)

        textView.setText(R.string.long_text)
        button1.setOnClickListener {
            Logs.i(TAG, "onCreate: resize: " + textView.resize())
            Logs.i(TAG, "onCreate: getCharNum: " + textView.getCharNum())
            Logs.i(TAG, "onCreate: getLineNum: " + textView.getLineNum())
        }
    }
}
