package com.badr.infodota.player.activity;

import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;

import com.badr.infodota.R;
import com.badr.infodota.base.activity.BaseActivity;
import com.badr.infodota.base.util.BitmapUtils;
import com.badr.infodota.player.adapter.pager.PlayerCommonStatsPagerAdapter;
import com.badr.infodota.player.api.Unit;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

/**
 * User: ABadretdinov
 * Date: 27.03.14
 * Time: 18:05
 */
public class PlayerCommonStatsActivity extends BaseActivity {
    FragmentPagerAdapter adapter;
    private Unit account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_stats_result);

        Bundle bundle = getIntent().getExtras();
        account = (Unit) bundle.get("account");
        initPager(bundle.getBundle("args"));
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null && account != null) {
            actionBar.setTitle(account.getName());
            Glide.with(this).load(account.getIcon()).into(new SimpleTarget<GlideDrawable>() {
                @Override
                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                    final TypedArray styledAttributes = getTheme().obtainStyledAttributes(new int[]{R.attr.actionBarSize});
                    int mActionBarSize = (int) styledAttributes.getDimension(0, 40) / 2;
                    styledAttributes.recycle();
                    Bitmap icon = BitmapUtils.getBitmap(resource);
                    if (icon != null) {
                        icon = Bitmap.createScaledBitmap(icon, mActionBarSize, mActionBarSize, false);
                        Drawable iconDrawable = new BitmapDrawable(getResources(), icon);
                        //actionBar.setDisplayShowHomeEnabled(true);
                        //actionBar.setIcon(iconDrawable);
                        mToolbar.setNavigationIcon(iconDrawable);
                    }

                }
            });
        }
    }

    private void initPager(Bundle args) {
        adapter = new PlayerCommonStatsPagerAdapter(getSupportFragmentManager(), this, args, account);

        final ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(1);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);
    }
}
