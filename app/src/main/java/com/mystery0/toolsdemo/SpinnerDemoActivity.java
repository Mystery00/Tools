package com.mystery0.toolsdemo;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.mystery0.ispinner.SpinnerItemClickListener;
import com.mystery0.ispinner.iSpinner;

public class SpinnerDemoActivity extends AppCompatActivity
{
    private static final String TAG = "SpinnerDemoActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner_demo);

        iSpinner spinner=(com.mystery0.ispinner.iSpinner)findViewById(R.id.spinner);
        String[] strings=new String[]{"dasdasd","dsddfff","123456","456798"};
        spinner.setStrings(strings);
        spinner.setSelected(0);
        spinner.setListBackground(Color.BLUE);
        spinner.setOnItemClickListener(new SpinnerItemClickListener()
        {
            @Override
            public void onItemClick(int position)
            {
                Log.i(TAG, "onItemClick: "+position);
            }
        });
        spinner.setViewBackground(Color.GRAY);
    }
}
