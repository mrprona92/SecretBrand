package com.badr.infodota.cosmetic.api.store;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * User: ABadretdinov
 * Date: 31.03.14
 * Time: 18:04
 */
public class ItemSet implements Serializable {
    @SerializedName("item_set")
    private String itemSet;
    private String name;
    @SerializedName("store_bundle")
    private String storeBundle;
    private List<String> items;

    public ItemSet() {
        super();
    }

    public ItemSet(String itemSet, String storeBundle) {
        this.itemSet = itemSet;
        this.storeBundle = storeBundle;
    }

    public String getItemSet() {
        return itemSet;
    }

    public void setItemSet(String itemSet) {
        this.itemSet = itemSet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStoreBundle() {
        return storeBundle;
    }

    public void setStoreBundle(String storeBundle) {
        this.storeBundle = storeBundle;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemSet)) return false;

        ItemSet itemSet = (ItemSet) o;
        /*if(itemSet.getItemSet()!=null &&itemSet!=null &&!itemSet.getItemSet().equals(itemSet)) return false;
        if(itemSet.getStoreBundle()!=null&&storeBundle!=null &&!itemSet.getStoreBundle().equals(storeBundle)) return false;
		return true;*/

        if ((itemSet.getItemSet() != null && this.itemSet != null && itemSet.getItemSet().equals(this.itemSet))
                || (itemSet.getStoreBundle() != null && storeBundle != null && itemSet.getStoreBundle().equals(storeBundle)))
            return true;
        return false;
    }

    @Override
    public int hashCode() {
        int result = itemSet != null ? itemSet.hashCode() : 0;
        result = 31 * result + (storeBundle != null ? storeBundle.hashCode() : 0);
        return result;
    }
}
