package com.badr.infodota.cosmetic.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ActionMenuView;
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
import com.badr.infodota.base.activity.ListHolderActivity;
import com.badr.infodota.base.fragment.SearchableFragment;
import com.badr.infodota.base.util.DialogUtils;
import com.badr.infodota.base.util.ProgressTask;
import com.badr.infodota.base.util.ResourceUtils;
import com.badr.infodota.cosmetic.activity.CosmeticItemDetailsActivity;
import com.badr.infodota.cosmetic.adapter.CosmeticItemsAdapter;
import com.badr.infodota.cosmetic.api.price.ItemPrice;
import com.badr.infodota.cosmetic.api.price.ItemsPricesHolder;
import com.badr.infodota.cosmetic.api.store.CosmeticItem;
import com.badr.infodota.cosmetic.api.store.ItemSet;
import com.badr.infodota.cosmetic.api.store.StoreItems;
import com.badr.infodota.cosmetic.api.store.StoreItemsHolder;
import com.badr.infodota.cosmetic.service.CosmeticService;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: ABadretdinov
 * Date: 31.03.14
 * Time: 17:12
 */
public class CosmeticItemsList extends Fragment implements SearchableFragment {
    public static final int REFRESH = 123123;
    private final static String FLURRY_EVENT = "openCosmeticList";
    BeanContainer beanContainer = BeanContainer.getInstance();
    CosmeticService service = beanContainer.getCosmeticService();
    private GridView gridView;
    private CosmeticItemsAdapter adapter;
    private TextView lastUpdatedTV;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
    private List<ItemSet> sets;
    private String searchQuery = null;
    private String filter = null;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        ActionMenuView actionMenuView = ((ListHolderActivity) getActivity()).getActionMenuView();
        Menu actionMenu = actionMenuView.getMenu();
        actionMenu.clear();
        actionMenuView.setVisibility(View.GONE);
        MenuItem item = menu.add(1, REFRESH, 0, R.string.refresh);
        item.setIcon(R.drawable.ic_menu_refresh);
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == REFRESH) {
            loadCosmeticItemsFromWeb();
            return true;
        } else if (item.getItemId() == R.id.filter) {
            PopupMenu popup = new PopupMenu(getActivity(), getActivity().findViewById(item.getItemId()));
            final Menu menu = popup.getMenu();
            String[] itemTypes = getResources().getStringArray(R.array.cosmetic_items_filter);
            for (int i = 0; i < itemTypes.length; i++) {
                menu.add(2, i, 0, itemTypes[i]);
            }
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    if (menuItem.getItemId() == 0) {
                        item.setTitle(R.string.filter);
                        filter = null;
                    } else {
                        item.setTitle(menuItem.getTitle());
                        filter = ResourceUtils.getCosmeticItemType(menuItem.getItemId());
                    }
                    if (adapter != null) {
                        adapter.setFilterValue(filter);
                        adapter.getFilter().filter(searchQuery != null ? searchQuery : "");
                    }
                    return true;
                }
            });
            popup.show();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cosmetic_items_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        gridView = (GridView) getView().findViewById(R.id.gridView);
        setColumnSize();
        loadSavedItems();

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
            List<CosmeticItem> items = adapter.getItems();

            int cosmeticItemSetIndex = items.indexOf(new CosmeticItem(itemSet.getStoreBundle()));
            if (cosmeticItemSetIndex > -1) {
                intent.putExtra("set", items.get(cosmeticItemSetIndex));
            }

            List<String> itemsNamesFromThisSet = itemSet.getItems();
            ArrayList<CosmeticItem> itemsFromThisSet = new ArrayList<CosmeticItem>();
            for (String itemName : itemsNamesFromThisSet) {
                int currentItemIndex = items.indexOf(new CosmeticItem(itemName));
                if (currentItemIndex > -1) {
                    itemsFromThisSet.add(items.get(currentItemIndex));
                }
            }
            intent.putExtra("setItems", itemsFromThisSet);
        }
    }

    private void setItemsToAdapter(Context context, List<CosmeticItem> items) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        long lastUpdatedTime = prefs.getLong("last_store_update_time", 0);
        View root = getView();
        Activity activity = getActivity();
        if (root == null || activity == null) {
            return;
        }
        lastUpdatedTV = (TextView) root.findViewById(R.id.update_time);
        if (lastUpdatedTime != 0) {
            lastUpdatedTV.setVisibility(View.VISIBLE);

            lastUpdatedTV.setText(MessageFormat.format(getString(R.string.last_updated_time), sdf.format(new Date(lastUpdatedTime))));
        } else {
            lastUpdatedTV.setVisibility(View.GONE);
        }
        adapter = new CosmeticItemsAdapter(activity, items);
        adapter.setFilterValue(filter);
        adapter.getFilter().filter(searchQuery != null ? searchQuery : "");
        gridView.setAdapter(adapter);
    }

    private void loadSavedItems() {
        final BaseActivity activity = (BaseActivity) getActivity();
        DialogUtils.showLoaderDialog(getFragmentManager(), new ProgressTask<List<CosmeticItem>>() {
            @Override
            public List<CosmeticItem> doTask(OnPublishProgressListener listener) throws Exception {
                Pair<StoreItemsHolder, String> storeResultsPair = service.getCosmeticItems(activity);
                List<CosmeticItem> items;
                List<ItemPrice> itemPrices;
                if (!TextUtils.isEmpty(storeResultsPair.second)) {
                    throw new Exception(storeResultsPair.second);
                }
                StoreItemsHolder storeItemsHolder = storeResultsPair.first;
                if (storeItemsHolder != null && storeItemsHolder.getStoreItems() != null) {
                    StoreItems stStoreItems = storeItemsHolder.getStoreItems();
                    items = stStoreItems.getItems();
                    sets = stStoreItems.getItemSets();
                } else {
                    items = new ArrayList<CosmeticItem>();
                    sets = new ArrayList<ItemSet>();
                }

                Pair<ItemsPricesHolder, String> pricesResultsPair = service.getCosmeticItemsPrices(activity);
                if (!TextUtils.isEmpty(pricesResultsPair.second)) {
                    throw new Exception(pricesResultsPair.second);
                }
                ItemsPricesHolder itemsPricesHolder = pricesResultsPair.first;
                if (itemsPricesHolder != null && itemsPricesHolder.getItemsPrices() != null) {
                    itemPrices = itemsPricesHolder.getItemsPrices().getItemPrices();
                } else {
                    itemPrices = new ArrayList<ItemPrice>();
                }
                return getItemsToShow(items, itemPrices);
            }

            @Override
            public void doAfterTask(List<CosmeticItem> result) {
                if (result != null && result.size() > 0) {
                    setItemsToAdapter(activity, result);
                } else {
                    loadCosmeticItemsFromWeb();
                }
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

    private List<CosmeticItem> getItemsToShow(List<CosmeticItem> items, List<ItemPrice> itemPrices) {
        List<CosmeticItem> itemsToShow = new ArrayList<CosmeticItem>();
        for (CosmeticItem item : items) {
            if (item.getQuality() != 0) {
                int index = itemPrices.indexOf(new ItemPrice(String.valueOf(item.getDefIndex())));
                if (index > -1) {
                    ItemPrice itemPrice = itemPrices.get(index);
                    item.setPrices(itemPrice.getPrices());
                }
                itemsToShow.add(item);
            }
        }
        return itemsToShow;
    }

    private void loadCosmeticItemsFromWeb() {
        final BaseActivity activity = (BaseActivity) getActivity();
        if (activity != null) {
            DialogUtils.showLoaderDialog(getFragmentManager(), new ProgressTask<List<CosmeticItem>>() {
                @Override
                public List<CosmeticItem> doTask(OnPublishProgressListener listener) throws Exception {
                    Pair<StoreItemsHolder, String> storeResultsPair = service.getUpdatedCosmeticItems(activity);
                    List<CosmeticItem> items;
                    List<ItemPrice> itemPrices;
                    if (!TextUtils.isEmpty(storeResultsPair.second)) {
                        throw new Exception(storeResultsPair.second);
                    }
                    StoreItemsHolder storeItemsHolder = storeResultsPair.first;
                    if (storeItemsHolder != null && storeItemsHolder.getStoreItems() != null) {
                        StoreItems stStoreItems = storeItemsHolder.getStoreItems();
                        items = stStoreItems.getItems();
                        sets = stStoreItems.getItemSets();
                    } else {
                        items = new ArrayList<CosmeticItem>();
                        sets = new ArrayList<ItemSet>();
                    }

                    Pair<ItemsPricesHolder, String> pricesResultsPair = service.getUpdatedCosmeticItemsPrices(activity);
                    if (!TextUtils.isEmpty(pricesResultsPair.second)) {
                        throw new Exception(pricesResultsPair.second);
                    }
                    ItemsPricesHolder itemsPricesHolder = pricesResultsPair.first;
                    if (itemsPricesHolder != null && itemsPricesHolder.getItemsPrices() != null) {
                        itemPrices = itemsPricesHolder.getItemsPrices().getItemPrices();
                    } else {
                        itemPrices = new ArrayList<ItemPrice>();
                    }
                    return getItemsToShow(items, itemPrices);
                }

                @Override
                public void doAfterTask(List<CosmeticItem> result) {
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
                    prefs.edit().putLong("last_store_update_time", new Date().getTime()).commit();
                    setItemsToAdapter(activity, result);
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

    @Override
    public void onDestroy() {
        ((AppCompatActivity) getActivity()).setSupportProgressBarIndeterminateVisibility(false);
        super.onDestroy();
    }
}
