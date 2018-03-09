/*
 * Created by Mystery0 on 18-3-10 上午12:54.
 * Copyright (c) 2018. All Rights reserved.
 *
 * Last modified 18-3-10 上午12:54
 */

package vip.mystery0.tools.utils

import android.graphics.Bitmap
import android.view.View

object Mystery0ViewUtil{
	/**
	 * 根据指定的view截图
	 *
	 * @param view 要截图的view
	 * @return Bitmap
	 */
	fun getViewBitmap(view: View?): Bitmap? {
		if (null == view)
			return null
		view.isDrawingCacheEnabled = true
		view.buildDrawingCache()
		view.measure(View.MeasureSpec.makeMeasureSpec(view.width, View.MeasureSpec.EXACTLY),
				View.MeasureSpec.makeMeasureSpec(view.height, View.MeasureSpec.EXACTLY))
		view.layout(view.x.toInt(), view.y.toInt(), view.x.toInt() + view.measuredWidth, view.y.toInt() + view.measuredHeight)

		val bitmap = Bitmap.createBitmap(view.drawingCache, 0, 0, view.measuredWidth, view.measuredHeight)
		view.isDrawingCacheEnabled = false
		view.destroyDrawingCache()
		return bitmap
	}
}