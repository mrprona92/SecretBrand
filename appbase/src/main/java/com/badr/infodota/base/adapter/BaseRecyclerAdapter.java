package com.badr.infodota.base.adapter;

import android.support.v7.widget.RecyclerView;

import com.badr.infodota.base.adapter.holder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ABadretdinov
 * 12.12.2014
 * 16:39
 */
public abstract class BaseRecyclerAdapter<T, R extends BaseViewHolder> extends RecyclerView.Adapter<R> {
    protected List<T> mData;
    protected OnItemClickListener mOnItemClickListener;

    public BaseRecyclerAdapter(List<T> data) {
        mData = data != null ? data : new ArrayList<T>();
    }

    public void setOnItemClickListener(OnItemClickListener mListener) {
        this.mOnItemClickListener = mListener;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public T getItem(int position) {
        return mData.get(position);
    }

    public List<T> getItems() {
        return mData;
    }
}
