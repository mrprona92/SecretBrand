package com.badr.infodota.item.api;

import com.badr.infodota.base.entity.HasId;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collection;

/**
 * User: Histler
 * Date: 18.01.14
 */
public class Item implements Comparable, HasId {

    private long id;
    private String dotaId;
    @SerializedName("dname")
    private String dotaName;
    @SerializedName("qual")
    private String quality;
    private String img;
    private int cost;
    @SerializedName("desc")
    private String description;
    private String notes;
    private String attrib;
    @SerializedName("mc")
    private Object manaCost;
    @SerializedName("cd")
    private Object cooldown;
    private String lore;
    private String[] components;
    private boolean created;
    private String type;

    public Item() {
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDotaName() {
        return dotaName;
    }

    public void setDotaName(String dotaName) {
        this.dotaName = dotaName;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getAttrib() {
        return attrib;
    }

    public void setAttrib(String attrib) {
        this.attrib = attrib;
    }

    public Object getManaCost() {
        return manaCost;
    }

    public void setManaCost(Object manaCost) {
        this.manaCost = manaCost;
    }

    public Object getCooldown() {
        return cooldown;
    }

    public void setCooldown(Object cooldown) {
        this.cooldown = cooldown;
    }

    public String getLore() {
        return lore;
    }

    public void setLore(String lore) {
        this.lore = lore;
    }

    public String[] getComponents() {
        return components;
    }

    public void setComponents(String[] components) {
        this.components = components;
    }

    public boolean isCreated() {
        return created;
    }

    public void setCreated(boolean created) {
        this.created = created;
    }

    public String getDotaId() {
        return dotaId;
    }

    public void setDotaId(String dotaId) {
        this.dotaId = dotaId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int compareTo(Object another) {
        if (!(another instanceof Item)) {
            return 1;
        }
        Item anItem = (Item) another;
        if (anItem.equals(this))
            return 0;
        return anItem.getDotaId().compareTo(getDotaId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;

        Item item = (Item) o;

        if (id != item.id) return false;
        if (dotaName != null ? !dotaName.equals(item.dotaName) : item.dotaName != null)
            return false;
        if (!dotaId.equals(item.dotaId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) id;
        result = 31 * result + dotaId.hashCode();
        result = 31 * result + (dotaName != null ? dotaName.hashCode() : 0);
        return result;
    }

    public static class List extends ArrayList<Item> {
        public List(Collection<? extends Item> collection) {
            super(collection);
        }
    }
}
