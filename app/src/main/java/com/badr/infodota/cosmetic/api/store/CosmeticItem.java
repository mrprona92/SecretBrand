package com.badr.infodota.cosmetic.api.store;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Map;

/**
 * User: ABadretdinov
 * Date: 31.03.14
 * Time: 12:20
 */
public class CosmeticItem implements Serializable {
    private String name;
    @SerializedName("defindex")
    private long defIndex;
    @SerializedName("item_class")
    private String itemClass;
    @SerializedName("item_type_name")
    private String itemTypeName;
    @SerializedName("item_name")
    private String itemName;
    //private boolean propep_name;
    @SerializedName("item_quality")
    private int quality;
    @SerializedName("image_inventory")
    private String imageInventory;
    @SerializedName("item_set")
    private String itemSet;
    //private int min_ilevel;
    //private int max_ilevel;
    @SerializedName("image_url")
    private String imageUrl;
    @SerializedName("image_url_large")
    private String imageUrlLarge;
    @SerializedName("item_description")
    private String description;
    private Map<String, Long> prices;
    //или map из boolean?
    //private Capability capabilities;
    //private Tool tool;
    //private List<Attribute> attributes;

    public CosmeticItem() {
        super();
    }

    public CosmeticItem(long defIndex) {
        this.defIndex = defIndex;
    }

    public CosmeticItem(String name) {
        this.name = name;
    }

    public String getItemClass() {
        return itemClass;
    }

    public void setItemClass(String itemClass) {
        this.itemClass = itemClass;
    }

    public Map<String, Long> getPrices() {
        return prices;
    }

    public void setPrices(Map<String, Long> prices) {
        this.prices = prices;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDefIndex() {
        return defIndex;
    }

    public void setDefIndex(long defIndex) {
        this.defIndex = defIndex;
    }

    public String getItemTypeName() {
        return itemTypeName;
    }

    public void setItemTypeName(String itemTypeName) {
        this.itemTypeName = itemTypeName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public String getImageInventory() {
        return imageInventory;
    }

    public void setImageInventory(String imageInventory) {
        this.imageInventory = imageInventory;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrlLarge() {
        return imageUrlLarge;
    }

    public void setImageUrlLarge(String imageUrlLarge) {
        this.imageUrlLarge = imageUrlLarge;
    }

    public String getItemSet() {
        return itemSet;
    }

    public void setItemSet(String itemSet) {
        this.itemSet = itemSet;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CosmeticItem)) return false;

        CosmeticItem item = (CosmeticItem) o;
        if ((item.getName() != null && name != null && item.getName().equals(name))
                || (item.getDefIndex() != 0 && defIndex != 0 && item.getDefIndex() == defIndex))
            return true;
        return false;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
