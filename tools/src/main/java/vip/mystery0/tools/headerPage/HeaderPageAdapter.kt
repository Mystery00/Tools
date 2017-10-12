package vip.mystery0.tools.headerPage

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import vip.mystery0.tools.R
import android.util.DisplayMetrics
import android.view.WindowManager


/**
 * Created by myste.
 */
class HeaderPageAdapter(private val context: Context,
						private val list: ArrayList<Header>) : RecyclerView.Adapter<HeaderPageAdapter.ViewHolder>()
{
	override fun getItemCount(): Int
	{
		return list.size
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
	{
		val view = LayoutInflater.from(context).inflate(R.layout.item_header_page, parent, false)
		return ViewHolder(view)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int)
	{
		val header = list[position]
		Glide.with(context)
				.load(header.imgPath)
				.asBitmap()
				.into(object : SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
				{
					override fun onResourceReady(bitmap: Bitmap,
												 glideAnimation: GlideAnimation<in Bitmap>?)
					{
						val layoutParams = holder.imageView.layoutParams
						layoutParams.height = getScreenWidth(context) * bitmap.height / bitmap.width
						layoutParams.width = getScreenWidth(context)
						holder.imageView.layoutParams = layoutParams
						holder.imageView.setImageBitmap(zoomImg(bitmap, layoutParams.width, layoutParams.height))
					}
				})
	}

	private fun zoomImg(bitmap: Bitmap, newWidth: Int, newHeight: Int): Bitmap
	{
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

	private fun getScreenWidth(context: Context): Int
	{
		val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
		val outMetrics = DisplayMetrics()
		wm.defaultDisplay.getMetrics(outMetrics)
		return outMetrics.widthPixels
	}

	class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
	{
		var imageView = itemView as ImageView
	}
}