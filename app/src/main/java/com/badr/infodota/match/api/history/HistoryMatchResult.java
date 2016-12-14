package com.badr.infodota.match.api.history;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * User: ABadretdinov
 * Date: 28.08.13
 * Time: 15:03
 */
public class HistoryMatchResult implements Serializable {
    private int status;
    private String statusDetail;
    @SerializedName("num_results")
    private long numberOfResults;
    @SerializedName("total_results")
    private long totalResults;
    @SerializedName("results_remaining")
    private long resultsRemaining;
    @SerializedName("matches")
    private List<HistoryMatch> historyMatches;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusDetail() {
        return statusDetail;
    }

    public void setStatusDetail(String statusDetail) {
        this.statusDetail = statusDetail;
    }

    public long getNumberOfResults() {
        return numberOfResults;
    }

    public void setNumberOfResults(long numberOfResults) {
        this.numberOfResults = numberOfResults;
    }

    public long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(long totalResults) {
        this.totalResults = totalResults;
    }

    public long getResultsRemaining() {
        return resultsRemaining;
    }

    public void setResultsRemaining(long resultsRemaining) {
        this.resultsRemaining = resultsRemaining;
    }

    public List<HistoryMatch> getHistoryMatches() {
        return historyMatches;
    }

    public void setHistoryMatches(List<HistoryMatch> historyMatches) {
        this.historyMatches = historyMatches;
    }
}
