/*
 * Created by Mystery0 on 17-12-26 下午11:29.
 * Copyright (c) 2017. All Rights reserved.
 *
 * Last modified 17-10-12 下午11:44
 */

package com.mystery0.toolsdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import vip.mystery0.tools.pictureChooser.IPictureChooser;
import vip.mystery0.tools.pictureChooser.IPictureChooserListener;

import java.util.ArrayList;
import java.util.List;

public class PictureChooserDemoActivity extends AppCompatActivity
{
	private IPictureChooser pictureChooser;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picture_chooser_demo);

		pictureChooser = findViewById(R.id.i_picture_chooser);
		List<String> list = new ArrayList<>();
		list.add("http://ww2.sinaimg.cn/orj480/76da98c1gw1f5yhzht65hj20qo1bfgul.jpg");
		pictureChooser.setDataList(new IPictureChooserListener()
		{
			@Override
			public void MainClick()
			{
				Intent intent = new Intent(Intent.ACTION_PICK);
				intent.setType("image/*");
				startActivityForResult(intent, IPictureChooser.Code.getIMG_CHOOSE());
			}
		});
		pictureChooser.setList(list);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == IPictureChooser.Code.getIMG_CHOOSE())
		{
			if (data != null)
			{
				pictureChooser.setUpdatedPicture(data.getData());
			}
		}
	}
}
