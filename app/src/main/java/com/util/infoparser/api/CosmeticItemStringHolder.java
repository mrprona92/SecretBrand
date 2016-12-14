package com.util.infoparser.api;

import java.io.Serializable;

/**
 * Created by Badr on 25.08.2015.
 */
public class CosmeticItemStringHolder implements Serializable {
    private CosmeticItemsStrings lang;

    public CosmeticItemsStrings getLang() {
        return lang;
    }

    public void setLang(CosmeticItemsStrings lang) {
        this.lang = lang;
    }
}
