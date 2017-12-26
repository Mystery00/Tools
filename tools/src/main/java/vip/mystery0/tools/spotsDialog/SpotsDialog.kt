/*
 * Created by Mystery0 on 17-12-26 下午11:29.
 * Copyright (c) 2017. All Rights reserved.
 *
 * Last modified 17-11-2 上午2:44
 */

package vip.mystery0.tools.spotsDialog

import android.animation.ObjectAnimator
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.annotation.StyleRes
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import vip.mystery0.tools.R
import vip.mystery0.tools.logs.Logs

/**
 * Created by myste.
 */
class SpotsDialog : AlertDialog {
    private var spotsCount = 2
    private var circlePosition = 50F
    private lateinit var spots: Array<ImageView>
    private var message = ""

    companion object {
        private val TAG = "SpotsDialog"
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, @StyleRes themeResId: Int) : super(context, themeResId)
    constructor(context: Context, cancelable: Boolean,
                cancelListener: DialogInterface.OnCancelListener) : super(context, cancelable, cancelListener)

    override fun onCreate(savedInstanceState: Bundle?) {
        Logs.i(TAG, "onCreate: ")
        super.onCreate(savedInstanceState)

        setContentView(R.layout.mystery0_spots_dialog)
        setCanceledOnTouchOutside(false)

        initAnimation()
        showAnimation()
    }

    override fun setMessage(message: CharSequence?) {
        Logs.i(TAG, "setMessage: ")
        this.message = message.toString()
//		(findViewById<TextView>(R.id.spots_message) as TextView).text = message
    }

    override fun show() {
        Logs.i(TAG, "show: ")
//		(findViewById<TextView>(R.id.spots_message) as TextView).text = message
        super.show()
    }

    private fun initAnimation() {
        spots = Array(spotsCount, { ImageView(context) })
        for (i in 0 until spots.size) {
            val view = spots[i]
            view.setImageResource(R.drawable.mystery0_ic_point)
            view.x = circlePosition
            view.y = 0F
            view.pivotX = circlePosition
            view.pivotY = circlePosition
//			frameLayout.addView(view)
            (findViewById<FrameLayout>(R.id.frameLayout) as FrameLayout).addView(view, 20, 20)
        }
    }

    private fun showAnimation() {
        val frameLayout = findViewById<FrameLayout>(R.id.frameLayout) as FrameLayout
        val params = frameLayout.layoutParams
        params.width = 500
        params.height = 500
        frameLayout.layoutParams = params
        val animators = Array<ObjectAnimator>(spotsCount, { i ->
            val animator = ObjectAnimator.ofFloat(spots[i], "rotation", 0F, 360F, 0F)
                    .setDuration(2000)
            animator.repeatCount = -1
            animator
        })
        animators.forEachIndexed { index, objectAnimator ->
            objectAnimator.startDelay = index * 500L
            objectAnimator.start()
        }
    }
}