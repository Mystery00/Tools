package vip.mystery0.tools.hTTPok

import okhttp3.*
import java.io.File
import java.io.IOException
import java.lang.StringBuilder

/**
 * Created by myste.
 */
class HTTPok
{
	private val TAG = "HTTPok"
	private var url = ""//请求地址
	private var requestMethod = 0//请求方式
	private var listener: HTTPokResponseListener? = null
	private var client: OkHttpClient? = null
	private var params: Map<String, Any> = HashMap()
	private var requestTag = RequestBodyType.STRING

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
		return this
	}

	fun isFileRequest(): HTTPok
	{
		requestTag = RequestBodyType.FILE
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
		val requestBuilder = Request.Builder()
		val requestBody: RequestBody
		when (requestMethod)
		{
			POST ->
			{
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
				requestBuilder.url(url)
				requestBuilder.post(requestBody)
			}
			GET ->
			{
				val iterator = params.entries.iterator()
				val urlBuilder = StringBuilder(url)
				if (params.isNotEmpty())
					urlBuilder.append('?')
				while (iterator.hasNext())
				{
					val map = iterator.next()
					urlBuilder.append(map.key).append('=').append(map.value)
					if (iterator.hasNext())
						urlBuilder.append('&')
				}
				requestBuilder.url(urlBuilder.toString())
				requestBuilder.get()
			}
		}
		client!!.newCall(requestBuilder.build())
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