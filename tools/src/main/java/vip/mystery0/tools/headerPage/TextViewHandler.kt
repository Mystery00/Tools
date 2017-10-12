package vip.mystery0.tools.headerPage

import android.os.Handler
import android.os.Message
import android.widget.TextView
import vip.mystery0.tools.logs.Logs

/**
 * Created by myste.
 */
class TextViewHandler : Handler()
{
	lateinit var textView: TextView
	lateinit var text: String

	override fun handleMessage(msg: Message?)
	{
		Logs.i("TAG", "handleMessage: "+text)
		textView.text = text
	}
}