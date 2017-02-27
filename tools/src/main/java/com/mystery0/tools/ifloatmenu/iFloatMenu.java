package com.mystery0.tools.ifloatmenu;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mystery0.tools.R;

import java.util.ArrayList;
import java.util.List;

public class iFloatMenu extends RelativeLayout
{
    private FloatingActionButton button;
    private List<Menu> menuList;
    private int number;
    private Animation animation_in;
    private Animation animation_out;
    private Animation button_in;
    private Animation button_out;
    private boolean isOpen = false;

    private class Menu
    {
        View fullView;
        FloatingActionButton button;
        TextView text;

        Menu(View layout)
        {
            fullView = layout;
            button = (FloatingActionButton) layout.findViewById(R.id.fab);
            text = (TextView) layout.findViewById(R.id.text);
        }
    }

    public iFloatMenu(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        menuList = new ArrayList<>();
        number = 4;
        LayoutInflater.from(context).inflate(R.layout.i_float_menu, this);
        button = (FloatingActionButton) findViewById(R.id.fab_menu);
        menuList.add(new Menu(findViewById(R.id.item1)));
        menuList.add(new Menu(findViewById(R.id.item2)));
        menuList.add(new Menu(findViewById(R.id.item3)));
        menuList.add(new Menu(findViewById(R.id.item4)));
        menuList.add(new Menu(findViewById(R.id.item5)));

        animation_in = AnimationUtils.loadAnimation(context, R.anim.float_menu_transform_in);
        animation_out = AnimationUtils.loadAnimation(context, R.anim.float_menu_transform_out);
        button_in = AnimationUtils.loadAnimation(context, R.anim.float_button_transform_in);
        button_out = AnimationUtils.loadAnimation(context, R.anim.float_button_transform_out);

        monitor();
    }

    public void setNumber(int number)//设置菜单数量
    {
        if (number <= 0 || number > 5)
            throw new NumberFormatException("Number error! Please make number>0 and number<=5! ");
        else
        {
            this.number = number;
        }
    }

    public void setIcons(int[] ids)
    {
        if (ids.length > number)
            throw new NumberFormatException("Number error! Please check the value! ");
        for (int i = 0; i < number; i++)
        {
            menuList.get(i).button.setImageResource(ids[i]);
        }
    }

    public void setTexts(String[] texts)
    {
        if (texts.length > number)
            throw new NumberFormatException("Number error! Please check the value! ");
        for (int i = 0; i < number; i++)
        {
            menuList.get(i).text.setText(texts[i]);
        }
    }

    public void setIcon(int resId)
    {
        button.setImageResource(resId);
    }

    public void setMenuClickListener(final MenuClick menuClick)
    {
        for (int i = 0; i < number; i++)
        {
            final int finalI = i;
            menuList.get(i).fullView.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    menuClick.menuClick(finalI);
                }
            });
        }
    }

    public void setMenuVisibility(int visibility)//设置菜单可见性
    {
        switch (visibility)
        {
            case GONE:
                isOpen = false;
                button.startAnimation(button_out);
                setMenuListVisibility(GONE);
                break;
            case VISIBLE:
                isOpen = true;
                button.startAnimation(button_in);
                setMenuListVisibility(VISIBLE);
                break;
        }
    }

    private void monitor()
    {
        button.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (isOpen)
                {
                    isOpen = false;
                    button.startAnimation(button_out);
                    setMenuListVisibility(GONE);
                } else
                {
                    isOpen = true;
                    button.startAnimation(button_in);
                    setMenuListVisibility(VISIBLE);
                }
            }
        });
    }

    private void setMenuListVisibility(int visibility)
    {
        for (int i = 0; i < number; i++)
        {
            setViewVisibility(menuList.get(i).fullView, visibility, i);
        }
    }

    private void setViewVisibility(View view, int visibility, int index)
    {
        if (index < number)
        {
            view.setVisibility(visibility);
            switch (visibility)
            {
                case GONE:
                    view.startAnimation(animation_out);

                    break;
                case VISIBLE:
                    view.startAnimation(animation_in);
                    break;
            }
        } else
        {
            view.setVisibility(GONE);
        }
    }
}