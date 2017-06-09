package com.mystery0.tools.MysteryNetFrameWork

import android.content.Context

import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.mystery0.tools.FileUtil.FileUtil
import okhttp3.*
import java.io.*


class HttpUtil(private val context: Context)
{
	private var requestMethod: RequestMethod? = null//请求方式
	private var url: String = ""//请求地址
	private var map: Map<String, String> = HashMap()//输入数据
	private var responseListener: ResponseListener? = null//回调
	private var isFileRequest: Boolean = false//是否为文件请求
	private var fileRequest: FileRequest? = null//文件请求方法
	private var fileMap: Map<String, File> = HashMap()//上传的文件
	private var filePath: String? = null//下载文件存储地址
	private var downloadFileName: String? = null//下载文件文件名（带扩展名）
	private var fileResponseListener: FileResponseListener? = null//文件请求回调

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

	fun setDownloadFilePath(filePath: String): HttpUtil
	{
		this.filePath = filePath
		return this
	}

	fun setDownloadFileName(downloadFileName: String): HttpUtil
	{
		this.downloadFileName = downloadFileName
		return this
	}

	fun setResponseListener(responseListener: ResponseListener): HttpUtil
	{
		this.responseListener = responseListener
		return this
	}

	fun setFileResponseListener(fileResponseListener: FileResponseListener): HttpUtil
	{
		this.fileResponseListener = fileResponseListener
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
			when (fileRequest)
			{
				FileRequest.UPLOAD ->
				{
					Thread(Runnable {
						val builder = MultipartBody.Builder()
						builder.setType(MultipartBody.FORM)
						val fileKeys: Set<String> = fileMap.keys
						for (tempKey: String in fileKeys)
						{
							val fileBody = RequestBody.create(MediaType.parse("*/*"), fileMap[tempKey])
							builder.addFormDataPart(tempKey, fileMap[tempKey]?.name, fileBody)
						}
						val keys: Set<String> = map.keys
						for (tempKey: String in keys)
						{
							builder.addFormDataPart(tempKey, map[tempKey])
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
					}).start()
				}
				FileRequest.DOWNLOAD ->
				{
					val requestQueue = Volley.newRequestQueue(context)
					val stringRequest = object : StringRequest(method, this.url,
							Response.Listener<String> { response ->
								if (filePath == "")
								{
									filePath = context.externalCacheDir!!.absolutePath + "/"
								}
								if (downloadFileName == null || downloadFileName == "")
								{
									downloadFileName = FileUtil.getFileNameWithType(url)
								}
								val file = File(filePath + downloadFileName)
								if (!file.exists() || file.delete())
								{
									file.createNewFile()
									val byteArrayInputStream = ByteArrayInputStream(response.toByteArray(charset("ISO-8859-1")))
									val fileOutputStream = FileOutputStream(file)
									val buffer = ByteArray(2048)
									var len: Int
									len = byteArrayInputStream.read(buffer)
									while (len != -1)
									{
										fileOutputStream.write(buffer, 0, len)
										len = byteArrayInputStream.read(buffer)
									}
									fileOutputStream.flush()
									fileOutputStream.close()
								}
								fileResponseListener!!.onResponse(1, file, null)
							},
							Response.ErrorListener { volleyError ->
								fileResponseListener!!.onResponse(0, null, volleyError.message)
							})
					{
						override fun getParams(): Map<String, String>
						{
							return map
						}
					}
					requestQueue.add(stringRequest)
				}
				else ->
				{
					throw NullPointerException("File Request cannot be null")
				}
			}
		}
		else
		{
			val requestQueue: RequestQueue = Volley.newRequestQueue(context)
			val stringRequest = object : StringRequest(method, this.url,
					Response.Listener<String> { response -> responseListener!!.onResponse(1, response) },
					Response.ErrorListener { volleyError -> responseListener!!.onResponse(0, volleyError.message) })
			{
				override fun getParams(): Map<String, String>
				{
					return map
				}
			}
			requestQueue.add(stringRequest)
		}
	}
}