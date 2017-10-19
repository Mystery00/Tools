package vip.mystery0.tools.headerPage

import android.content.Context
import android.graphics.Color
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import vip.mystery0.tools.R

/**
 * Created by myste.
 */
class HeaderPage(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs)
{
	private val fullView: View
	private val imageViewSearch: ImageView
	private val imageViewRefresh: ImageView
	private val recyclerView: RecyclerView
	private val textViewTitle: TextView
	private val textViewSubTitle: TextView
	private val pageIndicator: LinearLayout
	private var listener: SearchButtonOnClickListener? = null

	private val adapter: HeaderPageAdapter

	private val list: ArrayList<Header> = ArrayList()
	@DrawableRes private var icChecked: Int
	@DrawableRes private var icUnChecked: Int
	@DrawableRes private var icRefresh: Int
	@DrawableRes private var icSearch: Int
	private var titleColor: Int
	private var subtitleColor: Int
	private var titleSize: Float
	private var subtitleSize: Float
	private var lastItemPosition = 0
	private var lastPosition = 0F
	private var itemMaxHeight = 0
	private var imgRefreshHeight = 0
	private var imgRefreshWidth = 0
	private var refreshRange = 0
	private var pageIndicatorMargin: Int
	private var pageIndicatorSize: Int
	private var isRefresh = false
	private val titleHandler: TextViewHandler
	private val subtitleHandler: TextViewHandler

	init
	{
		val typedArray = context.obtainStyledAttributes(attrs, R.styleable.HeaderPage)
		titleColor = typedArray.getColor(R.styleable.HeaderPage_title_color, Color.BLACK)
		subtitleColor = typedArray.getColor(R.styleable.HeaderPage_subtitle_color, Color.BLACK)
		titleSize = typedArray.getDimension(R.styleable.HeaderPage_title_size, 32f)
		subtitleSize = typedArray.getDimension(R.styleable.HeaderPage_subtitle_size, 24f)
		icChecked = typedArray.getResourceId(R.styleable.HeaderPage_resource_checked, R.drawable.mystery0_ic_radio_button_checked)
		icUnChecked = typedArray.getResourceId(R.styleable.HeaderPage_resource_unchecked, R.drawable.mystery0_ic_radio_button_unchecked)
		icRefresh = typedArray.getResourceId(R.styleable.HeaderPage_ic_refresh, R.drawable.mystery0_ic_refresh)
		icSearch = typedArray.getResourceId(R.styleable.HeaderPage_ic_refresh, R.drawable.mystery0_ic_search)
		pageIndicatorMargin = typedArray.getDimensionPixelSize(R.styleable.HeaderPage_page_indicator_margin, 10)
		pageIndicatorSize = typedArray.getDimensionPixelSize(R.styleable.HeaderPage_page_indicator_size, 20)
		itemMaxHeight = typedArray.getDimensionPixelSize(R.styleable.HeaderPage_page_item_max_height, 0)
		refreshRange = typedArray.getInt(R.styleable.HeaderPage_refresh_range, 0)
		typedArray.recycle()

		LayoutInflater.from(context).inflate(R.layout.layout_header_page, this)
		fullView = findViewById(R.id.full_layout)
		imageViewSearch = findViewById(R.id.imageView_search)
		imageViewRefresh = findViewById(R.id.imageView_refresh)
		recyclerView = findViewById(R.id.recyclerView)
		textViewTitle = findViewById(R.id.textView_title)
		textViewSubTitle = findViewById(R.id.textView_subtitle)
		pageIndicator = findViewById(R.id.pageIndicator)

		titleHandler = TextViewHandler()
		subtitleHandler = TextViewHandler()

		imageViewRefresh.setImageResource(icRefresh)
		imageViewSearch.setImageResource(icSearch)
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
					pageIndicator.getChildAt(lastItemPosition).setBackgroundResource(icUnChecked)
					pageIndicator.getChildAt(newLastItemPosition).setBackgroundResource(icChecked)
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
			if (listener != null)
			{
				listener!!.onClick()
			}
		}

		fullView.setOnTouchListener { _, event ->
			if (itemMaxHeight == 0)
				itemMaxHeight = list[0].imgHeight + 300
			val image = recyclerView.layoutManager.findViewByPosition(lastItemPosition)
			val layoutParams = image.layoutParams
			val params = imageViewRefresh.layoutParams
			when (event.actionMasked)
			{
				MotionEvent.ACTION_MOVE ->
				{
					when
					{
						event.y > lastPosition ->
						{
							if (layoutParams.height < itemMaxHeight)
							{
								layoutParams.height += 10
								params.height += 2
								params.width += 2
								imageViewRefresh.alpha += 0.05F
							}
							if (layoutParams.height >= itemMaxHeight - refreshRange)
								isRefresh = true
						}
						event.y < lastPosition ->
						{
							if (layoutParams.height > list[lastItemPosition].imgHeight)
							{
								layoutParams.height -= 10
								params.height -= 2
								params.width -= 2
								if (!isRefresh)
									imageViewRefresh.alpha -= 0.05F
							}
							else
							{
								layoutParams.height = list[lastItemPosition].imgHeight
								params.height = imgRefreshHeight
								params.width = imgRefreshWidth
								if (isRefresh)
									imageViewRefresh.alpha = 1.0F
								else
									imageViewRefresh.alpha = 0F
							}
						}
					}
					lastPosition = event.y
				}
				MotionEvent.ACTION_UP ->
				{
					layoutParams.height = list[lastItemPosition].imgHeight
					params.height = imgRefreshHeight
					params.width = imgRefreshWidth
					if (isRefresh)
						imageViewRefresh.alpha = 1.0F
					else
						imageViewRefresh.alpha = 0F
				}
			}
			image.layoutParams = layoutParams
			imageViewRefresh.layoutParams = params
			true
		}
	}

	fun setCheckedResource(@DrawableRes resource: Int)
	{
		icChecked = resource
	}

	fun setUnCheckedResource(@DrawableRes resource: Int)
	{
		icUnChecked = resource
	}

	fun setTitleColor(@ColorRes resource: Int)
	{
		titleColor = resource
	}

	fun setSubtitleColor(@ColorRes resource: Int)
	{
		subtitleColor = resource
	}

	fun setTitleSize(size: Float)
	{
		titleSize = size
	}

	fun setSubtitleSize(size: Float)
	{
		subtitleSize = size
	}

	fun setPageIndicatorMargin(pageIndicatorMargin: Int)
	{
		this.pageIndicatorMargin = pageIndicatorMargin
	}

	fun setSearchButtonOnClickListener(listener: SearchButtonOnClickListener)
	{
		this.listener = listener
	}

	fun setData(newList: ArrayList<Header>)
	{
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
			view.setBackgroundResource(icUnChecked)
			val params = LinearLayout.LayoutParams(pageIndicatorSize, pageIndicatorSize)
			if (i != 0)
				params.leftMargin = pageIndicatorMargin
			else
				view.setBackgroundResource(icChecked)
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
}