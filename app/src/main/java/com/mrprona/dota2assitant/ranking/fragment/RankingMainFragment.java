package com.mrprona.dota2assitant.ranking.fragment;

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

import com.mrprona.dota2assitant.R;
import com.mrprona.dota2assitant.base.activity.ListHolderActivity;
import com.mrprona.dota2assitant.base.fragment.SCBaseFragment;
import com.mrprona.dota2assitant.base.util.Refresher;
import com.mrprona.dota2assitant.ranking.adapter.pager.RankingPagerAdapter;
import com.mrprona.dota2assitant.trackdota.activity.TrackdotaGameInfoActivity;
import com.mrprona.dota2assitant.trackdota.adapter.pager.TrackdotaPagerAdapter;
import com.mrprona.dota2assitant.trackdota.api.game.GamesResult;
import com.mrprona.dota2assitant.trackdota.task.GamesResultLoadRequest;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.UncachedSpiceService;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

/**
 * Created by ABadretdinov
 * 14.04.2015
 * 11:28
 */
public class RankingMainFragment extends SCBaseFragment {
    private RankingPagerAdapter adapter;
    private View progressBar;
    private Handler updateHandler = new Handler();
    private Runnable updateTask;
    public static final int SEARCH_TEAM = 400;

    @Override
    public void onStart() {

        super.onStart();
    }

    @Override
    public void onStop() {
        cancelDelayedUpdate();
        super.onStop();
    }

    @Override
    public void onDestroy() {

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
    public int getToolbarTitle() {
        return R.string.ranking_title;
    }

    @Override
    public String getToolbarTitleString() {
        return "RANKINGS";
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
       /* menu.clear();
        ((ListHolderActivity) getActivity()).getActionMenuView().setVisibility(View.GONE);
        MenuItem search = menu.add(1, SEARCH_TEAM, 0, getString(R.string.search_match));
        search.setIcon(R.drawable.search);
        MenuItemCompat.setShowAsAction(search, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      /*  if (item.getItemId() == SEARCH_MATCH) {
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
        }*/
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        initPager();
    }

    ViewPager pager;

    private void initPager() {
        View root = getView();
        Activity activity = getActivity();
        if (activity != null && root != null) {
            adapter = new RankingPagerAdapter(activity, getChildFragmentManager());

            pager = (ViewPager) root.findViewById(R.id.pager);
            pager.setAdapter(adapter);
            pager.setOffscreenPageLimit(1);

            TabLayout tabLayout = (TabLayout) root.findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(pager);
        }
    }

    public Fragment getActiveFragment() {
        int index = pager.getCurrentItem();
        adapter = ((RankingPagerAdapter) pager.getAdapter());
        Fragment fragment = adapter.getItem(index);
        return fragment;
    }

    private static String makeFragmentName(int viewId, int index) {
        return "android:switcher:" + viewId + ":" + index;
    }

}
