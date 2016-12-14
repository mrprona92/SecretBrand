package com.badr.infodota.stream.adapter.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.badr.infodota.R;
import com.badr.infodota.base.adapter.OnItemClickListener;
import com.badr.infodota.base.adapter.holder.BaseViewHolder;

/**
 * Created by Badr on 21.12.2014.
 */
public class StreamHolder extends BaseViewHolder {

    public ImageView img;
    public TextView status;
    public TextView channel;
    public TextView viewers;
    public ImageView favourite;
    public ImageView provider;

    public StreamHolder(View itemView, OnItemClickListener listener) {
        super(itemView, listener);
    }

    @Override
    protected void initView(View itemView) {
        img = (ImageView) itemView.findViewById(R.id.img);
        channel = (TextView) itemView.findViewById(R.id.channel);
        status = (TextView) itemView.findViewById(R.id.status);
        viewers = (TextView) itemView.findViewById(R.id.viewers);
        favourite = (ImageView) itemView.findViewById(R.id.favourite);
        provider = (ImageView) itemView.findViewById(R.id.provider);
    }
}
