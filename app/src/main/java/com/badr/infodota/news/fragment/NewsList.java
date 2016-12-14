package com.badr.infodota.news.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.ActionMenuView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Toast;

import com.badr.infodota.base.activity.BaseActivity;
import com.badr.infodota.base.activity.ListHolderActivity;
import com.badr.infodota.base.fragment.UpdatableRecyclerFragment;
import com.badr.infodota.base.view.EndlessRecycleScrollListener;
import com.badr.infodota.news.activity.NewsItemActivity;
import com.badr.infodota.news.adapter.NewsAdapter;
import com.badr.infodota.news.adapter.NewsItemViewHolder;
import com.badr.infodota.news.api.AppNews;
import com.badr.infodota.news.api.NewsItem;
import com.badr.infodota.news.task.NewsLoadRequest;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.UncachedSpiceService;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

/**
 * User: ABadretdinov
 * Date: 21.04.14
 * Time: 18:29
 */
public class NewsList extends UpdatableRecyclerFragment<NewsItem, NewsItemViewHolder> implements RequestListener<AppNews> {

    private SpiceManager mSpiceManager = new SpiceManager(UncachedSpiceService.class);
    private boolean mReloading = false;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        ActionMenuView actionMenuView = ((ListHolderActivity) getActivity()).getActionMenuView();
        Menu actionMenu = actionMenuView.getMenu();
        actionMenu.clear();
        actionMenuView.setVisibility(View.GONE);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        View root = getView();
        if (root == null) {
            return;
        }
        getRecyclerView().addOnScrollListener(new EndlessRecycleScrollListener() {
            @Override
            public void onLoadMore() {
                mReloading = false;
                loadNews();
            }
        });
    }

    private void loadNews() {
        final BaseActivity activity = (BaseActivity) getActivity();
        if (activity != null) {
            setRefreshing(true);
            Long fromDate = null;
            if (!mReloading) {
                NewsItem lastItem = getAdapter().getItem(getAdapter().getItemCount() - 1);
                fromDate = lastItem.getDate();
            }
            mSpiceManager.execute(new NewsLoadRequest(activity.getApplicationContext(), fromDate), this);
        }
    }

    @Override
    public void onRefresh() {
        mReloading = true;
        loadNews();
    }

    @Override
    public void onStart() {
        if (!mSpiceManager.isStarted()) {
            mSpiceManager.start(getActivity());
            onRefresh();
        }
        super.onStart();
    }

    @Override
    public void onDestroy() {
        if (mSpiceManager.isStarted()) {
            mSpiceManager.shouldStop();
        }
        super.onDestroy();
    }

    @Override
    public void onRequestFailure(SpiceException spiceException) {
        Toast.makeText(getActivity(), spiceException.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        setRefreshing(false);
    }

    @Override
    public void onRequestSuccess(AppNews newsItems) {
        if (!mReloading && getAdapter() != null) {
            ((NewsAdapter) getAdapter()).addData(newsItems.getNewsItems());
        } else {
            setAdapter(new NewsAdapter(newsItems.getNewsItems()));
        }
        setRefreshing(false);
    }

    @Override
    public void onItemClick(View view, int position) {
        NewsItem item = getAdapter().getItem(position);
        Intent intent = new Intent(getActivity(), NewsItemActivity.class);
        intent.putExtra("newsItem", item);
        startActivity(intent);
    }
}
