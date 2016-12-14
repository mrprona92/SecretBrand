package com.badr.infodota.match.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.R;
import com.badr.infodota.base.activity.BaseActivity;
import com.badr.infodota.base.util.LongPair;
import com.badr.infodota.match.adapter.pager.DetailedMatchPagerAdapter;
import com.badr.infodota.match.api.detailed.DetailedMatch;
import com.badr.infodota.match.api.detailed.Team;
import com.badr.infodota.match.service.team.TeamService;
import com.badr.infodota.match.task.MatchDetailsLoadRequest;
import com.badr.infodota.match.task.TeamLogoLoadRequest;
import com.badr.infodota.trackdota.activity.TrackdotaGameInfoActivity;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.UncachedSpiceService;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

/**
 * User: ABadretdinov
 * Date: 21.01.14
 * Time: 13:41
 */
public class MatchDetailsActivity extends BaseActivity implements RequestListener {
    public static final int TRACKDOTA_GAME_ID = 322;
    BeanContainer container = BeanContainer.getInstance();
    TeamService teamService = container.getTeamService();
    private String simpleMatchId;
    private DetailedMatchPagerAdapter adapter;
    private SpiceManager mSpiceManager = new SpiceManager(UncachedSpiceService.class);
    private DetailedMatch matchDetailedMatch;
    private MenuItem trackdotaItem;

    @Override
    protected void onStart() {
        if (!mSpiceManager.isStarted()) {
            mSpiceManager.start(this);
            mSpiceManager.execute(new MatchDetailsLoadRequest(getApplicationContext(), matchDetailedMatch, simpleMatchId), this);
        }
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        if (mSpiceManager.isStarted()) {
            mSpiceManager.shouldStop();
        }
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        trackdotaItem = menu.add(1, TRACKDOTA_GAME_ID, 1, R.string.trackdota_match);
        MenuItemCompat.setShowAsAction(trackdotaItem, MenuItemCompat.SHOW_AS_ACTION_NEVER);
        trackdotaItem.setVisible(matchDetailedMatch != null && ((matchDetailedMatch.getPicks_bans() != null && matchDetailedMatch.getPicks_bans().size() > 0) || matchDetailedMatch.getRadiantTeamId() != null || matchDetailedMatch.getDireTeamId() != null));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == trackdotaItem.getItemId()) {
            Intent intent = new Intent(this, TrackdotaGameInfoActivity.class);
            intent.putExtra("id", simpleMatchId != null ? Long.valueOf(simpleMatchId) : matchDetailedMatch.getId());
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.match_info);

        final Bundle intent = getIntent().getExtras();
        if (intent != null && (intent.containsKey("matchId") || intent.containsKey("match"))) {
            //accountId=intent.getLong("accountId");
            if (intent.containsKey("matchId")) {
                simpleMatchId = intent.getString("matchId");
            }
            if (intent.containsKey("match")) {
                matchDetailedMatch = (DetailedMatch) intent.getSerializable("match");
            }

            initPager();
        }
    }

    private void initPager() {
        adapter = new DetailedMatchPagerAdapter(getSupportFragmentManager(), this);

        final ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(3);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);
    }

    @Override
    public void onRequestFailure(SpiceException spiceException) {
        Toast.makeText(this, spiceException.getLocalizedMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestSuccess(Object o) {
        if (o instanceof LongPair) {
            LongPair pair = (LongPair) o;
            Team team = new Team();
            if (pair.first.equals(matchDetailedMatch.getRadiantLogo())) {
                team.setId(matchDetailedMatch.getRadiantTeamId());
                team.setTeamLogoId(matchDetailedMatch.getRadiantLogo());
            } else {
                team.setId(matchDetailedMatch.getDireTeamId());
                team.setTeamLogoId(matchDetailedMatch.getDireLogo());
            }
            team.setLogo(pair.second);
            teamService.saveTeam(MatchDetailsActivity.this, team);
           /*
            imageLoader.loadImage(pair.second, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String s, View view) {

                }

                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason) {

                }

                @Override
                public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                    Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                    drawable.setBounds(
                            0,
                            0,
                            Utils.dpSize(MatchInfoActivity.this, 40),
                            Utils.dpSize(MatchInfoActivity.this, 40));
                }

                @Override
                public void onLoadingCancelled(String s, View view) {

                }
            });*/
        } else if (o instanceof DetailedMatch) {
            matchDetailedMatch = (DetailedMatch) o;
            adapter.updateMatchDetails(matchDetailedMatch);
            ActionBar actionBar = getSupportActionBar();
            if (matchDetailedMatch.getRadiantLogo() != null) {
                Team radiant = teamService.getTeamById(MatchDetailsActivity.this, matchDetailedMatch.getRadiantTeamId());

                if (radiant != null && !TextUtils.isEmpty(radiant.getLogo())) {
                    /*imageLoader.loadImage(radiant.getLogo(), new ImageLoadingListener() {
                        @Override
                        public void onLoadingStarted(String s, View view) {

                        }

                        @Override
                        public void onLoadingFailed(String s, View view, FailReason failReason) {

                        }

                        @Override
                        public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                            Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                            drawable.setBounds(
                                    0,
                                    0,
                                    Utils.dpSize(MatchInfoActivity.this, 40),
                                    Utils.dpSize(MatchInfoActivity.this, 40));
                        }

                        @Override
                        public void onLoadingCancelled(String s, View view) {
                        }
                    });*/
                } else {
                    mSpiceManager.execute(new TeamLogoLoadRequest(this, matchDetailedMatch.getRadiantLogo()), this);
                }
            }
            if (matchDetailedMatch.getDireLogo() != null) {
                Team dire = teamService.getTeamById(MatchDetailsActivity.this, matchDetailedMatch.getDireTeamId());
                if (dire != null && !TextUtils.isEmpty(dire.getLogo())) {
                    /*Glide.with(this).load(dire.getLogo()).into(new SimpleTarget<GlideDrawable>() {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            resource.setBounds(
                                    0,
                                    0,
                                    Utils.dpSize(MatchInfoActivity.this, 40),
                                    Utils.dpSize(MatchInfoActivity.this, 40));

                        }
                    });*/
                    /*imageLoader.loadImage(dire.getLogo(), new ImageLoadingListener() {
                        @Override
                        public void onLoadingStarted(String s, View view) {

                        }

                        @Override
                        public void onLoadingFailed(String s, View view, FailReason failReason) {

                        }

                        @Override
                        public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                            Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                            drawable.setBounds(
                                    0,
                                    0,
                                    Utils.dpSize(MatchInfoActivity.this, 40),
                                    Utils.dpSize(MatchInfoActivity.this, 40));
										*//*dire.setIcon(drawable);
                                        dire.setText("");*//*
                        }

                        @Override
                        public void onLoadingCancelled(String s, View view) {

                        }
                    });*/
                } else {
                    mSpiceManager.execute(new TeamLogoLoadRequest(getApplicationContext(), matchDetailedMatch.getDireLogo()), this);
                }
            }

            trackdotaItem.setVisible((matchDetailedMatch.getPicks_bans() != null && matchDetailedMatch.getPicks_bans().size() > 0) || matchDetailedMatch.getRadiantTeamId() != null || matchDetailedMatch.getDireTeamId() != null);
            actionBar.setTitle(getString(
                    matchDetailedMatch.isRadiantWin() ?
                            R.string.radiant_win
                            : R.string.dire_win));
        }
    }


}
