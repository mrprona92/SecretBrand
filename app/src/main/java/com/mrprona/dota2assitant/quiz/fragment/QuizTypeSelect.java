package com.mrprona.dota2assitant.quiz.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.ActionMenuView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.mrprona.dota2assitant.R;
import com.mrprona.dota2assitant.base.activity.ListHolderActivity;
import com.mrprona.dota2assitant.base.configs.ScreenIDs;
import com.mrprona.dota2assitant.base.fragment.SCBaseFragment;
import com.mrprona.dota2assitant.quiz.activity.HighscoreActivity;
import com.mrprona.dota2assitant.quiz.activity.QuizActivity;


/**
 * User: Histler
 * Date: 09.02.14
 */
public class QuizTypeSelect extends SCBaseFragment {


    private AdView mAdView;
    private boolean isCheckTime=false;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        ActionMenuView actionMenuView = ((ListHolderActivity) getActivity()).getActionMenuView();
        Menu actionMenu = actionMenuView.getMenu();
        actionMenu.clear();
        actionMenuView.setVisibility(View.GONE);
    }

    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


         rootView = inflater.inflate(R.layout.quiz_type_select, container,
                false);

        // Gets the ad view defined in layout/ad_fragment.xml with ad unit ID set in
        // values/strings.xml.


        return rootView;
    }


    @Override
    public void onStart() {
        super.onStart();

        mAdView = (AdView) rootView.findViewById(R.id.ad_view);
        // Create an ad request. Check your logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("121EC3F83A2EAFBD46DB00F1773A13A0")
                .build();


        // Start loading the ad in the background.
        mAdView.loadAd(adRequest);

    }

    @Override
    public int getToolbarTitle() {
        return R.string.menu_quiz;
    }

    @Override
    public String getToolbarTitleString() {
        return "QUIZ GAME";
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
        setConfiguration();

        final CheckBox timer = (CheckBox) getView().findViewById(R.id.setTimer);
        timer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isCheckTime=isChecked;
                if (isChecked) {
                    getView().findViewById(R.id.for_record_help).setVisibility(View.VISIBLE);
                } else {
                    getView().findViewById(R.id.for_record_help).setVisibility(View.GONE);
                }
            }
        });
        getView().findViewById(R.id.by_items).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), QuizActivity.class);
                intent.putExtra("mode", QuizActivity.MODE_ITEMS);
                intent.putExtra("forRecord", timer.isChecked());
                //startActivity(intent);
                startActivityForResult(intent, 1);
            }
        });
        getView().findViewById(R.id.by_skills).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), QuizActivity.class);
                intent.putExtra("mode", QuizActivity.MODE_HEROES);
                intent.putExtra("forRecord", timer.isChecked());
                startActivityForResult(intent, 2);
               // startActivity(intent);
            }
        });
        getView().findViewById(R.id.by_random).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), QuizActivity.class);
                intent.putExtra("mode", QuizActivity.MODE_NONE);
                intent.putExtra("forRecord", timer.isChecked());
                startActivityForResult(intent, 3);
                //startActivity(intent);
            }
        });
        getView().findViewById(R.id.btnHighScoreChart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HighscoreActivity.class);
                Bundle sendData= new Bundle();
                intent.putExtras(sendData);
                startActivity(intent);
                //startActivity(intent);
            }
        });

       /* getView().findViewById(R.id.achievements).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), QuizActivity.class);
                intent.putExtra("forAchievements",true);
                startActivity(intent);
            }
        });
        getView().findViewById(R.id.leaderboard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), QuizActivity.class);
                intent.putExtra("forLeaderboards",true);
                startActivity(intent);
            }
        });*/
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setConfiguration();
    }

    private void setConfiguration() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            ((LinearLayout) getView().findViewById(R.id.buttonsHolder)).setOrientation(LinearLayout.HORIZONTAL);
        } else {
            ((LinearLayout) getView().findViewById(R.id.buttonsHolder)).setOrientation(LinearLayout.VERTICAL);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getActivity();
        Log.d("BINH", "onActivityResult() called with: requestCode = [" + requestCode + "], resultCode = [" + resultCode + "], data = [" + data + "]");
        Bundle mBundle = new Bundle();
        if(data==null){
            return;
        }
        mBundle= data.getExtras();
        if (resultCode == Activity.RESULT_OK) {
            //some code
            mActivity.openScreen(ScreenIDs.ScreenTab.QUIZ, GameOverFragment.class, mBundle, false, false);
        }
    }
}
