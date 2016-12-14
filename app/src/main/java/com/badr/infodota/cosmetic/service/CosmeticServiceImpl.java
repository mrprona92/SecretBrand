package com.badr.infodota.cosmetic.service;

import android.content.Context;
import android.util.Log;
import android.util.Pair;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.R;
import com.badr.infodota.base.util.FileUtils;
import com.badr.infodota.base.util.SteamUtils;
import com.badr.infodota.cosmetic.api.player.PlayerCosmeticItem;
import com.badr.infodota.cosmetic.api.price.ItemsPricesHolder;
import com.badr.infodota.cosmetic.api.store.StoreItemsHolder;

import java.io.File;
import java.util.List;

/**
 * User: ABadretdinov
 * Date: 02.04.14
 * Time: 13:11
 */
public class CosmeticServiceImpl implements CosmeticService {

    @Override
    public Pair<StoreItemsHolder, String> getUpdatedCosmeticItems(Context context) {
        try {
            String language = context.getString(R.string.language);
            language = language.substring(0, 2);
            StoreItemsHolder result = BeanContainer.getInstance().getSteamService().getCosmeticItems(language);
            String message = null;
            if (result == null) {
                message = "Failed to get cosmetic items";
                Log.e(CosmeticServiceImpl.class.getName(), message);
            } else {
                File externalFilesDir = FileUtils.externalFileDir(context);
                FileUtils.saveJsonFile(externalFilesDir.getAbsolutePath() + File.separator + "store" + File.separator + "storeItems.json",
                        result);
            }
            return Pair.create(result, message);
        } catch (Exception e) {
            String message = "Failed to get cosmetic items, cause: " + e.getMessage();
            Log.e(CosmeticServiceImpl.class.getName(), message, e);
            return Pair.create(null, message);
        }
    }

    @Override
    public Pair<StoreItemsHolder, String> getCosmeticItems(Context context) {
        StoreItemsHolder storeItemsHolder = null;
        String message = null;
        try {
            File externalFilesDir = FileUtils.externalFileDir(context);
            String fileName = externalFilesDir.getAbsolutePath() + File.separator + "store" + File.separator + "storeItems.json";
            if (new File(fileName).exists()) {
                storeItemsHolder = FileUtils.entityFromFile(
                        fileName,
                        StoreItemsHolder.class);
            }
        } catch (Exception e) {
            message = e.getLocalizedMessage();
            Log.e(CosmeticServiceImpl.class.getName(), message, e);
        }
        return Pair.create(storeItemsHolder, message);
    }

    @Override
    public Pair<ItemsPricesHolder, String> getCosmeticItemsPrices(Context context) {
        ItemsPricesHolder itemsPricesHolder = null;
        String message = null;
        try {
            File externalFilesDir = FileUtils.externalFileDir(context);
            String fileName = externalFilesDir.getAbsolutePath() + File.separator + "store" + File.separator + "storePrices.json";
            if (new File(fileName).exists()) {
                itemsPricesHolder = FileUtils.entityFromFile(
                        fileName,
                        ItemsPricesHolder.class);
            }
        } catch (Exception e) {
            message = e.getMessage();
            Log.e(CosmeticServiceImpl.class.getName(), message, e);
        }
        return Pair.create(itemsPricesHolder, message);
    }

    @Override
    public Pair<ItemsPricesHolder, String> getUpdatedCosmeticItemsPrices(Context context) {
        try {
            ItemsPricesHolder result = BeanContainer.getInstance().getSteamService().getCosmeticItemsPrices();
            String message = null;
            if (result == null) {
                message = "Failed to get cosmetic item prices";
                Log.e(CosmeticServiceImpl.class.getName(), message);
            } else {
                File externalFilesDir = FileUtils.externalFileDir(context);
                FileUtils.saveJsonFile(externalFilesDir.getAbsolutePath() + File.separator + "store" + File.separator + "storePrices.json",
                        result);
            }
            return Pair.create(result, message);
        } catch (Exception e) {
            String message = "Failed to get cosmetic item prices, cause: " + e.getMessage();
            Log.e(CosmeticServiceImpl.class.getName(), message, e);
            return Pair.create(null, message);
        }
    }

    @Override
    public Pair<List<PlayerCosmeticItem>, String> getPlayersCosmeticItems(Context context, long steam32Id) {
        try {
            List<PlayerCosmeticItem> result = BeanContainer.getInstance().getSteamService().getPlayerCosmeticItems(SteamUtils.steam32to64(steam32Id));
            String message = null;
            if (result == null) {
                message = "Failed to get cosmetic items for player";
                Log.e(CosmeticServiceImpl.class.getName(), message);
            }
            return Pair.create(result, message);
        } catch (Exception e) {
            String message = "Failed to get cosmetic items for player, cause: " + e.getMessage();
            Log.e(CosmeticServiceImpl.class.getName(), message, e);
            return Pair.create(null, message);
        }
    }
}
