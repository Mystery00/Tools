package com.mystery0.ifloatmenu;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class iFloatMenu extends RelativeLayout
{
    private Context context;
    private FloatingActionButton button;
    private List<FloatingActionButton> menuList;
    private int number;
    private int openIcon;
    private int closeIcon;
    private Animation animation_in;
    private Animation animation_out;
    private Animation alpha_in;
    private Animation alpha_out;
    private boolean isOpen = false;

    public iFloatMenu(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.context = context;
        menuList = new ArrayList<>();
        number = 5;
        openIcon = R.drawable.float_menu_default_open;
        closeIcon = R.drawable.float_menu_default_close;
        LayoutInflater.from(context).inflate(R.layout.i_float_menu, this);
        button = (FloatingActionButton) findViewById(R.id.fab_menu);
        FloatingActionButton menu1 = (FloatingActionButton) findViewById(R.id.fab1);
        FloatingActionButton menu2 = (FloatingActionButton) findViewById(R.id.fab2);
        FloatingActionButton menu3 = (FloatingActionButton) findViewById(R.id.fab3);
        FloatingActionButton menu4 = (FloatingActionButton) findViewById(R.id.fab4);
        FloatingActionButton menu5 = (FloatingActionButton) findViewById(R.id.fab5);
        menuList.add(menu1);
        menuList.add(menu2);
        menuList.add(menu3);
        menuList.add(menu4);
        menuList.add(menu5);

        animation_in= AnimationUtils.loadAnimation(context,R.anim.float_menu_transform_in);
        animation_out=AnimationUtils.loadAnimation(context,R.anim.float_menu_transform_out);
        alpha_in=AnimationUtils.loadAnimation(context,R.anim.float_menu_alpha_in);
        alpha_out=AnimationUtils.loadAnimation(context,R.anim.float_menu_alpha_out);

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
        for (int i = 0; i < number; i++)
        {
            menuList.get(i).setImageResource(ids[i]);
        }
    }

    public void setOpenIcon(int resId)
    {
        openIcon = resId;
    }

    public void setCloseIcon(int resId)
    {
        closeIcon = resId;
        button.setImageResource(resId);
    }

    public void setMenuClickListener(final MenuClick menuClick)
    {
        for(int i=0;i<number;i++)
        {
            final int finalI = i;
            menuList.get(i).setOnClickListener(new OnClickListener()
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
                button.startAnimation(alpha_in);
                button.setImageResource(closeIcon);
                button.startAnimation(alpha_out);
                setMenuListVisibility(GONE);
                break;
            case VISIBLE:
                isOpen = true;
                button.startAnimation(alpha_in);
                button.setImageResource(openIcon);
                button.startAnimation(alpha_out);
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
                    button.startAnimation(alpha_in);
                    button.setImageResource(closeIcon);
                    button.startAnimation(alpha_out);
                    setMenuListVisibility(GONE);
                } else
                {
                    isOpen = true;
                    button.startAnimation(alpha_in);
                    button.setImageResource(openIcon);
                    button.startAnimation(alpha_out);
                    setMenuListVisibility(VISIBLE);
                }
            }
        });
    }

    private void setMenuListVisibility(int visibility)
    {
        for (int i = 0; i < number; i++)
        {
            setViewVisibility(menuList.get(i), visibility, i + 1);
        }
    }

    private void setViewVisibility(View view, int visibility, int index)
    {
        if (index <= number)
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
