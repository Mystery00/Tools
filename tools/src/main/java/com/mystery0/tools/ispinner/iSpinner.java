package com.mystery0.tools.ispinner;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mystery0.tools.R;

public class iSpinner extends LinearLayout
{
    private static final String TAG = "iSpinner";
    private String[] strings;
    private boolean isOpen = false;
    private Context context;
    private TextView text;
    private ListView listView;
    private LinearLayout head;
    private View view_spinner;
    private LinearLayout layout;
    private View line;
    private Animation animation_in;
    private Animation animation_out;
    private int index = 0;

    public iSpinner(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.mystery0_ispinner, this);
        line = findViewById(R.id.line);
        head = (LinearLayout) findViewById(R.id.i_spinner);
        text = (TextView) findViewById(R.id.head_text);
        listView = (ListView) findViewById(R.id.list);
        layout = (LinearLayout) findViewById(R.id.layout);
        view_spinner = findViewById(R.id.view);

        animation_in = AnimationUtils.loadAnimation(context, R.anim.mystery0_spinner_transform_in);
        animation_out = AnimationUtils.loadAnimation(context, R.anim.mystery0_spinner_transform_out);
    }

    public void setStrings(String[] strings)
    {
        Log.i(TAG, "setStrings: 加载数据");
        this.strings = strings;
        monitor();
    }

    public void setSelected(int position)
    {
        text.setText(strings[position]);
        index = position;
    }

    public int getIndex()
    {
        return index;
    }

    public void setListBackground(int color)
    {
        listView.setBackgroundColor(color);
        line.setBackgroundColor(color);
    }

    public void setViewBackground(int color)
    {
        view_spinner.setBackgroundColor(color);
    }

    public ListView getList()
    {
        return listView;
    }

    public View getView()
    {
        return layout;
    }

    public boolean isOpen()
    {
        return isOpen;
    }

    public void setLayoutVisiblity(int visibility)
    {
        setViewVisibility(layout, visibility);
        switch (visibility)
        {
            case GONE:
                isOpen = false;
                break;
            case VISIBLE:
                isOpen = true;
                break;
        }
    }

    public View getHead()
    {
        return head;
    }

    public void setOnItemClickListener(final SpinnerItemClickListener spinnerItemClickListener)
    {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                text.setText(strings[position]);
                layout.setVisibility(GONE);
                line.setVisibility(GONE);
                isOpen = false;
                index = position;
                spinnerItemClickListener.onItemClick(position);
            }
        });
    }

    private void monitor()
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                context, R.layout.mystery0_ispinner_item, strings
        );
        listView.setAdapter(adapter);
        head.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (isOpen)
                {
                    isOpen = false;
                    setViewVisibility(layout, GONE);
                } else
                {
                    isOpen = true;
                    setViewVisibility(layout, VISIBLE);
                }
            }
        });
        view_spinner.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                isOpen = false;
                setViewVisibility(layout, GONE);
            }
        });
    }

    private void setViewVisibility(View view, int visibility)
    {
        switch (visibility)
        {
            case GONE:
                view.setVisibility(visibility);
                line.setVisibility(visibility);
                view.startAnimation(animation_out);
                line.startAnimation(animation_out);
                break;
            case VISIBLE:
                view.setVisibility(visibility);
                line.setVisibility(visibility);
                view.startAnimation(animation_in);
                line.startAnimation(animation_in);
                break;
        }
    }
}