/*
 * Created by Mystery0 on 17-12-26 下午11:42.
 * Copyright (c) 2017. All Rights reserved.
 *
 * Last modified 17-12-26 下午11:42
 */

package vip.mystery0.tools.flexibleCardView

import android.content.Context
import android.os.Handler
import android.os.Message
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import vip.mystery0.tools.R
import vip.mystery0.tools.logs.Logs
import kotlin.math.max

class FlexibleCardView : CardView {
    private val TAG = "FlexibleCardView"
    private var maxHeight = 0
    private var minHeight = 20
    private var isExpand = false
    private var isAnim = false

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initAttributes(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initAttributes(attrs)
    }

    private fun initAttributes(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.FlexibleCardView)
        maxHeight = typedArray.getDimensionPixelSize(R.styleable.FlexibleCardView_maxHeight, 0)
        minHeight = typedArray.getDimensionPixelSize(R.styleable.FlexibleCardView_minHeight, 0)
        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //父控件传进来的宽度和高度以及对应的测量模式
        val sizeWidth = MeasureSpec.getSize(widthMeasureSpec)
        val modeWidth = MeasureSpec.getMode(widthMeasureSpec)
        val sizeHeight = MeasureSpec.getSize(heightMeasureSpec)
        val modeHeight = MeasureSpec.getMode(heightMeasureSpec)

        //如果当前ViewGroup的宽高为wrap_content的情况
        var width = 0//自己测量的宽度
        var height = 0//自己测量的高度
        //记录每一行的宽度和高度
        var lineWidth = 0
        var lineHeight = 0

        //获取子view的个数
        val childCount = childCount
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            //测量子View的宽和高
            measureChild(child, widthMeasureSpec, heightMeasureSpec)
            //得到LayoutParams
            val lp = child.layoutParams as MarginLayoutParams

            //子View占据的宽度
            val childWidth = child.measuredWidth + lp.leftMargin + lp.rightMargin
            //子View占据的高度
            val childHeight = child.measuredHeight + lp.topMargin + lp.bottomMargin
            //换行时候
            if (lineWidth + childWidth > sizeWidth) {
                //对比得到最大的宽度
                width = Math.max(width, lineWidth)
                //重置lineWidth
                lineWidth = childWidth
                //记录行高
                height += lineHeight
                lineHeight = childHeight
            } else {//不换行情况
                //叠加行宽
                lineWidth += childWidth
                //得到最大行高
                lineHeight = Math.max(lineHeight, childHeight)
            }
            //处理最后一个子View的情况
            if (i == childCount - 1) {
                width = Math.max(width, lineWidth)
                height += lineHeight
            }
        }
        maxHeight = max(maxHeight, height)//取最大值为最高高度
        //wrap_content
//        setMeasuredDimension(if (modeWidth == MeasureSpec.EXACTLY) sizeWidth else width,
//                if (modeHeight == MeasureSpec.EXACTLY) sizeHeight else height)
        setMeasuredDimension(if (modeWidth == MeasureSpec.EXACTLY) sizeWidth else width, minHeight)
    }

    fun showAnime() {
        setExpand(!isExpand)
    }

    private fun setExpand(isExpand: Boolean) {
        Logs.i(TAG, "setExpand: minHeight: " + minHeight)
        Logs.i(TAG, "setExpand: maxHeight: " + maxHeight)
        if (isAnim)
            return
        isAnim = true
        this.isExpand = isExpand
        val showArray = Array(31, { i -> ((maxHeight - minHeight) / 30F) * i + minHeight })
        if (!isExpand)
            showArray.reverse()
        val params = layoutParams
        Observable.create<Int> { subscriber ->
            showArray.forEach {
                subscriber.onNext(it.toInt())
                Thread.sleep(8)
            }
        }
                .subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Int> {
                    override fun onSubscribe(d: Disposable) {
                        isAnim = true
                    }

                    override fun onComplete() {
                        isAnim = false
                    }

                    override fun onNext(t: Int) {
                        layoutParams.height = t
                        layoutParams = params
                    }

                    override fun onError(e: Throwable) {
                        isAnim = false
                    }
                })
    }
}