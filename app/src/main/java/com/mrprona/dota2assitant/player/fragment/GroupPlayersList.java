package com.mrprona.dota2assitant.player.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Toast;

import com.mrprona.dota2assitant.BeanContainer;
import com.mrprona.dota2assitant.R;
import com.mrprona.dota2assitant.base.fragment.UpdatableRecyclerFragment;
import com.mrprona.dota2assitant.player.activity.PlayerInfoActivity;
import com.mrprona.dota2assitant.player.adapter.PlayersAdapter;
import com.mrprona.dota2assitant.player.adapter.holder.PlayerHolder;
import com.mrprona.dota2assitant.player.api.Unit;
import com.mrprona.dota2assitant.player.service.PlayerService;
import com.mrprona.dota2assitant.player.task.PlayersGroupLoadRequest;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.UncachedSpiceService;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

/**
 * User: Histler
 * Date: 04.02.14
 */
public class GroupPlayersList extends UpdatableRecyclerFragment<Unit, PlayerHolder> implements TextWatcher, RequestListener<Unit.List> {

    private EditText search;
    private String query = null;
    private Filter filter;
    private SpiceManager mSpiceManager = new SpiceManager(UncachedSpiceService.class);

    public static GroupPlayersList newInstance(Unit.Groups group) {
        GroupPlayersList fragment = new GroupPlayersList();
        Bundle bundle = new Bundle();
        bundle.putSerializable("group", group);
        fragment.setArguments(bundle);
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setLayoutId(R.layout.group_players_list);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public int getToolbarTitle() {
        return 0;
    }

    @Override
    public String getToolbarTitleString() {
        return null;
    }

    @Override
    protected int getViewContent() {
        return 0;
    }

    @Override
    protected void initUI() {

    }

    @Override
    protected void initControls() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void hideInformation() {

    }

    @Override
    protected void registerListeners() {

    }

    @Override
    protected void unregisterListener() {

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
            search = (EditText) root.findViewById(R.id.search);

            search.addTextChangedListener(this);
            root.findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    search.setText("");
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

    @Override
    public void onItemClick(View view, int position) {
        Unit unit = mAdapter.getItem(position);
        unit.setSearched(true);
        PlayerService playerService = BeanContainer.getInstance().getPlayerService();
        playerService.saveAccount(getActivity(), unit);

        Intent intent = new Intent(getActivity(), PlayerInfoActivity.class);
        intent.putExtra("account", unit);
        startActivity(intent);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        query = s.toString();
        if (filter != null) {
            filter.filter(query);
        } else {
            onRefresh();
        }
    }

    @Override
    public void onRefresh() {
        Activity activity = getActivity();
        if (activity != null) {
            setRefreshing(true);
            mSpiceManager.execute(new PlayersGroupLoadRequest(activity.getApplicationContext(), (Unit.Groups) getArguments().getSerializable("group")), this);
        }
    }

    @Override
    public void onRequestFailure(SpiceException spiceException) {
        setRefreshing(false);
        Toast.makeText(getActivity(), spiceException.getLocalizedMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestSuccess(Unit.List units) {
        setRefreshing(false);
        PlayersAdapter adapter = new PlayersAdapter(units, false, getResources().getStringArray(R.array.match_history_title));
        filter = adapter.getFilter();
        filter.filter(query);
        setAdapter(adapter);
    }

}
