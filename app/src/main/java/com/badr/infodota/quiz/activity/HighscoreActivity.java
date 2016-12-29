package com.badr.infodota.quiz.activity;

import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.badr.infodota.R;
import com.badr.infodota.base.activity.BaseActivity;
import com.badr.infodota.base.util.FileUtils;
import com.badr.infodota.quiz.adapter.HighscorePagerAdapter;

/**
 * User: ABadretdinov
 * Date: 02.09.13
 * Time: 13:24
 */
public class HighscoreActivity extends BaseActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
          /*  case 1001:
                Intent intent = new Intent(this, GuideActivity.class);
                intent.putExtra("id", hero.getId());
                startActivity(intent);*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hero_info);

            final TypedArray styledAttributes = getTheme()
                    .obtainStyledAttributes(new int[]{R.attr.actionBarSize});
            int mActionBarSize = (int) styledAttributes.getDimension(0, 40) / 2;
            styledAttributes.recycle();
            Bitmap icon = FileUtils.getBitmapFromAsset(this, "quiz" + "/highscore.png");
            if (icon != null) {
                icon = Bitmap.createScaledBitmap(icon, mActionBarSize, mActionBarSize, false);
                Drawable iconDrawable = new BitmapDrawable(getResources(), icon);
                mToolbar.setNavigationIcon(iconDrawable);
            }

            getSupportActionBar().setTitle(getString(R.string.quiz_high_score));

            FragmentPagerAdapter adapter = new HighscorePagerAdapter(getSupportFragmentManager(), this);

            final ViewPager pager = (ViewPager) findViewById(R.id.pager);
            pager.setAdapter(adapter);
            pager.setOffscreenPageLimit(1);
            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(pager);
    }


}
