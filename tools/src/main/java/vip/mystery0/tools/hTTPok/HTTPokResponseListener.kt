package vip.mystery0.tools.hTTPok

/**
 * Created by myste.
 */
interface HTTPokResponseListener
{
	fun onError(message: String?)
	fun onResponse(response: HTTPokResponse)
}