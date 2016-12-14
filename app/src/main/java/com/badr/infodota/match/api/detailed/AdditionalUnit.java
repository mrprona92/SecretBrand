package com.badr.infodota.match.api.detailed;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * User: ABadretdinov
 * Date: 28.08.13
 * Time: 16:53
 */
public class AdditionalUnit implements Serializable {
    @SerializedName("unitname")
    private String name;
    @SerializedName("item_0")
    private int item0;
    private String item0dotaId;
    @SerializedName("item_1")
    private int item1;
    private String item1dotaId;
    @SerializedName("item_2")
    private int item2;
    private String item2dotaId;
    @SerializedName("item_3")
    private int item3;
    private String item3dotaId;
    @SerializedName("item_4")
    private int item4;
    private String item4dotaId;
    @SerializedName("item_5")
    private int item5;
    private String item5dotaId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getItem0() {
        return item0;
    }

    public void setItem0(int item0) {
        this.item0 = item0;
    }

    public int getItem1() {
        return item1;
    }

    public void setItem1(int item1) {
        this.item1 = item1;
    }

    public int getItem2() {
        return item2;
    }

    public void setItem2(int item2) {
        this.item2 = item2;
    }

    public int getItem3() {
        return item3;
    }

    public void setItem3(int item3) {
        this.item3 = item3;
    }

    public int getItem4() {
        return item4;
    }

    public void setItem4(int item4) {
        this.item4 = item4;
    }

    public int getItem5() {
        return item5;
    }

    public void setItem5(int item5) {
        this.item5 = item5;
    }

    public String getItem0dotaId() {
        return item0dotaId;
    }

    public void setItem0dotaId(String item0dotaId) {
        this.item0dotaId = item0dotaId;
    }

    public String getItem1dotaId() {
        return item1dotaId;
    }

    public void setItem1dotaId(String item1dotaId) {
        this.item1dotaId = item1dotaId;
    }

    public String getItem2dotaId() {
        return item2dotaId;
    }

    public void setItem2dotaId(String item2dotaId) {
        this.item2dotaId = item2dotaId;
    }

    public String getItem3dotaId() {
        return item3dotaId;
    }

    public void setItem3dotaId(String item3dotaId) {
        this.item3dotaId = item3dotaId;
    }

    public String getItem4dotaId() {
        return item4dotaId;
    }

    public void setItem4dotaId(String item4dotaId) {
        this.item4dotaId = item4dotaId;
    }

    public String getItem5dotaId() {
        return item5dotaId;
    }

    public void setItem5dotaId(String item5dotaId) {
        this.item5dotaId = item5dotaId;
    }
}
