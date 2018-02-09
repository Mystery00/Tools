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

class DirAdapter(private val list: List<File>, var rootPath: String) : RecyclerView.Adapter<DirAdapter.ViewHolder>() {
    var dirSelectedListener: DirSelectedListener? = null
    private var currentFile: File = File(rootPath)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.mystery0_dir_item, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val file = list[position]
        if (file.absolutePath == currentFile.parentFile.absolutePath)
            holder.textViewTitle.text = ".."
        else
            holder.textViewTitle.text = file.name
        holder.itemView.setOnClickListener {
            currentFile = file
            if (dirSelectedListener != null) {
                dirSelectedListener!!.onSelected(file)
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<ImageView>(R.id.imageView)
        val textViewTitle = itemView.findViewById<TextView>(R.id.textView_title)
    }

    interface DirSelectedListener {
        fun onSelected(selectedFile: File)
    }
}