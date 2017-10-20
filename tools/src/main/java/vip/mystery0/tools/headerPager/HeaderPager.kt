package vip.mystery0.tools.headerPager

import android.content.Context
import android.support.v4.widget.NestedScrollView
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import vip.mystery0.tools.logs.Logs


/**
 * Created by myste.
 */
class HeaderPager : NestedScrollView
{
	companion object
	{
		private val TAG = "HeaderPager"

		private val TAG_HEADER = "header"        //头布局Tag
		private val TAG_ZOOM = "zoom"            //缩放布局Tag
		private val TAG_CONTENT = "content"      //内容布局Tag
	}

	private var headerView: View? = null                //头布局
	private var zoomView: View? = null                  //用于缩放的View
	private var contentView: View? = null               //主体内容View
	private var headerParams: ViewGroup.LayoutParams? = null
	private var zoomParams: ViewGroup.LayoutParams? = null
	private var headerHeight = 0
	private var isZooming = false
	private var downX = 0F                    //Down事件的X坐标
	private var downY = 0F                    //Down事件的Y坐标
	private var touchSlop = 0
	private var headerMaxHeight = 0
	private var lastPosition = 0F

	constructor(context: Context) : super(context)
	constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
	constructor(context: Context, attrs: AttributeSet?,
				defStyleAttr: Int) : super(context, attrs, defStyleAttr)

	/** 递归遍历所有的View，查询Tag  */
	private fun findTagViews(view: View)
	{
		if (view is ViewGroup)
		{
			for (i in 0 until view.childCount)
			{
				val childView = view.getChildAt(i)
				val tag = childView.tag
				if (tag != null)
				{
					if (TAG_CONTENT == tag && contentView == null) contentView = childView
					if (TAG_HEADER == tag && headerView == null) headerView = childView
					if (TAG_ZOOM == tag && zoomView == null) zoomView = childView
				}
				if (childView is ViewGroup)
				{
					findTagViews(childView)
				}
			}
		}
		else
		{
			val tag = view.tag
			if (tag != null)
			{
				if (TAG_CONTENT == tag && contentView == null) contentView = view
				if (TAG_HEADER == tag && headerView == null) headerView = view
				if (TAG_ZOOM == tag && zoomView == null) zoomView = view
			}
		}
	}

	override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int)
	{
		super.onSizeChanged(w, h, oldw, oldh)
		findTagViews(this)
		headerParams = headerView!!.layoutParams
		zoomParams = zoomView!!.layoutParams
		headerHeight = zoomParams!!.height
		if (headerMaxHeight == 0)
			headerMaxHeight = headerHeight + 300
	}

	override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int)
	{
		super.onScrollChanged(l, t, oldl, oldt)
		Logs.i(TAG, "onScrollChanged: t: " + t)
		if (t in 0..headerHeight)
			headerView!!.scrollTo(0, -(0.65 * t).toInt())
		else
			headerView!!.scrollTo(0, 0)
	}

	override fun onTouchEvent(event: MotionEvent): Boolean
	{
		when (event.actionMasked)
		{
			MotionEvent.ACTION_DOWN ->
			{
				lastPosition = event.y
			}
			MotionEvent.ACTION_MOVE ->
			{
				when
				{
					event.y > lastPosition ->
					{
						if (scrollY <= 0)
						{
							when
							{
								zoomParams!!.height < headerMaxHeight ->
								{
									zoomParams!!.height += 10
									isZooming = true
								}
								else -> isZooming = false
							}
						}
					}
					event.y < lastPosition ->
					{
						if (zoomParams!!.height > headerHeight)
						{
							zoomParams!!.height -= 10
							isZooming = true
						}
						else
						{
							zoomParams!!.height = headerHeight
							isZooming = false
						}
					}
				}
				lastPosition = event.y
			}
			MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL ->
			{
				zoomParams!!.height = headerHeight
				isZooming = false
			}
		}
		zoomView!!.layoutParams = zoomParams
		return isZooming || super.onTouchEvent(event)
	}

	override fun onInterceptTouchEvent(event: MotionEvent): Boolean
	{
		val action = event.action
		when (action)
		{
			MotionEvent.ACTION_DOWN ->
			{
				downX = event.x
				downY = event.y
			}
			MotionEvent.ACTION_MOVE ->
			{
				if (Math.abs(event.y - downY) > touchSlop)
				{
					return true
				}
			}
		}
		return super.onInterceptTouchEvent(event)
	}
}