package com.util.infoparser.loader;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.base.remote.SteamService;
import com.badr.infodota.base.service.TaskRequest;
import com.badr.infodota.base.util.FileUtils;
import com.badr.infodota.base.util.VDFtoJsonParser;
import com.badr.infodota.cosmetic.api.icon.ItemIconHolder;
import com.badr.infodota.cosmetic.api.store.StoreItemsHolder;
import com.google.gson.Gson;
import com.util.infoparser.api.CosmeticItem;
import com.util.infoparser.api.CosmeticItemAutograph;
import com.util.infoparser.api.CosmeticItemSet;
import com.util.infoparser.api.CosmeticsResult;
import com.util.infoparser.api.GameCosmetics;
import com.util.infoparser.remote.URLRemoteService;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by ABadretdinov
 * 23.06.2015
 * 16:07
 */
public class CosmeticItemsLoadRequest extends TaskRequest<String> {
    private Context mContext;
    public CosmeticItemsLoadRequest(Context context) {
        super(String.class);
        mContext=context;
    }

    @Override
    public String loadData() throws Exception {
        BeanContainer beanContainer=BeanContainer.getInstance();
        SteamService steamService=beanContainer.getSteamService();
        StoreItemsHolder storeItemsHolder = steamService.getCosmeticItems("ru");
        if (storeItemsHolder != null && storeItemsHolder.getStoreItems() != null) {
            String path = storeItemsHolder.getStoreItems().getItemsGameUrl();
            URLRemoteService urlRemoteService=new URLRemoteService();
            String items=urlRemoteService.loadResult(mContext,path);
            FileUtils.saveStringFile("cosmetic_items.txt", items);

            String parsedItems=VDFtoJsonParser.parse(items);
            items=null;
            FileUtils.saveStringFile("cosmetic_items.json",parsedItems);
            CosmeticsResult result=new Gson().fromJson(parsedItems,CosmeticsResult.class);
            parsedItems=null;

            GameCosmetics cosmetics=result.getCosmetics();
            int size=cosmetics.getItems().size();
            int i=0;
            Iterator<String> iterator=cosmetics.getItems().keySet().iterator();
            while (iterator.hasNext()){
                CosmeticItem cosmeticItem=cosmetics.getItems().get(iterator.next());
                if(TextUtils.isEmpty(cosmeticItem.getImageInventory())){
                    iterator.remove();
                }
                else {
                    String[] parts=cosmeticItem.getImageInventory().split("/");
                    String iconPath=parts[parts.length-1];
                    try {
                        ItemIconHolder iconResult = steamService.getItemIconPath(iconPath.toLowerCase());
                        if (iconResult != null && iconResult.getItemIcon() != null) {
                            cosmeticItem.setImageUrl(iconResult.getItemIcon().getPath());
                            cosmeticItem.setImageInventory(null);
                            System.out.println("LOADING COMPLETE:   "+i+"/"+size);
                        }
                    }
                    catch (Exception e){
                        System.out.println("LOADING ERROR:"+iconPath);
                    }
                    i++;
                }
            }
            Map<String, CosmeticItemSet> sets = cosmetics.getSets();
            cosmetics.setSets(new HashMap<String, CosmeticItemSet>());
            iterator = sets.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                CosmeticItemSet set = sets.get(key);
                String name = set.getBundleItemName();
                set.setBundleItemName(key);
                Map<String, String> itemsMap = set.getItems();
                set.setItems(new HashMap<String, String>());
                Set<String> itemsKeySet = itemsMap.keySet();
                for (String itemKey : itemsKeySet) {
                    String count = itemsMap.get(itemKey);
                    boolean found = false;
                    Map<String, CosmeticItem> ciMap = cosmetics.getItems();
                    Iterator<String> ciIterator = ciMap.keySet().iterator();
                    while (!found && ciIterator.hasNext()) {
                        String ciItemKey = ciIterator.next();
                        CosmeticItem itemToCheck = ciMap.get(ciItemKey);
                        if (itemToCheck.getName().equals(itemKey)) {
                            found = true;
                            set.getItems().put(ciItemKey, count);
                        }
                    }
                }
                cosmetics.getSets().put(name, set);
                iterator.remove();
            }
            iterator=cosmetics.getAutographs().keySet().iterator();
            while (iterator.hasNext()){
                CosmeticItemAutograph autograph=cosmetics.getAutographs().get(iterator.next());
                if(TextUtils.isEmpty(autograph.getIconPath())){
                    iterator.remove();
                }
                else {
                    String[] parts=autograph.getIconPath().split("/");
                    String iconPath=parts[parts.length-1];
                    try {
                        ItemIconHolder iconResult = steamService.getItemIconPath(iconPath.toLowerCase());
                        if (iconResult != null && iconResult.getItemIcon() != null) {
                            autograph.setImageUrl(iconResult.getItemIcon().getPath());
                            autograph.setIconPath(null);
                        }
                    }
                    catch (Exception e){
                        System.out.println("LOADING ERROR:"+iconPath);
                    }
                }
            }
            FileUtils.saveJsonFile(Environment.getExternalStorageDirectory().getPath() + "/dota/"+"game_cosmetics.json",result.getCosmetics());
        }
        return null;
    }
}
