package vip.mystery0.tools.HTTPok

import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

/**
 * Created by myste.
 */
class HTTPokResponse(private val inputStream: InputStream)
{
	private val TAG = "HTTPokResponse"

	fun <T> getJSON(className: Class<T>): T
	{
		return Gson().fromJson(InputStreamReader(inputStream), className)
	}

	fun getMessage(): String
	{
		val reader = BufferedReader(InputStreamReader(inputStream))
		val response = StringBuilder()
		val chars = CharArray(1024)
		var len = reader.read(chars)
		while (len >= 0)
		{
			response.append(chars, 0, len)
			len = reader.read(chars)
		}
		return response.toString()
	}
}