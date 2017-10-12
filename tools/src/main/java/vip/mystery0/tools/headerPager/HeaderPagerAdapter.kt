package vip.mystery0.tools.headerPager

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import java.util.ArrayList

/**
 * Created by myste.
 */
class HeaderPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager)
{
	private val fragmentList = ArrayList<HeaderPagerFragment>()

	fun addFragment(fragment: HeaderPagerFragment)
	{
		fragmentList.add(fragment)
	}

	override fun getItem(position: Int): Fragment
	{
		return fragmentList[position]
	}

	override fun getCount(): Int
	{
		return 5
	}
}