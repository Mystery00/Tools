package com.mystery0.toolsdemo;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.mystery0.tools.CrashHandler.CrashHandler;
import com.mystery0.tools.FileUtil.FileUtil;
import com.mystery0.tools.Logs.Logs;
import com.mystery0.tools.MysteryNetFrameWork.FileResponseListener;
import com.mystery0.tools.MysteryNetFrameWork.HttpUtil;
import com.mystery0.tools.MysteryNetFrameWork.ResponseListener;

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

		requestQueue = Volley.newRequestQueue(MainActivity.this);

		Button float_menu = (Button) findViewById(R.id.float_menu);
		Button picture_chooser = (Button) findViewById(R.id.picture_chooser);
		Button spinner = (Button) findViewById(R.id.m_spinner);
		Button send = (Button) findViewById(R.id.sendHttp);
		Button sendJson = (Button) findViewById(R.id.sendHttpGetJson);
		Button sendFileHttp = (Button) findViewById(R.id.sendFileHttp);
		Button downloadFile = (Button) findViewById(R.id.testDownloadFile);
		Button testLog = (Button) findViewById(R.id.testLog);
		Button testCrash = (Button) findViewById(R.id.testCrash);
		Button testImageLoader = (Button) findViewById(R.id.testImageLoader);

		float_menu.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				startActivity(new Intent(MainActivity.this, FloatMenuDemoActivity.class));
			}
		});

		picture_chooser.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				startActivity(new Intent(MainActivity.this, PictureChooserDemoActivity.class));
			}
		});

		spinner.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				startActivity(new Intent(MainActivity.this, SpinnerDemoActivity.class));
			}
		});

		send.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Map<String, String> map = new HashMap<>();
				map.put("username", "123");
				map.put("password", "123");
				new HttpUtil(MainActivity.this)
						.setRequestQueue(requestQueue)
						.setRequestMethod(HttpUtil.RequestMethod.GET)
						.setUrl("http://www.mutour.vip/mutour/mtlog.handle.php")
						.setMap(map)
						.setResponseListener(new ResponseListener()
						{
							@Override
							public void onResponse(int code, String message)
							{
								Logs.i(TAG, "onResponse: " + message);
								Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT)
										.show();
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
				map.put("username", "123");
				map.put("password", "123");
				final HttpUtil httpUtil = new HttpUtil(MainActivity.this);
				httpUtil.setRequestMethod(HttpUtil.RequestMethod.GET)
						.setRequestQueue(requestQueue)
						.setUrl("http://www.mutour.vip/mutour/mtlog.handle.php")
						.setMap(map)
						.setResponseListener(new ResponseListener()
						{
							@Override
							public void onResponse(int code, String message)
							{
								Logs.i(TAG, "onResponse: " + message);
								Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG)
										.show();
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

		CrashHandler.getInstance(getApplicationContext())
				.clean(new CrashHandler.AutoCleanListener()
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
			Map<String, String> map = new HashMap<>();
			Map<String, File> fileMap = new HashMap<>();
			fileMap.put("upload_file", new File(path));
			map.put("group", new File(path).getName());
			map.put("method", "uploadFile");
			new HttpUtil(MainActivity.this)
					.setRequestQueue(requestQueue)
					.setUrl("http://123.206.186.70/hitokoto.php")
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
							Logs.i(TAG, "onResponse: " + message);
						}
					})
					.open();
		}
	}
}
