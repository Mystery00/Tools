package com.mystery0.tools.PictureChooser

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.mystery0.tools.R

class iPictureChooserAdapter constructor(var pathList: ArrayList<String>, var add_img: Int,
										 var context: Context,
										 var listener: iPictureChooserListener) : RecyclerView.Adapter<iPictureChooserAdapter.ViewHolder>()
{
	class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
	{
		var imageView: ImageView = itemView as ImageView
	}

	constructor(pathList: ArrayList<String>, context: Context,
				listener: iPictureChooserListener) : this(pathList, R.drawable.mystery0_i_picture_chooser_add, context, listener)

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
	{
		val view: View = LayoutInflater.from(parent.context).inflate(R.layout.mystery0_i_picture_chooser_item, parent, false)
		return ViewHolder(view)
	}

	override fun getItemCount(): Int
	{
		return pathList.size + 1
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int)
	{
		if (position == 0)
		{
			holder.imageView.setImageResource(add_img)
			holder.imageView.setOnClickListener { listener.MainClick() }
		}
		else
		{
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