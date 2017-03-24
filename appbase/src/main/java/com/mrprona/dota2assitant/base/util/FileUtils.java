package com.mrprona.dota2assitant.base.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Type;

/**
 * User: ABadretdinov
 * Date: 20.02.14
 * Time: 11:32
 */
public class FileUtils {
    public static File externalFileDir(Context context) {
        File externalFilesDir = context.getExternalFilesDir(null);
        if (externalFilesDir == null || externalFilesDir.getAbsolutePath() == null) {
            externalFilesDir = Environment.getExternalStorageDirectory();
            if (externalFilesDir == null) {
                externalFilesDir = new File(context.getFilesDir().getPath() + context.getPackageName() + "/cache");
            } else {
                externalFilesDir = new File(externalFilesDir, "/Android/data/" + context.getPackageName() + "/cache");
                if (!externalFilesDir.exists()) {
                    externalFilesDir.mkdirs();
                }
            }
        }
        return externalFilesDir;
    }

    public static String[] childrenFileNamesFromAssets(Context context, String path) {
        Resources res = context.getResources();
        AssetManager am = res.getAssets();
        String fileList[] = null;
        try {
            fileList = am.list(path);
        } catch (IOException e) {
            Log.d(context.getClass().getName(), e.getLocalizedMessage());
        }
        return fileList;
    }

    public static void setDrawableFromAsset(ImageView imageView, String strName) {
        Glide.with(imageView.getContext()).load("file:///android_asset/" + strName).into(imageView);
    }

    public static Drawable getDrawableFromAsset(Context context, String strName) {
        AssetManager assetManager = context.getAssets();
        InputStream istr;
        Drawable drawable;
        try {
            istr = assetManager.open(strName);

            Bitmap bitmap = BitmapFactory.decodeStream(istr);
            drawable = new BitmapDrawable(context.getResources(), bitmap);
            drawable.setBounds(0, 0, Utils.dpSize(context, drawable.getIntrinsicWidth()), Utils.dpSize(context, drawable.getIntrinsicWidth()));
        } catch (IOException e) {
            drawable = null;
        }

        return drawable;
    }

    public static String getTextFromFile(String fileName) {
        File file = new File(fileName);
        StringBuilder text = new StringBuilder();
        if (file.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                while ((line = br.readLine()) != null) {
                    text.append(line);
                    text.append('\n');
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return text.toString();
    }

    public static String getTextFromAsset(Context context, String fileName) {
        AssetManager assetManager = context.getAssets();
        String text;
        InputStream inputStream;
        try {
            inputStream = assetManager.open(fileName);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int i;
            i = inputStream.read();
            while (i != -1) {
                byteArrayOutputStream.write(i);
                i = inputStream.read();
            }
            inputStream.close();
            text = byteArrayOutputStream.toString();
        } catch (IOException e) {
            e.printStackTrace();
            text = null;
        }

        return text;
    }


    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;

    public static int isHavePermisson(Context mContext,String permisson) { // make a folder under Environment.DIRECTORY_DCIM
        int returnValue = 0;
        String state = Environment.getExternalStorageState();
        if (!Environment.MEDIA_MOUNTED.equals(state)) {
            Log.d("myAppName", "Error: external storage is unavailable");
            return 0;
        }
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            Log.d("myAppName", "Error: external storage is read only.");
            return 0;
        }
        Log.d("myAppName", "External storage is not read only or unavailable");

        if (ContextCompat.checkSelfPermission(mContext, // request permission when it is not granted.
                permisson)
                != PackageManager.PERMISSION_GRANTED) {
            Log.d("myAppName", "permission:WRITE_EXTERNAL_STORAGE: NOT granted!");
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext,
                   permisson)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions((Activity) mContext,
                        new String[]{permisson},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            returnValue = 1;
        }
        return returnValue;
    }

    public static boolean saveFile(Context mContext, String fileName, InputStream input) {
        if (FileUtils.isHavePermisson(mContext,Manifest.permission.WRITE_EXTERNAL_STORAGE) == 1) {
            File parent = new File(fileName).getParentFile();
            if (!parent.exists()) {
                parent.mkdirs();
            }
            try {
                OutputStream output = new FileOutputStream(fileName);
                byte data[] = new byte[1024];
                int count;

                while ((count = input.read(data)) != -1) {
                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        } else {
            return false;
        }
    }

    public static <T> boolean saveJsonFile(String fileName, T object) {
        try {
            File parent = new File(fileName).getParentFile();
            if (!parent.exists()) {
                parent.mkdirs();
            }
            Writer writer = new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8");
            Gson gson = new GsonBuilder().create();
            gson.toJson(object, writer);
            writer.flush();
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void saveStringFile(String fileName, String data) {
        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/dota/" + fileName);
        file.getParentFile().mkdirs();
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(data);
            myOutWriter.close();
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Bitmap getBitmapFromAsset(Context context, String strName) {
        AssetManager assetManager = context.getAssets();
        InputStream istr;
        Bitmap bitmap;
        try {
            istr = assetManager.open(strName);
            bitmap = BitmapFactory.decodeStream(istr);
        } catch (IOException e) {
            bitmap = null;
        }

        return bitmap;
    }

    public static <T> T entityFromFile(String path, Type resultType) throws Exception {
        File file = new File(path);
        FileInputStream fileInputStream = new FileInputStream(file);
        JsonReader reader = new JsonReader(new InputStreamReader(fileInputStream, "UTF-8"));
        T pricesResult;
        try {
            pricesResult = new Gson().fromJson(reader, resultType);
        } finally {
            reader.close();
        }
        return pricesResult;
    }
}
