package com.mystery0.toolsdemo;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.toolbox.Volley;
import com.mystery0.tools.CrashHandler.CrashHandler;
import com.mystery0.tools.Logs.Logs;
import com.mystery0.tools.MysteryNetFrameWork.HttpUtil;
import com.mystery0.tools.MysteryNetFrameWork.ResponseListener;
import com.mystery0.tools.SnackBar.ASnackBar;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class App extends Application
{
	private static final String TAG = "App";
	@Override
	public void onCreate()
	{
		super.onCreate();
		Logs.setLevel(Logs.LogLevel.Debug);
		CrashHandler.getInstance(this)
				.setDirectory("test")
				.setPrefixName("log")
				.setExtensionName("log")
				.isAutoClean()
				.sendException(new CrashHandler.CatchExceptionListener()
				{
					@Override
					public void onException(@NonNull File file, @NonNull String appVersionName, int appVersionCode, @NonNull String AndroidVersion, int sdk, @NonNull String vendor, @NonNull String model)
					{
						Log.i(TAG, "onException: " + file.getAbsolutePath());
						Log.i(TAG, "onException: " + appVersionName);
						Log.i(TAG, "onException: ");
						Map<String, String> map = new HashMap<>();
						Map<String, File> fileMap = new HashMap<>();
						fileMap.put("logFile", file);
						map.put("date", Calendar.getInstance().toString());
						map.put("appName", getString(R.string.app_name));
						map.put("appVersionName", appVersionName);
						map.put("appVersionCode", String.valueOf(appVersionCode));
						map.put("androidVersion", AndroidVersion);
						map.put("sdk", String.valueOf(sdk));
						map.put("vendor", vendor);
						map.put("model", model);
						new HttpUtil(getApplicationContext())
								.setRequestQueue(Volley.newRequestQueue(getApplicationContext()))
								.setUrl("http://123.206.186.70/php/uploadLog/upload_file.php")
								.setRequestMethod(HttpUtil.RequestMethod.POST)
								.setFileRequest(HttpUtil.FileRequest.UPLOAD)
								.isFileRequest(true)
								.setMap(map)
								.setFileMap(fileMap)
								.setResponseListener(new ResponseListener()
								{
									@Override
									public void onResponse(int code, String message)
									{
										Logs.i("info", ": " + message);
									}
								})
								.open();
					}
				})
				.init();
	}
}
