package com.badr.infodota.stream.api.douyu;

import java.io.Serializable;

/**
 * Created by ABadretdinov
 * 26.05.2015
 * 14:51
 */
public class Server implements Serializable {
    private String ip;
    private String port;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
