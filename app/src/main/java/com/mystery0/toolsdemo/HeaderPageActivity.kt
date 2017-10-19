package com.mystery0.toolsdemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_header_page.*
import vip.mystery0.tools.headerPage.Header
import vip.mystery0.tools.headerPage.OnRefreshListener
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
		list.add(Header("http://img.kaiyanapp.com/4cd80e67eb0e293b84d45cf3151536b9.jpeg?imageMogr2/quality/60/format/jpg", "title4", "subtitle1"))
		list.add(Header("http://img.kaiyanapp.com/4cd80e67eb0e293b84d45cf3151536b9.jpeg?imageMogr2/quality/60/format/jpg", "title5", "subtitle1"))
		list.add(Header("http://img.kaiyanapp.com/4cd80e67eb0e293b84d45cf3151536b9.jpeg?imageMogr2/quality/60/format/jpg", "title6", "subtitle1"))
		list.add(Header("http://img.kaiyanapp.com/4cd80e67eb0e293b84d45cf3151536b9.jpeg?imageMogr2/quality/60/format/jpg", "title7", "subtitle1"))
		list.add(Header("http://img.kaiyanapp.com/83d3dddeeefa3f6549030938ab24533c.jpeg?imageMogr2/quality/60/format/jpg", "title8", "subtitle1"))

		headerPage.setData(list)
		headerPage.setOnRefreshListener(object : OnRefreshListener
		{
			override fun onRefresh()
			{
				Logs.i(TAG, "onRefresh: ")

				Thread(Runnable {
					Thread.sleep(10000)
					headerPage.needRefresh = false
				}).start()
			}
		})
	}
}
