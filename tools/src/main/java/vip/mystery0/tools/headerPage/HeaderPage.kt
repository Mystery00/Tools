package vip.mystery0.tools.headerPage

import android.content.Context
import android.support.annotation.DrawableRes
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import vip.mystery0.tools.R
import vip.mystery0.tools.logs.Logs

/**
 * Created by myste.
 */
class HeaderPage(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs)
{
	private val TAG = "HeaderPage"
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
	private var lastItemPosition = 0
	private var pageIndicatorMargin: Int

	init
	{
		val typedArray = context.obtainStyledAttributes(attrs, R.styleable.HeaderPage)
		icChecked = typedArray.getResourceId(R.styleable.HeaderPage_resource_checked, R.drawable.mystery0_ic_radio_button_checked)
		icUnChecked = typedArray.getResourceId(R.styleable.HeaderPage_resource_unchecked, R.drawable.mystery0_ic_radio_button_unchecked)
		pageIndicatorMargin = typedArray.getDimensionPixelSize(R.styleable.HeaderPage_page_indicator_margin, 10)
		typedArray.recycle()

		LayoutInflater.from(context).inflate(R.layout.layout_header_page, this)
		imageViewSearch = findViewById(R.id.imageView_search)
		imageViewRefresh = findViewById(R.id.imageView_refresh)
		recyclerView = findViewById(R.id.recyclerView)
		textViewTitle = findViewById(R.id.textView_title)
		textViewSubTitle = findViewById(R.id.textView_subtitle)
		pageIndicator = findViewById(R.id.pageIndicator)

		adapter = HeaderPageAdapter(context, list)
		val linearLayoutManger = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
		recyclerView.layoutManager = linearLayoutManger
		recyclerView.adapter = adapter
		recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener()
		{
			override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int)
			{
				super.onScrollStateChanged(recyclerView, newState)
				Logs.i(TAG, "onScrollStateChanged: "+newState)
				val newLastItemPosition = linearLayoutManger.findLastVisibleItemPosition()
				if (newState == RecyclerView.SCROLL_STATE_IDLE && lastItemPosition != newLastItemPosition)
				{
					pageIndicator.getChildAt(lastItemPosition).setBackgroundResource(icUnChecked)
					pageIndicator.getChildAt(newLastItemPosition).setBackgroundResource(icChecked)
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
	}

	fun setCheckedResource(@DrawableRes resource: Int)
	{
		icChecked = resource
	}

	fun setUnCheckedResource(@DrawableRes resource: Int)
	{
		icUnChecked = resource
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
		adapter.notifyDataSetChanged()
		for (i in 0 until list.size)
		{
			val view = View(context)
			view.setBackgroundResource(icUnChecked)
			val params = LinearLayout.LayoutParams(20, 20)
			if (i != 0)
				params.leftMargin = pageIndicatorMargin
			else
				view.setBackgroundResource(icChecked)
			view.layoutParams = params
			pageIndicator.addView(view)
		}
	}
}