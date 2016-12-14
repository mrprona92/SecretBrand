package com.badr.infodota.base.menu.entity;

/**
 * Created by HungTD on 6/6/16.
 */
public class MenuItem {
    public int iconRes;
    public int titleRes;
    public int type;
    public boolean grayBg;

    public MenuItem(int titleRes, int iconRes, int type, boolean grayBg) {
        this.iconRes = iconRes;
        this.titleRes = titleRes;
        this.type = type;
        this.grayBg = grayBg;
    }
}
