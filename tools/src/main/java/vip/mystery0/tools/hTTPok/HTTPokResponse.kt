package vip.mystery0.tools.hTTPok

import com.google.gson.Gson
import java.io.*

/**
 * Created by myste.
 */
class HTTPokResponse(val inputStream: InputStream?)
{
	fun <T> getJSON(className: Class<T>): T
	{
		return getJSON(Gson(), className)
	}

	fun <T> getJSON(gson: Gson, className: Class<T>): T
	{
		return gson.fromJson(InputStreamReader(inputStream), className)
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

	fun getFile(saveFile: File): Boolean
	{
		try
		{
			val dataInputStream = DataInputStream(BufferedInputStream(inputStream))
			val dataOutputStream = DataOutputStream(BufferedOutputStream(FileOutputStream(saveFile)))
			val bytes = ByteArray(1024 * 1024)
			while (true)
			{
				val read = dataInputStream.read(bytes)
				if (read <= 0)
					break
				dataOutputStream.write(bytes, 0, read)
			}
			dataOutputStream.close()
			return true
		}
		catch (e: Exception)
		{
			return false
		}
	}
}