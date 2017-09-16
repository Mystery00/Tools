package vip.mystery0.tools.MysteryNetFrameWork

import java.net.HttpURLConnection
import java.net.URL
import vip.mystery0.tools.Logs.Logs
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder

/**
 * Created by myste.
 */
class PostHttpConnect : HttpConnect()
{
	private val TAG = "PostHttpConnect"
	private val requestMethod = "POST"//请求方式

	fun setURL(url: String)
	{
		address = url
	}

	fun putParams(params: Map<String, String>)
	{
		this.params = params
	}

	override fun request()
	{
		if (address == "")
		{
			throw NullPointerException("the url can not be null! ")
		}
		try
		{
			val url = URL(address)
			val connection = url.openConnection() as HttpURLConnection
			connection.requestMethod = requestMethod
			connection.useCaches = false
			connection.connectTimeout = timeout
			connection.readTimeout = timeout
			connection.doInput = true

			if (params.isNotEmpty())
			{
				val body = getParamString(params)
				val data = body.toString().toByteArray()
				connection.doOutput = true
				connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
				connection.setRequestProperty("Content-Length", data.size.toString())
				connection.outputStream.write(data)
				connection.outputStream.flush()
				connection.outputStream.close()
			}

			if (connection.responseCode == HttpURLConnection.HTTP_OK)
			{
				val inputStream = connection.inputStream
				val reader = BufferedReader(InputStreamReader(inputStream))
				val response = StringBuilder()
				val chars = CharArray(1024)
				var len = reader.read(chars)
				while (len >= 0)
				{
					response.append(chars, 0, len)
					len = reader.read(chars)
				}
				inputStream.close()
				Logs.i(TAG, "request: Response: " + response.toString())
			}
			else
			{
				Logs.i(TAG, "request: 请求错误")
			}
			connection.disconnect()
		}
		catch (e: Exception)
		{
			e.printStackTrace()
		}
	}

	private fun getParamString(params: Map<String, String>): StringBuffer
	{
		val result = StringBuffer()
		val iterator = params.entries.iterator()
		while (iterator.hasNext())
		{
			val param = iterator.next()
			val key = param.key
			val value = param.value
			result.append(key).append('=').append(value)
			if (iterator.hasNext())
			{
				result.append('&')
			}
		}
		return result
	}
}