package vip.mystery0.tools.HTTPok

/**
 * Created by myste.
 */
interface HTTPokResponseListener
{
	fun onError(message: String?)
	fun onResponse(response: HTTPokResponse)
}