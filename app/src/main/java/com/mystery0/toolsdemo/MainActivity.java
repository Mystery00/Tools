package com.mystery0.toolsdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button float_menu = (Button) findViewById(R.id.float_menu);
        Button picture_chooser = (Button) findViewById(R.id.picture_chooser);
        Button spinner = (Button) findViewById(R.id.m_spinner);

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
    }
}
