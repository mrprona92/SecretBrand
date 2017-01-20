package com.mrprona.dota2assitant.cosmetic.service;

import android.content.Context;
import android.util.Pair;

import com.mrprona.dota2assitant.cosmetic.api.player.PlayerCosmeticItem;
import com.mrprona.dota2assitant.cosmetic.api.price.ItemsPricesHolder;
import com.mrprona.dota2assitant.cosmetic.api.store.StoreItemsHolder;

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
