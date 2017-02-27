package com.mystery0.toolsdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.mystery0.tools.ifloatmenu.MenuClick;
import com.mystery0.tools.ifloatmenu.iFloatMenu;

public class FloatMenuDemoActivity extends AppCompatActivity
{
    private static final String TAG = "FloatMenuDemoActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_float_menu);

        iFloatMenu iFloatMenu = (iFloatMenu) findViewById(R.id.ifloat);
        iFloatMenu.setNumber(5);
        iFloatMenu.setIcon(R.drawable.ic_android);
        iFloatMenu.setTexts(new String[]{"1","ssss","test","1","456"});
        iFloatMenu.setIcons(new int[]{R.drawable.ic_android, R.drawable.ic_android, R.drawable.ic_android, R.drawable.ic_android, R.drawable.ic_android});
        iFloatMenu.setMenuClickListener(new MenuClick()
        {
            @Override
            public void menuClick(int position)
            {
                Log.i(TAG, "menuClick: " + position);
            }
        });
    }
}
