package com.badr.infodota.item.service;

import android.content.Context;

import com.badr.infodota.InitializingBean;
import com.badr.infodota.item.api.Item;

import java.util.List;

/**
 * Created by ABadretdinov
 * 25.12.2014
 * 14:36
 */
public interface ItemService extends InitializingBean {

    Item.List getItems(Context context, String filter);

    List<Item> getAllItems(Context context);

    List<Item> getItemsByName(Context context, String name);

    Item getItemById(Context context, long id);

    Item getItemByDotaId(Context context, String dotaId);

    void saveItem(Context context, Item item);

    void saveFromToItems(Context context, List<Item> items);

    List<Item> getComplexItems(Context context);

    List<Item> getItemsFromThis(Context context, Item item);

    List<Item> getItemsToThis(Context context, Item item);
}
