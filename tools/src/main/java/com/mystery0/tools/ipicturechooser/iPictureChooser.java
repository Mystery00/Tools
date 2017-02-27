package com.mystery0.tools.ipicturechooser;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.mystery0.tools.R;

import java.util.ArrayList;
import java.util.List;

public class iPictureChooser extends RelativeLayout
{
    public static final int REQUEST_IMG_CHOOSE = 22;
    private Context context;
    private RecyclerView recyclerView;
    private List<String> showList = new ArrayList<>();
    private iPictureChooserAdapter adapter;

    public iPictureChooser(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.i_picture_chooser_main, this);
        recyclerView = (RecyclerView) findViewById(R.id.i_picture_chooser_layout);
    }

    public void setDataList(int defaultImage, iPictureChooserListener listener)
    {
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        if (defaultImage == 0)
        {
            adapter = new iPictureChooserAdapter(showList, context, listener);
            recyclerView.setAdapter(adapter);
        } else
        {
            adapter = new iPictureChooserAdapter(showList, defaultImage, context, listener);
            recyclerView.setAdapter(adapter);
        }
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    public void setDataList(iPictureChooserListener listener)
    {
        setDataList(0, listener);
    }

    public List<String> getList()
    {
        return showList;
    }

    public void setUpdatedPicture(Uri uri)
    {
        showList.add(iPictureChooserFile.getPath(context, uri));
        adapter.notifyDataSetChanged();
    }

    public void setList(List<String> list)
    {
        showList.clear();
        showList.addAll(list);
        adapter.notifyDataSetChanged();
    }
}
