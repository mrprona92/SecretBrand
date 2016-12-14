package com.badr.infodota.base.activity;

import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

import com.badr.infodota.base.R;
import com.badr.infodota.base.util.FileUtils;

/**
 * User: ABadretdinov
 * Date: 29.01.14
 * Time: 16:35
 */
public class AboutActivity extends AppCompatActivity {
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        TextView text = (TextView) findViewById(R.id.text);
        text.setText(Html.fromHtml(FileUtils.getTextFromAsset(this, "about.html"), new DrawableImageGetter(), null));
        text.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public class DrawableImageGetter implements Html.ImageGetter {

        @Override
        public Drawable getDrawable(String source) {
            Drawable icon = null;
            try {
                icon = getPackageManager().getActivityIcon(getComponentName());
                icon.setBounds(0, 0, icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            return icon;
        }
    }
}
