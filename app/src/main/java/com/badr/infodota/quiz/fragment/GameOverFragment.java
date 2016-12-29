package com.badr.infodota.quiz.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
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

import com.badr.infodota.R;
import com.badr.infodota.base.activity.ListHolderActivity;
import com.badr.infodota.base.configs.ScreenIDs;
import com.badr.infodota.base.fragment.SCBaseFragment;
import com.badr.infodota.quiz.activity.QuizActivity;
import com.badr.infodota.quiz.dialog.SubmitHighscoreDialog;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * User: Histler
 * Date: 09.02.14
 */
public class GameOverFragment extends SCBaseFragment {


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
                SubmitHighscoreDialog mSubmit = new SubmitHighscoreDialog(getContext(),mBundle);
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
        mBundle= data.getExtras();
        if (resultCode == Activity.RESULT_OK) {
            //some code
            updateTextScore2(mBundle);
        }
    }

}
