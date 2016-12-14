package com.util.infoparser.api;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Badr on 25.08.2015.
 */
public class CosmeticItemsStrings implements Serializable {

    private String Language;

    private Map<String, String> Tokens;

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }

    public Map<String, String> getTokens() {
        return Tokens;
    }

    public void setTokens(Map<String, String> tokens) {
        Tokens = tokens;
    }
}
