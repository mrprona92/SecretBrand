package com.badr.infodota.hero.api.guide.custom;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * User: Histler
 * Date: 28.01.14
 */
public class Guide implements Serializable {
    @SerializedName("Hero")
    private String hero;
    @SerializedName("Title")
    private String title;
    @SerializedName("GuideRevision")
    private String guideRevision;
    @SerializedName("FileVersion")
    private String fileVersion;
    @SerializedName("ItemBuild")
    private ItemBuild itemBuild;
    @SerializedName("AbilityBuild")
    private AbilityBuild abilityBuild;

    public String getHero() {
        return hero;
    }

    public void setHero(String hero) {
        this.hero = hero;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGuideRevision() {
        return guideRevision;
    }

    public void setGuideRevision(String guideRevision) {
        this.guideRevision = guideRevision;
    }

    public String getFileVersion() {
        return fileVersion;
    }

    public void setFileVersion(String fileVersion) {
        this.fileVersion = fileVersion;
    }

    public ItemBuild getItemBuild() {
        return itemBuild;
    }

    public void setItemBuild(ItemBuild itemBuild) {
        this.itemBuild = itemBuild;
    }

    public AbilityBuild getAbilityBuild() {
        return abilityBuild;
    }

    public void setAbilityBuild(AbilityBuild abilityBuild) {
        this.abilityBuild = abilityBuild;
    }
}
