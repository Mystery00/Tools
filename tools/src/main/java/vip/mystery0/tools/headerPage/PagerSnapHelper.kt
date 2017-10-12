package vip.mystery0.tools.headerPage

import android.support.v7.widget.LinearSnapHelper
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.LinearLayoutManager
import vip.mystery0.tools.logs.Logs

/**
 * Created by myste.
 */
class PagerSnapHelper : LinearSnapHelper()
{
	private val TAG = "PagerSnapHelper"

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
			currentPosition = when
			{
				targetPos < currentPosition -> last
				targetPos > currentPosition -> first
				else -> currentPosition
			}
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