/*
 * Created by Mystery0 on 18-2-9 下午5:16.
 * Copyright (c) 2018. All Rights reserved.
 *
 * Last modified 18-2-9 下午5:16
 */

package vip.mystery0.tools.dirManager

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import vip.mystery0.tools.R
import java.io.File

class DirAdapter(private val list: List<File>) : RecyclerView.Adapter<DirAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.mystery0_dir_item, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size + 1
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == 0)
            holder.textViewTitle.text = ".."
        else
            holder.textViewTitle.text = list[position - 1].name
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<ImageView>(R.id.imageView)
        val textViewTitle = itemView.findViewById<TextView>(R.id.textView_title)
    }
}