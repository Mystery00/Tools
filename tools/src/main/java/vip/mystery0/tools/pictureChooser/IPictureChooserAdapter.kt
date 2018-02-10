/*
 * Created by Mystery0 on 17-12-26 下午11:29.
 * Copyright (c) 2017. All Rights reserved.
 *
 * Last modified 17-10-12 下午11:42
 */

package vip.mystery0.tools.pictureChooser

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import vip.mystery0.tools.R

internal class IPictureChooserAdapter(private var pathList: ArrayList<String>, private var add_img: Int,
                                      var context: Context,
                                      private var listener: IPictureChooserListener) : RecyclerView.Adapter<IPictureChooserAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView = itemView as ImageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.mystery0_i_picture_chooser_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return pathList.size + 1
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == 0) {
            holder.imageView.setImageResource(add_img)
            holder.imageView.setOnClickListener { listener.onMainClick() }
        } else {
            Glide.with(context)
                    .load(pathList[position - 1])
                    .into(holder.imageView)
            holder.imageView.setOnLongClickListener {
                pathList.removeAt(holder.adapterPosition - 1)
                notifyItemRemoved(holder.adapterPosition)
                true
            }
        }
    }
}