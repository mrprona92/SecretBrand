package com.mrprona.dota2assitant.hero.api;

import com.mrprona.dota2assitant.base.entity.HasId;

import java.io.Serializable;

/**
 * Created by BinhTran on 12/20/16.
 */

public class TalentTree implements Serializable, HasId {
    private long id;
    private String name;
    private String lv25Left;
    private String lv20Left;
    private String lv15Left;
    private String lv10Left;
    private String lv25Right;
    private String lv20Right;
    private String lv15Right;
    private String lv10Right;

    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {

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

    public String getLv25Left() {
        return lv25Left;
    }

    public void setLv25Left(String lv25Left) {
        this.lv25Left = lv25Left;
    }

    public String getLv20Left() {
        return lv20Left;
    }

    public void setLv20Left(String lv20Left) {
        this.lv20Left = lv20Left;
    }

    public String getLv15Left() {
        return lv15Left;
    }

    public void setLv15Left(String lv15Left) {
        this.lv15Left = lv15Left;
    }

    public String getLv10Left() {
        return lv10Left;
    }

    public void setLv10Left(String lv10Left) {
        this.lv10Left = lv10Left;
    }

    public String getLv25Right() {
        return lv25Right;
    }

    public void setLv25Right(String lv25Right) {
        this.lv25Right = lv25Right;
    }

    public String getLv20Right() {
        return lv20Right;
    }

    public void setLv20Right(String lv20Right) {
        this.lv20Right = lv20Right;
    }

    public String getLv15Right() {
        return lv15Right;
    }

    public void setLv15Right(String lv15Right) {
        this.lv15Right = lv15Right;
    }

    public String getLv10Right() {
        return lv10Right;
    }

    public void setLv10Right(String lv10Right) {
        this.lv10Right = lv10Right;
    }
}
