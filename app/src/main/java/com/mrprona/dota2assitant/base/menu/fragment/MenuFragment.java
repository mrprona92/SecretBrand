package com.mrprona.dota2assitant.base.menu.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.mrprona.dota2assitant.R;
import com.mrprona.dota2assitant.base.activity.ListHolderActivity;
import com.mrprona.dota2assitant.base.fragment.SCBaseFragment;
import com.mrprona.dota2assitant.base.menu.adapter.DHMenuAdapter;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * User: Histler
 * Date: 09.02.14
 */
public class MenuFragment extends SCBaseFragment implements DHMenuAdapter.OnItemClickListener{

    private static WeakReference<ListHolderActivity> mActivityRef;

    public static void updateActivity(ListHolderActivity activity) {
        mActivityRef = new WeakReference<>(activity);
    }


    @BindView(R.id.recyclerMenu)
    RecyclerView mRecyclerMenu;

    protected View mView;
    protected Unbinder mUnbinder;

    private AdView mAdView;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        ActionMenuView actionMenuView = ((ListHolderActivity) getActivity()).getActionMenuView();
        Menu actionMenu = actionMenuView.getMenu();
        actionMenu.clear();
        actionMenuView.setVisibility(View.GONE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_menu, container, false);
            mUnbinder = ButterKnife.bind(this, mView);
        }
        DHMenuAdapter adapterMenu = new DHMenuAdapter(getContext());
        mRecyclerMenu.setAdapter(adapterMenu);
        adapterMenu.setOnItemClickListener(this);
        mRecyclerMenu.setHasFixedSize(false);
        mRecyclerMenu.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerMenu.setNestedScrollingEnabled(false);


        //TODO binh.th commment block admod
       /*  // Gets the ad view defined in layout/ad_fragment.xml with ad unit ID set in
        // values/strings.xml.
        mAdView = (AdView) mView.findViewById(R.id.ad_view);
        // Create an ad request. Check your logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("121EC3F83A2EAFBD46DB00F1773A13A0")
                .build();

        // Start loading the ad in the background.
        mAdView.loadAd(adRequest);*/

        return mView;
    }

    @Override
    public int getToolbarTitle() {
        return R.string.menu_menuname;
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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }



    @Override
    public void onClick(View view, int position) {
        mActivityRef.get().onChangeFragment(position);
    }

}
