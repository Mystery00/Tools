package com.mystery0.toolsdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import vip.mystery0.tools.ImageLoader.ImageCache;

public class ImageLoaderActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_loader);

        Button button = (Button) findViewById(R.id.button);
        final ImageView imageView = (ImageView) findViewById(R.id.image);

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                RequestQueue requestQueue = Volley.newRequestQueue(ImageLoaderActivity.this);
                ImageLoader imageLoader = new ImageLoader(requestQueue, new ImageCache(ImageLoaderActivity.this));
                ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView, R.drawable.ic_cloud_done, R.drawable.ic_error);
                imageLoader.get("https://www.google.co.jp/logos/doodles/2017/tamas-18th-birthday-4812762818543616.2-l.png", listener);
            }
        });
    }
}
