/*
 * Created by Mystery0 on 17-12-28 上午2:42.
 * Copyright (c) 2017. All Rights reserved.
 *
 * Last modified 17-12-28 上午2:42
 */

package com.mystery0.toolsdemo

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class TestAdapter : RecyclerView.Adapter<TestAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_test, parent, false))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}