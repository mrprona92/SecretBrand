package com.mrprona.dota2assitant.news.task;

import android.content.Context;

import com.mrprona.dota2assitant.BeanContainer;
import com.mrprona.dota2assitant.base.service.TaskRequest;
import com.mrprona.dota2assitant.news.api.AppNews;
import com.mrprona.dota2assitant.news.service.NewsService;

/**
 * Created by ABadretdinov
 * 20.08.2015
 * 15:53
 */
public class NewsLoadRequest extends TaskRequest<AppNews> {
    private Long mFromDate;
    private Context mContext;

    public NewsLoadRequest(Context context, Long fromDate) {
        super(AppNews.class);
        this.mContext = context;
        this.mFromDate = fromDate;
    }

    @Override
    public AppNews loadData() throws Exception {

        BeanContainer container = BeanContainer.getInstance();
        NewsService newsService = container.getNewsService();
        return newsService.getNews(mContext, mFromDate);
    }
}
