package vip.mystery0.tools.headerPager

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import vip.mystery0.tools.R

/**
 * Created by myste.
 */
class HeaderPageAdapter(
		private val list: List<Header>) : RecyclerView.Adapter<HeaderPageAdapter.ViewHolder>()
{
	override fun getItemCount(): Int
	{
		return list.size
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
	{
		val view = LayoutInflater.from(parent.context).inflate(R.layout.item_header_page, parent, false)
		return ViewHolder(view)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int)
	{

	}

	class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)
	{

	}
}