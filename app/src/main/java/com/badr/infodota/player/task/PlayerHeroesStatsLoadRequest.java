package com.badr.infodota.player.task;

import android.content.Context;
import android.text.TextUtils;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.base.service.TaskRequest;
import com.badr.infodota.hero.api.Hero;
import com.badr.infodota.hero.service.HeroService;
import com.badr.infodota.player.activity.PlayerByHeroStatsActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by ABadretdinov
 * 20.08.2015
 * 16:08
 */

public class PlayerHeroesStatsLoadRequest extends TaskRequest<PlayerByHeroStatsActivity.PlayerHeroesStats> {
    private String url;
    private Context context;

    public PlayerHeroesStatsLoadRequest(Context context, String url) {
        super(PlayerByHeroStatsActivity.PlayerHeroesStats.class);
        this.context = context;
        this.url = url;
    }

    @Override
    public PlayerByHeroStatsActivity.PlayerHeroesStats loadData() throws Exception {
        HeroService heroService = BeanContainer.getInstance().getHeroService();
        PlayerByHeroStatsActivity.PlayerHeroesStats result = new PlayerByHeroStatsActivity.PlayerHeroesStats();
        Document doc = Jsoup.connect(url).get();
        Element table = doc.select("table").first();
        Element tableHeader = table.select("thead").first();
        Elements headers = tableHeader.select("th");
        result.horizontalHeaders = new ArrayList<>();
        for (Element elementHeader : headers) {
            String horizontalHeader = elementHeader.text();
            result.horizontalHeaders.add(horizontalHeader);
        }
        Element tableBody = table.select("tbody").first();
        Elements rows = tableBody.select("tr");
        result.heroResults = new LinkedHashMap<>();
        for (Element row : rows) {
            Hero hero = null;
            List<String> heroResults = new ArrayList<String>();
            if (TextUtils.isEmpty(row.attr("class"))) {
                Elements columns = row.select("td");
                for (Element column : columns) {
                    String className = column.attr("class");
                    if (!TextUtils.isEmpty(className)) {
                        if ("cell-xlarge".equals(className)) {
                            Element a = column.select("a").first();
                            String heroName = a.text();
                            List<Hero> possibleHeroes = heroService.getHeroesByName(context, heroName);
                            if (possibleHeroes != null && possibleHeroes.size() > 0) {
                                hero = possibleHeroes.get(0);
                                heroResults.add(hero.getLocalizedName());
                            }
                        } else if (!"cell-icon".equals(className)) {
                            String columnResult = column.text();
                            heroResults.add(columnResult);
                        }
                    } else {
                        String columnResult = column.text();
                        heroResults.add(columnResult);
                    }
                }
            }
            if (hero != null) {
                result.heroResults.put(hero, heroResults);
            }
        }
        return result;
    }
}
