/*
 * Created by Mystery0 on 17-12-26 下午11:29.
 * Copyright (c) 2017. All Rights reserved.
 *
 * Last modified 17-11-4 上午1:03
 */

package com.mystery0.toolsdemo;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import vip.mystery0.tools.crashHandler.AutoCleanListener;
import vip.mystery0.tools.crashHandler.CrashHandler;
import vip.mystery0.tools.fileUtil.FileUtil;
import vip.mystery0.tools.flexibleCardView.FlexibleCardView;
import vip.mystery0.tools.logs.Logs;
import vip.mystery0.tools.spotsDialog.SpotsDialog;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//		setTitle(getString(R.string.app_name) + "-" + getString(R.string.app_version) + "-" + getString(R.string.app_version_code));

        Button picture_chooser = findViewById(R.id.picture_chooser);
        Button send = findViewById(R.id.sendHttp);
        Button sendJson = findViewById(R.id.sendHttpGetJson);
        Button sendFileHttp = findViewById(R.id.sendFileHttp);
        Button downloadFile = findViewById(R.id.testDownloadFile);
        Button testLog = findViewById(R.id.testLog);
        Button testCrash = findViewById(R.id.testCrash);
        Button testHeaderPage = findViewById(R.id.testHeaderPage);
        Button testSnackBar = findViewById(R.id.testSnackBar);
        final FlexibleCardView flexibleCardView=findViewById(R.id.flexibleCardView);

        flexibleCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flexibleCardView.showAnime();
            }
        });

        picture_chooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PictureChooserDemoActivity.class));
            }
        });

//		send.setOnClickListener(new View.OnClickListener()
//		{
//			@Override
//			public void onClick(View v)
//			{
//				Map<String, String> map = new HashMap<>();
//				map.put("test", "hello");
//				new HTTPok()
//						.setURL("http://123.206.186.70/test.php")
//						.setRequestMethod(HTTPok.Companion.getPOST())
//						.setParams(map)
//						.setListener(new HTTPokResponseListener()
//						{
//							@Override
//							public void onError(String message)
//							{
//								Logs.i(TAG, "onError: " + message);
//							}
//
//							@Override
//							public void onResponse(HTTPokResponse response)
//							{
//								Logs.i(TAG, "onResponse: " + response.getMessage());
//							}
//						})
//						.open();
//			}
//		});
//
//
//		sendJson.setOnClickListener(new View.OnClickListener()
//		{
//			@Override
//			public void onClick(View v)
//			{
//				Map<String, String> map = new HashMap<>();
//				map.put("test", "hello");
//				new HTTPok()
//						.setURL("http://123.206.186.70/php/hitokoto.php")
//						.setRequestMethod(HTTPok.Companion.getGET())
//						.setParams(map)
//						.setListener(new HTTPokResponseListener()
//						{
//							@Override
//							public void onError(@Nullable String message)
//							{
//								Logs.e(TAG, "onError: " + message);
//							}
//
//							@Override
//							public void onResponse(HTTPokResponse response)
//							{
//								Hitokoto hitokoto = response.getJSON(Hitokoto.class);
//								Logs.i(TAG, "onResponse: " + hitokoto);
//								Logs.i(TAG, "onResponse: " + hitokoto.getContent());
//							}
//						})
//						.open();
//			}
//		});
//
//		sendFileHttp.setOnClickListener(new View.OnClickListener()
//		{
//			@Override
//			public void onClick(View view)
//			{
//				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//				intent.setType("*/*");
//				intent.addCategory(Intent.CATEGORY_OPENABLE);
//				startActivityForResult(intent, 2333);
//			}
//		});
//
//		downloadFile.setOnClickListener(new View.OnClickListener()
//		{
//			@Override
//			public void onClick(View view)
//			{
//				new HTTPok()
//						.setURL("http://img05.tooopen.com/images/20160121/tooopen_sy_155168162826.jpg")
//						.isFileRequest()
//						.setRequestMethod(HTTPok.Companion.getGET())
//						.setListener(new HTTPokResponseListener()
//						{
//							@Override
//							public void onError(@Nullable String message)
//							{
//								Logs.e(TAG, "onError: " + message);
//							}
//
//							@Override
//							public void onResponse(HTTPokResponse response)
//							{
//								File file = new File(Environment.getExternalStorageDirectory().getPath() + "/Download/test.jpg");
//								Logs.i(TAG, "onResponse: " + response.getFile(file));
//							}
//						})
//						.open();
//			}
//		});

        testLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logs.v(TAG, "verbose");
                Logs.i(TAG, "info");
                Logs.d(TAG, "debug");
                Logs.w(TAG, "warning");
                Logs.e(TAG, "error");
            }
        });

        testCrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = Integer.parseInt("test");
                Logs.i(TAG, String.valueOf(temp));
            }
        });
        testHeaderPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HeaderPageActivity.class));
            }
        });
        testSnackBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//				Snackbar.make(view, "testSnackBar", Snackbar.LENGTH_SHORT)
//						.show();
                SpotsDialog spotsDialog = new SpotsDialog(MainActivity.this);
                spotsDialog.setMessage("test");
                spotsDialog.show();
            }
        });

        CrashHandler.getInstance(getApplicationContext())
                .clean(new AutoCleanListener() {
                    @Override
                    public void done() {
                        Logs.i(TAG, "done: ");
                    }

                    @Override
                    public void error(String message) {
                        Logs.i(TAG, "error: " + message);
                    }
                });
    }

//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data)
//	{
//		if (requestCode == 2333 && data != null)
//		{
//			String path = FileUtil.getPath(MainActivity.this, data.getData());
//			Logs.i(TAG, "onActivityResult: " + path);
//			Map<String, Object> map = new HashMap<>();
//			map.put("file", new File(path));
//			map.put("test", "test hello");
//			new HTTPok()
//					.setURL("http://123.206.186.70/test.php")
//					.setRequestMethod(HTTPok.Companion.getPOST())
//					.isFileRequest()
//					.setParams(map)
//					.setListener(new HTTPokResponseListener()
//					{
//						@Override
//						public void onError(String message)
//						{
//							Logs.i(TAG, "onError: " + message);
//						}
//
//						@Override
//						public void onResponse(HTTPokResponse response)
//						{
//							Logs.i(TAG, "onResponse: " + response.getMessage());
//						}
//					})
//					.open();
//		}
//	}
}
