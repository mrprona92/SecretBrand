package com.badr.infodota.match.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.R;
import com.badr.infodota.base.util.GrayImageLoadListener;
import com.badr.infodota.base.util.SteamUtils;
import com.badr.infodota.base.util.Utils;
import com.badr.infodota.hero.activity.HeroInfoActivity;
import com.badr.infodota.hero.api.Hero;
import com.badr.infodota.hero.service.HeroService;
import com.badr.infodota.match.api.detailed.DetailedMatch;
import com.badr.infodota.match.api.detailed.PickBan;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * User: Histler
 * Date: 22.01.14
 */
public class MatchSummary extends Fragment {
    DetailedMatch match;
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm  dd.MM.yyyy");
    private int mMapWidth;
    private int mMapHeight;
    private Point[] towers = new Point[]{
            new Point(13, 39), new Point(13, 55), new Point(9, 71), new Point(40, 59),
            new Point(28, 68), new Point(22, 74), new Point(82, 86), new Point(46, 88),
            new Point(26, 87), new Point(15, 79), new Point(18, 82), new Point(21, 13),
            new Point(50, 13), new Point(71, 14), new Point(56, 48), new Point(65, 37),
            new Point(75, 27), new Point(88, 61), new Point(88, 47), new Point(88, 32),
            new Point(79, 19), new Point(82, 22)
    };
    private Point[] barracks = new Point[]{
            new Point(10, 73), new Point(6, 73), new Point(19, 76),
            new Point(17, 74), new Point(22, 84), new Point(22, 88),
            new Point(72, 15), new Point(72, 11), new Point(77, 24),
            new Point(75, 22), new Point(89, 26), new Point(85, 26)
    };

    public static MatchSummary newInstance(DetailedMatch match) {
        MatchSummary fragment = new MatchSummary();
        fragment.setMatch(match);
        return fragment;
    }

    public void setMatch(DetailedMatch match) {
        this.match = match;
    }

    public void updateWithMatchInfo(DetailedMatch match) {
        this.match = match;
        if (match != null) {
            initMatch();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.match_summary, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();
        sdf.setTimeZone(tz);
        if (match != null) {
            initMatch();
        }
    }

    private void initMatch() {
        View root = getView();
        if (root != null) {
            initMap(root.findViewById(R.id.dota_map));
            TableLayout table = (TableLayout) root.findViewById(R.id.match_summary_table);
            table.setVisibility(View.VISIBLE);
            ((TextView) root.findViewById(R.id.match_id)).setText(String.valueOf(match.getId()));

            long timestamp = match.getStartTime();
            String localTime = sdf.format(new Date(timestamp * 1000));
            ((TextView) root.findViewById(R.id.start_time)).setText(localTime);

            long durationInSeconds = match.getDuration();
            long minutes = durationInSeconds / 60;
            long seconds = durationInSeconds - minutes * 60;
            ((TextView) root.findViewById(R.id.match_length))
                    .setText(minutes + ":" + (seconds < 10 ? "0" : "") + seconds);

            String[] lobbyTypes = getResources().getStringArray(R.array.lobby_types);
            ((TextView) root.findViewById(R.id.lobby_type)).setText(
                    match.getLobbyType() != -1 && match.getLobbyType() < lobbyTypes.length ? lobbyTypes[match
                            .getLobbyType()] : "Invalid");

            String[] gameModes = getResources().getStringArray(R.array.game_modes);
            ((TextView) root.findViewById(R.id.game_mode)).setText(match.getGame_mode() <= gameModes.length ? gameModes[Math.max(0, (int) match.getGame_mode() - 1)] : "Invalid");

            if (TextUtils.isEmpty(match.getRadiantName()) || TextUtils.isEmpty(match.getDireName())) {
                root.findViewById(R.id.team_names).setVisibility(View.GONE);
            } else {
                root.findViewById(R.id.team_names).setVisibility(View.VISIBLE);
                ((TextView) root.findViewById(R.id.radiant_name)).setText(match.getRadiantName());
                ((TextView) root.findViewById(R.id.dire_name)).setText(match.getDireName());
            }
            if (match.getPicks_bans() != null && match.getPicks_bans().size() > 0) {
                TableLayout cmModeTable = (TableLayout) root.findViewById(R.id.cm_mode_table);
                List<PickBan> pickBans = match.getPicks_bans();
                Activity activity = getActivity();
                if (activity != null) {
                    LayoutInflater inflater = activity.getLayoutInflater();
                    BeanContainer container = BeanContainer.getInstance();
                    HeroService heroService = container.getHeroService();

                    for (PickBan pickBan : pickBans) {
                        ViewGroup row = (ViewGroup) inflater.inflate(R.layout.pick_ban, cmModeTable, false);
                        ImageView currentImage;
                        if (pickBan.getTeam() == 0) {
                            currentImage = (ImageView) row.findViewById(R.id.radiant_hero);
                        } else {
                            currentImage = (ImageView) row.findViewById(R.id.dire_hero);
                        }
                        Hero hero = heroService.getHeroById(activity, pickBan.getHeroId());
                        if (hero != null) {
                            if (pickBan.isPick()) {
                                Glide.with(activity).load(SteamUtils.getHeroFullImage(hero.getDotaId())).into(currentImage);
                            } else {
                                Glide.with(activity).load(SteamUtils.getHeroFullImage(hero.getDotaId())).into(new GrayImageLoadListener(currentImage));
                            }
                            currentImage.setOnClickListener(new HeroInfoActivity.OnDotaHeroClickListener(hero.getId()));
                        }
                        cmModeTable.addView(row);
                    }
                }
            }
        }
    }

    private void initMap(final View map) {
        map.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            @SuppressWarnings("deprecation")
            public void onGlobalLayout() {
                mMapHeight = map.getHeight();
                mMapWidth = map.getWidth();
                map.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                RelativeLayout mapObjectsHolder = (RelativeLayout) ((View) map.getParent()).findViewById(R.id.map_objects_holder);
                mapObjectsHolder.removeAllViews();
                drawBuildings(mapObjectsHolder, match.getTowerStatusRadiant(), match.getTowerStatusDire(), match.getBarracksStatusRadiant(), match.getBarracksStatusDire());
            }
        });
    }

    private void drawBuildings(RelativeLayout holder, int towersStateRadiant, int towersStateDire, int barracksStateRadiant, int barracksStateDire) {
        ImageView buildingsImage = new ImageView(getActivity());
        buildingsImage.setMinimumWidth(mMapWidth);
        buildingsImage.setMinimumHeight(mMapHeight);
        Bitmap bitmap = Bitmap.createBitmap(mMapWidth, mMapHeight, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Canvas canvas = new Canvas(bitmap);
        int towerMaxRadius = Utils.dpSize(getActivity(), 7);
        int towerMinRadius = Utils.dpSize(getActivity(), 5);
        int radiantColor = getResources().getColor(R.color.ally_team);
        int radiantInnerColor = getResources().getColor(R.color.radiant_transparent);
        int direColor = getResources().getColor(R.color.enemy_team);
        int direInnerColor = getResources().getColor(R.color.dire_transparent);

        int halfTowersLength = towers.length / 2;
        for (int i = 0; i < halfTowersLength; i++) {
            drawTower(i, towers[i], paint, canvas, towersStateRadiant, radiantColor, radiantInnerColor, towerMaxRadius, towerMinRadius);
            drawTower(i, towers[i + halfTowersLength], paint, canvas, towersStateDire, direColor, direInnerColor, towerMaxRadius, towerMinRadius);
        }

        int barrackSize = Utils.dpSize(getActivity(), 8);
        int barrackInnerMargin = Utils.dpSize(getActivity(), 2);
        int barrackInnerSize = Utils.dpSize(getActivity(), 6);
        int halfBarracksLength = barracks.length / 2;
        for (int i = 0; i < halfBarracksLength; i++) {
            drawBarrack(i, barracks[i], paint, canvas, barracksStateRadiant, radiantColor, radiantInnerColor, barrackSize, barrackInnerMargin, barrackInnerSize);
            drawBarrack(i, barracks[i + halfBarracksLength], paint, canvas, barracksStateDire, direColor, direInnerColor, barrackSize, barrackInnerMargin, barrackInnerSize);
        }
        buildingsImage.setAdjustViewBounds(true);
        buildingsImage.setImageBitmap(bitmap);
        holder.addView(buildingsImage);
    }

    private void drawBarrack(int i, Point barrack, Paint paint, Canvas canvas, int barracksState, int color, int innerColor, int barrackSize, int barrackInnerMargin, int barrackInnerSize) {
        int alive = 1 << i & barracksState;
        int left = (int) (barrack.x / 100f * mMapWidth);
        int top = (int) (barrack.y / 100f * mMapHeight);
        if (alive == 0) {
            paint.setColor(Color.WHITE);
        } else {
            paint.setColor(color);
        }
        canvas.drawRect(
                left,
                top,
                left + barrackSize,
                top + barrackSize,
                paint);

        if (alive == 0) {
            paint.setColor(Color.BLACK);
        } else {
            paint.setColor(innerColor);
        }
        canvas.drawRect(
                left + barrackInnerMargin,
                top + barrackInnerMargin,
                left + barrackInnerSize,
                top + barrackInnerSize,
                paint);
    }


    private void drawTower(int i, Point tower, Paint paint, Canvas canvas, int towersState, int color, int innerColor, int towerMaxRadius, int towerMinRadius) {
        int alive = 1 << i & towersState;
        int left = (int) (tower.x / 100f * mMapWidth);
        int top = (int) (tower.y / 100f * mMapHeight);

        if (alive == 0) {
            paint.setColor(Color.WHITE);
        } else {
            paint.setColor(color);
        }
        canvas.drawCircle(left, top, towerMaxRadius, paint);

        if (alive == 0) {
            paint.setColor(Color.BLACK);
        } else {
            paint.setColor(innerColor);
        }
        canvas.drawCircle(left, top, towerMinRadius, paint);
    }
}
