package vip.mystery0.tools.headerPage

import android.content.Context
import android.graphics.Color
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import vip.mystery0.tools.R
import vip.mystery0.tools.logs.Logs

/**
 * Created by myste.
 */
class HeaderPage : NestedScrollView
{
	private val TAG = "HeaderPage"

	private var attrs: AttributeSet? = null
	private val imageViewSearch: ImageView
	private val imageViewRefresh: ImageView
	private val recyclerView: RecyclerView
	private val textViewTitle: TextView
	private val textViewSubTitle: TextView
	private val pageIndicator: LinearLayout
	var searchButtonOnClickListener: SearchButtonOnClickListener? = null
	var onRefreshListener: OnRefreshListener? = null

	private val adapter: HeaderPageAdapter

	private val list: ArrayList<Header> = ArrayList()
	@DrawableRes
	var checkedRes: Int
	@DrawableRes
	var unCheckedRes: Int
	@DrawableRes
	var refreshRes: Int
	@DrawableRes
	var searchRes: Int
	@ColorRes
	var titleColor: Int
	@ColorRes
	var subtitleColor: Int
	var titleSize: Float
	var subtitleSize: Float
	var lastItemPosition = 0
	var itemMaxHeight = 0
	var imgRefreshHeight = 0
	var imgRefreshWidth = 0
	var refreshRange = 0
	var pageIndicatorMargin: Int
	var pageIndicatorSize: Int
	var needRefresh = false
	var isRefresh = false
	private val titleHandler: TextViewHandler
	private val subtitleHandler: TextViewHandler

	private var downX = 0
	private var downY = 0
	private var touchSlop = 0

	constructor(context: Context) : super(context)
	constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
	{
		this.attrs = this.attrs
	}

	constructor(context: Context, attrs: AttributeSet?,
				defStyleAttr: Int) : super(context, attrs, defStyleAttr)
	{
		this.attrs = this.attrs
	}

	init
	{
		touchSlop = ViewConfiguration.get(context).scaledTouchSlop

		val typedArray = context.obtainStyledAttributes(attrs, R.styleable.HeaderPage)
		titleColor = typedArray.getColor(R.styleable.HeaderPage_title_color, Color.BLACK)
		subtitleColor = typedArray.getColor(R.styleable.HeaderPage_subtitle_color, Color.BLACK)
		titleSize = typedArray.getDimension(R.styleable.HeaderPage_title_size, 32f)
		subtitleSize = typedArray.getDimension(R.styleable.HeaderPage_subtitle_size, 24f)
		checkedRes = typedArray.getResourceId(R.styleable.HeaderPage_resource_checked, R.drawable.mystery0_ic_radio_button_checked)
		unCheckedRes = typedArray.getResourceId(R.styleable.HeaderPage_resource_unchecked, R.drawable.mystery0_ic_radio_button_unchecked)
		refreshRes = typedArray.getResourceId(R.styleable.HeaderPage_ic_refresh, R.drawable.mystery0_ic_refresh)
		searchRes = typedArray.getResourceId(R.styleable.HeaderPage_ic_refresh, R.drawable.mystery0_ic_search)
		pageIndicatorMargin = typedArray.getDimensionPixelSize(R.styleable.HeaderPage_page_indicator_margin, 10)
		pageIndicatorSize = typedArray.getDimensionPixelSize(R.styleable.HeaderPage_page_indicator_size, 20)
		itemMaxHeight = typedArray.getDimensionPixelSize(R.styleable.HeaderPage_page_item_max_height, 0)
		refreshRange = typedArray.getInt(R.styleable.HeaderPage_refresh_range, 0)
		typedArray.recycle()

		LayoutInflater.from(context).inflate(R.layout.layout_header_page, this)
//		fullView = findViewById(R.id.full_layout)
		imageViewSearch = findViewById(R.id.imageView_search)
		imageViewRefresh = findViewById(R.id.imageView_refresh)
		recyclerView = findViewById(R.id.recyclerView)
		textViewTitle = findViewById(R.id.textView_title)
		textViewSubTitle = findViewById(R.id.textView_subtitle)
		pageIndicator = findViewById(R.id.pageIndicator)

		titleHandler = TextViewHandler()
		subtitleHandler = TextViewHandler()

		imageViewRefresh.setImageResource(refreshRes)
		imageViewSearch.setImageResource(searchRes)
		titleHandler.textView = textViewTitle
		subtitleHandler.textView = textViewSubTitle

		imgRefreshHeight = imageViewRefresh.layoutParams.height
		imgRefreshWidth = imageViewRefresh.layoutParams.width

		adapter = HeaderPageAdapter(context, list)
		val linearLayoutManger = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
		recyclerView.layoutManager = linearLayoutManger
		recyclerView.adapter = adapter
		recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener()
		{
			override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int)
			{
				super.onScrollStateChanged(recyclerView, newState)
				val newLastItemPosition = linearLayoutManger.findLastVisibleItemPosition()
				if (newState == RecyclerView.SCROLL_STATE_IDLE && lastItemPosition != newLastItemPosition)
				{
					pageIndicator.getChildAt(lastItemPosition).setBackgroundResource(unCheckedRes)
					pageIndicator.getChildAt(newLastItemPosition).setBackgroundResource(checkedRes)
					textViewTitle.textSize = titleSize
					textViewSubTitle.textSize = subtitleSize
					textViewTitle.setTextColor(titleColor)
					textViewSubTitle.setTextColor(subtitleColor)
					showAnim(newLastItemPosition)
					lastItemPosition = newLastItemPosition
				}
			}
		})
		PagerSnapHelper().attachToRecyclerView(recyclerView)

		imageViewSearch.setOnClickListener {
			if (searchButtonOnClickListener != null)
			{
				searchButtonOnClickListener!!.onClick()
			}
		}
	}

	fun setData(newList: ArrayList<Header>)
	{
		Logs.i(TAG, "setData: ")
		list.clear()
		list.addAll(newList)
		textViewTitle.textSize = titleSize
		textViewSubTitle.textSize = subtitleSize
		textViewTitle.setTextColor(titleColor)
		textViewSubTitle.setTextColor(subtitleColor)
		adapter.notifyDataSetChanged()
		pageIndicator.removeAllViews()
		for (i in 0 until list.size)
		{
			val view = View(context)
			view.setBackgroundResource(unCheckedRes)
			val params = LinearLayout.LayoutParams(pageIndicatorSize, pageIndicatorSize)
			if (i != 0)
				params.leftMargin = pageIndicatorMargin
			else
				view.setBackgroundResource(checkedRes)
			view.layoutParams = params
			pageIndicator.addView(view)
		}
		showAnim(0)
	}

	private fun showAnim(position: Int)
	{
		var textIndex = 0
		val title = list[position].title
		val subtitle = list[position].subtitle
		titleHandler.text = ""
		subtitleHandler.text = ""
		Thread(Runnable {
			while (textIndex < title.length || textIndex < subtitle.length)
			{
				if (textIndex < title.length)
				{
					titleHandler.text = titleHandler.text + title[textIndex]
					titleHandler.sendEmptyMessage(0)
				}
				if (textIndex < subtitle.length)
				{
					subtitleHandler.text = subtitleHandler.text + subtitle[textIndex]
					subtitleHandler.sendEmptyMessage(0)
				}
				textIndex++
				Thread.sleep(100)
			}
		}).start()
	}

	private fun showRefreshAnim()
	{
		if (isRefresh)
			return
		val handler = RefreshHandler()
		handler.imageView = imageViewRefresh
		Thread(Runnable {
			while (true)
			{
				isRefresh = true
				if (!needRefresh)
					break
				handler.sendEmptyMessage(0)
				Thread.sleep(1000)
			}
			isRefresh = false
			imageViewRefresh.alpha = 0F
		}).start()
	}

	override fun onStartNestedScroll(child: View, target: View, nestedScrollAxes: Int): Boolean
	{
		if (itemMaxHeight == 0)
			itemMaxHeight = list[0].imgHeight + 300
		return super.onStartNestedScroll(child, target, nestedScrollAxes)
	}

	override fun onNestedScroll(target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int,
								dyUnconsumed: Int)
	{
		super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed)
		val image = recyclerView.layoutManager.findViewByPosition(lastItemPosition)
		val layoutParams = image.layoutParams
		val params = imageViewRefresh.layoutParams
		when
		{
			dyUnconsumed > 0 ->//上滑
			{
				if (layoutParams.height > list[lastItemPosition].imgHeight)
				{
					layoutParams.height -= 10
					params.height -= 2
					params.width -= 2
					if (!needRefresh)
						imageViewRefresh.alpha -= 0.05F
				}
				else
				{
					layoutParams.height = list[lastItemPosition].imgHeight
					params.height = imgRefreshHeight
					params.width = imgRefreshWidth
					if (needRefresh)
						imageViewRefresh.alpha = 1.0F
					else
						imageViewRefresh.alpha = 0F
				}
			}
			dyUnconsumed < 0 ->//下滑
			{
				if (layoutParams.height < itemMaxHeight)
				{
					layoutParams.height += 10
					params.height += 2
					params.width += 2
					imageViewRefresh.alpha += 0.05F
				}
				if (layoutParams.height >= itemMaxHeight - refreshRange)
					needRefresh = true
			}
		}
		image.layoutParams = layoutParams
		imageViewRefresh.layoutParams = params
	}

	override fun onStopNestedScroll(target: View)
	{
		super.onStopNestedScroll(target)
		val image = recyclerView.layoutManager.findViewByPosition(lastItemPosition)
		val layoutParams = image.layoutParams
		val params = imageViewRefresh.layoutParams
		layoutParams.height = list[lastItemPosition].imgHeight
		params.height = imgRefreshHeight
		params.width = imgRefreshWidth
		if (needRefresh)
		{
			imageViewRefresh.alpha = 1.0F
			if (onRefreshListener != null)
				onRefreshListener!!.onRefresh()
			showRefreshAnim()
		}
		else
			imageViewRefresh.alpha = 0F
		image.layoutParams = layoutParams
		imageViewRefresh.layoutParams = params
	}
}