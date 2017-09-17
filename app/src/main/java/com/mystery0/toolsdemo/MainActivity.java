package com.mystery0.toolsdemo;

import android.content.Intent;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import vip.mystery0.tools.CrashHandler.AutoCleanListener;
import vip.mystery0.tools.CrashHandler.CrashHandler;
import vip.mystery0.tools.FileUtil.FileUtil;
import vip.mystery0.tools.Logs.Logs;
import vip.mystery0.tools.MysteryNetFrameWork.FileResponseListener;
import vip.mystery0.tools.HTTPok.HTTPok;
import vip.mystery0.tools.HTTPok.HTTPokResponse;
import vip.mystery0.tools.HTTPok.HTTPokResponseListener;
import vip.mystery0.tools.MysteryNetFrameWork.HttpUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
{
	private static final String TAG = "MainActivity";
	private RequestQueue requestQueue;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

//		setTitle(getString(R.string.app_name) + "-" + getString(R.string.app_version) + "-" + getString(R.string.app_version_code));

		requestQueue = Volley.newRequestQueue(MainActivity.this);

		Button picture_chooser = findViewById(R.id.picture_chooser);
		Button send = findViewById(R.id.sendHttp);
		Button sendJson = findViewById(R.id.sendHttpGetJson);
		Button sendFileHttp = findViewById(R.id.sendFileHttp);
		Button downloadFile = findViewById(R.id.testDownloadFile);
		Button testLog = findViewById(R.id.testLog);
		Button testCrash = findViewById(R.id.testCrash);
		Button testImageLoader = findViewById(R.id.testImageLoader);
		Button testSnackBar = findViewById(R.id.testSnackBar);

		picture_chooser.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				startActivity(new Intent(MainActivity.this, PictureChooserDemoActivity.class));
			}
		});

		send.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Map<String, String> map = new HashMap<>();
				map.put("test", "hello");
				new HTTPok()
						.setURL("http://123.206.186.70/test.php")
						.setRequestMethod(HTTPok.Companion.getPOST())
						.setParams(map)
						.setListener(new HTTPokResponseListener()
						{
							@Override
							public void onError(String message)
							{
								Logs.i(TAG, "onError: " + message);
							}

							@Override
							public void onResponse(@NotNull HTTPokResponse response)
							{
								Logs.i(TAG, "onResponse: " + response.getMessage());
							}
						})
						.open();
			}
		});


		sendJson.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Map<String, String> map = new HashMap<>();
				map.put("test", "hello");
				new HTTPok()
						.setURL("http://123.206.186.70/php/hitokoto.php")
						.setRequestMethod(HTTPok.Companion.getGET())
						.setParams(map)
						.setListener(new HTTPokResponseListener()
						{
							@Override
							public void onError(@Nullable String message)
							{
								Logs.e(TAG, "onError: " + message);
							}

							@Override
							public void onResponse(@NotNull HTTPokResponse response)
							{
								Hitokoto hitokoto = response.getJSON(Hitokoto.class);
								Logs.i(TAG, "onResponse: " + hitokoto);
								Logs.i(TAG, "onResponse: " + hitokoto.getContent());
							}
						})
						.open();
			}
		});

		sendFileHttp.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.setType("*/*");
				intent.addCategory(Intent.CATEGORY_OPENABLE);
				startActivityForResult(intent, 2333);
			}
		});

		downloadFile.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				new HttpUtil(MainActivity.this)
						.setRequestQueue(requestQueue)
						.setUrl("http://img05.tooopen.com/images/20160121/tooopen_sy_155168162826.jpg")
						.isFileRequest(true)
						.setFileRequest(HttpUtil.FileRequest.DOWNLOAD)
						.setDownloadFilePath(Environment.getExternalStorageDirectory().getPath() + "/Download/")
						.setFileResponseListener(new FileResponseListener()
						{
							@Override
							public void onResponse(int code, File file, String message)
							{
								Logs.i(TAG, "onResponse: " + code);
								Logs.i(TAG, "onResponse: " + (file != null ? file.getPath() : "空"));
								Logs.i(TAG, "onResponse: " + message);
							}
						})
						.open();
			}
		});

		testLog.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Logs.v(TAG, "verbose");
				Logs.i(TAG, "info");
				Logs.d(TAG, "debug");
				Logs.w(TAG, "warning");
				Logs.e(TAG, "error");
			}
		});

		testCrash.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				int temp = Integer.parseInt("test");
				Logs.i(TAG, String.valueOf(temp));
			}
		});
		testImageLoader.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				startActivity(new Intent(MainActivity.this, ImageLoaderActivity.class));
			}
		});
		testSnackBar.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Snackbar.make(view, "testSnackBar", Snackbar.LENGTH_SHORT)
						.show();
			}
		});

		CrashHandler.getInstance(getApplicationContext())
				.clean(new AutoCleanListener()
				{
					@Override
					public void done()
					{
						Logs.i(TAG, "done: ");
					}

					@Override
					public void error(String message)
					{
						Logs.i(TAG, "error: " + message);
					}
				});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == 2333 && data != null)
		{
			String path = FileUtil.getPath(MainActivity.this, data.getData());
			Logs.i(TAG, "onActivityResult: " + path);
			Map<String, Object> map = new HashMap<>();
			map.put("file", new File(path));
			new HTTPok()
					.setURL("http://123.206.186.70/test.php")
					.setRequestMethod(HTTPok.Companion.getPOST())
					.isFileRequest()
					.setParams(map)
					.setListener(new HTTPokResponseListener()
					{
						@Override
						public void onError(String message)
						{
							Logs.i(TAG, "onError: " + message);
						}

						@Override
						public void onResponse(@NotNull HTTPokResponse response)
						{
							Logs.i(TAG, "onResponse: " + response.getMessage());
						}
					})
					.open();
		}
	}
}
