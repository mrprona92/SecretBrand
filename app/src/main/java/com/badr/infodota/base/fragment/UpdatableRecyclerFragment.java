package com.badr.infodota.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.badr.infodota.R;
import com.badr.infodota.base.adapter.BaseRecyclerAdapter;
import com.badr.infodota.base.adapter.OnItemClickListener;
import com.badr.infodota.base.adapter.holder.BaseViewHolder;

/**
 * Created by Badr on 21.12.2014.
 */
public abstract class UpdatableRecyclerFragment<T, VIEW_HOLDER extends BaseViewHolder> extends Fragment implements OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    protected SwipeRefreshLayout mListContainer;
    protected RecyclerView mRecyclerView;
    protected View mProgressBar;
    protected View mEmptyView;
    protected int layoutId = R.layout.updatable_recycler_content;
    protected BaseRecyclerAdapter<T, VIEW_HOLDER> mAdapter;

    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }

    public RecyclerView getRecyclerView() {
        if (mRecyclerView == null) {
            ensureList(getView());
        }
        return mRecyclerView;
    }

    public RecyclerView.LayoutManager getLayoutManager(Context context) {
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        return manager;
    }

    private void ensureList(View root) {
        if (root == null) {
            throw new IllegalStateException("Content view not yet created");
        }
        if (root instanceof RecyclerView) {
            mRecyclerView = (RecyclerView) root;
        } else {
            mRecyclerView = (RecyclerView) root.findViewById(android.R.id.list);
        }
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(getLayoutManager(mRecyclerView.getContext()));
        mRecyclerView.setVerticalScrollBarEnabled(true);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        mRecyclerView.setItemAnimator(itemAnimator);
        mListContainer = (SwipeRefreshLayout) root.findViewById(R.id.listContainer);
        if (mListContainer != null) {
            mListContainer.setColorSchemeResources(R.color.primary);
            mListContainer.setOnRefreshListener(this);
        }
        mEmptyView = root.findViewById(R.id.internalEmpty);
        mProgressBar = root.findViewById(R.id.progressBar);
        if (mAdapter != null) {
            setAdapter(mAdapter);
        }
    }

    public BaseRecyclerAdapter<T, VIEW_HOLDER> getAdapter() {
        return mAdapter;
    }

    public void setAdapter(BaseRecyclerAdapter<T, VIEW_HOLDER> adapter) {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.GONE);
        }
        mAdapter = adapter;
        if (mRecyclerView != null) {
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.setOnItemClickListener(this);
        }
        if (mEmptyView != null) {
            if (mAdapter.getItemCount() == 0) {
                mEmptyView.setVisibility(View.VISIBLE);
            } else {
                mEmptyView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(layoutId, container, false);
        ensureList(view);
        return view;
    }

    public void setRefreshing(boolean refreshing) {
        mListContainer.setRefreshing(refreshing);
    }
}
