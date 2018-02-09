/*
 * Created by Mystery0 on 18-1-23 下午3:33.
 * Copyright (c) 2018. All Rights reserved.
 *
 * Last modified 18-1-23 下午3:33
 */

package com.mystery0.toolsdemo

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView

class LongTextView(context: Context?, attrs: AttributeSet?) : TextView(context, attrs) {
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        resize()
    }

    /**
     * 去除当前页无法显示的字
     * @return 去掉的字数
     */
    fun resize(): Int {
        val oldContent = text
        val newContent = oldContent.subSequence(0, getCharNum())
        text = newContent
        return oldContent.length - newContent.length
    }

    /**
     * 获取当前页总字数
     */
    fun getCharNum(): Int {
        return layout.getLineEnd(getLineNum())
    }

    /**
     * 获取当前页总行数
     */
    fun getLineNum(): Int {
        val layout = layout
        val topOfLastLine = height - paddingTop - paddingBottom - lineHeight
        return layout.getLineForVertical(topOfLastLine)
    }
}