package com.badr.infodota.counter.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ActionMenuView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.R;
import com.badr.infodota.base.activity.BaseActivity;
import com.badr.infodota.base.activity.ListHolderActivity;
import com.badr.infodota.base.fragment.SCBaseFragment;
import com.badr.infodota.base.util.SteamUtils;
import com.badr.infodota.base.view.FlowLayout;
import com.badr.infodota.counter.activity.CounterPickerHeroesSelectActivity;
import com.badr.infodota.counter.api.TruepickerHero;
import com.badr.infodota.counter.service.CounterService;
import com.badr.infodota.counter.task.TruepickerCounterLoadRequest;
import com.badr.infodota.hero.activity.HeroInfoActivity;
import com.bumptech.glide.Glide;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.UncachedSpiceService;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.ArrayList;

/**
 * User: ABadretdinov
 * Date: 20.02.14
 * Time: 17:46
 */
public class CounterPickFilter extends SCBaseFragment implements RequestListener<TruepickerHero.List> {
    private ScrollView scroll;
    private FlowLayout recommendationsView;
    private View recommendsTitle;
    private View progressBar;
    private ArrayList<Integer> enemies;
    private ArrayList<Integer> allies;
    private ImageView[] enemyViews = new ImageView[5];
    private ImageView[] allyViews = new ImageView[4];
    //private boolean[] roles;
    private String[] roleCodes = new String[]{
            "       ",
            "easy triple carry",
            "hard triple carry",
            "easy triple support",
            "hard triple support",
            "jungler",
            "easy double carry",
            "easy double support",
            "hard double carry",
            "hard double support",
            "hard solo",
            "easy solo",
            "mid solo",
            "mid double carry",
            "mid double support"
    };
    private SpiceManager mSpiceManager = new SpiceManager(UncachedSpiceService.class);
    private View.OnClickListener allyListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), CounterPickerHeroesSelectActivity.class);
            intent.putExtra("enemies", enemies);
            intent.putExtra("allies", allies);
            intent.putExtra("mode", CounterPickerHeroesSelectActivity.ALLY);
            startActivityForResult(intent, CounterPickerHeroesSelectActivity.ALLY);
        }
    };

    private View.OnClickListener enemyListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), CounterPickerHeroesSelectActivity.class);
            intent.putExtra("enemies", enemies);
            intent.putExtra("allies", allies);
            intent.putExtra("mode", CounterPickerHeroesSelectActivity.ENEMY);
            startActivityForResult(intent, CounterPickerHeroesSelectActivity.ENEMY);
        }
    };

    @Override
    public void onStart() {
        if (!mSpiceManager.isStarted()) {
            mSpiceManager.start(getActivity());
        }
        super.onStart();
    }

    @Override
    public void onDestroy() {
        if (mSpiceManager.isStarted()) {
            mSpiceManager.shouldStop();
        }
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.counter_filters, container, false);
    }

    @Override
    public int getToolbarTitle() {
        return R.string.menu_counter_pick;
    }

    @Override
    public String getToolbarTitleString() {
        return null;
    }

    @Override
    public int getViewContent() {
        return R.layout.counter_filters;
    }

    @Override
    protected void initUI() {

    }

    @Override
    protected void initControls() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void hideInformation() {

    }

    @Override
    protected void registerListeners() {

    }

    @Override
    protected void unregisterListener() {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        Activity activity = getActivity();
        ActionMenuView actionMenuView = ((ListHolderActivity) activity).getActionMenuView();
        Menu actionMenu = actionMenuView.getMenu();
        actionMenu.clear();
        actionMenuView.setVisibility(View.INVISIBLE);

       /* MenuItem truePicker = actionMenu.add(1, 1011, 1, R.string.truepicker);
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View truePickerView = layoutInflater.inflate(R.layout.truepicker_logo, null, false);

        truePickerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://truepicker.com/"));
                startActivity(intent);
            }
        });
        MenuItemCompat.setActionView(truePicker, truePickerView);
        MenuItemCompat.setShowAsAction(truePicker, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
        truePicker.setIcon(R.drawable.truepicker_logo);*/

        actionMenuView.setOnMenuItemClickListener(new ActionMenuView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return onOptionsItemSelected(menuItem);
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if (!prefs.getBoolean("truePickerDialogShowed", false)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.attention);
            builder.setMessage(R.string.truepicker_attention);
            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("truePickerDialogShowed", true);
                    editor.commit();
                    dialog.dismiss();
                }
            });
            builder.show();
        }
        enemies = new ArrayList<>();
        allies = new ArrayList<>();
        View root = getView();
        if (root != null) {
            recommendationsView = (FlowLayout) root.findViewById(R.id.holder);
            progressBar = root.findViewById(R.id.progressBar);
            recommendsTitle = root.findViewById(R.id.holder_title);
            scroll = (ScrollView) root.findViewById(R.id.scroll);

            enemyViews[0] = (ImageView) root.findViewById(R.id.enemy0);
            enemyViews[0].setOnClickListener(enemyListener);
            enemyViews[1] = (ImageView) root.findViewById(R.id.enemy1);
            enemyViews[1].setOnClickListener(enemyListener);
            enemyViews[2] = (ImageView) root.findViewById(R.id.enemy2);
            enemyViews[2].setOnClickListener(enemyListener);
            enemyViews[3] = (ImageView) root.findViewById(R.id.enemy3);
            enemyViews[3].setOnClickListener(enemyListener);
            enemyViews[4] = (ImageView) root.findViewById(R.id.enemy4);
            enemyViews[4].setOnClickListener(enemyListener);

            allyViews[0] = (ImageView) root.findViewById(R.id.ally0);
            allyViews[0].setOnClickListener(allyListener);
            allyViews[1] = (ImageView) root.findViewById(R.id.ally1);
            allyViews[1].setOnClickListener(allyListener);
            allyViews[2] = (ImageView) root.findViewById(R.id.ally2);
            allyViews[2].setOnClickListener(allyListener);
            allyViews[3] = (ImageView) root.findViewById(R.id.ally3);
            allyViews[3].setOnClickListener(allyListener);
            root.findViewById(R.id.show).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressBar.setVisibility(View.VISIBLE);
                    recommendsTitle.setVisibility(View.GONE);
                    recommendationsView.removeAllViews();
                    mSpiceManager.execute(new TruepickerCounterLoadRequest(v.getContext().getApplicationContext(), allies, enemies), CounterPickFilter.this);
                }
            });
            loadImages();
        }
    }

    private void loadImages() {
        final BaseActivity activity = (BaseActivity) getActivity();
        if (activity != null) {
            CounterService counterService = BeanContainer.getInstance().getCounterService();
            for (int i = 0; i < 4; i++) //поправка, т.к. союзников не может быть 5, ведь как же мы?
            {
                if (allies.size() > i) {
                    TruepickerHero hero = counterService.getTruepickerHeroByTpId(activity, allies.get(i));
                    Glide.with(activity).load(SteamUtils.getHeroFullImage(hero.getDotaId())).placeholder(R.drawable.default_img).into(allyViews[i]);
                } else {
                    Glide.with(activity).load(Uri.parse("file:///android_asset/default_img.png")).into(allyViews[i]);
                }
                if (enemies.size() > i) {
                    TruepickerHero hero = counterService.getTruepickerHeroByTpId(activity, enemies.get(i));
                    Glide.with(activity).load(SteamUtils.getHeroFullImage(hero.getDotaId())).placeholder(R.drawable.default_img).into(enemyViews[i]);
                } else {
                    Glide.with(activity).load(Uri.parse("file:///android_asset/default_img.png")).into(enemyViews[i]);
                }
            }
            if (enemies.size() == 5)//та же поправка
            {
                TruepickerHero hero = counterService.getTruepickerHeroByTpId(activity, enemies.get(4));
                Glide.with(activity).load(SteamUtils.getHeroFullImage(hero.getDotaId())).placeholder(R.drawable.default_img).into(enemyViews[4]);
            } else {
                Glide.with(activity).load(Uri.parse("file:///android_asset/default_img.png")).into(enemyViews[4]);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CounterPickerHeroesSelectActivity.ALLY || requestCode == CounterPickerHeroesSelectActivity.ENEMY) {
                enemies = data.getIntegerArrayListExtra("enemies");
                if (enemies == null) {
                    enemies = new ArrayList<>();
                }
                allies = data.getIntegerArrayListExtra("allies");
                if (allies == null) {
                    allies = new ArrayList<>();
                }
                loadImages();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestFailure(SpiceException spiceException) {
        Toast.makeText(getActivity(), spiceException.getLocalizedMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestSuccess(TruepickerHero.List truepickerHeroes) {
        if (truepickerHeroes != null) {
            View root = getView();
            Activity activity = getActivity();
            if (root != null && activity != null) {
                progressBar.setVisibility(View.GONE);
                recommendsTitle.setVisibility(View.VISIBLE);
                LayoutInflater inflater = activity.getLayoutInflater();
                for (TruepickerHero hero : truepickerHeroes) {
                    View view = inflater.inflate(R.layout.hero_row, recommendationsView, false);
                    ((TextView) view.findViewById(R.id.name)).setText(hero.getLocalizedName());
                    Glide.with(activity).load(SteamUtils.getHeroFullImage(hero.getDotaId())).into((ImageView) view.findViewById(R.id.img));
                    view.setOnClickListener(new HeroInfoActivity.OnDotaHeroClickListener(hero.getId()));
                    recommendationsView.addView(view);
                }
                scroll.post(new Runnable() {
                    @Override
                    public void run() {
                        scroll.fullScroll(View.FOCUS_DOWN);
                    }
                });
            }
        }
    }

}
