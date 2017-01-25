package com.mrprona.dota2assitant.quiz.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.ActionMenuView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.chartboost.sdk.CBLocation;
import com.chartboost.sdk.Chartboost;
import com.chartboost.sdk.Libraries.CBLogging;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.mrprona.dota2assitant.R;
import com.mrprona.dota2assitant.base.activity.ListHolderActivity;
import com.mrprona.dota2assitant.base.fragment.SCBaseFragment;
import com.mrprona.dota2assitant.quiz.activity.QuizActivity;
import com.mrprona.dota2assitant.quiz.dialog.SubmitHighscoreDialog;


import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * User: Histler
 * Date: 09.02.14
 */
public class GameOverFragment extends SCBaseFragment implements SubmitHighscoreDialog.ConfirmDialogListener {


    @BindView(R.id.btnSubmitScore)
    Button btnSubmitScore;


    @BindView(R.id.btnRetry)
    Button btnRetry;


    @BindView(R.id.btnShareFb)
    Button btnShareFb;


    @BindView(R.id.lblNoticeScore)
    TextView lblScore;

    private int mode;
    private boolean isForRecord;
    private long score;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        ActionMenuView actionMenuView = ((ListHolderActivity) getActivity()).getActionMenuView();
        Menu actionMenu = actionMenuView.getMenu();
        actionMenu.clear();
        actionMenuView.setVisibility(View.GONE);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListenerForButton();
        updateTextScore();

        Chartboost.setActivityCallbacks(false);
        Chartboost.setLoggingLevel(CBLogging.Level.ALL);
        Chartboost.onCreate(mActivity);
        hideSystemUI();

        Chartboost.showInterstitial(CBLocation.LOCATION_GAMEOVER);

        if(score<=0||!isForRecord){
            btnSubmitScore.setEnabled(false);
        }else{
            btnSubmitScore.setEnabled(true);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_quiz_notice_endgame, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public int getToolbarTitle() {
        return R.string.quiz_end_game_title;
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


    private void initListenerForButton(){
        btnSubmitScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle mBundle= new Bundle();
                mBundle.putLong("score",score);
                mBundle.putInt("mode", mode);
                SubmitHighscoreDialog mSubmit = new SubmitHighscoreDialog(getContext(),mBundle,GameOverFragment.this);
                mSubmit.show();
            }
        });
        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), QuizActivity.class);
                intent.putExtra("mode", mode);
                intent.putExtra("forRecord", isForRecord);
                startActivityForResult(intent, 1);
            }
        });

        btnShareFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareLinkContent content = new ShareLinkContent.Builder()
                        .setContentUrl(Uri.parse("https://www.facebook.com/Dota-Assistant-185471691916832/"))
                        .setContentTitle(String.format(getString(R.string.quiz_high_score_notice_share), score+""))
                        .setImageUrl(Uri.parse("http://s.pictub.club/2017/01/13/suKeYm.png"))
                        .build();
                ShareDialog shareDialog = new ShareDialog(getActivity());
                shareDialog.show(content, ShareDialog.Mode.AUTOMATIC);
            }
        });

    }

    protected void updateTextScore() {
        Bundle scoreEndGame= getArguments();
        score= scoreEndGame.getLong("score");
        mode = scoreEndGame.getInt("mode");
        isForRecord= scoreEndGame.getBoolean("forRecord");
        lblScore.setText(String.format(getString(R.string.quiz_high_score_notice_score), score+""));
    }

    protected void updateTextScore2(Bundle mBundle) {
        score= mBundle.getLong("score");
        mode = mBundle.getInt("mode");
        isForRecord= mBundle.getBoolean("forRecord");
        lblScore.setText(String.format(getString(R.string.quiz_high_score_notice_score), score+""));
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
            updateTextScore2(mBundle);
        }
    }

    @Override
    public void onSelect(int indexButton, int mode) {
        if(indexButton==1){
            btnSubmitScore.setEnabled(true);
        }else{
            btnSubmitScore.setEnabled(false);
        }
    }




    @Override
    public void onStart() {
        super.onStart();
        hideSystemUI();
        Chartboost.onStart(mActivity);
    }

    @Override
    public void onPause() {
        super.onPause();
        Chartboost.onPause(mActivity);
    }

    @Override
    public void onResume() {
        super.onResume();
        hideSystemUI();
        Chartboost.onResume(mActivity);
    }

    @Override
    public void onStop() {
        super.onStop();
        Chartboost.onStop(mActivity);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Chartboost.onDestroy(mActivity);
    }



    @TargetApi(Build.VERSION_CODES.KITKAT)
    protected void hideSystemUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mActivity.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                            | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }




}
