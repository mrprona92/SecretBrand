package com.badr.infodota.trackdota.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import com.badr.infodota.R;
import com.badr.infodota.base.activity.ListHolderActivity;
import com.badr.infodota.base.util.Refresher;
import com.badr.infodota.trackdota.activity.TrackdotaGameInfoActivity;
import com.badr.infodota.trackdota.adapter.pager.TrackdotaPagerAdapter;
import com.badr.infodota.trackdota.api.game.GamesResult;
import com.badr.infodota.trackdota.task.GamesResultLoadRequest;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.UncachedSpiceService;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

/**
 * Created by ABadretdinov
 * 14.04.2015
 * 11:28
 */
public class TrackdotaMain extends Fragment implements RequestListener<GamesResult>, Refresher {
    public static final int SEARCH_MATCH = 322;
    private static final long DELAY_20_SEC = 20000;
    private SpiceManager mSpiceManager = new SpiceManager(UncachedSpiceService.class);
    private TrackdotaPagerAdapter adapter;
    private View progressBar;
    private Handler updateHandler = new Handler();
    private Runnable updateTask;

    @Override
    public void onStart() {
        if (!mSpiceManager.isStarted()) {
            mSpiceManager.start(getActivity());
            onRefresh();
        } else {
            startDelayedUpdate();
        }
        super.onStart();
    }

    @Override
    public void onStop() {
        cancelDelayedUpdate();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        if (mSpiceManager.isStarted()) {
            mSpiceManager.shouldStop();
        }
        super.onDestroy();
    }

    private void cancelDelayedUpdate() {
        if (updateTask != null) {
            updateHandler.removeCallbacks(updateTask);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.trackdota, container, false);
        progressBar = view.findViewById(R.id.progressBar);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        ((ListHolderActivity) getActivity()).getActionMenuView().setVisibility(View.GONE);
        MenuItem search = menu.add(1, SEARCH_MATCH, 0, getString(R.string.search_match));
        search.setIcon(R.drawable.search);
        MenuItemCompat.setShowAsAction(search, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == SEARCH_MATCH) {
            Activity activity = getActivity();
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            final EditText textView = new EditText(activity);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            textView.setLayoutParams(lp);
            textView.setHint(R.string.match_id);
            textView.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
            builder.setTitle(R.string.search_match);
            builder.setView(textView);
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setPositiveButton(R.string.search, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String matchId = textView.getText().toString();
                    if (!TextUtils.isEmpty(matchId)) {
                        Intent intent = new Intent(getActivity(), TrackdotaGameInfoActivity.class);
                        intent.putExtra("id", Long.valueOf(matchId));
                        startActivity(intent);
                    }
                    dialog.dismiss();
                }
            });
            builder.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        initPager();
    }

    private void initPager() {
        View root = getView();
        Activity activity = getActivity();
        if (activity != null && root != null) {
            adapter = new TrackdotaPagerAdapter(activity, getChildFragmentManager(), this);

            ViewPager pager = (ViewPager) root.findViewById(R.id.pager);
            pager.setAdapter(adapter);
            pager.setOffscreenPageLimit(3);

            TabLayout tabLayout = (TabLayout) root.findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(pager);
        }
    }

    @Override
    public void onRefresh() {
        cancelDelayedUpdate();
        progressBar.setVisibility(View.VISIBLE);
        mSpiceManager.execute(new GamesResultLoadRequest(), this);
    }

    @Override
    public void onRequestFailure(SpiceException spiceException) {
        progressBar.setVisibility(View.GONE);
        adapter.update(null);
        Toast.makeText(getActivity(), spiceException.getLocalizedMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestSuccess(GamesResult gamesResult) {
        progressBar.setVisibility(View.GONE);
        adapter.update(gamesResult);
        if (gamesResult != null && gamesResult.getApiDowntime() > 0) {
            Toast.makeText(progressBar.getContext(), R.string.api_is_down, Toast.LENGTH_LONG).show();
        }
        startDelayedUpdate();
    }

    private void startDelayedUpdate() {
        cancelDelayedUpdate();
        updateTask = new Runnable() {
            @Override
            public void run() {
                onRefresh();
            }
        };
        updateHandler.postDelayed(updateTask, DELAY_20_SEC);
    }


}
