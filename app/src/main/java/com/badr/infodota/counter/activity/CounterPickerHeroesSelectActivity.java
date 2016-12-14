package com.badr.infodota.counter.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.GridView;
import android.widget.Toast;

import com.badr.infodota.R;
import com.badr.infodota.base.activity.BaseActivity;
import com.badr.infodota.base.service.LocalSpiceService;
import com.badr.infodota.base.util.ResourceUtils;
import com.badr.infodota.counter.adapter.HeroesSelectAdapter;
import com.badr.infodota.counter.api.TruepickerHero;
import com.badr.infodota.counter.task.TruepickerHeroesLoadRequest;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.text.MessageFormat;
import java.util.ArrayList;

/**
 * User: ABadretdinov
 * Date: 21.02.14
 * Time: 18:17
 */
public class CounterPickerHeroesSelectActivity extends BaseActivity implements SearchView.OnQueryTextListener, RequestListener<TruepickerHero.List> {
    public static final int ENEMY = 0;
    public static final int ALLY = 1;
    HeroesSelectAdapter adapter;
    GridView gridView;
    private String search = null;
    private String selectedFilter = null;
    private ArrayList<Integer> enemies;
    private ArrayList<Integer> allies;
    private int mode;
    private SpiceManager mSpiceManager = new SpiceManager(LocalSpiceService.class);

    @Override
    protected void onStart() {
        if (!mSpiceManager.isStarted()) {
            mSpiceManager.start(this);
            loadHeroesForGridView();
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
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == R.id.filter) {
            PopupMenu popup = new PopupMenu(this, findViewById(item.getItemId()));
            final Menu menu = popup.getMenu();
            String[] heroRoles = getResources().getStringArray(R.array.hero_roles);
            for (int i = 0; i < heroRoles.length; i++) {
                menu.add(2, i, 0, heroRoles[i]);
            }
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    if (menuItem.getItemId() == 0) {
                        item.setTitle(R.string.filter);
                        selectedFilter = null;
                    } else {
                        item.setTitle(menuItem.getTitle());
                        selectedFilter = ResourceUtils.getHeroRole(menuItem.getItemId());
                    }
                    loadHeroesForGridView();
                    return true;
                }
            });
            popup.show();
            return true;
        } else if (item.getItemId() == R.id.ready) {
            Intent intent = new Intent();
            intent.putIntegerArrayListExtra("enemies", enemies);
            intent.putIntegerArrayListExtra("allies", allies);
            setResult(RESULT_OK, intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        getMenuInflater().inflate(R.menu.hero_select, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint(getString(android.R.string.search_go));
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.counter_picker_heroes_select);
        gridView = (GridView) findViewById(R.id.gridView);
        setColumnSize();
        ActionBar actionBar = getSupportActionBar();
        Bundle bundle = getIntent().getExtras();
        enemies = bundle.getIntegerArrayList("enemies");
        allies = bundle.getIntegerArrayList("allies");
        mode = bundle.getInt("mode");
        if (mode == ALLY) {
            actionBar.setTitle(MessageFormat.format(getString(R.string.allies_selected), allies.size()));
        } else {
            actionBar.setTitle(MessageFormat.format(getString(R.string.enemies_selected), enemies.size()));
        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ActionBar actionBar = getSupportActionBar();
                if (mode == ALLY) {
                    adapter.setSelectedHero((int) id);
                    actionBar.setTitle(MessageFormat.format(getString(R.string.allies_selected), allies.size()));
                } else {
                    adapter.setSelectedHero((int) id);
                    actionBar.setTitle(MessageFormat.format(getString(R.string.enemies_selected), enemies.size()));
                }
            }
        });
        //gridView.smoothScrollToPosition(position);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setColumnSize();
    }

    private void setColumnSize() {
        if (getResources().getBoolean(R.bool.is_tablet)) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                if (gridView != null) {
                    gridView.setNumColumns(6);
                }
            } else {
                if (gridView != null) {
                    gridView.setNumColumns(4);
                }
            }
        } else {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                if (gridView != null) {
                    gridView.setNumColumns(4);
                }
            } else {
                if (gridView != null) {
                    gridView.setNumColumns(3);
                }
            }
        }
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return true;
    }

    public boolean onQueryTextChange(String textNew) {
        if (!TextUtils.isEmpty(search) || !TextUtils.isEmpty(textNew)) {
            this.search = textNew;
            loadHeroesForGridView();
        }
        return true;
    }

    private void loadHeroesForGridView() {
        mSpiceManager.execute(new TruepickerHeroesLoadRequest(getApplicationContext(), selectedFilter), this);
    }

    @Override
    public void onRequestFailure(SpiceException spiceException) {
        Toast.makeText(this, spiceException.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestSuccess(TruepickerHero.List truepickerHeros) {
        adapter = new HeroesSelectAdapter(CounterPickerHeroesSelectActivity.this, truepickerHeros, allies, enemies, mode);
        Filter filter = adapter.getFilter();
        filter.filter(search);
        gridView.setAdapter(adapter);
    }


}
