package vip.mystery0.tools.MysteryNetFrameWork

import java.io.File

interface FileResponseListener
{
	fun onResponse(code: Int, file: File?, message: String?)
}