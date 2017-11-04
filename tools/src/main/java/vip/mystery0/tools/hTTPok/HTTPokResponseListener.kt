package vip.mystery0.tools.hTTPok

/**
 * Created by myste.
 */
interface HTTPokResponseListener
{
	/**
	 * @deprecated
	 * 使用OKhttp+Retrofit吧
	 */
	fun onError(message: String?)
	/**
	 * @deprecated
	 * 使用OKhttp+Retrofit吧
	 */
	fun onResponse(response: HTTPokResponse?)
}