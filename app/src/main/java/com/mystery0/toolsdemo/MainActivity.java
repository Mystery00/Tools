package com.mystery0.toolsdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mystery0.tools.Logs.Logs;
import com.mystery0.tools.MysteryNetFrameWork.HttpUtil;
import com.mystery0.tools.MysteryNetFrameWork.ResponseListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
{
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button float_menu = (Button) findViewById(R.id.float_menu);
        Button picture_chooser = (Button) findViewById(R.id.picture_chooser);
        Button spinner = (Button) findViewById(R.id.m_spinner);
        Button send = (Button) findViewById(R.id.sendHttp);
        Button sendJson = (Button) findViewById(R.id.sendHttpGetJson);
        Button testLog = (Button) findViewById(R.id.testLog);

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
                        .setUrl("http://www.mutour.vip/mutour/mtlog.handle.php")
                        .setMap(map)
                        .setResponseListener(new ResponseListener()
                        {
                            @Override
                            public void onResponse(int code, String message)
                            {
                                Response response = httpUtil.fromJson(message, Response.class);
                                Logs.i(TAG, "onResponse: " + response);
                                Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_LONG)
                                        .show();
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
    }
}
