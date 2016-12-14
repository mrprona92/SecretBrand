package com.badr.infodota.base.api.ti4;

import java.io.Serializable;

/**
 * User: ABadretdinov
 * Date: 14.05.14
 * Time: 18:46
 */
public class PrizePoolHolder implements Serializable {
    private PrizePool result;

    public PrizePool getResult() {
        return result;
    }

    public void setResult(PrizePool result) {
        this.result = result;
    }
}
