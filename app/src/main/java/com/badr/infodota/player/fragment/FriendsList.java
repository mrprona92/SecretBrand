package com.badr.infodota.player.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.R;
import com.badr.infodota.base.fragment.UpdatableRecyclerFragment;
import com.badr.infodota.player.activity.PlayerInfoActivity;
import com.badr.infodota.player.adapter.PlayersAdapter;
import com.badr.infodota.player.adapter.holder.PlayerHolder;
import com.badr.infodota.player.api.Unit;
import com.badr.infodota.player.service.PlayerService;
import com.badr.infodota.player.task.SteamFriendsLoadRequest;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.UncachedSpiceService;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

/**
 * User: ABadretdinov
 * Date: 21.04.14
 * Time: 17:05
 */
public class FriendsList extends UpdatableRecyclerFragment<Unit, PlayerHolder> implements RequestListener<Unit.List> {
    private Unit account;
    private PlayerService playerService = BeanContainer.getInstance().getPlayerService();
    private SpiceManager mSpiceManager = new SpiceManager(UncachedSpiceService.class);

    public static FriendsList newInstance(Unit unit) {
        FriendsList fragment = new FriendsList();
        fragment.setAccount(unit);
        return fragment;
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

    public void setAccount(Unit account) {
        this.account = account;
    }

    @Override
    public void onRefresh() {
        mSpiceManager.execute(new SteamFriendsLoadRequest(account.getAccountId()), this);
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager(Context context) {
        return new GridLayoutManager(context, 1);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setColumnSize();
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

    @Override
    public void onItemClick(View view, int position) {
        Unit unit = getAdapter().getItem(position);

        unit.setSearched(true);
        Activity activity = getActivity();
        playerService.saveAccount(activity, unit);

        Intent intent = new Intent(activity, PlayerInfoActivity.class);
        intent.putExtra("account", unit);
        startActivity(intent);
    }

    @Override
    public void onRequestFailure(SpiceException spiceException) {
        setRefreshing(false);
        Toast.makeText(getActivity(), spiceException.getLocalizedMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestSuccess(Unit.List units) {
        setRefreshing(false);
        Activity activity = getActivity();
        if (units != null && activity != null) {
            PlayersAdapter adapter = new PlayersAdapter(units, true, activity.getResources().getStringArray(R.array.match_history_title));
            setAdapter(adapter);
        }
    }
}
