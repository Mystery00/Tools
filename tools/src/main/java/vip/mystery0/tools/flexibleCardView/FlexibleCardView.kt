/*
 * Created by Mystery0 on 17-12-26 下午11:42.
 * Copyright (c) 2017. All Rights reserved.
 *
 * Last modified 17-12-26 下午11:42
 */

package vip.mystery0.tools.flexibleCardView

import android.content.Context
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
import kotlin.math.max

class FlexibleCardView : CardView {
    private var maxHeight = 0
    private var minHeight = 0
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
        val sizeHeight = MeasureSpec.getSize(heightMeasureSpec)
        val modeHeight = MeasureSpec.getMode(heightMeasureSpec)
        if (minHeight == 0 && modeHeight == MeasureSpec.EXACTLY)
            minHeight = sizeHeight
        if (maxHeight != 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            return
        }

        //如果当前ViewGroup的宽高为wrap_content的情况
        var height = 0//自己测量的高度
        //记录每一行的宽度和高度
        var lineHeight = 0
        val viewGroup = getChildAt(0) as ViewGroup//获取内部的ViewGroup
        for (i in 0 until viewGroup.childCount) {
            val child = viewGroup.getChildAt(i)
            //测量子View的宽和高
            measureChild(child, widthMeasureSpec, heightMeasureSpec)
            //得到LayoutParams
            val lp = child.layoutParams as MarginLayoutParams
            //子View占据的高度
            val childHeight = child.measuredHeight + lp.topMargin + lp.bottomMargin
            //换行时候
            if (lineHeight + childHeight > sizeHeight) {
                //记录行高
                height += lineHeight
                lineHeight = childHeight
            } else {//不换行情况
                //得到最大行高
                lineHeight = Math.max(lineHeight, childHeight)
            }
            //处理最后一个子View的情况
            if (i == childCount - 1) {
                height += lineHeight
            }
        }
        maxHeight = max(maxHeight, height)//取最大值为最高高度
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    fun getMinHeight(): Int = minHeight
    fun getMaxHeight(): Int = maxHeight

    fun showAnime() {
        val animeArray = Array(31, { i -> ((maxHeight - minHeight) / 30F) * i + minHeight })
        showAnime(animeArray, 8)
    }

    fun showAnime(animeArray: Array<Float>, itemTime: Long) {
        setExpand(this, !isExpand, animeArray, itemTime)
    }

    private fun setExpand(view: View, isExpand: Boolean, animeArray: Array<Float>, itemTime: Long) {
        if (isAnim)
            return
        isAnim = true
        this.isExpand = isExpand
        if (!isExpand)
            animeArray.reverse()
        val params = view.layoutParams
        Observable.create<Int> { subscriber ->
            animeArray.forEach {
                subscriber.onNext(it.toInt())
                Thread.sleep(itemTime)
            }
            subscriber.onComplete()
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
                        params.height = t
                        view.layoutParams = params
                    }

                    override fun onError(e: Throwable) {
                        isAnim = false
                    }
                })
    }
}