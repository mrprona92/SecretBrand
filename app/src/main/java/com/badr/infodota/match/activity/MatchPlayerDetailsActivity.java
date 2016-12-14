package com.badr.infodota.match.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.R;
import com.badr.infodota.base.activity.BaseActivity;
import com.badr.infodota.base.util.BitmapUtils;
import com.badr.infodota.base.util.SteamUtils;
import com.badr.infodota.hero.activity.HeroInfoActivity;
import com.badr.infodota.hero.api.Hero;
import com.badr.infodota.hero.service.HeroService;
import com.badr.infodota.match.adapter.pager.MatchPlayerPagerAdapter;
import com.badr.infodota.match.api.Player;
import com.badr.infodota.player.activity.PlayerInfoActivity;
import com.badr.infodota.player.api.Unit;
import com.badr.infodota.player.service.PlayerService;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.text.MessageFormat;

/**
 * User: ABadretdinov
 * Date: 22.01.14
 * Time: 17:03
 */
public class MatchPlayerDetailsActivity extends BaseActivity {
    private boolean randomSkills;

    private Player player;
    private Unit account;
    private Menu menu;

    private PlayerService playerService = BeanContainer.getInstance().getPlayerService();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                account.setSearched(true);
                playerService.saveAccount(this, account);
                Intent intent = new Intent(this, PlayerInfoActivity.class);
                intent.putExtra("account", account);
                startActivity(intent);
                return true;
            case R.id.add_btn:
                SteamUtils.addPlayerToListDialog(this, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            account.setGroup(Unit.Groups.FRIEND);
                        } else {
                            account.setGroup(Unit.Groups.PRO);
                        }
                        playerService.saveAccount(MatchPlayerDetailsActivity.this, account);

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
                        playerService.deleteAccount(MatchPlayerDetailsActivity.this, account);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        if (account != null) {
            if (account.getGroup() == Unit.Groups.NONE) {
                menuForUnknown();
            } else {
                menuForSaved();
            }
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ability_upgrade);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey("player")) {
            player = (Player) bundle.get("player");
            randomSkills = bundle.getBoolean("randomSkills", false);

            account = playerService.getAccountById(this, player.getAccountId());
            if (account == null) {
                account = player.getAccount();
            }
            if (account != null) {
                final ActionBar actionBar = getSupportActionBar();
                actionBar.setTitle(account.getName());
                //actionBar.setDisplayShowHomeEnabled(true);
                final TypedArray styledAttributes = getTheme()
                        .obtainStyledAttributes(new int[]{R.attr.actionBarSize});
                Glide.with(this).load(account.getIcon()).into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        int mActionBarSize = (int) styledAttributes.getDimension(0, 40) / 2;
                        styledAttributes.recycle();
                        Bitmap icon = BitmapUtils.getBitmap(resource);
                        if (icon != null) {
                            icon = Bitmap.createScaledBitmap(icon, mActionBarSize, mActionBarSize, false);
                            Drawable iconDrawable = new BitmapDrawable(getResources(), icon);
                            //actionBar.setDisplayShowHomeEnabled(true);
                            //actionBar.setIcon(iconDrawable);
                            mToolbar.setNavigationIcon(iconDrawable);
                            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    account.setSearched(true);
                                    playerService.saveAccount(MatchPlayerDetailsActivity.this, account);
                                    Intent intent = new Intent(MatchPlayerDetailsActivity.this, PlayerInfoActivity.class);
                                    intent.putExtra("account", account);
                                    startActivity(intent);
                                }
                            });
                        }
                    }
                }).onLoadStarted(getDrawable(R.drawable.default_img));
            } else {
                getSupportActionBar().setTitle(getString(R.string.unknown_nickname));
            }
            Integer leaver = player.getLeaverStatus();
            TextView leaverTV = (TextView) findViewById(R.id.leaver);
            if (leaver == null) {
                leaverTV.setText(getString(R.string.bot));
                leaverTV.setVisibility(View.VISIBLE);
            } else switch (leaver) {
                case 1: {
                    leaverTV.setText(getString(R.string.disconnected));
                    leaverTV.setVisibility(View.VISIBLE);
                    break;
                }
                case 2: {
                    leaverTV.setText(getString(R.string.abandoned));
                    leaverTV.setVisibility(View.VISIBLE);
                    break;
                }
                case 3: {
                    leaverTV.setText(getString(R.string.leaved));
                    leaverTV.setVisibility(View.VISIBLE);
                    break;
                }
                case 4: {
                    leaverTV.setText(getString(R.string.afk));
                    leaverTV.setVisibility(View.VISIBLE);
                    break;
                }
                case 5: {
                    leaverTV.setText(getString(R.string.never_connected));
                    leaverTV.setVisibility(View.VISIBLE);
                    break;
                }
                case 6: {
                    leaverTV.setText(getString(R.string.never_connected_too_long));
                    leaverTV.setVisibility(View.VISIBLE);
                    break;
                }
                default: {
                    leaverTV.setVisibility(View.INVISIBLE);
                }
            }
            Long respawnTime = player.getRespawnTimer();
            if (respawnTime != null) {
                findViewById(R.id.alive_status).setVisibility(View.VISIBLE);
                TextView aliveStatus = (TextView) findViewById(R.id.alive);
                if (respawnTime > 0) {
                    aliveStatus.setTextColor(getResources().getColor(R.color.enemy_team));
                    aliveStatus.setText(getString(R.string.dead));
                } else {
                    aliveStatus.setTextColor(getResources().getColor(R.color.ally_team));
                    aliveStatus.setText(getString(R.string.alive));
                }
            }
            Integer ultState = player.getUltState();
            if (ultState != null) {
                findViewById(R.id.ult_status).setVisibility(View.VISIBLE);
                TextView ultStatus = (TextView) findViewById(R.id.ultimate);
                switch (ultState) {
                    case 0:
                        ultStatus.setTextColor(Color.WHITE);
                        ultStatus.setText(getString(R.string.not_levelled));
                        break;
                    case 1:
                        if (player.getUltCooldown() != null) {
                            ultStatus.setTextColor(getResources().getColor(R.color.enemy_team));
                            ultStatus.setText(MessageFormat.format(getString(R.string.on_cooldown), player.getUltCooldown()));
                        }
                        break;
                    case 2:
                        ultStatus.setTextColor(getResources().getColor(R.color.enemy_team));
                        ultStatus.setText(getString(R.string.no_mana));
                        break;
                    case 3:
                        ultStatus.setTextColor(getResources().getColor(R.color.ally_team));
                        ultStatus.setText(getString(R.string.is_ready));
                        break;
                }
            }
            HeroService heroService = BeanContainer.getInstance().getHeroService();
            ((TextView) findViewById(R.id.player_lvl)).setText(getString(R.string.level) + ": " + player.getLevel());
            Hero hero = heroService.getHeroById(MatchPlayerDetailsActivity.this, player.getHeroId());
            ImageView heroImg = (ImageView) findViewById(R.id.hero_img);
            TextView heroName = (TextView) findViewById(R.id.hero_name);
            if (hero != null) {
                Glide.with(this).load(SteamUtils.getHeroFullImage(hero.getDotaId())).into(heroImg).onLoadStarted(getDrawable(R.drawable.default_img));
                heroImg.setOnClickListener(new HeroInfoActivity.OnDotaHeroClickListener(hero.getId()));
                heroName.setText(hero.getLocalizedName());
            } else {
                heroImg.setImageResource(R.drawable.default_img);
                heroName.setText("");
            }
            initPager();
        }
    }

    private void initPager() {
        FragmentPagerAdapter adapter = new MatchPlayerPagerAdapter(getSupportFragmentManager(), this, randomSkills, player);

        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(1);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);
    }
}
