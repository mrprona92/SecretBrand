package com.badr.infodota.base.adapter;

import com.badr.infodota.base.adapter.holder.BaseViewHolder;

import java.util.List;

/**
 * Created by ABadretdinov
 * 24.08.2015
 * 18:25
 */
public abstract class BasePageableRecyclerAdapter<T, R extends BaseViewHolder> extends BaseRecyclerAdapter<T, R> {
    public BasePageableRecyclerAdapter(List<T> data) {
        super(data);
    }

    public void addData(List<T> data) {
        int position = mData.size();
        int addedSize = 0;
        for (T entity : data) {
            if (!mData.contains(entity)) {
                addedSize++;
                mData.add(entity);
            }
        }
        notifyItemRangeInserted(position, addedSize);
    }
}
