package com.badr.infodota.cosmetic.api.store;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * User: ABadretdinov
 * Date: 31.03.14
 * Time: 17:58
 */
public class StoreItems implements Serializable {
    private int status;
    @SerializedName("items_game_url")
    private String itemsGameUrl;
    /*private Map<String,Integer> qualities;
    private List<Origin> originNames;*/
    private List<CosmeticItem> items;
    @SerializedName("item_sets")
    private List<ItemSet> itemSets;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getItemsGameUrl() {
        return itemsGameUrl;
    }

    public void setItemsGameUrl(String itemsGameUrl) {
        this.itemsGameUrl = itemsGameUrl;
    }

	/*public Map<String, Integer> getQualities()
    {
		return qualities;
	}

	public void setQualities(Map<String, Integer> qualities)
	{
		this.qualities = qualities;
	}

	public List<Origin> getOriginNames()
	{
		return originNames;
	}

	public void setOriginNames(List<Origin> originNames)
	{
		this.originNames = originNames;
	}*/

    public List<CosmeticItem> getItems() {
        return items;
    }

    public void setItems(List<CosmeticItem> items) {
        this.items = items;
    }

    public List<ItemSet> getItemSets() {
        return itemSets;
    }

    public void setItemSets(List<ItemSet> itemSets) {
        this.itemSets = itemSets;
    }
}
