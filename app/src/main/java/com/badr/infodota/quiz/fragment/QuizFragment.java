package com.badr.infodota.quiz.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.LinearLayout;

import com.badr.infodota.R;

import java.util.Random;

/**
 * User: ABadretdinov
 * Date: 10.02.14
 * Time: 13:20
 */
public abstract class QuizFragment extends Fragment {
    protected OnQuizStateChangeListener listener;
    protected Random idRandom;

    public void setListener(OnQuizStateChangeListener listener) {
        this.listener = listener;
    }

    public void setIdRandom(Random idRandom) {
        this.idRandom = idRandom;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setConfiguration();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setConfiguration();
    }

    private void setConfiguration() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            ((LinearLayout) getView().findViewById(R.id.orientationHolder)).setOrientation(LinearLayout.HORIZONTAL);
        } else {
            ((LinearLayout) getView().findViewById(R.id.orientationHolder)).setOrientation(LinearLayout.VERTICAL);
        }
    }


    protected void wrongAnswerChoose() {
        if (listener != null) {
            listener.onQuizAttemptLoosed();
        }
    }

    protected void answered() {
        if (listener != null) {
            listener.onQuizAnswered();
        }
    }

    public abstract void showLoosed();

    public interface OnQuizStateChangeListener {
        void onQuizAnswered();

        void onQuizAttemptLoosed();

        void onLoosed();
    }
}
