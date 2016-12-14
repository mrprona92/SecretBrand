package com.badr.infodota.cosmetic.service;

import android.content.Context;
import android.util.Pair;

import com.badr.infodota.cosmetic.api.player.PlayerCosmeticItem;
import com.badr.infodota.cosmetic.api.price.ItemsPricesHolder;
import com.badr.infodota.cosmetic.api.store.StoreItemsHolder;

import java.util.List;

/**
 * User: ABadretdinov
 * Date: 02.04.14
 * Time: 13:04
 */
public interface CosmeticService {
    Pair<StoreItemsHolder, String> getCosmeticItems(Context context);

    Pair<StoreItemsHolder, String> getUpdatedCosmeticItems(Context context);

    Pair<ItemsPricesHolder, String> getCosmeticItemsPrices(Context context);

    Pair<ItemsPricesHolder, String> getUpdatedCosmeticItemsPrices(Context context);

    Pair<List<PlayerCosmeticItem>, String> getPlayersCosmeticItems(Context context, long steam32Id);

}
