/*
 * Created by Mystery0 on 18-2-9 下午5:16.
 * Copyright (c) 2018. All Rights reserved.
 *
 * Last modified 18-2-9 下午5:16
 */

package vip.mystery0.tools.dirManager

import android.content.Context
import android.os.Environment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import java.io.File

class DirManager : RecyclerView {
    private var rootPath: String = Environment.getExternalStorageDirectory().absolutePath//默认根目录是/sdcard
    private var showList = ArrayList<File>()
    private lateinit var dirAdapter: DirAdapter

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView()
    }

    private fun initView() {
        layoutManager=LinearLayoutManager(context)
        val adapter = DirAdapter(showList)
        dirAdapter = adapter
        this.adapter = adapter
        updateList(File(rootPath))
    }

    fun setRootPath(rootPath: File) {
        setRootPath(rootPath.absolutePath)
    }

    fun setRootPath(rootPath: String) {
        this.rootPath = rootPath
    }

    private fun updateList(currentPath: File) {
        showList.clear()
        showList.addAll(currentPath.listFiles())
        val iterator = showList.iterator()
        while (iterator.hasNext())
            if (!iterator.next().isDirectory)
                iterator.remove()
        dirAdapter.notifyDataSetChanged()
    }
}