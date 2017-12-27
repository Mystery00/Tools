/*
 * Created by Mystery0 on 17-12-28 上午4:02.
 * Copyright (c) 2017. All Rights reserved.
 *
 * Last modified 17-12-28 上午4:02
 */

package vip.mystery0.tools.flexibleRecyclerView

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View.MeasureSpec
import android.view.ViewGroup


class FlexibleLinearLayoutManager(context: Context?, orientation: Int) : LinearLayoutManager(context, orientation, false) {
    override fun onMeasure(recycler: RecyclerView.Recycler, state: RecyclerView.State?, widthSpec: Int, heightSpec: Int) {
        var height = 0
        var width = 0
        val childCount = itemCount
        for (i in 0 until childCount) {
            val child = recycler.getViewForPosition(i)
            measureChild(child, widthSpec, heightSpec)
            val lp = child.layoutParams as ViewGroup.MarginLayoutParams
            height += child.measuredHeight + lp.topMargin + lp.bottomMargin
            width += child.measuredWidth + lp.leftMargin + lp.rightMargin
        }
        setMeasuredDimension(width, height)
    }
}