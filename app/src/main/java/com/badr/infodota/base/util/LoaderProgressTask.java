package com.badr.infodota.base.util;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

/**
 * User: ABadretdinov
 * Date: 06.02.14
 * Time: 13:52
 */
public class LoaderProgressTask<R> extends AsyncTask<Object, String, R> implements ProgressTask.OnPublishProgressListener {
    public static final String USER_CANCELED = "user.canceled";
    private final ProgressTask<R> task;
    private OnProgressUpdateListener listener;
    private String error = null;

    public LoaderProgressTask(ProgressTask<R> task, OnProgressUpdateListener listener) {
        this.task = task;
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (listener != null) {
            listener.onStart();
        }
    }

    @Override
    protected R doInBackground(Object... params) {
        try {
            return task.doTask(this);
        } catch (Exception e) {
            error = e.getLocalizedMessage();
            Log.e(getClass().getName(), "Error in: " + task.getName(), e);
            return null;
        }
    }

    @Override
    protected void onProgressUpdate(String... values) {
        if (listener != null && !isCancelled()) {
            //todo хз как сделать. если OnProgressUpdateListener
            listener.onProgressUpdate(values);
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        task.handleError(USER_CANCELED);
    }

    @Override
    protected void onPostExecute(R r) {
        super.onPostExecute(r);
        if (!isCancelled()) {
            if (r == null) {
                task.handleError(error);
            } else {
                task.doAfterTask(r);
            }
            //todo проверить еще, мб зря вынес
            if (listener != null) {
                listener.onFinish();
            }
        } else {
            task.handleError(USER_CANCELED);
        }
    }

    @Override
    public void progressUpdated(String... progress) {
        //todo если прогресс есть, но <1, то не вызывать, иначе слишком затратно.
        if (!isCancelled()) {
            publishProgress(progress);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB) // API 11
    public final AsyncTask<Object, String, R> execute() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, 1);
        } else {
            return execute(1);
        }
    }


    public interface OnProgressUpdateListener {
        void onStart();

        void onProgressUpdate(String... progress);

        void onFinish();
    }
}
/*public class LoaderProgressTask<R> implements ProgressTask.OnPublishProgressListener {
    private final ProgressTask<R> task;
    private OnProgressUpdateListener listener;
    private String error = null;

    public LoaderProgressTask(ProgressTask<R> task, OnProgressUpdateListener listener) {
        this.task = task;
        this.listener = listener;
    }

    @Override
    public void progressUpdated(String... progress) {
        if (listener != null) {
            listener.onProgressUpdate(progress);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB) // API 11
    public final LoaderProgressTask<R> execute() {
        Observable
                .create(new Observable.OnSubscribe<R>() {
                    @Override
                    public void call(Subscriber<? super R> subscriber) {
                        subscriber.onNext(task.doTask(LoaderProgressTask.this));
                        subscriber.onCompleted();
                    }
                })
                .timeout(10, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<R>() {
                    @Override
                    public void onCompleted() {
                        if (listener != null) {
                            listener.onFinish();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        task.handleError(e.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(R r) {
                        task.doAfterTask(r);
                    }
                });
        return this;
    }


    public interface OnProgressUpdateListener {
        void onStart();

        void onProgressUpdate(String... progress);

        void onFinish();
    }
}
*/