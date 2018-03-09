/*
 * Created by Mystery0 on 18-3-10 上午12:52.
 * Copyright (c) 2018. All Rights reserved.
 *
 * Last modified 18-3-10 上午12:21
 */

package vip.mystery0.tools.utils

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.support.annotation.RequiresApi
import java.io.*
import java.text.DecimalFormat

object Mystery0FileUtil {
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @JvmStatic
    fun getPath(context: Context, uri: Uri): String? {
        when {
            DocumentsContract.isDocumentUri(context, uri) -> {
                when (uri.authority) {
                    "com.android.externalstorage.documents" -> {
                        val docId = DocumentsContract.getDocumentId(uri)
                        val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                        val type = split[0]

                        if ("primary".equals(type, ignoreCase = true)) {
                            return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                        }
                    }
                    "com.android.providers.downloads.documents" -> {
                        val id = DocumentsContract.getDocumentId(uri)
                        val contentUri = ContentUris.withAppendedId(
                                Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id)!!)

                        return getDataColumn(context, contentUri, null, null)
                    }
                    "com.android.providers.media.documents" -> {
                        val docId = DocumentsContract.getDocumentId(uri)
                        val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                        val type = split[0]

                        var contentUri: Uri? = null
                        when (type) {
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
                              selectionArgs: Array<String>?): String? {
        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(column)
        try {
            cursor = context.contentResolver.query(uri, projection, selection, selectionArgs, null)
            if (cursor != null && cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(columnIndex)
            }
        } finally {
            if (cursor != null)
                cursor.close()
        }
        return null
    }

    fun formatFileSize(fileSize: Long): String {
        return formatFileSize(fileSize, "#.00")
    }

    fun formatFileSize(fileSize: Long, format: String): String {
        val df = DecimalFormat(format)
        val fileSizeString: String
        fileSizeString = when {
            fileSize < 1024 -> df.format(fileSize.toDouble()) + "B"
            fileSize < 1048576 -> df.format(fileSize.toDouble() / 1024) + "KB"
            fileSize < 1073741824 -> df.format(fileSize.toDouble() / 1048576) + "MB"
            else -> df.format(fileSize.toDouble() / 1073741824) + "GB"
        }
        return fileSizeString
    }

    fun saveFile(inputStream: InputStream?, file: File): Boolean {
        try {
            if (!file.parentFile.exists())
                file.parentFile.mkdirs()
            if (file.exists())
                file.delete()
            val dataInputStream = DataInputStream(BufferedInputStream(inputStream))
            val dataOutputStream = DataOutputStream(BufferedOutputStream(FileOutputStream(file)))
            val bytes = ByteArray(1024 * 1024)
            while (true) {
                val read = dataInputStream.read(bytes)
                if (read <= 0)
                    break
                dataOutputStream.write(bytes, 0, read)
            }
            dataOutputStream.close()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }
}
