package vip.mystery0.tools.headerPager

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import vip.mystery0.tools.R
import vip.mystery0.tools.logs.Logs

/**
 * Created by myste.
 */
class HeaderPagerLayoutParams : FrameLayout.LayoutParams
{
	constructor(source: ViewGroup.LayoutParams?) : super(source)
	constructor(width: Int, height: Int) : super(width, height)
	constructor(context: Context, attr: AttributeSet?) : super(context, attr)
	{
		val typedArray = context.obtainStyledAttributes(attr, R.styleable.HeaderPager)
		Logs.i(TAG, "view type: " + typedArray.getInt(R.styleable.HeaderPager_view_type, -1))
		typedArray.recycle()
	}

	companion object
	{
		private val TAG = "HeaderPagerLayoutParams"

		val ZOOM = 0
		val HEADER = 1
		val CONTENT = 2
	}
}