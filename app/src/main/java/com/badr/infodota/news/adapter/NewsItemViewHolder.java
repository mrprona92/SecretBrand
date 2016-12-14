package com.badr.infodota.news.adapter;

import android.view.View;
import android.widget.TextView;

import com.badr.infodota.R;
import com.badr.infodota.base.adapter.OnItemClickListener;
import com.badr.infodota.base.adapter.holder.BaseViewHolder;

/**
 * Created by ABadretdinov
 * 24.08.2015
 * 18:27
 */
public class NewsItemViewHolder extends BaseViewHolder {
    public TextView title;
    public TextView author;
    public TextView date;

    public NewsItemViewHolder(View itemView, OnItemClickListener listener) {
        super(itemView, listener);
    }

    @Override
    protected void initView(View itemView) {
        title = (TextView) itemView.findViewById(R.id.title);
        author = (TextView) itemView.findViewById(R.id.author);
        date = (TextView) itemView.findViewById(R.id.date);

    }
}
