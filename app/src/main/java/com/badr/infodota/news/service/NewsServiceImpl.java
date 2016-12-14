package com.badr.infodota.news.service;

import android.content.Context;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.news.api.AppNews;
import com.badr.infodota.news.api.AppNewsHolder;

/**
 * User: Histler
 * Date: 21.04.14
 */
public class NewsServiceImpl implements NewsService {

    @Override
    public AppNews getNews(Context context, Long fromDate) {
        AppNewsHolder result = BeanContainer.getInstance().getSteamService().getNews(fromDate);
        if (result != null) {
            return result.getAppNews();
        }
        return null;
    }
}
