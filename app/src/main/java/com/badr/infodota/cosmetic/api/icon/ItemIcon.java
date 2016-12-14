package com.badr.infodota.cosmetic.api.icon;

import java.io.Serializable;

/**
 * Created by ABadretdinov
 * 24.06.2015
 * 12:00
 */
public class ItemIcon implements Serializable {
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
