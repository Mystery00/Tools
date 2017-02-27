package com.mystery0.toolsdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mystery0.tools.ipicturechooser.iPictureChooser;
import com.mystery0.tools.ipicturechooser.iPictureChooserListener;

public class PictureChooserDemoActivity extends AppCompatActivity
{
    private iPictureChooser pictureChooser;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_chooser_demo);

        pictureChooser = (iPictureChooser) findViewById(R.id.i_picture_chooser);
        pictureChooser.setDataList(R.drawable.ic_android, new iPictureChooserListener()
        {
            @Override
            public void MainClick()
            {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, iPictureChooser.REQUEST_IMG_CHOOSE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == iPictureChooser.REQUEST_IMG_CHOOSE)
        {
            if (data != null)
            {
                pictureChooser.setUpdatedPicture(data.getData());
            }
        }
    }
}
