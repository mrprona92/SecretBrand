package com.util.infoparser.remote;

import android.content.Context;
import android.util.Pair;

import com.badr.infodota.base.remote.BaseRemoteServiceImpl;

import java.lang.reflect.Type;

/**
 * Created by ABadretdinov
 * 23.06.2015
 * 16:44
 */
public class URLRemoteService extends BaseRemoteServiceImpl {
    public <T> Pair<T,String> loadJson(Context context,String url, Type classType) throws Exception{
        return basicRequestSend(context,url,classType);
    }

    public String loadResult(Context context,String url) throws Exception{
        return basicRequestSend(context,url).first;
    }
}