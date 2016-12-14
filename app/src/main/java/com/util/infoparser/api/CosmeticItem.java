package com.util.infoparser.api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ABadretdinov
 * 24.06.2015
 * 10:58
 */
public class CosmeticItem implements Serializable {
    private String id;
    private String name;
    private String prefab;
    @SerializedName("creation_name")
    private String creationDate;
    @SerializedName("image_inventory")
    private String imageInventory;

    private String imageUrl;

    @SerializedName("item_description")
    private String description;

    @SerializedName("item_rarity")
    private String rarity;

    @SerializedName("expiration_date")
    private String expirationDate;

    @SerializedName("tournament_url")
    private String tournamentUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrefab() {
        return prefab;
    }

    public void setPrefab(String prefab) {
        this.prefab = prefab;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getTournamentUrl() {
        return tournamentUrl;
    }

    public void setTournamentUrl(String tournamentUrl) {
        this.tournamentUrl = tournamentUrl;
    }
}
