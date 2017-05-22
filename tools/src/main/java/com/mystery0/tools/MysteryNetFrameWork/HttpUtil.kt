package com.mystery0.tools.MysteryNetFrameWork

import android.content.Context

import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import java.io.File
import okhttp3.*
import java.io.IOException


class HttpUtil(private val context: Context)
{
	private var requestMethod: RequestMethod? = null//请求方式
	private var url: String = ""//请求地址
	private var map: Map<String, String>? = null//输入数据
	private var responseListener: ResponseListener? = null//回调
	private var isFileRequest: Boolean = false//是否为文件请求
	private var fileRequest: FileRequest? = null//文件请求方法
	private var fileMap: Map<String, File>? = null//上传的文件

	enum class FileRequest
	{
		UPLOAD, DOWNLOAD
	}

	enum class RequestMethod
	{
		POST, GET
	}

	fun setRequestMethod(requestMethod: RequestMethod): HttpUtil
	{
		this.requestMethod = requestMethod
		return this
	}

	fun setUrl(url: String): HttpUtil
	{
		this.url = url
		return this
	}

	fun setResponseListener(responseListener: ResponseListener): HttpUtil
	{
		this.responseListener = responseListener
		return this
	}

	fun setMap(map: Map<String, String>): HttpUtil
	{
		this.map = map
		return this
	}

	fun isFileRequest(isFileRequest: Boolean): HttpUtil
	{
		this.isFileRequest = isFileRequest
		return this
	}

	fun setFileRequest(fileRequest: FileRequest): HttpUtil
	{
		this.fileRequest = fileRequest
		return this
	}

	fun setFileMap(fileMap: Map<String, File>): HttpUtil
	{
		this.fileMap = fileMap
		return this
	}

	fun open()
	{
		if (url == "")
		{
			throw NullPointerException("url cannot be null")
		}
		var method = Request.Method.GET
		if (this.requestMethod == RequestMethod.POST)
		{
			method = Request.Method.POST
		}
		if (isFileRequest)
		{
			if (fileRequest == null)
			{
				throw NullPointerException("File Request cannot be null")
			}
			Thread(
					Runnable {
						val builder = MultipartBody.Builder()
						builder.setType(MultipartBody.FORM)
						val fileKeys: Set<String> = fileMap!!.keys
						for (tempKey: String in fileKeys)
						{
							val fileBody = RequestBody.create(MediaType.parse("*/*"), (fileMap as Map<String, File>)[tempKey])
							builder.addFormDataPart(tempKey, (fileMap as Map<String, File>)[tempKey]?.name, fileBody)
						}
						val keys: Set<String> = map!!.keys
						for (tempKey: String in keys)
						{
							builder.addFormDataPart(tempKey, (map as Map<String, String>)[tempKey])
						}
						val requestBody = builder.build()
						val request = okhttp3.Request.Builder()
								.url(url)
								.post(requestBody)
								.build()
						val okHttpClient = OkHttpClient()
						val call = okHttpClient.newCall(request)
						call.enqueue(object : Callback
						{
							override fun onFailure(call: Call, e: IOException)
							{
								responseListener!!.onResponse(0, e.message)
							}

							override fun onResponse(call: Call, response: okhttp3.Response)
							{
								responseListener!!.onResponse(1, response.body().string())
							}
						})
					}
			).start()
		}
		else
		{
			val requestQueue = Volley.newRequestQueue(context)
			val stringRequest = object : StringRequest(method, this.url,
					Response.Listener<String> { response -> responseListener!!.onResponse(1, response) },
					Response.ErrorListener { volleyError -> responseListener!!.onResponse(0, volleyError.message) })
			{
			}
			requestQueue.add(stringRequest)
		}
	}
}