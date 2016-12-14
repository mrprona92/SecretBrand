package com.badr.infodota.hero.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.R;
import com.badr.infodota.base.activity.BaseActivity;
import com.badr.infodota.base.util.FileUtils;
import com.badr.infodota.hero.adapter.pager.HeroPagerAdapter;
import com.badr.infodota.hero.api.Hero;
import com.badr.infodota.hero.service.HeroService;

/**
 * User: ABadretdinov
 * Date: 02.09.13
 * Time: 13:24
 */
public class HeroInfoActivity extends BaseActivity {
    private Hero hero;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem guides = menu.add(1, 1001, 1, R.string.guides);
        MenuItemCompat.setShowAsAction(guides, MenuItemCompat.SHOW_AS_ACTION_ALWAYS | MenuItemCompat.SHOW_AS_ACTION_WITH_TEXT);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case 1001:
                Intent intent = new Intent(this, GuideActivity.class);
                intent.putExtra("id", hero.getId());
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hero_info);

        Bundle intent = getIntent().getExtras();
        if (intent != null && intent.containsKey("id")) {
            HeroService heroService = BeanContainer.getInstance().getHeroService();
            hero = heroService.getHeroWithStatsById(this, intent.getLong("id"));

            final TypedArray styledAttributes = getTheme()
                    .obtainStyledAttributes(new int[]{R.attr.actionBarSize});
            int mActionBarSize = (int) styledAttributes.getDimension(0, 40) / 2;
            styledAttributes.recycle();
            Bitmap icon = FileUtils.getBitmapFromAsset(this, "heroes/" + hero.getDotaId() + "/mini.png");
            if (icon != null) {
                icon = Bitmap.createScaledBitmap(icon, mActionBarSize, mActionBarSize, false);
                Drawable iconDrawable = new BitmapDrawable(getResources(), icon);
                mToolbar.setNavigationIcon(iconDrawable);
            }
            getSupportActionBar().setTitle(hero.getLocalizedName());


            FragmentPagerAdapter adapter = new HeroPagerAdapter(getSupportFragmentManager(), this, hero);

            final ViewPager pager = (ViewPager) findViewById(R.id.pager);
            pager.setAdapter(adapter);
            pager.setOffscreenPageLimit(1);
            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(pager);
        }
    }

    public static class OnDotaHeroClickListener implements View.OnClickListener {
        private long heroId;

        public OnDotaHeroClickListener(long heroId) {
            this.heroId = heroId;
        }

        @Override
        public void onClick(View v) {
            Context context = v.getContext();
            Intent intent = new Intent(context, HeroInfoActivity.class);
            intent.putExtra("id", heroId);
            context.startActivity(intent);
        }
    }

}
