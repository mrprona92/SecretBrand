package com.badr.infodota.match.api.team;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * User: ABadretdinov
 * Date: 15.05.14
 * Time: 17:01
 */
public class LogoUrlHolder implements Serializable {
    @SerializedName("data")
    private LogoUrl logoUrl;

    public LogoUrl getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(LogoUrl logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getUrl() {
        if (logoUrl != null) {
            return logoUrl.getUrl();
        }
        return null;
    }
}
