package com.badr.infodota.player.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.PopupMenu;
import android.text.TextUtils;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.R;
import com.badr.infodota.base.activity.BaseActivity;
import com.badr.infodota.base.fragment.SearchableFragment;
import com.badr.infodota.base.util.DialogUtils;
import com.badr.infodota.base.util.ProgressTask;
import com.badr.infodota.base.util.ResourceUtils;
import com.badr.infodota.cosmetic.activity.CosmeticItemDetailsActivity;
import com.badr.infodota.cosmetic.adapter.CosmeticItemsAdapter;
import com.badr.infodota.cosmetic.api.player.PlayerCosmeticItem;
import com.badr.infodota.cosmetic.api.store.CosmeticItem;
import com.badr.infodota.cosmetic.api.store.ItemSet;
import com.badr.infodota.cosmetic.api.store.StoreItems;
import com.badr.infodota.cosmetic.api.store.StoreItemsHolder;
import com.badr.infodota.cosmetic.service.CosmeticService;
import com.badr.infodota.player.api.Unit;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: Histler
 * Date: 02.04.14
 */
public class PlayerCosmeticItemsList extends Fragment implements SearchableFragment {
    public static final int REFRESH = 123123;
    BeanContainer beanContainer = BeanContainer.getInstance();
    CosmeticService service = beanContainer.getCosmeticService();
    private GridView gridView;
    private Unit account;
    private CosmeticItemsAdapter adapter;
    private TextView lastUpdatedTV;
    private TextView filterTV;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
    private List<CosmeticItem> allItems;
    private List<ItemSet> sets;
    private String searchQuery = null;
    private String filter = null;

    public static PlayerCosmeticItemsList newInstance(Unit account) {
        PlayerCosmeticItemsList fragment = new PlayerCosmeticItemsList();
        fragment.setAccount(account);
        return fragment;
    }

    public void setAccount(Unit account) {
        this.account = account;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem item = menu.add(1, REFRESH, 0, R.string.refresh);
        item.setIcon(R.drawable.ic_menu_refresh);
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == REFRESH) {
            loadAllCosmeticItems();
            return true;
        }
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.players_cosmetic_items_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        gridView = (GridView) getView().findViewById(R.id.gridView);
        lastUpdatedTV = (TextView) getView().findViewById(R.id.update_time);
        lastUpdatedTV.setText(getActivity().getString(R.string.press_refresh_button));
        lastUpdatedTV.setVisibility(View.VISIBLE);
        filterTV = (TextView) getView().findViewById(R.id.filter);
        filterTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getActivity(), filterTV);
                final Menu menu = popup.getMenu();
                String[] itemTypes = getResources().getStringArray(R.array.cosmetic_items_filter);
                for (int i = 0; i < itemTypes.length; i++) {
                    menu.add(2, i, 0, itemTypes[i]);
                }
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == 0) {
                            filterTV.setText(R.string.filter);
                            filter = null;
                        } else {
                            filterTV.setText(menuItem.getTitle());
                            filter = ResourceUtils.getCosmeticItemType(menuItem.getItemId());
                        }
                        if (adapter != null && adapter.getFilter() != null) {
                            adapter.setFilterValue(filter);
                            adapter.getFilter().filter(searchQuery != null ? searchQuery : "");
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });
        setColumnSize();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CosmeticItem item = adapter.getItem(position);
                showInfoForItem(item);
            }
        });
    }

    private void showInfoForItem(CosmeticItem item) {
        Intent intent = new Intent(getActivity(), CosmeticItemDetailsActivity.class);
        intent.putExtra("item", item);
        if ("bundle".equals(item.getItemClass())) {
            String name = item.getName();
            putSetsDataToIntent(intent, null, name);
        } else if (!TextUtils.isEmpty(item.getItemSet())) {
            String item_set = item.getItemSet();
            putSetsDataToIntent(intent, item_set, null);
        }
        startActivity(intent);
    }

    private void putSetsDataToIntent(Intent intent, String item_set, String name) {
        int setIndex = sets.indexOf(new ItemSet(item_set, name));
        if (setIndex > -1) {
            ItemSet itemSet = sets.get(setIndex);

            int cosmeticItemSetIndex = allItems.indexOf(new CosmeticItem(itemSet.getStoreBundle()));
            if (cosmeticItemSetIndex > -1) {
                intent.putExtra("set", allItems.get(cosmeticItemSetIndex));
            }

            List<String> itemsNamesFromThisSet = itemSet.getItems();
            ArrayList<CosmeticItem> itemsFromThisSet = new ArrayList<CosmeticItem>();
            for (String itemName : itemsNamesFromThisSet) {
                int currentItemIndex = allItems.indexOf(new CosmeticItem(itemName));
                if (currentItemIndex > -1) {
                    itemsFromThisSet.add(allItems.get(currentItemIndex));
                }
            }
            intent.putExtra("setItems", itemsFromThisSet);
        }
    }

    private void setItemsToAdapter(List<CosmeticItem> items, BaseActivity activity) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        long lastUpdatedTime = prefs.getLong("last_store_update_time", 0);
        if (lastUpdatedTime != 0) {
            lastUpdatedTV.setVisibility(View.VISIBLE);

            lastUpdatedTV.setText(MessageFormat.format(getString(R.string.last_updated_time), sdf.format(new Date(lastUpdatedTime))));
        } else {
            lastUpdatedTV.setVisibility(View.GONE);
        }
        adapter = new CosmeticItemsAdapter(getActivity(), items);
        adapter.setFilterValue(filter);
        adapter.getFilter().filter(searchQuery != null ? searchQuery : "");
        gridView.setAdapter(adapter);
    }

    private void loadAllCosmeticItems() {
        final BaseActivity activity = (BaseActivity) getActivity();
        DialogUtils.showLoaderDialog(getFragmentManager(), new ProgressTask<List<CosmeticItem>>() {
            @Override
            public List<CosmeticItem> doTask(OnPublishProgressListener listener) throws Exception {
                Pair<StoreItemsHolder, String> storeResultsPair = service.getUpdatedCosmeticItems(activity);
                if (!TextUtils.isEmpty(storeResultsPair.second)) {
                    throw new Exception(storeResultsPair.second);
                }
                StoreItemsHolder storeItemsHolder = storeResultsPair.first;
                if (storeItemsHolder != null && storeItemsHolder.getStoreItems() != null) {
                    StoreItems stStoreItems = storeItemsHolder.getStoreItems();
                    allItems = stStoreItems.getItems();
                    sets = stStoreItems.getItemSets();
                } else {
                    allItems = new ArrayList<>();
                    sets = new ArrayList<>();
                }

                Pair<List<PlayerCosmeticItem>, String> playerCosmeticItemsResultsPair = service.getPlayersCosmeticItems(activity, account.getAccountId());
                if (!TextUtils.isEmpty(playerCosmeticItemsResultsPair.second)) {
                    throw new Exception(playerCosmeticItemsResultsPair.second);
                }
                List<CosmeticItem> itemsToShow = new ArrayList<CosmeticItem>();
                for (PlayerCosmeticItem cosmeticItem : playerCosmeticItemsResultsPair.first) {
                    int itemIndex = allItems.indexOf(new CosmeticItem(cosmeticItem.getDefIndex()));
                    if (itemIndex > -1) {
                        itemsToShow.add(allItems.get(itemIndex));
                    }
                }
                return itemsToShow;
            }

            @Override
            public void doAfterTask(List<CosmeticItem> result) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
                prefs.edit().putLong("last_store_update_time", new Date().getTime()).commit();
                setItemsToAdapter(result, activity);
            }

            @Override
            public void handleError(String error) {
                Toast.makeText(activity, error, Toast.LENGTH_LONG).show();
            }

            @Override
            public String getName() {
                return null;
            }
        });
    }

    @Override
    public void onTextSearching(String text) {
        if (!TextUtils.isEmpty(searchQuery) || !TextUtils.isEmpty(text)) {
            this.searchQuery = text;
            if (adapter != null) {
                adapter.getFilter().filter(searchQuery);
            }
        }
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

}
