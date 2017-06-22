package com.mystery0.tools.PictureChooser

import android.content.Context
import android.net.Uri
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout

import com.mystery0.tools.FileUtil.FileUtil
import com.mystery0.tools.R

import java.util.ArrayList

class iPictureChooser(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs)
{
	private val recyclerView: RecyclerView
	private val showList = ArrayList<String>()
	private var adapter: iPictureChooserAdapter? = null

	companion object Code
	{
		val IMG_CHOOSE = 22
	}

	var list: List<String>
		get() = showList
		set(list)
		{
			showList.clear()
			showList.addAll(list)
			adapter!!.notifyDataSetChanged()
		}

	init
	{
		LayoutInflater.from(context).inflate(R.layout.mystery0_i_picture_chooser_main, this)
		recyclerView = findViewById<RecyclerView>(R.id.i_picture_chooser_layout)
	}

	fun setDataList(defaultImage: Int, listener: iPictureChooserListener)
	{
		recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
		if (defaultImage == 0)
		{
			adapter = iPictureChooserAdapter(showList, context, listener)
			recyclerView.adapter = adapter
		}
		else
		{
			adapter = iPictureChooserAdapter(showList, defaultImage, context, listener)
			recyclerView.adapter = adapter
		}
		recyclerView.itemAnimator = DefaultItemAnimator()
	}

	fun setDataList(listener: iPictureChooserListener)
	{
		setDataList(0, listener)
	}

	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	fun setUpdatedPicture(uri: Uri)
	{
		showList.add(FileUtil.getPath(context, uri)!!)
		adapter!!.notifyDataSetChanged()
	}
}
