package com.badr.infodota.quiz.fragment;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ActionMenuView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.badr.infodota.R;
import com.badr.infodota.base.activity.ListHolderActivity;
import com.badr.infodota.quiz.activity.QuizActivity;

/**
 * User: Histler
 * Date: 09.02.14
 */
public class QuizTypeSelect extends Fragment {

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
        return inflater.inflate(R.layout.quiz_type_select, container, false);
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
                startActivity(intent);
            }
        });
        getView().findViewById(R.id.by_skills).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), QuizActivity.class);
                intent.putExtra("mode", QuizActivity.MODE_HEROES);
                intent.putExtra("forRecord", timer.isChecked());
                startActivity(intent);
            }
        });
        getView().findViewById(R.id.by_random).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), QuizActivity.class);
                intent.putExtra("mode", QuizActivity.MODE_NONE);
                intent.putExtra("forRecord", timer.isChecked());
                startActivity(intent);
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
}
