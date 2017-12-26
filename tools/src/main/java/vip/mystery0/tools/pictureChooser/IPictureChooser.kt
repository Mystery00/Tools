/*
 * Created by Mystery0 on 17-12-26 下午11:29.
 * Copyright (c) 2017. All Rights reserved.
 *
 * Last modified 17-10-12 下午11:42
 */

package vip.mystery0.tools.pictureChooser

import android.content.Context
import android.net.Uri
import android.os.Build
import android.support.annotation.DrawableRes
import android.support.annotation.RequiresApi
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout

import vip.mystery0.tools.fileUtil.FileUtil
import vip.mystery0.tools.R

import java.util.ArrayList

class IPictureChooser(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {
    private val recyclerView: RecyclerView
    private val showList = ArrayList<String>()
    private var adapter: IPictureChooserAdapter? = null
    @DrawableRes private var imgResource: Int

    companion object Code {
        val IMG_CHOOSE = 22
    }

    var list: List<String>
        get() = showList
        set(list) {
            showList.clear()
            showList.addAll(list)
            adapter!!.notifyDataSetChanged()
        }

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.IPictureChooser)
        imgResource = typedArray.getResourceId(R.styleable.IPictureChooser_img_resource, R.drawable.mystery0_i_picture_chooser_add)
        typedArray.recycle()
        LayoutInflater.from(context).inflate(R.layout.mystery0_i_picture_chooser_main, this)
        recyclerView = findViewById(R.id.i_picture_chooser_layout)
    }

    fun setDataList(listener: IPictureChooserListener) {
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        adapter = IPictureChooserAdapter(showList, imgResource, context, listener)
        recyclerView.adapter = adapter
        recyclerView.itemAnimator = DefaultItemAnimator()
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    fun setUpdatedPicture(uri: Uri) {
        showList.add(FileUtil.getPath(context, uri)!!)
        adapter!!.notifyDataSetChanged()
    }
}
