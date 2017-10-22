package vip.mystery0.tools.headerPage

import android.content.Context
import android.support.v4.widget.NestedScrollView
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import vip.mystery0.tools.logs.Logs

/**
 * Created by myste.
 */
class HeaderPage : NestedScrollView
{

	companion object
	{
		private val TAG = "HeaderPage"
	}

	private val contentLinearLayout: LinearLayout = LinearLayout(context)

	private var headerView: View? = null
	private var contentView: View? = null

	constructor(context: Context) : super(context)
	constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
	constructor(context: Context, attrs: AttributeSet?,
				defStyleAttr: Int) : super(context, attrs, defStyleAttr)

	init
	{
		contentLinearLayout.orientation = LinearLayout.VERTICAL
		addView(contentLinearLayout, LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
		viewTreeObserver.addOnGlobalLayoutListener {
			Logs.i(TAG, "init: " + headerView)
			Logs.i(TAG, "init: " + contentView)
		}
	}

	override fun onTouchEvent(event: MotionEvent): Boolean
	{
		when (event.actionMasked)
		{
			MotionEvent.ACTION_DOWN ->
			{

			}
			MotionEvent.ACTION_MOVE ->
			{

			}
			MotionEvent.ACTION_UP ->
			{

			}
		}
		return super.onTouchEvent(event)
	}

//	override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int)
//	{
//		for (i in 0 until contentLinearLayout.childCount)
//		{
//			val child = contentLinearLayout.getChildAt(i)
//			val params = child.layoutParams as HeaderPageLayoutParams
//			when (params.viewType)
//			{
//				1 -> headerView = child
//				2 -> contentView = child
//			}
//		}
//		Logs.i(TAG, "onLayout: " + childCount)
//		super.onLayout(changed, l, t, r, b)
//	}

	private fun findView(view: View?, params: HeaderPageLayoutParams)
	{
		when (params.viewType)
		{
			1 -> headerView = view
			2 -> contentView = view
		}
	}

	override fun addView(child: View?)
	{
		if (childCount >= 1)
			contentLinearLayout.addView(child)
		else
			super.addView(child)
	}

	override fun addView(child: View?, index: Int)
	{
		if (childCount >= 1)
			contentLinearLayout.addView(child, index)
		else
			super.addView(child, index)
	}

	override fun addView(child: View?, width: Int, height: Int)
	{
		if (childCount >= 1)
			contentLinearLayout.addView(child, width, height)
		else
			super.addView(child, width, height)
	}

	override fun addView(child: View?, params: ViewGroup.LayoutParams?)
	{
		if (childCount >= 1)
			contentLinearLayout.addView(child, params)
		else
			super.addView(child, params)
		if (params is HeaderPageLayoutParams)
			findView(child, params)
	}

	override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?)
	{
		if (childCount >= 1)
			contentLinearLayout.addView(child, index, params)
		else
			super.addView(child, index, params)
		if (params is HeaderPageLayoutParams)
			findView(child, params)
	}

	override fun generateLayoutParams(attrs: AttributeSet?): FrameLayout.LayoutParams
	{
		return HeaderPageLayoutParams(context, attrs)
	}

	override fun generateLayoutParams(lp: ViewGroup.LayoutParams?): ViewGroup.LayoutParams
	{
		return HeaderPageLayoutParams(lp)
	}

	override fun generateDefaultLayoutParams(): FrameLayout.LayoutParams
	{
		return HeaderPageLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
	}

	override fun checkLayoutParams(p: ViewGroup.LayoutParams?): Boolean
	{
		return p is HeaderPageLayoutParams
	}
}