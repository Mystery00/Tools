/*
 * Created by Mystery0 on 17-12-28 上午1:13.
 * Copyright (c) 2017. All Rights reserved.
 *
 * Last modified 17-12-28 上午1:13
 */

package vip.mystery0.tools.flexibleRecyclerView

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import vip.mystery0.tools.R
import vip.mystery0.tools.logs.Logs

class FlexibleRecyclerView : RecyclerView {
    private val TAG = "FlexibleRecyclerView"
    private var showOrientation = 0
    private var maxSize = -1
    private var isShow = false
    private var isAnim = false

    companion object {
        val HORIZONTAL = 1
        val VERTICAL = 2
    }

    constructor(context: Context) : super(context){
        initLayoutManager()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initAttributes(attrs)
        initLayoutManager()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initAttributes(attrs)
        initLayoutManager()
    }

    private fun initAttributes(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.FlexibleRecyclerView)
        maxSize = typedArray.getDimensionPixelSize(R.styleable.FlexibleRecyclerView_max_size, 0)
        setShowPosition(typedArray.getInt(R.styleable.FlexibleRecyclerView_show_position, 2))
        typedArray.recycle()
    }

    private fun initLayoutManager() {
//        val layoutManager = FlexibleLinearLayoutManager(context, when (showOrientation) {
//            HORIZONTAL -> LinearLayoutManager.HORIZONTAL
//            VERTICAL -> LinearLayoutManager.VERTICAL
//            else -> throw NullPointerException("The show position can not be null! ")
//        })
//        this@FlexibleRecyclerView.layoutManager = layoutManager
    }

    fun getMaxSize(): Int = maxSize

    fun setClickView(view: View) {
        view.setOnClickListener {
            showAnime()
        }
    }

    fun setClickView(view: View, animeArray: Array<Float>, itemTime: Long) {
        view.setOnClickListener {
            showAnime(animeArray, itemTime)
        }
    }

    fun setShowPosition(showOrientation: Int) {
        if (showOrientation in 1..2)
            this.showOrientation = showOrientation
        else throw NullPointerException("The show position can not be null! ")
    }

    fun setShowState(isShow: Boolean) {
        this.isShow = isShow
        val params = this@FlexibleRecyclerView.layoutParams
        params.height = if (isShow) maxSize else 0
        this@FlexibleRecyclerView.layoutParams = params
    }

    fun showAnime() {
        val animeArray = Array(31, { i -> (maxSize / 30F) * i })
//        showAnime(animeArray, 8)
        showAnime(animeArray, 20)
    }

    fun showAnime(animeArray: Array<Float>, itemTime: Long) {
        setShow(!isShow, animeArray, itemTime)
    }

    private fun setShow(isShow: Boolean, animeArray: Array<Float>, itemTime: Long) {
        if (isAnim)
            return
        Logs.i(TAG, "setShow: " + isShow)
        isAnim = true
        this.isShow = isShow
        if (!isShow)
            animeArray.reverse()
        val params = this@FlexibleRecyclerView.layoutParams
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
                        this@FlexibleRecyclerView.layoutParams = params
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        isAnim = false
                    }
                })
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (!isShow) {
            when (showOrientation) {
                1 -> setMeasuredDimension(0, measureSize(heightMeasureSpec))
                2 -> setMeasuredDimension(measureSize(widthMeasureSpec), 0)
                else -> throw NullPointerException("The show position can not be null! ")
            }
        }
    }

    private fun measureSize(measureSpec: Int): Int {
        var result: Int
        val mode = MeasureSpec.getMode(measureSpec)
        val size = MeasureSpec.getSize(measureSpec)
        if (mode == MeasureSpec.EXACTLY) {
            result = size
        } else {
            result = 75
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size)
            }
        }
        return result
    }
}