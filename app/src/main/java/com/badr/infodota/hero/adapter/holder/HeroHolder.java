package com.badr.infodota.hero.adapter.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.badr.infodota.R;
import com.badr.infodota.base.adapter.OnItemClickListener;
import com.badr.infodota.base.adapter.holder.BaseViewHolder;

/**
 * Created by ABadretdinov
 * 17.12.2014
 * 18:39
 */
public class HeroHolder extends BaseViewHolder {
    public TextView name;
    public ImageView image;

    public HeroHolder(View itemView, OnItemClickListener listener) {
        super(itemView, listener);
    }

    @Override
    protected void initView(View itemView) {
        name = (TextView) itemView.findViewById(R.id.name);
        image = (ImageView) itemView.findViewById(R.id.img);
    }
}
