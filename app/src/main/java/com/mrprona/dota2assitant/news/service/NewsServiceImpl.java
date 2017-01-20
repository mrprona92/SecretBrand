package com.mrprona.dota2assitant.news.service;

import android.content.Context;

import com.mrprona.dota2assitant.BeanContainer;
import com.mrprona.dota2assitant.news.api.AppNews;
import com.mrprona.dota2assitant.news.api.AppNewsHolder;

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
