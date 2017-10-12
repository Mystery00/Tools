package vip.mystery0.tools.headerPage

import android.support.v7.widget.LinearSnapHelper
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.LinearLayoutManager

/**
 * Created by myste.
 */
class PagerSnapHelper : LinearSnapHelper()
{
	override fun findTargetSnapPosition(layoutManager: RecyclerView.LayoutManager, velocityX: Int,
										velocityY: Int): Int
	{
		var targetPos = super.findTargetSnapPosition(layoutManager, velocityX, velocityY)
		val currentView = findSnapView(layoutManager)
		if (targetPos != RecyclerView.NO_POSITION && currentView != null)
		{
			var currentPosition = layoutManager.getPosition(currentView)
			val first = (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
			val last = layoutManager.findLastVisibleItemPosition()
			currentPosition = if (targetPos < currentPosition) last else if (targetPos > currentPosition) first else currentPosition
			targetPos = when
			{
				targetPos < currentPosition -> currentPosition - 1
				targetPos > currentPosition -> currentPosition + 1
				else -> currentPosition
			}
		}
		return targetPos
	}
}