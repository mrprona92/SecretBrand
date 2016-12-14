package com.badr.infodota.counter.remote;

import android.content.Context;
import android.util.Pair;

import com.badr.infodota.base.remote.BaseRemoteService;
import com.badr.infodota.counter.api.Counter;

import java.util.List;

/**
 * User: ABadretdinov
 * Date: 02.04.14
 * Time: 14:38
 */
public interface CounterRemoteEntityService extends BaseRemoteService {
    Pair<List<Counter>, String> getCounters(
            Context context,
            List<Integer> allies,
            List<Integer> enemies,
            int roleCode) throws Exception;
}
