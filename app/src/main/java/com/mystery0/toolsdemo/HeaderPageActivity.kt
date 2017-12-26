/*
 * Created by Mystery0 on 17-12-26 下午11:29.
 * Copyright (c) 2017. All Rights reserved.
 *
 * Last modified 17-10-23 下午9:45
 */

package com.mystery0.toolsdemo

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import com.bumptech.glide.Glide
//import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_header_page.*

class HeaderPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_header_page)

        Glide.with(this)
                .load("http://img.kaiyanapp.com/83d3dddeeefa3f6549030938ab24533c.jpeg?imageMogr2/quality/60/format/jpg")
//                .asBitmap()
//                .into(object : SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
//                    override fun onResourceReady(bitmap: Bitmap,
//                                                 glideAnimation: GlideAnimation<in Bitmap>?) {
//                        val layoutParams = imageView.layoutParams
//                        layoutParams.height = getScreenWidth(this@HeaderPageActivity) * bitmap.height / bitmap.width
//                        layoutParams.width = getScreenWidth(this@HeaderPageActivity)
//                        imageView.layoutParams = layoutParams
//                        imageView.setImageBitmap(zoomImg(bitmap, layoutParams.width, layoutParams.height))
//                    }
//                })

        val list = ArrayList<String>()
        list.add("http://img.kaiyanapp.com/83d3dddeeefa3f6549030938ab24533c.jpeg?imageMogr2/quality/60/format/jpg")
        list.add("http://img.kaiyanapp.com/8c66311bc96f662edd67bbcb1769684b.jpeg?imageMogr2/quality/60/format/jpg")
        list.add("http://img.kaiyanapp.com/83d3dddeeefa3f6549030938ab24533c.jpeg?imageMogr2/quality/60/format/jpg")
        list.add("http://img.kaiyanapp.com/4cd80e67eb0e293b84d45cf3151536b9.jpeg?imageMogr2/quality/60/format/jpg")
        list.add("http://img.kaiyanapp.com/4cd80e67eb0e293b84d45cf3151536b9.jpeg?imageMogr2/quality/60/format/jpg")
        list.add("http://img.kaiyanapp.com/4cd80e67eb0e293b84d45cf3151536b9.jpeg?imageMogr2/quality/60/format/jpg")
        list.add("http://img.kaiyanapp.com/4cd80e67eb0e293b84d45cf3151536b9.jpeg?imageMogr2/quality/60/format/jpg")
        list.add("http://img.kaiyanapp.com/83d3dddeeefa3f6549030938ab24533c.jpeg?imageMogr2/quality/60/format/jpg")
        list.add("http://img.kaiyanapp.com/83d3dddeeefa3f6549030938ab24533c.jpeg?imageMogr2/quality/60/format/jpg")
        list.add("http://img.kaiyanapp.com/83d3dddeeefa3f6549030938ab24533c.jpeg?imageMogr2/quality/60/format/jpg")
        list.add("http://img.kaiyanapp.com/83d3dddeeefa3f6549030938ab24533c.jpeg?imageMogr2/quality/60/format/jpg")
        list.add("http://img.kaiyanapp.com/83d3dddeeefa3f6549030938ab24533c.jpeg?imageMogr2/quality/60/format/jpg")
        list.add("http://img.kaiyanapp.com/83d3dddeeefa3f6549030938ab24533c.jpeg?imageMogr2/quality/60/format/jpg")
        list.add("http://img.kaiyanapp.com/83d3dddeeefa3f6549030938ab24533c.jpeg?imageMogr2/quality/60/format/jpg")
        list.add("http://img.kaiyanapp.com/83d3dddeeefa3f6549030938ab24533c.jpeg?imageMogr2/quality/60/format/jpg")
        list.add("http://img.kaiyanapp.com/83d3dddeeefa3f6549030938ab24533c.jpeg?imageMogr2/quality/60/format/jpg")
        list.add("http://img.kaiyanapp.com/83d3dddeeefa3f6549030938ab24533c.jpeg?imageMogr2/quality/60/format/jpg")
        list.add("http://img.kaiyanapp.com/83d3dddeeefa3f6549030938ab24533c.jpeg?imageMogr2/quality/60/format/jpg")
        list.add("http://img.kaiyanapp.com/83d3dddeeefa3f6549030938ab24533c.jpeg?imageMogr2/quality/60/format/jpg")
        list.add("http://img.kaiyanapp.com/83d3dddeeefa3f6549030938ab24533c.jpeg?imageMogr2/quality/60/format/jpg")
        list.add("http://img.kaiyanapp.com/83d3dddeeefa3f6549030938ab24533c.jpeg?imageMogr2/quality/60/format/jpg")
        list.add("http://img.kaiyanapp.com/83d3dddeeefa3f6549030938ab24533c.jpeg?imageMogr2/quality/60/format/jpg")
        list.add("http://img.kaiyanapp.com/83d3dddeeefa3f6549030938ab24533c.jpeg?imageMogr2/quality/60/format/jpg")
        list.add("http://img.kaiyanapp.com/83d3dddeeefa3f6549030938ab24533c.jpeg?imageMogr2/quality/60/format/jpg")

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = Adapter(list)
        recyclerView.isNestedScrollingEnabled = false
    }

    class Adapter(private val list: ArrayList<String>) : RecyclerView.Adapter<Holder>() {
        private val TAG = "Adapter"

        override fun onBindViewHolder(holder: Holder, position: Int) {
            holder.textView.text = list[position]
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            return Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_header, parent, false))
        }
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView = itemView.findViewById(R.id.textView)
    }

    private fun zoomImg(bitmap: Bitmap, newWidth: Int, newHeight: Int): Bitmap {
        // 获得图片的宽高
        val width = bitmap.width
        val height = bitmap.height
        // 计算缩放比例
        val scaleWidth = newWidth.toFloat() / width
        val scaleHeight = newHeight.toFloat() / height
        // 取得想要缩放的matrix参数
        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeight)
        // 得到新的图片
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true)
    }

    private fun getScreenWidth(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(outMetrics)
        return outMetrics.widthPixels
    }
}
