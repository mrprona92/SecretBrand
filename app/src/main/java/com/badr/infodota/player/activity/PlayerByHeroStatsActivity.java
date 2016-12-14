package com.badr.infodota.player.activity;

import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.badr.infodota.R;
import com.badr.infodota.base.activity.BaseActivity;
import com.badr.infodota.base.util.BitmapUtils;
import com.badr.infodota.base.util.ResourceUtils;
import com.badr.infodota.base.util.SteamUtils;
import com.badr.infodota.base.view.HorizontalScrollViewListener;
import com.badr.infodota.base.view.ObservableHorizontalScrollView;
import com.badr.infodota.hero.activity.HeroInfoActivity;
import com.badr.infodota.hero.api.Hero;
import com.badr.infodota.player.api.Unit;
import com.badr.infodota.player.task.PlayerHeroesStatsLoadRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.UncachedSpiceService;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * User: ABadretdinov
 * Date: 19.02.14
 * Time: 17:11
 */
public class PlayerByHeroStatsActivity extends BaseActivity implements HorizontalScrollViewListener, RequestListener<PlayerByHeroStatsActivity.PlayerHeroesStats> {
    private ObservableHorizontalScrollView obs1;
    private ObservableHorizontalScrollView obs2;
    private LinearLayout content;
    private LinearLayout verticalHeader;
    private LinearLayout horizontalHeader;
    private View contentHolder;
    private View progressBarHolder;
    private SpiceManager mSpiceManager = new SpiceManager(UncachedSpiceService.class);
    private Unit account;

    @Override
    protected void onStart() {
        super.onStart();
        if (!mSpiceManager.isStarted()) {
            mSpiceManager.start(this);
            loadPlayerHeroesStats();

        }
    }

    private void loadPlayerHeroesStats() {
        Bundle bundle = getIntent().getExtras();
        StringBuilder urlBuilder = new StringBuilder("http://dotabuff.com/players/");
        urlBuilder.append(account.getAccountId());
        urlBuilder.append("/heroes?");
        urlBuilder.append("date=");
        urlBuilder.append(bundle.getString("date"));
        urlBuilder.append("&game_mode=");
        urlBuilder.append(bundle.getString("game_mode"));
        urlBuilder.append("&match_type=");
        urlBuilder.append(bundle.getString("match_type"));
        urlBuilder.append("&metric=");
        urlBuilder.append(bundle.getString("metric"));

        mSpiceManager.execute(new PlayerHeroesStatsLoadRequest(getApplicationContext(), urlBuilder.toString()), this);
    }

    @Override
    protected void onDestroy() {
        if (mSpiceManager.isStarted()) {
            mSpiceManager.shouldStop();
        }
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_by_hero_stats);

        contentHolder = findViewById(R.id.content_holder);
        progressBarHolder = findViewById(R.id.progressBarHolder);

        obs1 = (ObservableHorizontalScrollView) findViewById(R.id.observable1);
        obs2 = (ObservableHorizontalScrollView) findViewById(R.id.observable2);
        obs1.setScrollViewListener(this);
        obs2.setScrollViewListener(this);

        content = (LinearLayout) findViewById(R.id.content);
        horizontalHeader = (LinearLayout) findViewById(R.id.horizontal_header_holder);
        verticalHeader = (LinearLayout) findViewById(R.id.vertical_header_holder);


        Bundle bundle = getIntent().getExtras();
        account = (Unit) bundle.get("account");
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

        horizontalHeader.removeAllViews();
        verticalHeader.removeAllViews();
        content.removeAllViews();
    }

    @Override
    public void onScrollChanged(ObservableHorizontalScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (scrollView == obs1) {
            obs2.scrollTo(x, y);
        } else if (scrollView == obs2) {
            obs1.scrollTo(x, y);
        }
    }

    @Override
    public void onRequestFailure(SpiceException spiceException) {
        spiceException.printStackTrace();
        progressBarHolder.setVisibility(View.GONE);
        Toast.makeText(this, spiceException.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestSuccess(PlayerHeroesStats playerHeroesStats) {
        progressBarHolder.setVisibility(View.GONE);
        contentHolder.setVisibility(View.VISIBLE);
        if (playerHeroesStats != null) {
            LayoutInflater inflater = getLayoutInflater();
            if (playerHeroesStats.horizontalHeaders != null) {
                for (String header : playerHeroesStats.horizontalHeaders) {
                    int headerId = ResourceUtils.getByHeroStatsHeaders(header);
                    String realHeader;
                    if (headerId != 0) {
                        realHeader = getString(headerId);
                    } else {
                        realHeader = header;
                    }
                    View v = inflater.inflate(R.layout.player_by_hero_stats_header, horizontalHeader, false);
                    ((TextView) v.findViewById(android.R.id.text1)).setText(realHeader);
                    horizontalHeader.addView(v);
                }
            }
            if (playerHeroesStats.heroResults != null) {
                Set<Hero> heroes = playerHeroesStats.heroResults.keySet();
                for (Hero hero : heroes) {
                    List<String> results = playerHeroesStats.heroResults.get(hero);
                    View verticalHeaderRow = inflater.inflate(R.layout.player_by_hero_stats_vertical, verticalHeader, false);
                    Glide.with(this).load(SteamUtils.getHeroFullImage(hero.getDotaId())).into(
                            (ImageView) verticalHeaderRow.findViewById(R.id.image));
                    verticalHeader.addView(verticalHeaderRow);
                    verticalHeaderRow.setOnClickListener(new HeroInfoActivity.OnDotaHeroClickListener(hero.getId()));
                    LinearLayout row = (LinearLayout) inflater.inflate(R.layout.player_by_hero_stats_row, content, false);
                    for (String verticalResult : results) {
                        LinearLayout cell = (LinearLayout) inflater.inflate(R.layout.player_by_hero_stats_cell, row, false);
                        TextView resultTV = (TextView) cell.findViewById(android.R.id.text1);
                        resultTV.setText(verticalResult);
                        row.addView(cell);
                    }
                    content.addView(row);
                }
            }
        }
    }

    public static class PlayerHeroesStats {
        public List<String> horizontalHeaders;
        public Map<Hero, List<String>> heroResults;
    }
}
