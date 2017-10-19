package vip.mystery0.tools.headerPage

import android.animation.ObjectAnimator
import android.os.Handler
import android.os.Message
import android.widget.ImageView

/**
 * Created by myste.
 */
class RefreshHandler : Handler()
{
	lateinit var imageView: ImageView

	override fun handleMessage(msg: Message?)
	{
		val animator = ObjectAnimator.ofFloat(imageView, "rotation", 0F, 360F)
		animator.duration = 1000
		animator.start()
	}
}