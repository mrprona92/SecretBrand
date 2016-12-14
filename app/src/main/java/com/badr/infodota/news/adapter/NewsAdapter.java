package com.badr.infodota.news.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.badr.infodota.R;
import com.badr.infodota.base.adapter.BasePageableRecyclerAdapter;
import com.badr.infodota.news.api.NewsItem;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * User: ABadretdinov
 * Date: 21.04.14
 * Time: 18:30
 */
public class NewsAdapter extends BasePageableRecyclerAdapter<NewsItem, NewsItemViewHolder> {
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm  dd.MM.yyyy");

    public NewsAdapter(List<NewsItem> newsItems) {
        super(newsItems);
        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();
        sdf.setTimeZone(tz);
    }
/*
    public void addNewsItems(List<NewsItem> newsItems) {
        if (newsItems != null) {
            for (NewsItem newsItem : newsItems) {
                if (!this.newsItems.contains(newsItem)) {
                    this.newsItems.add(newsItem);
                }
            }
            Collections.sort(this.newsItems);
        }
        notifyDataSetChanged();
    }*/

    @Override
    public NewsItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_row, parent, false);
        return new NewsItemViewHolder(view, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(NewsItemViewHolder holder, int position) {
        NewsItem item = getItem(position);
        holder.title.setText(item.getTitle());
        String author;
        if (TextUtils.isEmpty(item.getAuthor())) {
            author = item.getFeedLabel();
        } else {
            author = item.getAuthor() + " (" + item.getFeedLabel() + ")";
        }
        holder.author.setText(author);
        long timestamp = item.getDate();
        String localTime = sdf.format(new Date(timestamp * 1000));
        holder.date.setText(localTime);
    }
}
