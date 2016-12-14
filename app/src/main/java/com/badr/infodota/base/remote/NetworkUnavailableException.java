package com.badr.infodota.base.remote;

/**
 * User: ABadretdinov
 * Date: 02.04.14
 * Time: 10:58
 */
public class NetworkUnavailableException extends Exception {
    public NetworkUnavailableException() {
    }

    public NetworkUnavailableException(String detailMessage) {
        super(detailMessage);
    }

    public NetworkUnavailableException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public NetworkUnavailableException(Throwable throwable) {
        super(throwable);
    }

}
