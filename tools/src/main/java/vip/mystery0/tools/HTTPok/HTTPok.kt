package vip.mystery0.tools.HTTPok

import okhttp3.*
import java.io.File
import java.io.IOException

/**
 * Created by myste.
 */
class HTTPok
{
	private var url = ""//请求地址
	private var requestMethod = 0//请求方式
	private var listener: HTTPokResponseListener? = null
	private var client: OkHttpClient? = null
	private var params: Map<String, Any> = HashMap()
	private var requestTag = 0

	init
	{
		client = OkHttpClient()
	}

	companion object
	{
		val POST = 1
		val GET = 2
	}

	private object RequestBodyType
	{
		val STRING = 1
		val FILE = 2
	}

	fun setParams(params: Map<String, Any>): HTTPok
	{
		this.params = params
		return this
	}

	fun setOkHttpClient(client: OkHttpClient): HTTPok
	{
		this.client = client
		return this
	}

	fun setURL(url: String): HTTPok
	{
		this.url = url
		return this
	}

	fun setRequestMethod(requestMethod: Int): HTTPok
	{
		this.requestMethod = requestMethod
		if (requestMethod == POST)
			requestTag = RequestBodyType.STRING
		return this
	}

	fun setListener(listener: HTTPokResponseListener): HTTPok
	{
		this.listener = listener
		return this
	}

	fun open()
	{
		if (url == "")
			throw HTTPokException("the url can not be null")
		if (requestMethod == 0)
			throw HTTPokException("the request method can not be null")
		if (listener == null)
			throw HTTPokException("the response listener can not be null")
		if (client == null)
			throw HTTPokException("client can not be null")
		val requestBody: RequestBody
		when (requestTag)
		{
			RequestBodyType.STRING ->
			{
				val builder = FormBody.Builder()
				for (key in params.keys)
					builder.add(key, params[key].toString())
				requestBody = builder.build()
			}
			RequestBodyType.FILE ->
			{
				val builder = MultipartBody.Builder()
				builder.setType(MultipartBody.FORM)
				for (key in params.keys)
				{
					if (params[key] is File)
					{
						val tempFile = params[key] as File
						val fileBody = RequestBody.create(MediaType.parse("*/*"), tempFile)
						builder.addFormDataPart(key, tempFile.name, fileBody)
					}
					else
					{
						builder.addFormDataPart(key, params[key].toString())
					}
				}
				requestBody = builder.build()
			}
			else -> throw HTTPokException("request body can not be null")
		}
		val builder = Request.Builder()
		builder.url(url)
		when (requestMethod)
		{
			POST -> builder.post(requestBody)
			GET -> builder.get()
		}
		client!!.newCall(builder.build())
				.enqueue(object : Callback
				{
					override fun onFailure(call: Call, e: IOException)
					{
						listener?.onError(e.message)
					}

					override fun onResponse(call: Call, response: Response)
					{
						listener?.onResponse(HTTPokResponse(response.body().byteStream()))
					}
				})
	}
}