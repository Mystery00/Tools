package com.mystery0.tools.ipicturechooser;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mystery0.tools.R;

import java.util.List;

public class iPictureChooserAdapter extends RecyclerView.Adapter<iPictureChooserAdapter.ViewHolder>
{
    private List<String> pathList;
    private Context context;
    private int add_img;
    private iPictureChooserListener listener;

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;

        ViewHolder(View itemView)
        {
            super(itemView);
            imageView = (ImageView) itemView;
        }
    }

    public iPictureChooserAdapter(List<String> pathList, Context context, iPictureChooserListener listener)
    {
        this(pathList, R.drawable.i_picture_chooser_add, context, listener);
    }

    public iPictureChooserAdapter(List<String> pathList, int add_img, Context context, iPictureChooserListener listener)
    {
        this.pathList = pathList;
        this.context = context;
        this.add_img = add_img;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType)
    {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.i_picture_chooser_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        if (pathList.size() == 0)
        {
            viewHolder.imageView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    listener.MainClick();
                }
            });
        } else
        {
            viewHolder.imageView.setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View v)
                {
                    pathList.remove(viewHolder.getAdapterPosition() - 1);
                    notifyItemRemoved(viewHolder.getAdapterPosition());
                    return true;
                }
            });
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        if (position == 0)
        {
            holder.imageView.setImageResource(add_img);
        } else
        {
            Glide
                    .with(context)
                    .load(pathList.get(position - 1))
                    .centerCrop()
                    .crossFade()
                    .into(holder.imageView);
        }
    }

    @Override
    public int getItemCount()
    {
        return pathList.size() + 1;
    }
}