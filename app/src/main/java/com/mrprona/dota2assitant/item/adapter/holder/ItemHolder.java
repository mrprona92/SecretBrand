package com.mrprona.dota2assitant.item.adapter.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mrprona.dota2assitant.R;
import com.mrprona.dota2assitant.base.adapter.OnItemClickListener;
import com.mrprona.dota2assitant.base.adapter.holder.BaseViewHolder;

/**
 * Created by ABadretdinov
 * 17.12.2014
 * 18:05
 */
public class ItemHolder extends BaseViewHolder {
    public TextView name;
    public ImageView image;

    public ItemHolder(View itemView, OnItemClickListener listener) {
        super(itemView, listener);
    }

    @Override
    protected void initView(View itemView) {
        name = (TextView) itemView.findViewById(R.id.name);
        image = (ImageView) itemView.findViewById(R.id.img);
    }
}
