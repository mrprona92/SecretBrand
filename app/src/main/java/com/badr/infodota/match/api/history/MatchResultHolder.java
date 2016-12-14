package com.badr.infodota.match.api.history;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * User: ABadretdinov
 * Date: 20.01.14
 * Time: 18:42
 */
public class MatchResultHolder implements Serializable {
    @SerializedName("result")
    private HistoryMatchResult historyMatchResult;

    public HistoryMatchResult getHistoryMatchResult() {
        return historyMatchResult;
    }

    public void setHistoryMatchResult(HistoryMatchResult historyMatchResult) {
        this.historyMatchResult = historyMatchResult;
    }
}
