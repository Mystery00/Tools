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
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import vip.mystery0.tools.R
import vip.mystery0.tools.logs.Logs
import java.io.File

class DirManager : FrameLayout {
    private val TAG = "DirManager"
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private var rootPath: String = Environment.getExternalStorageDirectory().absolutePath//默认根目录是/sdcard
    private lateinit var currentPath: File
    private var isRefresh = false
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
        LayoutInflater.from(context).inflate(R.layout.mystery0_dir_view, this)
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.GONE
        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = DirAdapter(showList, rootPath)
        adapter.dirSelectedListener = object : DirAdapter.DirSelectedListener {
            override fun onSelected(selectedFile: File) {
                Logs.i(TAG, "onSelected: " + selectedFile.absolutePath)
                currentPath = selectedFile
                updateList()
            }
        }
        dirAdapter = adapter
        recyclerView.adapter = adapter
        currentPath = File(rootPath)
        updateList()
    }

    fun setRootPath(rootPath: File) {
        setRootPath(rootPath.absolutePath)
    }

    fun setRootPath(rootPath: String) {
        this.rootPath = rootPath
        dirAdapter.rootPath = rootPath
    }

    fun getRootPath(): String {
        return rootPath
    }

    fun setCurrentPath(currentPath: String) {
        setCurrentPath(File(currentPath))
    }

    fun setCurrentPath(currentPath: File) {
        this.currentPath = currentPath
        updateList()
    }

    fun getCurrentPath(): String {
        return currentPath.absolutePath
    }

    private fun updateList() {
        if (isRefresh)
            return
        isRefresh = true
        Observable.create<Boolean> {
            showList.clear()
            if (currentPath.absolutePath != rootPath)
                showList.add(currentPath.parentFile)
            showList.addAll(currentPath.listFiles())
            val iterator = showList.iterator()
            while (iterator.hasNext())
                if (!iterator.next().isDirectory)
                    iterator.remove()
            showList.sort()
            it.onComplete()
        }
                .subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Boolean> {
                    override fun onComplete() {
                        dirAdapter.notifyDataSetChanged()
                        progressBar.visibility = View.GONE
                        isRefresh = false
                    }

                    override fun onSubscribe(d: Disposable) {
                        progressBar.visibility = View.VISIBLE
                        isRefresh = false
                    }

                    override fun onNext(t: Boolean) {
                    }

                    override fun onError(e: Throwable) {
                        progressBar.visibility = View.GONE
                        isRefresh = false
                        Logs.wtf(TAG, "onError: ", e)
                    }
                })
    }
}