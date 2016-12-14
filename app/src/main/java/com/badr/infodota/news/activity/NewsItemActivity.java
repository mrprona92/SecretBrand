package com.badr.infodota.news.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.badr.infodota.R;
import com.badr.infodota.base.activity.BaseActivity;
import com.badr.infodota.base.util.web.URLImageParser;
import com.badr.infodota.news.api.NewsItem;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * User: Histler
 * Date: 21.04.14
 */
public class NewsItemActivity extends BaseActivity {
    NewsItem newsItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_item);
        Bundle intent = getIntent().getExtras();
        if (intent != null && intent.containsKey("newsItem")) {
            newsItem = (NewsItem) intent.get("newsItem");
            TextView content = (TextView) findViewById(R.id.content);
            TextView author = (TextView) findViewById(R.id.author);
            TextView title = (TextView) findViewById(R.id.title);
            TextView date = (TextView) findViewById(R.id.date);
            Button viewOriginal = (Button) findViewById(R.id.original);
            content.setText(Html.fromHtml(newsItem.getContents(), new URLImageParser(content, this), null));
            author.setText(!TextUtils.isEmpty(newsItem.getAuthor()) ? newsItem.getAuthor() : newsItem.getFeedLabel());
            title.setText(newsItem.getTitle());
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm  dd.MM.yyyy");
            Calendar cal = Calendar.getInstance();
            TimeZone tz = cal.getTimeZone();
            sdf.setTimeZone(tz);
            long timestamp = newsItem.getDate();
            String localTime = sdf.format(new Date(timestamp * 1000));
            date.setText(localTime);
            viewOriginal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent inBrowser = new Intent(Intent.ACTION_VIEW);
                    inBrowser.setData(Uri.parse(newsItem.getUrl()));
                    startActivity(inBrowser);
                }
            });
        } else {
            finish();
        }
    }
}
