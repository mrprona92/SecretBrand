package com.badr.infodota.cosmetic.api.player;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * User: ABadretdinov
 * Date: 31.03.14
 * Time: 17:06
 */
public class PlayerCosmeticItem implements Serializable {
    private long id;
    @SerializedName("original_id")
    private long originalId;
    @SerializedName("defindex")
    private long defIndex;
    private int level;
    private int quality;
    private long inventory;
    private int quantity;
    private List<Equipped> equipped;
    private int style;
    @SerializedName("custom_desc")
    private String customDescription;
    private List<Attribute> attributes;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOriginalId() {
        return originalId;
    }

    public void setOriginalId(long originalId) {
        this.originalId = originalId;
    }

    public long getDefIndex() {
        return defIndex;
    }

    public void setDefIndex(long defIndex) {
        this.defIndex = defIndex;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public long getInventory() {
        return inventory;
    }

    public void setInventory(long inventory) {
        this.inventory = inventory;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<Equipped> getEquipped() {
        return equipped;
    }

    public void setEquipped(List<Equipped> equipped) {
        this.equipped = equipped;
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public String getCustomDescription() {
        return customDescription;
    }

    public void setCustomDescription(String customDescription) {
        this.customDescription = customDescription;
    }
}
