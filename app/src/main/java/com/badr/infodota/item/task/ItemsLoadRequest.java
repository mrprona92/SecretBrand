package com.badr.infodota.item.task;

import android.content.Context;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.base.service.TaskRequest;
import com.badr.infodota.item.api.Item;
import com.badr.infodota.item.service.ItemService;

/**
 * Created by ABadretdinov
 * 20.08.2015
 * 15:33
 */
public class ItemsLoadRequest extends TaskRequest<Item.List> {
    private Context mContext;
    private String mFilters;

    public ItemsLoadRequest(Context context, String filters) {
        super(Item.List.class);
        this.mContext = context;
        this.mFilters = filters;
    }

    @Override
    public Item.List loadData() throws Exception {
        ItemService itemService = BeanContainer.getInstance().getItemService();
        return itemService.getItems(mContext, mFilters);
    }
}
