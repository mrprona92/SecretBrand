package com.badr.infodota.player.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.R;
import com.badr.infodota.base.adapter.BaseRecyclerAdapter;
import com.badr.infodota.base.fragment.UpdatableRecyclerFragment;
import com.badr.infodota.player.activity.PlayerInfoActivity;
import com.badr.infodota.player.adapter.PlayersAdapter;
import com.badr.infodota.player.adapter.holder.PlayerHolder;
import com.badr.infodota.player.api.Unit;
import com.badr.infodota.player.service.PlayerService;
import com.badr.infodota.player.task.SearchedPlayersLoadRequest;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.UncachedSpiceService;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

/**
 * User: ABadretdinov
 * Date: 20.01.14
 * Time: 18:14
 */
public class SearchedPlayersList extends UpdatableRecyclerFragment<Unit, PlayerHolder> implements TextView.OnEditorActionListener, RequestListener<Unit.List> {
    private TextView searchRequest;
    private View listHeader;
    private SpiceManager mSpiceManager = new SpiceManager(UncachedSpiceService.class);
    private
    PlayerService playerService = BeanContainer.getInstance().getPlayerService();

    @Override
    public void onStart() {
        if (!mSpiceManager.isStarted()) {
            mSpiceManager.start(getActivity());
            initData();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setLayoutId(R.layout.players_list);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager(Context context) {
        return new GridLayoutManager(context, 1);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View root = getView();
        if (root != null) {
            setColumnSize();
            searchRequest = (TextView) root.findViewById(R.id.player_name);
            searchRequest.setOnEditorActionListener(this);
            listHeader = root.findViewById(R.id.search_history);
            root.findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    searchRequest.setText("");
                    initData();
                }
            });
            root.findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRefresh();
                }
            });
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setColumnSize();
    }

    private void setColumnSize() {
        if (getResources().getBoolean(R.bool.is_tablet)) {
            ((GridLayoutManager) getRecyclerView().getLayoutManager()).setSpanCount(2);
        } else {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                ((GridLayoutManager) getRecyclerView().getLayoutManager()).setSpanCount(2);
            } else {
                ((GridLayoutManager) getRecyclerView().getLayoutManager()).setSpanCount(1);
            }
        }
    }

    private void initData() {
        Activity activity = getActivity();
        if (activity != null) {
            mSpiceManager.execute(new SearchedPlayersLoadRequest(activity.getApplicationContext(), null), this);
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                actionId == EditorInfo.IME_ACTION_DONE || event == null ||
                event.getAction() == KeyEvent.ACTION_DOWN &&
                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            onRefresh();
            return true;
        }
        return false; // pass on to other listeners.
    }

    @Override
    public void onItemClick(View view, int position) {
        Unit unit = mAdapter.getItem(position);

        unit.setSearched(true);
        playerService.saveAccount(getActivity(), unit);

        Intent intent = new Intent(getActivity(), PlayerInfoActivity.class);
        intent.putExtra("account", unit);
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        Activity activity = getActivity();
        if (activity != null) {
            setRefreshing(true);
            mSpiceManager.execute(new SearchedPlayersLoadRequest(activity.getApplicationContext(), searchRequest.getText().toString()), this);
        }
    }

    @Override
    public void onRequestFailure(SpiceException spiceException) {
        setRefreshing(false);
        Toast.makeText(getActivity(), spiceException.getLocalizedMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void setAdapter(BaseRecyclerAdapter<Unit, PlayerHolder> adapter) {
        super.setAdapter(adapter);
        if (adapter.getItemCount() == 0) {
            listHeader.setVisibility(View.GONE);
        } else {
            mEmptyView.setVisibility(View.GONE);
            if (TextUtils.isEmpty(searchRequest.getText().toString())) {
                listHeader.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onRequestSuccess(Unit.List units) {
        setRefreshing(false);
        PlayersAdapter adapter = new PlayersAdapter(units, true, getResources().getStringArray(R.array.match_history_title));
        setAdapter(adapter);
    }


}