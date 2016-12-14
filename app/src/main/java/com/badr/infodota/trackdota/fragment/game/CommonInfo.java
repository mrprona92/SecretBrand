package com.badr.infodota.trackdota.fragment.game;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.badr.infodota.R;
import com.badr.infodota.base.util.GrayImageLoadListener;
import com.badr.infodota.base.util.Refresher;
import com.badr.infodota.base.util.SteamUtils;
import com.badr.infodota.base.util.Updatable;
import com.badr.infodota.hero.activity.HeroInfoActivity;
import com.badr.infodota.hero.api.Hero;
import com.badr.infodota.match.activity.MatchDetailsActivity;
import com.badr.infodota.trackdota.TrackdotaUtils;
import com.badr.infodota.trackdota.api.GameManager;
import com.badr.infodota.trackdota.api.core.BanPick;
import com.badr.infodota.trackdota.api.core.CoreResult;
import com.badr.infodota.trackdota.api.game.League;
import com.badr.infodota.trackdota.api.game.Team;
import com.badr.infodota.trackdota.api.live.LiveGame;
import com.bumptech.glide.Glide;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ABadretdinov
 * 14.04.2015
 * 16:44
 */
public class CommonInfo extends Fragment implements Updatable<Pair<CoreResult, LiveGame>> {
    private Refresher refresher;
    private CoreResult coreResult;
    private LiveGame liveGame;
    private SwipeRefreshLayout mScrollContainer;
    final private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            if (refresher != null) {
                mScrollContainer.setRefreshing(true);
                refresher.onRefresh();
            }
        }
    };
    private MenuItem summaryInfo;

    public static CommonInfo newInstance(Refresher refresher, CoreResult coreResult, LiveGame liveGame) {
        CommonInfo fragment = new CommonInfo();
        fragment.refresher = refresher;
        fragment.coreResult = coreResult;
        fragment.liveGame = liveGame;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.trackdota_game_common, container, false);

        mScrollContainer = (SwipeRefreshLayout) view.findViewById(R.id.listContainer);
        mScrollContainer.setOnRefreshListener(mOnRefreshListener);
        mScrollContainer.setColorSchemeResources(R.color.primary);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        summaryInfo = menu.add(2, 0, 0, R.string.match_short_info);
        if (coreResult != null && coreResult.getStatus() == 4) {
            summaryInfo.setVisible(true);
        } else {
            summaryInfo.setVisible(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.equals(summaryInfo)) {
            Intent intent = new Intent(getActivity(), MatchDetailsActivity.class);
            intent.putExtra("matchId", String.valueOf(coreResult.getId()));
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        initView();
    }

    @Override
    public void onUpdate(Pair<CoreResult, LiveGame> entity) {
        mScrollContainer.setRefreshing(false);
        this.coreResult = entity.first;
        this.liveGame = entity.second;
        initView();
    }

    private void initView() {
        View root = getView();
        Activity activity = getActivity();
        GameManager gameManager = GameManager.getInstance();
        if (coreResult != null && root != null && activity != null) {
            League league = coreResult.getLeague();
            if (league != null) {
                if (league.isHasImage()) {
                    Glide.with(activity).load(TrackdotaUtils.getLeagueImageUrl(league.getId()))/*.placeholder(R.drawable.empty_item)*/.into((ImageView) root.findViewById(R.id.league_logo));
                }
                ((TextView) root.findViewById(R.id.league_name)).setText(league.getName());
                final String leagueUrl = league.getUrl();
                if (!TextUtils.isEmpty(leagueUrl)) {
                    root.findViewById(R.id.league_holder).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent inBrowser = new Intent(Intent.ACTION_VIEW);
                            inBrowser.setData(Uri.parse(leagueUrl));
                            startActivity(inBrowser);
                        }
                    });
                }
            }
            TextView gameRdWins = (TextView) root.findViewById(R.id.game_rd_wins);
            gameRdWins.setText(coreResult.getRadiantWins() + " - " + coreResult.getDireWins());
            StringBuilder gameState = new StringBuilder(getString(R.string.game));
            gameState.append(" ");
            gameState.append(coreResult.getDireWins() + coreResult.getRadiantWins() + 1);
            gameState.append(" / ");
            gameState.append(getString(R.string.bo));
            gameState.append(1 + coreResult.getSeriesType() * 2);
            if (coreResult.getSeriesType() == 0) {
                gameRdWins.setText("-");

            }
            /*
            switch (coreResult.getSeriesType()){
                case 0:
                    gameState.append(1);
                    break;
                case 1:
                    gameState.append(3);
                    break;
                default:
                    gameState.append("{").append(coreResult.getSeriesType()).append("}");
            }*/
            ((TextView) root.findViewById(R.id.game_state)).setText(gameState.toString());
            ((TextView) root.findViewById(R.id.viewers)).setText(MessageFormat.format(getString(R.string.viewers_), coreResult.getSpectators()));
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("HH:mm  dd.MM.yyyy");
            ((TextView) root.findViewById(R.id.game_start_time)).setText(dateTimeFormat.format(new Date(coreResult.getStartTime() * 1000L)));

            Team radiant = coreResult.getRadiant();
            if (radiant != null) {
                ((TextView) root.findViewById(R.id.radiant_tag)).setText(TrackdotaUtils.getTeamTag(radiant, TrackdotaUtils.RADIANT));
                ((TextView) root.findViewById(R.id.radiant_name)).setText(TrackdotaUtils.getTeamName(radiant, TrackdotaUtils.RADIANT));
                if (radiant.isHasLogo()) {
                    Glide.with(activity).load(TrackdotaUtils.getTeamImageUrl(radiant))/*.placeholder(R.drawable.empty_item)*/.into((ImageView) root.findViewById(R.id.radiant_logo));
                }
                ((TextView) root.findViewById(R.id.radiant_picks_header)).setText(MessageFormat.format(getString(R.string.picks_), TrackdotaUtils.getTeamTag(radiant, TrackdotaUtils.RADIANT)));
                ((TextView) root.findViewById(R.id.radiant_bans_header)).setText(MessageFormat.format(getString(R.string.bans_), TrackdotaUtils.getTeamTag(radiant, TrackdotaUtils.RADIANT)));
            }
            Team dire = coreResult.getDire();
            if (dire != null) {
                ((TextView) root.findViewById(R.id.dire_tag)).setText(TrackdotaUtils.getTeamTag(dire, TrackdotaUtils.DIRE));
                ((TextView) root.findViewById(R.id.dire_name)).setText(TrackdotaUtils.getTeamName(dire, TrackdotaUtils.DIRE));
                if (dire.isHasLogo()) {
                    Glide.with(activity).load(TrackdotaUtils.getTeamImageUrl(dire))/*.placeholder(R.drawable.empty_item)*/.into((ImageView) root.findViewById(R.id.dire_logo));
                }
                ((TextView) root.findViewById(R.id.dire_picks_header)).setText(MessageFormat.format(getString(R.string.picks_), TrackdotaUtils.getTeamTag(dire, TrackdotaUtils.DIRE)));
                ((TextView) root.findViewById(R.id.dire_bans_header)).setText(MessageFormat.format(getString(R.string.bans_), TrackdotaUtils.getTeamTag(dire, TrackdotaUtils.DIRE)));
            }
            long minutes = coreResult.getDuration() / 60;
            long seconds = coreResult.getDuration() - minutes * 60;
            ((TextView) root.findViewById(R.id.game_duration)).setText(minutes + ":" + (seconds < 10 ? "0" : "") + seconds);
            //((TextView)root.findViewById(R.id.nwa_team_tag)).setText();
            TextView gameStatus = (TextView) root.findViewById(R.id.game_status);
            gameStatus.setText(TrackdotaUtils.getGameStatus(activity, coreResult.getStatus()));
            if (summaryInfo != null) {
                summaryInfo.setVisible(coreResult.getStatus() == 4);
            }
            //todo net worth advantage team tag, gold advantage - from LiveGame
            if (liveGame != null) {
                ((TextView) root.findViewById(R.id.roshan_status)).setText(liveGame.getRoshanRespawnTimer() > 0 ? MessageFormat.format(getString(R.string.respawn_in), liveGame.getRoshanRespawnTimer()) : getString(R.string.alive));

                ((TextView) root.findViewById(R.id.radiant_score)).setText(String.valueOf(liveGame.getRadiant().getScore()));
                ((TextView) root.findViewById(R.id.dire_score)).setText(String.valueOf(liveGame.getDire().getScore()));

                if (liveGame.isPaused()) {
                    gameStatus.setText(getString(R.string.paused));
                }
            }
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            /*radiant picks&bans*/
            LinearLayout radiantPicks = (LinearLayout) root.findViewById(R.id.radiant_picks);
            radiantPicks.removeAllViews();
            if (coreResult.getRadiantPicks() != null) {
                for (int i = 0, size = coreResult.getRadiantPicks().size(); i < size; i++) {
                    BanPick pick = coreResult.getRadiantPicks().get(i);
                    View row = inflater.inflate(R.layout.trackdota_hero_pickban, radiantPicks, false);
                    ((TextView) row.findViewById(R.id.number)).setText(String.valueOf(i + 1));
                    Hero hero = gameManager.getHero(pick.getHeroId());
                    if (hero != null) {
                        Glide.with(activity).load(SteamUtils.getHeroFullImage(hero.getDotaId())).into((ImageView) row.findViewById(R.id.image));
                        row.setOnClickListener(new HeroInfoActivity.OnDotaHeroClickListener(pick.getHeroId()));
                    }
                    radiantPicks.addView(row);
                    //((LinearLayout.LayoutParams)row.getLayoutParams()).weight=1;
                }
            }

            LinearLayout radiantBans = (LinearLayout) root.findViewById(R.id.radiant_bans);
            radiantBans.removeAllViews();
            if (coreResult.getRadiantBans() != null) {
                for (int i = 0, size = coreResult.getRadiantBans().size(); i < size; i++) {
                    BanPick ban = coreResult.getRadiantBans().get(i);
                    View row = inflater.inflate(R.layout.trackdota_hero_pickban, radiantPicks, false);
                    ((TextView) row.findViewById(R.id.number)).setText(String.valueOf(i + 1));
                    Hero hero = gameManager.getHero(ban.getHeroId());
                    if (hero != null) {
                        Glide.with(activity).load(SteamUtils.getHeroFullImage(hero.getDotaId()))/*.placeholder(R.drawable.empty_item)*/.into(new GrayImageLoadListener((ImageView) row.findViewById(R.id.image)));
                        row.setOnClickListener(new HeroInfoActivity.OnDotaHeroClickListener(ban.getHeroId()));
                    }
                    radiantBans.addView(row);
                    // ((LinearLayout.LayoutParams)row.getLayoutParams()).weight=1;
                }
            }

            /*dire picks&bans*/
            LinearLayout direPicks = (LinearLayout) root.findViewById(R.id.dire_picks);
            direPicks.removeAllViews();
            if (coreResult.getDirePicks() != null) {
                for (int i = 0, size = coreResult.getDirePicks().size(); i < size; i++) {
                    BanPick pick = coreResult.getDirePicks().get(i);
                    View row = inflater.inflate(R.layout.trackdota_hero_pickban, radiantPicks, false);
                    ((TextView) row.findViewById(R.id.number)).setText(String.valueOf(i + 1));
                    Hero hero = gameManager.getHero(pick.getHeroId());
                    if (hero != null) {
                        Glide.with(activity).load(SteamUtils.getHeroFullImage(hero.getDotaId()))/*.placeholder(R.drawable.empty_item)*/.into((ImageView) row.findViewById(R.id.image));
                        row.setOnClickListener(new HeroInfoActivity.OnDotaHeroClickListener(pick.getHeroId()));
                    }
                    direPicks.addView(row);
                    //((LinearLayout.LayoutParams)row.getLayoutParams()).weight=1;
                }
            }

            LinearLayout direBans = (LinearLayout) root.findViewById(R.id.dire_bans);
            direBans.removeAllViews();
            if (coreResult.getDireBans() != null) {
                for (int i = 0, size = coreResult.getDireBans().size(); i < size; i++) {
                    BanPick ban = coreResult.getDireBans().get(i);
                    View row = inflater.inflate(R.layout.trackdota_hero_pickban, radiantPicks, false);
                    ((TextView) row.findViewById(R.id.number)).setText(String.valueOf(i + 1));
                    Hero hero = gameManager.getHero(ban.getHeroId());
                    if (hero != null) {
                        Glide.with(activity).load(SteamUtils.getHeroFullImage(hero.getDotaId()))/*.placeholder(R.drawable.empty_item)*/.into(new GrayImageLoadListener((ImageView) row.findViewById(R.id.image)));
                        row.setOnClickListener(new HeroInfoActivity.OnDotaHeroClickListener(ban.getHeroId()));
                    }
                    direBans.addView(row);
                    //((LinearLayout.LayoutParams)row.getLayoutParams()).weight=1;
                }
            }
        }
    }
}
