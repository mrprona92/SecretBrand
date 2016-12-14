package com.badr.infodota.hero.api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * User: Histler
 * Date: 16.01.14
 */
public class Skill implements Serializable {
    private int id;
    private String name;
    @SerializedName("dname")
    private String dotaName;
    private String affects;
    private String attrib;
    private String notes;
    private String dmg;
    private String cmb;
    private String lore;
    private String hurl;
    private String desc;
    private String youtube;

    public Skill() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDotaName() {
        return dotaName;
    }

    public void setDotaName(String dotaName) {
        this.dotaName = dotaName;
    }

    public String getAffects() {
        return affects;
    }

    public void setAffects(String affects) {
        this.affects = affects;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDmg() {
        return dmg;
    }

    public void setDmg(String dmg) {
        this.dmg = dmg;
    }

    public String getCmb() {
        return cmb;
    }

    public void setCmb(String cmb) {
        this.cmb = cmb;
    }

    public String getLore() {
        return lore;
    }

    public void setLore(String lore) {
        this.lore = lore;
    }

    public String getHurl() {
        return hurl;
    }

    public void setHurl(String hurl) {
        this.hurl = hurl;
    }

    public String getAttrib() {
        return attrib;
    }

    public void setAttrib(String attrib) {
        this.attrib = attrib;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    @Override
    public String toString() {
        return name;
    }

    public static class List extends ArrayList<Skill> {

    }
}
