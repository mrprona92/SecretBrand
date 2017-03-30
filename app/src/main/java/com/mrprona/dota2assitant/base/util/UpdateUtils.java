package com.mrprona.dota2assitant.base.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Pair;

import com.mrprona.dota2assitant.BeanContainer;
import com.mrprona.dota2assitant.R;
import com.mrprona.dota2assitant.base.activity.BaseActivity;
import com.mrprona.dota2assitant.base.activity.ListHolderActivity;
import com.mrprona.dota2assitant.base.remote.BaseRemoteServiceImpl;
import com.mrprona.dota2assitant.base.service.update.UpdateService;

/**
 * Created by Badr on 17.02.2015.
 */
public class UpdateUtils {

    private static Context mContext;

    public static void checkNewVersion(final Context context, final boolean userInitiative) {
        mContext = context;
        if (BaseRemoteServiceImpl.isNetworkAvailable(context)) {
            ProgressTask<Pair<Boolean, String>> task = new ProgressTask<Pair<Boolean, String>>() {
                @Override
                public Pair<Boolean, String> doTask(OnPublishProgressListener listener) throws Exception {
                    UpdateService updateService = BeanContainer.getInstance().getUpdateService();
                    return updateService.checkUpdate(context);
                }

                @Override
                public void doAfterTask(Pair<Boolean, String> result) {
                    if (result.first != null) {
                        if (result.first) {
                            DialogUtils.showYesNoDialog(
                                    context,
                                    context.getString(R.string.new_app_available),
                                    result.second,
                                    context.getString(R.string.download),
                                    context.getString(android.R.string.cancel),
                                    new DialogInterface.OnClickListener() {
                                        private long enqueueId;

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                   /* UpdateService updateService=BeanContainer.getInstance().getUpdateService();
                                    BroadcastReceiver receiver = new BroadcastReceiver() {
                                        @Override
                                        public void onReceive(Context context, Intent intent) {
                                            String action = intent.getAction();
                                            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                                                            long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
                                                            DownloadManager.Query query = new DownloadManager.Query();
                                                            query.setFilterById(enqueueId);
                                                            Cursor cursor = ((DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE)).query(query);
                                                            if (cursor.moveToFirst()) {
                                                                if (DownloadManager.STATUS_SUCCESSFUL == cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))) {
                                                                    String uriStr = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                                                                    Intent promptInstall = new Intent(Intent.ACTION_VIEW)
                                                                            .setDataAndType(Uri.parse(uriStr),
                                                                                    "application/vnd.android.package-archive");
                                                                    context.startActivity(promptInstall);
                                                    }
                                                }
                                            }
                                        }
                                    };
                                    context.registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));



                                    if(checkPermission()){
                                        enqueueId = updateService.loadNewVersion(context);
                                    }*/
                                            openGooglePlay();
                                            dialog.dismiss();
                                        }
                                    }, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                        } else if (userInitiative) {
                            DialogUtils.showAlert(context, null, context.getString(R.string.no_new_version), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                        }
                    } else if (userInitiative) {
                        DialogUtils.showAlert(context, null, result.second, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                    }
                }

                @Override
                public void handleError(String error) {

                }

                @Override
                public String getName() {
                    return null;
                }
            };
            if (userInitiative) {
                DialogUtils.showLoaderDialog(((BaseActivity) context).getSupportFragmentManager(), task);
            } else {
                new LoaderProgressTask<Pair<Boolean, String>>(task, null).execute();
            }
        }
    }


    public static void openGooglePlay() {
        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=".concat(mContext.getApplicationContext().getPackageName())));
        mContext.startActivity(myIntent);
    }


    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;

    public static boolean checkPermission() {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) ListHolderActivity.getAppContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    android.support.v7.app.AlertDialog.Builder alertBuilder = new android.support.v7.app.AlertDialog.Builder(mContext);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("To download a file it is necessary to allow required permission");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) mContext, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                        }
                    });
                    android.support.v7.app.AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions((Activity) mContext, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

}
