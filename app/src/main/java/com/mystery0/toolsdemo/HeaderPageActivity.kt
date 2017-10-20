package com.mystery0.toolsdemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_header_page.*
import vip.mystery0.tools.headerPage.Header
import vip.mystery0.tools.headerPage.HeaderPage
import vip.mystery0.tools.headerPage.OnRefreshListener
import vip.mystery0.tools.headerPage.SearchButtonOnClickListener
import vip.mystery0.tools.logs.Logs

class HeaderPageActivity : AppCompatActivity()
{
	private val TAG = "HeaderPageActivity"

	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_header_page)

		val list = ArrayList<Header>()
		list.add(Header("http://img.kaiyanapp.com/83d3dddeeefa3f6549030938ab24533c.jpeg?imageMogr2/quality/60/format/jpg", "title1", "subtitle1"))
		list.add(Header("http://img.kaiyanapp.com/8c66311bc96f662edd67bbcb1769684b.jpeg?imageMogr2/quality/60/format/jpg", "title2", "subtitle1"))
		list.add(Header("http://img.kaiyanapp.com/83d3dddeeefa3f6549030938ab24533c.jpeg?imageMogr2/quality/60/format/jpg", "title3", "subtitle1"))

//		testHeaderPage.setData(list)
//		testHeaderPage.onRefreshListener = object : OnRefreshListener
//		{
//			override fun onRefresh()
//			{
//				Logs.i(TAG, "onRefresh: ")
//
//				Thread(Runnable {
//					Thread.sleep(5000)
//					testHeaderPage.needRefresh = false
//				}).start()
//			}
//		}
//		testHeaderPage.searchButtonOnClickListener = object : SearchButtonOnClickListener
//		{
//			override fun onClick()
//			{
//				Logs.i(TAG, "onClick: ")
//			}
//		}
		list.add(Header("http://img.kaiyanapp.com/4cd80e67eb0e293b84d45cf3151536b9.jpeg?imageMogr2/quality/60/format/jpg", "title4", "subtitle1"))
		list.add(Header("http://img.kaiyanapp.com/4cd80e67eb0e293b84d45cf3151536b9.jpeg?imageMogr2/quality/60/format/jpg", "title5", "subtitle1"))
		list.add(Header("http://img.kaiyanapp.com/4cd80e67eb0e293b84d45cf3151536b9.jpeg?imageMogr2/quality/60/format/jpg", "title6", "subtitle1"))
		list.add(Header("http://img.kaiyanapp.com/4cd80e67eb0e293b84d45cf3151536b9.jpeg?imageMogr2/quality/60/format/jpg", "title7", "subtitle1"))
		list.add(Header("http://img.kaiyanapp.com/83d3dddeeefa3f6549030938ab24533c.jpeg?imageMogr2/quality/60/format/jpg", "title8", "subtitle1"))
		recyclerView.layoutManager = LinearLayoutManager(this)
		recyclerView.adapter = Adapter(list)
	}

	class Adapter(private val list: ArrayList<Header>) : RecyclerView.Adapter<Holder>()
	{
		private val TAG = "Adapter"

		override fun onBindViewHolder(holder: Holder, position: Int)
		{
			holder.headerPage.setData(list)
			holder.headerPage.onRefreshListener = object : OnRefreshListener
			{
				override fun onRefresh()
				{
					Logs.i(TAG, "onRefresh: ")

					Thread(Runnable {
						Thread.sleep(5000)
						holder.headerPage.needRefresh = false
					}).start()
				}
			}
			holder.headerPage.searchButtonOnClickListener = object : SearchButtonOnClickListener
			{
				override fun onClick()
				{
					Logs.i(TAG, "onClick: ")
				}
			}
		}

		override fun getItemCount(): Int
		{
			return 5
		}

		override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder
		{
			return Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_header, parent, false))
		}
	}

	class Holder(itemView: View) : RecyclerView.ViewHolder(itemView)
	{
		var headerPage: HeaderPage = itemView.findViewById(R.id.headerPage)
	}
}
