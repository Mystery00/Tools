package com.mystery0.tools.FileUtil

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.support.annotation.RequiresApi
import java.text.DecimalFormat

object FileUtil
{
	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	@JvmStatic fun getPath(context: Context, uri: Uri): String?
	{
		when
		{
			DocumentsContract.isDocumentUri(context, uri) ->
			{
				when (uri.authority)
				{
					"com.android.externalstorage.documents" ->
					{
						val docId = DocumentsContract.getDocumentId(uri)
						val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
						val type = split[0]

						if ("primary".equals(type, ignoreCase = true))
						{
							return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
						}
					}
					"com.android.providers.downloads.documents" ->
					{
						val id = DocumentsContract.getDocumentId(uri)
						val contentUri = ContentUris.withAppendedId(
								Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id)!!)

						return getDataColumn(context, contentUri, null, null)
					}
					"com.android.providers.media.documents" ->
					{
						val docId = DocumentsContract.getDocumentId(uri)
						val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
						val type = split[0]

						var contentUri: Uri? = null
						when (type)
						{
							"image" -> contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
							"video" -> contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
							"audio" -> contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
						}

						val selection = "_id=?"
						val selectionArgs = arrayOf(split[1])

						return getDataColumn(context, contentUri!!, selection, selectionArgs)
					}
				}
			}
			"content".equals(uri.scheme, ignoreCase = true) ->
				return getDataColumn(context, uri, null, null)
			"file".equals(uri.scheme, ignoreCase = true) ->
				return uri.path
		}
		return null
	}

	private fun getDataColumn(context: Context, uri: Uri, selection: String?,
							  selectionArgs: Array<String>?): String?
	{
		var cursor: Cursor? = null
		val column = "_data"
		val projection = arrayOf(column)
		try
		{
			cursor = context.contentResolver.query(uri, projection, selection, selectionArgs, null)
			if (cursor != null && cursor.moveToFirst())
			{
				val column_index = cursor.getColumnIndexOrThrow(column)
				return cursor.getString(column_index)
			}
		}
		finally
		{
			if (cursor != null)
				cursor.close()
		}
		return null
	}

	fun FormatFileSize(fileSize: Long): String
	{
		return FormatFileSize(fileSize, "#.00")
	}

	fun FormatFileSize(fileSize: Long, format: String): String
	{
		val df = DecimalFormat(format)
		val fileSizeString: String
		if (fileSize < 1024)
		{
			fileSizeString = df.format(fileSize.toDouble()) + "B"
		}
		else if (fileSize < 1048576)
		{
			fileSizeString = df.format(fileSize.toDouble() / 1024) + "KB"
		}
		else if (fileSize < 1073741824)
		{
			fileSizeString = df.format(fileSize.toDouble() / 1048576) + "MB"
		}
		else
		{
			fileSizeString = df.format(fileSize.toDouble() / 1073741824) + "GB"
		}
		return fileSizeString
	}
}
