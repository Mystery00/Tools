package vip.mystery0.tools.headerPager

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v4.app.FragmentManager
import android.support.v4.view.ViewPager
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
	private val viewPager: ViewPager
	private val pageIndicator: LinearLayout

	init
	{
		LayoutInflater.from(context).inflate(R.layout.layout_header_pager, this)
		imageViewSearch = findViewById(R.id.imageView_search)
		imageViewRefresh = findViewById(R.id.imageView_refresh)
		viewPager = findViewById(R.id.viewPager)
		pageIndicator = findViewById(R.id.pageIndicator)
	}

	fun setData(list: List<Header>,
				fragmentManager: FragmentManager)
	{
		val viewPagerAdapter = HeaderPagerAdapter(fragmentManager)
		list.forEachIndexed { index, item ->
			viewPagerAdapter.addFragment(HeaderPagerFragment.newInstance(item, index))
		}
		viewPager.adapter = viewPagerAdapter
	}
}