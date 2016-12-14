package com.badr.infodota.player.activity;

import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.R;
import com.badr.infodota.base.activity.BaseActivity;
import com.badr.infodota.base.util.BitmapUtils;
import com.badr.infodota.base.util.SteamUtils;
import com.badr.infodota.player.adapter.pager.PlayerGroupsPagerAdapter;
import com.badr.infodota.player.adapter.pager.PlayerInfoPagerAdapter;
import com.badr.infodota.player.api.Unit;
import com.badr.infodota.player.service.PlayerService;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

/**
 * User: Histler
 * Date: 21.01.14
 */
public class PlayerInfoActivity extends BaseActivity {
    PlayerService playerService = BeanContainer.getInstance().getPlayerService();
    private Unit account;
    private Menu menu;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        if (account.getGroup() == Unit.Groups.NONE) {
            menuForUnknown();
        } else {
            menuForSaved();
        }
        return super.onCreateOptionsMenu(menu);
    }

    private void menuForUnknown() {
        MenuItem add = menu.add(0, R.id.add_btn, 1, R.string.add_player_title);
        add.setIcon(R.drawable.ic_menu_add);
        MenuItemCompat.setShowAsAction(add, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
    }

    private void menuForSaved() {
        MenuItem group = menu.add(0, R.id.group_id, 1, getResources().getStringArray(R.array.match_history_title)[account.getGroup().ordinal()]);
        MenuItemCompat.setShowAsAction(group, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
        MenuItem delete = menu.add(0, R.id.delete_btn, 2, R.string.delete_player_title);
        delete.setIcon(R.drawable.ic_menu_delete);
        MenuItemCompat.setShowAsAction(delete, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_btn:
                SteamUtils.addPlayerToListDialog(this, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            account.setGroup(Unit.Groups.FRIEND);
                        } else {
                            account.setGroup(Unit.Groups.PRO);
                        }
                        playerService.saveAccount(PlayerInfoActivity.this, account);
                        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
                        if (viewPager != null && viewPager.getAdapter() instanceof PlayerGroupsPagerAdapter) {
                            PlayerGroupsPagerAdapter adapter = (PlayerGroupsPagerAdapter) viewPager.getAdapter();
                            adapter.update();
                        }
                        menu.removeItem(R.id.add_btn);
                        menuForSaved();
                        dialog.dismiss();
                    }
                });
                return true;
            case R.id.delete_btn:
                SteamUtils.deletePlayerFromListDialog(this, account, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        playerService.deleteAccount(PlayerInfoActivity.this, account);
                        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
                        if (viewPager != null && viewPager.getAdapter() instanceof PlayerGroupsPagerAdapter) {
                            PlayerGroupsPagerAdapter adapter = (PlayerGroupsPagerAdapter) viewPager.getAdapter();
                            adapter.update();
                        }
                        menu.removeItem(R.id.delete_btn);
                        menu.removeItem(R.id.group_id);
                        menuForUnknown();
                        dialog.dismiss();
                    }
                });

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_info);

        Bundle bundle = getIntent().getExtras();
        if (bundle.containsKey("account")) {
            account = (Unit) bundle.get("account");
            final ActionBar actionBar = getSupportActionBar();
            if (actionBar != null && account != null) {
                actionBar.setTitle(account.getName());
                Glide.with(this).load(account.getIcon()).into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        final TypedArray styledAttributes = getTheme()
                                .obtainStyledAttributes(new int[]{R.attr.actionBarSize});
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
            FragmentPagerAdapter adapter = new PlayerInfoPagerAdapter(this, getSupportFragmentManager(), account);
            final ViewPager pager = (ViewPager) findViewById(R.id.pager);
            pager.setAdapter(adapter);
            pager.setOffscreenPageLimit(3);
            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(pager);
            pager.setCurrentItem(2);
        }

    }
}
