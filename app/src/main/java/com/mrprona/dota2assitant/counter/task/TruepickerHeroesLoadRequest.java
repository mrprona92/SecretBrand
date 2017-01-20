package com.mrprona.dota2assitant.counter.task;

import android.content.Context;

import com.mrprona.dota2assitant.BeanContainer;
import com.mrprona.dota2assitant.base.service.TaskRequest;
import com.mrprona.dota2assitant.counter.api.TruepickerHero;
import com.mrprona.dota2assitant.counter.service.CounterService;

/**
 * Created by ABadretdinov
 * 20.08.2015
 * 16:52
 */
public class TruepickerHeroesLoadRequest extends TaskRequest<TruepickerHero.List> {
    private String mFilter;
    private Context mContext;

    public TruepickerHeroesLoadRequest(Context context, String filter) {
        super(TruepickerHero.List.class);
        this.mFilter = filter;
        this.mContext = context;
    }

    @Override
    public TruepickerHero.List loadData() throws Exception {
        CounterService counterService = BeanContainer.getInstance().getCounterService();
        return counterService.getTruepickerHeroes(mContext, mFilter);
    }
}
