package com.badr.infodota.base.service.update;

import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.util.Pair;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.R;
import com.badr.infodota.base.api.Constants;
import com.badr.infodota.base.api.MultiLanguageString;
import com.badr.infodota.base.api.Update;
import com.badr.infodota.base.remote.update.UpdateRemoteService;

import java.util.Locale;

/**
 * Created by Badr on 17.02.2015.
 */
public class UpdateServiceImpl implements UpdateService {

    @Override
    public Pair<Boolean, String> checkUpdate(Context context) {
        BeanContainer container = BeanContainer.getInstance();
        UpdateRemoteService service = container.getUpdateRemoteService();
        try {
            Pair<Update, String> result = service.getUpdate(context);
            if (result.first == null) {
                String message = "Failed to get update, cause: " + result.second;
                Log.e(UpdateServiceImpl.class.getName(), message);
            } else {
                Update update = result.first;
                PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                int versionCode = pInfo.versionCode;
                if (update.getVersionCode() > versionCode) {
                    MultiLanguageString strings = update.getMessage();
                    Locale locale = context.getResources().getConfiguration().locale;
                    String message = strings.get(locale.getLanguage());
                    return Pair.create(true, message != null ? message : strings.get("en-us"));
                } else {
                    return Pair.create(false, null);
                }
            }
            return Pair.create(null, result.second);
        } catch (Exception e) {
            String message = "Failed to get update, cause: " + e.getMessage();
            Log.e(UpdateServiceImpl.class.getName(), message, e);
            return Pair.create(null, message);
        }
    }

    @Override
    public long loadNewVersion(Context context) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(Constants.GITHUB_LAST_APK_URL));
        request.setDescription(context.getString(R.string.new_version));
        request.setTitle("Infodota.apk");

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        request.setMimeType("application/vnd.android.package-archive");
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Infodota.apk");

        // get download service and enqueue file
        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        return manager.enqueue(request);
    }


    @Override
    public void initialize() {

    }
}
