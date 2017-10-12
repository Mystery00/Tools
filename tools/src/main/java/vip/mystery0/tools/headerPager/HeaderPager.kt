package vip.mystery0.tools.headerPager

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v4.app.FragmentManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import vip.mystery0.tools.R

/**
 * Created by myste.
 */
class HeaderPager(context: Context?, attrs: AttributeSet?) : ConstraintLayout(context, attrs)
{
	private val imageViewSearch: ImageView
	private val imageViewRefresh: ImageView
	private val recyclerView: RecyclerView
	private val pageIndicator: LinearLayout
	private var listener: SearchButtonOnClickListener? = null

	init
	{
		LayoutInflater.from(context).inflate(R.layout.layout_header_page, this)
		imageViewSearch = findViewById(R.id.imageView_search)
		imageViewRefresh = findViewById(R.id.imageView_refresh)
		recyclerView = findViewById(R.id.recyclerView)
		pageIndicator = findViewById(R.id.pageIndicator)

		imageViewSearch.setOnClickListener {
			if (listener != null)
			{
				listener!!.onClick()
			}
		}
	}

	fun setSearchButtonOnClickListener(listener: SearchButtonOnClickListener)
	{
		this.listener = listener
	}

	fun setData(list: List<Header>,
				fragmentManager: FragmentManager)
	{

	}
}