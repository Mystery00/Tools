/*
 * Created by Mystery0 on 17-12-26 下午11:29.
 * Copyright (c) 2017. All Rights reserved.
 *
 * Last modified 17-10-22 下午8:18
 */

package vip.mystery0.tools.headerPage

import android.animation.ObjectAnimator
import android.content.Context
import android.support.v4.view.ViewCompat
import android.support.v4.widget.NestedScrollView
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Scroller

/**
 * Created by myste.
 */
class HeaderPage : NestedScrollView {

    companion object {
    }

    private val contentLinearLayout: LinearLayout = LinearLayout(context)
    private lateinit var headerView: View
    private lateinit var contentView: View
    private lateinit var headerViewLayoutParams: ViewGroup.LayoutParams

    private var downX = 0F                    //Down事件的X坐标
    private var downY = 0F                    //Down事件的Y坐标
    private var touchSlop: Int
    private var lastEventX = 0F
    private var lastEventY = 0F
    private var headerViewHeight = 0
    private var isZooming = false
    private var isStartScroll = false
    private var isActionDown = false

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?,
                defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        contentLinearLayout.orientation = LinearLayout.VERTICAL
        addView(contentLinearLayout, LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        touchSlop = ViewConfiguration.get(context).scaledTouchSlop
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        overScrollMode = View.OVER_SCROLL_NEVER
        headerViewLayoutParams = headerView.layoutParams
        headerViewHeight = headerViewLayoutParams.height
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        smoothScrollTo(0, 0)
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        if (t in 0..headerViewHeight) {
            headerView.scrollTo(0, (-0.65 * t).toInt())
        } else {
            headerView.scrollTo(0, 0)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                lastEventX = event.x
                lastEventY = event.y
                downX = event.y
                downY = event.y
                isActionDown = true
            }
            MotionEvent.ACTION_MOVE -> {
                if (!isActionDown) {
                    lastEventX = event.x
                    lastEventY = event.y
                    downX = event.y
                    downY = event.y
                    isActionDown = true
                }
                val shiftX = Math.abs(event.x - downX)
                val shiftY = Math.abs(event.y - downY)
                val dx = event.x - lastEventX
                val dy = event.y - lastEventY
                lastEventY = event.y
                if (scrollY <= 0) {
                    if (shiftY > shiftX && shiftY > touchSlop) {
                        var height = (headerViewLayoutParams.height + dy / 1.5 + 0.5).toInt()
                        if (height <= headerViewHeight) {
                            height = headerViewHeight
                            isZooming = false
                        } else {
                            isZooming = true
                        }
                        zoom(height)
                    }
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                isActionDown = false
                if (isZooming) {
                    val distance = 10F
                    val animation = ObjectAnimator.ofFloat(0.0F, 1.0F).setDuration((distance * 0.7).toLong())
                    animation.addUpdateListener {
                        zoom((distance * (1 - animation.animatedValue as Float)).toInt())
                    }
                    animation.start()
                    isZooming = false
                    ViewCompat.postInvalidateOnAnimation(this)
                }
            }
        }
        return isZooming || super.onTouchEvent(event)
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = event.x
                downY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                if (Math.abs(event.y - downY) > touchSlop) {
                    return true
                }
            }
        }
        return super.onInterceptTouchEvent(event)
    }

    private fun findView(view: View, params: HeaderPageLayoutParams) {
        when (params.viewType) {
            1 -> headerView = view
            2 -> contentView = view
        }
    }

    private fun zoom(height: Int) {
        headerViewLayoutParams.height = height
        headerView.layoutParams = headerViewLayoutParams
    }

    override fun addView(child: View?) {
        if (childCount >= 1)
            contentLinearLayout.addView(child)
        else
            super.addView(child)
    }

    override fun addView(child: View?, index: Int) {
        if (childCount >= 1)
            contentLinearLayout.addView(child, index)
        else
            super.addView(child, index)
    }

    override fun addView(child: View?, width: Int, height: Int) {
        if (childCount >= 1)
            contentLinearLayout.addView(child, width, height)
        else
            super.addView(child, width, height)
    }

    override fun addView(child: View?, params: ViewGroup.LayoutParams?) {
        if (childCount >= 1)
            contentLinearLayout.addView(child, params)
        else
            super.addView(child, params)
        if (params is HeaderPageLayoutParams)
            findView(child!!, params)
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (childCount >= 1)
            contentLinearLayout.addView(child, index, params)
        else
            super.addView(child, index, params)
        if (params is HeaderPageLayoutParams)
            findView(child!!, params)
    }

    override fun generateLayoutParams(attrs: AttributeSet?): FrameLayout.LayoutParams {
        return HeaderPageLayoutParams(context, attrs)
    }

    override fun generateLayoutParams(lp: ViewGroup.LayoutParams?): ViewGroup.LayoutParams {
        return HeaderPageLayoutParams(lp)
    }

    override fun generateDefaultLayoutParams(): FrameLayout.LayoutParams {
        return HeaderPageLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    override fun checkLayoutParams(p: ViewGroup.LayoutParams?): Boolean {
        return p is HeaderPageLayoutParams
    }
}