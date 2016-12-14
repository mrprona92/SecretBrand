package com.badr.infodota.base.service.ti4;

import android.content.Context;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.base.api.ti4.PrizePoolHolder;

/**
 * User: ABadretdinov
 * Date: 14.05.14
 * Time: 18:55
 */
public class TI4ServiceImpl implements TI4Service {

    @Override
    public Long getPrizePool(Context context) {
        PrizePoolHolder result = BeanContainer.getInstance().getSteamService().getLeaguePrizePool(600);
        if (result == null) {
            return null;
        } else {
            return result.getResult().getPrizePool();
        }
    }
}
