package com.badr.infodota.stream.api.twitch;

import java.io.Serializable;

/**
 * User: Histler
 * Date: 27.02.14
 */
public class TwitchAccessToken implements Serializable {
    private String token;
    private String sig;

    public String getSig() {
        return sig;
    }

    public void setSig(String sig) {
        this.sig = sig;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
