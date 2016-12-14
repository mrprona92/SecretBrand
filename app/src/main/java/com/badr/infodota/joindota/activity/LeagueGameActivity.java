package com.badr.infodota.joindota.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.CalendarContract;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.R;
import com.badr.infodota.base.activity.BaseActivity;
import com.badr.infodota.base.util.DateUtils;
import com.badr.infodota.base.util.GrayImageLoadListener;
import com.badr.infodota.base.util.SteamUtils;
import com.badr.infodota.base.view.FlowLayout;
import com.badr.infodota.hero.activity.HeroInfoActivity;
import com.badr.infodota.hero.api.Hero;
import com.badr.infodota.hero.service.HeroService;
import com.badr.infodota.joindota.api.LiveStream;
import com.badr.infodota.joindota.api.MatchItem;
import com.badr.infodota.joindota.api.SubmatchItem;
import com.badr.infodota.joindota.task.JoindotaMatchLoadRequest;
import com.badr.infodota.joindota.task.JoindotaMatchSteamLoadRequest;
import com.badr.infodota.stream.activity.TwitchPlayActivity;
import com.bumptech.glide.Glide;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.UncachedSpiceService;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.List;

/**
 * User: ABadretdinov
 * Date: 22.04.14
 * Time: 18:58
 */
public class LeagueGameActivity extends BaseActivity implements RequestListener, SwipeRefreshLayout.OnRefreshListener {
    public static final int ADD_CALENDAR_EVENT_ID = 4321;
    SwipeRefreshLayout mSwipeRefreshView;
    View progressBar;
    private MatchItem matchItem;
    private Menu menu;
    private Button showStreams;
    private LinearLayout streamsHolder;
    private SpiceManager mSpiceManager = new SpiceManager(UncachedSpiceService.class);

    @Override
    protected void onStart() {
        if (!mSpiceManager.isStarted()) {
            mSpiceManager.start(this);
            onRefresh();
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
        this.menu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == ADD_CALENDAR_EVENT_ID) {
            createCalendarEvent();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.league_game_info);
        mSwipeRefreshView = (SwipeRefreshLayout) findViewById(R.id.listContainer);
        mSwipeRefreshView.setOnRefreshListener(this);
        TypedValue typedValue = new TypedValue();
        TypedArray a = obtainStyledAttributes(typedValue.data, new int[]{R.attr.colorAccent});
        int colorAccent = a.getColor(0, 0);
        a.recycle();
        mSwipeRefreshView.setColorSchemeColors(colorAccent);
        progressBar = findViewById(R.id.progressBar);
        showStreams = (Button) findViewById(R.id.show_streams);
        streamsHolder = (LinearLayout) findViewById(R.id.streams_holder);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey("matchItem")) {
            matchItem = (MatchItem) bundle.get("matchItem");
            ((TextView) findViewById(R.id.team1)).setText(matchItem.getTeam1name());
            ((TextView) findViewById(R.id.team2)).setText(matchItem.getTeam2name());
            Glide.with(this).load(matchItem.getTeam1flagLink()).placeholder(R.drawable.flag_default).into((ImageView) findViewById(R.id.flag1));
            Glide.with(this).load(matchItem.getTeam2flagLink()).placeholder(R.drawable.flag_default).into((ImageView) findViewById(R.id.flag2));
        } else {
            finish();
        }
    }

    private void reloadMatchDetails() {
        progressBar.setVisibility(View.VISIBLE);
        showStreams.setVisibility(View.GONE);
        streamsHolder.setVisibility(View.GONE);
        mSpiceManager.execute(new JoindotaMatchLoadRequest(matchItem), this);
    }

    private void loadStreams() {
        progressBar.setVisibility(View.VISIBLE);
        mSpiceManager.execute(new JoindotaMatchSteamLoadRequest(matchItem.getStreams()), this);
    }

    private void fillGameInfo() {
        ((TextView) findViewById(R.id.text_title)).setText(matchItem.getTitle());
        getSupportActionBar().setTitle(matchItem.getTitle());
        ((TextView) findViewById(R.id.middle_text)).setText(matchItem.getMiddleText());
        MenuItem calendar = menu.findItem(ADD_CALENDAR_EVENT_ID);
        if (matchItem.getDetailedDate() != null) {
            ((TextView) findViewById(R.id.text_detailed_date)).setText(DateUtils.DATE_TIME_FORMAT.format(matchItem.getDetailedDate()));
            if (calendar == null) {
                calendar = menu.add(0, ADD_CALENDAR_EVENT_ID, 1, R.string.add_calendar_event);
                calendar.setIcon(R.drawable.ic_menu_calendar);
                MenuItemCompat.setShowAsAction(calendar, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
            }
        } else {
            if (calendar != null) {
                menu.removeItem(ADD_CALENDAR_EVENT_ID);
            }
            ((TextView) findViewById(R.id.text_detailed_date)).setText("TBA");
        }
        Glide.with(this).load(matchItem.getTeam1logoLink()).placeholder(R.drawable.flag_default).into((ImageView) findViewById(R.id.logo1));
        Glide.with(this).load(matchItem.getTeam2logoLink()).placeholder(R.drawable.flag_default).into((ImageView) findViewById(R.id.logo2));
        HeroService heroService = BeanContainer.getInstance().getHeroService();
        LinearLayout holder = (LinearLayout) findViewById(R.id.games_holder);
        holder.removeAllViews();
        LayoutInflater inflater = getLayoutInflater();
        for (SubmatchItem submatchItem : matchItem.getSubmatches()) {
            LinearLayout view = (LinearLayout) inflater.inflate(R.layout.league_game_submatch, holder, false);
            FlowLayout team1bans = (FlowLayout) view.findViewById(R.id.bans1);
            for (String heroName : submatchItem.getTeam1bans()) {
                List<Hero> heroes = heroService.getHeroesByName(this, heroName);
                if (heroes != null && heroes.size() > 0) {
                    Hero hero = heroes.get(0);
                    LinearLayout imageLayout = (LinearLayout) inflater.inflate(R.layout.image_holder, team1bans, false);
                    imageLayout.setOnClickListener(new HeroInfoActivity.OnDotaHeroClickListener(hero.getId()));
                    team1bans.addView(imageLayout);
                    Glide
                            .with(this)
                            .load(SteamUtils.getHeroFullImage(hero.getDotaId()))
                            .placeholder(R.drawable.default_img)
                            .into(new GrayImageLoadListener((ImageView) imageLayout.findViewById(R.id.img)));
                }
            }
            FlowLayout team2bans = (FlowLayout) view.findViewById(R.id.bans2);
            for (String heroName : submatchItem.getTeam2bans()) {
                List<Hero> heroes = heroService.getHeroesByName(this, heroName);
                if (heroes != null && heroes.size() > 0) {
                    Hero hero = heroes.get(0);
                    LinearLayout imageLayout = (LinearLayout) inflater.inflate(R.layout.image_holder, team2bans, false);
                    imageLayout.setOnClickListener(new HeroInfoActivity.OnDotaHeroClickListener(hero.getId()));
                    team2bans.addView(imageLayout);
                    ImageView imageView = (ImageView) imageLayout.findViewById(R.id.img);
                    Glide
                            .with(this)
                            .load(SteamUtils.getHeroFullImage(hero.getDotaId()))
                            .placeholder(R.drawable.default_img)
                            .into(new GrayImageLoadListener(imageView));
                }
            }
            LinearLayout team1 = (LinearLayout) view.findViewById(R.id.team1);
            LinearLayout team2 = (LinearLayout) view.findViewById(R.id.team2);
            int size = Math.max(submatchItem.getTeam1picks().size(), submatchItem.getTeam2picks().size());
            for (int i = 0; i < size; i++) {
                LinearLayout team1HeroHolder = (LinearLayout) inflater.inflate(R.layout.image_text_row_left, team1, false);
                LinearLayout team2HeroHolder = (LinearLayout) inflater.inflate(R.layout.image_text_row_right, team2, false);
                ((TextView) team1HeroHolder.findViewById(R.id.text)).setText(submatchItem.getTeam1playerNames().get(i));
                ((TextView) team2HeroHolder.findViewById(R.id.text)).setText(submatchItem.getTeam2playerNames().get(i));
                List<Hero> heroes = submatchItem.getTeam1picks().size() > i ? heroService.getHeroesByName(this, submatchItem.getTeam1picks().get(i)) : null;
                if (heroes != null && heroes.size() > 0) {
                    Hero hero = heroes.get(0);
                    ImageView imageView = (ImageView) team1HeroHolder.findViewById(R.id.img);
                    Glide.with(this).load(SteamUtils.getHeroFullImage(hero.getDotaId())).placeholder(R.drawable.default_img).into(imageView);
                    imageView.setOnClickListener(new HeroInfoActivity.OnDotaHeroClickListener(hero.getId()));
                }
                heroes = submatchItem.getTeam2picks().size() > i ? heroService.getHeroesByName(this, submatchItem.getTeam2picks().get(i)) : null;
                if (heroes != null && heroes.size() > 0) {
                    Hero hero = heroes.get(0);
                    ImageView imageView = (ImageView) team2HeroHolder.findViewById(R.id.img);
                    Glide.with(this).load(SteamUtils.getHeroFullImage(hero.getDotaId())).placeholder(R.drawable.default_img).into(imageView);
                    imageView.setOnClickListener(new HeroInfoActivity.OnDotaHeroClickListener(hero.getId()));
                }
                team1.addView(team1HeroHolder);
                team2.addView(team2HeroHolder);
            }
            ((TextView) view.findViewById(R.id.match_title)).setText(submatchItem.getScore());
            holder.addView(view);
        }
    }

    private void createCalendarEvent() {
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, matchItem.getDetailedDate())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, matchItem.getDetailedDate())
                .putExtra(CalendarContract.Events.TITLE, matchItem.getTitle() + ", " + matchItem.getTeam1name() + " vs " + matchItem.getTeam2name());
        startActivity(intent);
    }

    @Override
    public void onRequestFailure(SpiceException spiceException) {
        mSwipeRefreshView.setRefreshing(false);
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, spiceException.getLocalizedMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestSuccess(Object object) {
        mSwipeRefreshView.setRefreshing(false);
        progressBar.setVisibility(View.GONE);
        if (object instanceof String) {
            String result = (String) object;
            if (!TextUtils.isEmpty(result)) {
                onRequestFailure(new SpiceException(result));
            } else if (matchItem.getStreams() != null && matchItem.getStreams().size() > 0) {
                showStreams.setVisibility(View.VISIBLE);
                showStreams.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (streamsHolder.getVisibility() == View.VISIBLE) {
                            streamsHolder.setVisibility(View.GONE);
                        } else {
                            streamsHolder.setVisibility(View.VISIBLE);
                            streamsHolder.removeAllViews();
                            if (matchItem.getStreams() != null) {
                                LayoutInflater inflater = getLayoutInflater();
                                for (final LiveStream stream : matchItem.getStreams()) {
                                    View streamRow = inflater.inflate(R.layout.league_game_stream_row, streamsHolder, false);
                                    TextView name = (TextView) streamRow.findViewById(R.id.name);
                                    name.setText(stream.getName());
                                    TextView language = (TextView) streamRow.findViewById(R.id.language);
                                    language.setText(stream.getLanguage());
                                    TextView viewersStatus = (TextView) streamRow.findViewById(R.id.viewers);
                                    if (TextUtils.isEmpty(stream.getViewers())) {
                                        viewersStatus.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                                        viewersStatus.setText(stream.getStatus());
                                    } else {
                                        viewersStatus.setText(stream.getViewers());
                                        streamRow.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if (TextUtils.isEmpty(stream.getChannelName())) {
                                                    Intent inBrowser = new Intent(Intent.ACTION_VIEW);
                                                    inBrowser.setData(Uri.parse(stream.getUrl()));
                                                    startActivity(inBrowser);
                                                } else {
                                                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(LeagueGameActivity.this);
                                                    Intent intent;
                                                    switch (preferences.getInt("player_type", 0)) {
                                                        case 0:
                                                            intent = new Intent(LeagueGameActivity.this, TwitchPlayActivity.class);
                                                            intent.putExtra("channelName", stream.getChannelName());
                                                            intent.putExtra("channelTitle", matchItem.getTitle());
                                                            break;
                                                        default:
                                                            String url = "http://www.twitch.tv/" + stream.getChannelName();
                                                            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                                            break;
                                                    }
                                                    startActivity(intent);
                                                }
                                            }
                                        });
                                    }
                                    streamsHolder.addView(streamRow);
                                }
                            }
                        }
                    }
                });
            }
        } else if (object instanceof MatchItem) {
            matchItem = (MatchItem) object;
            loadStreams();
            fillGameInfo();
        }
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshView.setRefreshing(true);
        reloadMatchDetails();
    }
}
