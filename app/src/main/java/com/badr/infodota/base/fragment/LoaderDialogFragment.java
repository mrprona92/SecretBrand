package com.badr.infodota.base.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.badr.infodota.R;
import com.badr.infodota.base.util.LoaderProgressTask;
import com.badr.infodota.base.util.ProgressTask;

/**
 * User: ABadretdinov
 * Date: 06.02.14
 * Time: 15:14
 */
public class LoaderDialogFragment<T> extends DialogFragment {

    private ProgressTask<T> task;
    private String loadingMessage;
    private ProgressBar progress;
    private TextView progressStatus;

    public void setLoadingMessage(String loadingMessage) {
        this.loadingMessage = loadingMessage;
    }

    public void setTask(ProgressTask<T> task) {
        this.task = task;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.loading_dialog, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, 0);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ViewGroup root = (ViewGroup) getView();
        progress = (ProgressBar) root.findViewById(R.id.progress);
        progress.setIndeterminate(true);//false - horizontal
        progressStatus = (TextView) root.findViewById(R.id.progress_status);
        new LoaderProgressTask<T>(task, new LoaderProgressTask.OnProgressUpdateListener() {
            @Override
            public void onStart() {
                FragmentActivity activity = getActivity();
                if (activity != null) {
                    if (TextUtils.isEmpty(loadingMessage)) {
                        progressStatus.setText(getString(R.string.loading));
                    } else {
                        progressStatus.setText(loadingMessage);
                    }
                    progress.setMax(100);
                    progress.setProgress(0);
                }
            }

            @Override
            public void onProgressUpdate(String... p) {
                FragmentActivity activity = getActivity();
                if (activity != null) {
                    progressStatus.setText(p[0]);
                    if (p.length > 1) {
                        progress.setProgress(Integer.valueOf(p[1]));
                    }
                }
            }

            @Override
            public void onFinish() {
                FragmentActivity activity = getActivity();
                if (activity != null) {
                    progress.setProgress(100);
                    progressStatus.setText(getString(R.string.loading_complete));
                }

                if (isVisible() && !isRemoving()) {
                    try {
                        dismissAllowingStateLoss();
                    } catch (Exception e) {
                        //  java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState
                        Log.e(getClass().getName(), e.getMessage(), e);
                    }
                }
            }
        }).execute();
    }
}
