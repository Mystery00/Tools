/*
 * Created by Mystery0 on 18-2-10 上午12:00.
 * Copyright (c) 2018. All Rights reserved.
 *
 * Last modified 18-2-10 上午12:00
 */

package vip.mystery0.tools.dirManager

import android.content.Context
import android.os.Environment
import android.support.v7.app.AlertDialog
import vip.mystery0.tools.R
import vip.mystery0.tools.logs.Logs

class DirManagerDialog(private val context: Context, private val themeResId: Int) {
    private val TAG = "DirManagerDialog"
    private var alertDialog: AlertDialog? = null
    private var cancelable = true
    private var canceledOnTouchOutside = true
    private var rootPath: String = Environment.getExternalStorageDirectory().absolutePath//默认根目录是/sdcard

    constructor(context: Context) : this(context, R.style.dir_manager_dialog)

    fun setRootPath(rootPath: String) {
        this.rootPath = rootPath
    }

    fun create(): AlertDialog {
        if (alertDialog != null)
            cancel()
        val builder = AlertDialog.Builder(context, themeResId)
        val dirManager = DirManager(context)
        builder.setView(dirManager)
        builder.setCancelable(cancelable)
        builder.setPositiveButton(android.R.string.ok, { _, _ ->
            Logs.i(TAG, "create: ${dirManager.getCurrentPath()}")
        })
        builder.setNegativeButton(android.R.string.cancel, null)
        val dialog = builder.create()
        dialog.setCanceledOnTouchOutside(canceledOnTouchOutside)
        return dialog
    }

    fun show() {
        if (alertDialog != null)
            alertDialog!!.show()
        else
            create().show()
    }

    fun dismiss() {
        if (alertDialog != null)
            alertDialog!!.dismiss()
        alertDialog = null
    }

    fun cancel() {
        if (alertDialog != null)
            alertDialog!!.cancel()
        alertDialog = null
    }
}