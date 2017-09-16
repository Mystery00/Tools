package vip.mystery0.tools.MysteryNetFrameWork

/**
 * Created by myste.
 */
abstract class HttpConnect
{
	var address = ""//请求地址
	var timeout = 20000//超时时间
	var params: Map<String, String> = HashMap()

	abstract fun request()
}