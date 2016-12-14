package com.badr.infodota.base.util.web;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.text.Html;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * User: Histler
 * Date: 21.04.14
 */
public class URLImageParser implements Html.ImageGetter {
    private Context mContext;
    private TextView mContainer;

    /**
     * Construct the URLImageParser which will execute AsyncTask and refresh the container
     *
     * @param holder
     * @param context
     */
    public URLImageParser(TextView holder, Context context) {
        this.mContext = context;
        this.mContainer = holder;
    }

    public Drawable getDrawable(String source) {
        URLDrawable urlDrawable = new URLDrawable();

        // get the actual source
        ImageGetterAsyncTask asyncTask =
                new ImageGetterAsyncTask(urlDrawable);

        asyncTask.execute(source);
        //Glide.with(mContext).load(source).into()

        // return reference to URLDrawable where I will change with actual image from
        // the src tag
        return urlDrawable;
    }

    public class ImageGetterAsyncTask extends AsyncTask<String, Void, Drawable> {
        URLDrawable urlDrawable;

        public ImageGetterAsyncTask(URLDrawable d) {
            this.urlDrawable = d;
        }

        @Override
        protected Drawable doInBackground(String... params) {
            String source = params[0];
            return fetchDrawable(source);
        }

        @Override
        protected void onPostExecute(Drawable result) {
            // set the correct bound according to the result from HTTP call
            urlDrawable.setBounds(result.getBounds());

            // change the reference of the current drawable to the result
            // from the HTTP call
            urlDrawable.drawable = result;

            // redraw the image by invalidating the container
            URLImageParser.this.mContainer.invalidate();

            // For ICS
            URLImageParser.this.mContainer.setHeight((mContainer.getHeight()
                    + result.getBounds().bottom));

            // Pre ICS
            URLImageParser.this.mContainer.setEllipsize(null);
        }

        /**
         * Get the Drawable from URL
         *
         * @param urlString
         * @return
         */
        @SuppressWarnings("deprecation")
        public Drawable fetchDrawable(String urlString) {
            try {
                InputStream is = fetch(urlString);
                Drawable drawable = Drawable.createFromStream(is, "src");

                WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
                Display display = wm.getDefaultDisplay();
                int width;
                int height;
                if (Build.VERSION.SDK_INT >= 13) {
                    Point size = new Point();
                    display.getSize(size);
                    height = size.y;
                    width = size.x;
                } else {
                    width = display.getWidth();
                    height = display.getHeight();
                }
                int scale = Math.min(width, height);
                drawable.setBounds(0, 0, scale, drawable.getIntrinsicWidth() != 0 ? drawable.getIntrinsicHeight() * scale / drawable.getIntrinsicWidth() : drawable.getIntrinsicHeight());
                return drawable;
            } catch (Exception e) {
                return null;
            }
        }

        private InputStream fetch(String urlString) throws IOException {
            URL url = new URL(urlString);
            URLConnection urlConnection = url.openConnection();
            /*DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet request = new HttpGet(urlString);
            HttpResponse response = httpClient.execute(request);*/
            return urlConnection.getInputStream()/*response.getEntity().getContent()*/;
        }
    }
}
