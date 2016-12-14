package com.badr.infodota.news.service;

import android.content.Context;

import com.badr.infodota.news.api.AppNews;

/**
 * User: Histler
 * Date: 21.04.14
 */
public interface NewsService {
    AppNews getNews(Context context, Long fromDate);
}
