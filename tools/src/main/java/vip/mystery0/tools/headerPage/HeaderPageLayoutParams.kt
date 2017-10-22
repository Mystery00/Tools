package vip.mystery0.tools.headerPage

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import vip.mystery0.tools.R

/**
 * Created by myste.
 */
class HeaderPageLayoutParams : FrameLayout.LayoutParams
{
	companion object
	{
		private val TAG = "HeaderPageLayoutParams"
	}

	var viewType = 0

	constructor(source: ViewGroup.LayoutParams?) : super(source)
	constructor(width: Int, height: Int) : super(width, height)
	constructor(context: Context, attr: AttributeSet?) : super(context, attr)
	{
		val typedArray = context.obtainStyledAttributes(attr, R.styleable.HeaderPage)
		viewType = typedArray.getInt(R.styleable.HeaderPage_item_type, 0)
		typedArray.recycle()
	}
}