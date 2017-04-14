package com.util.infoparser.loader;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.mrprona.dota2assitant.BeanContainer;
import com.mrprona.dota2assitant.base.api.Constants;
import com.mrprona.dota2assitant.base.service.TaskRequest;
import com.mrprona.dota2assitant.base.util.FileUtils;
import com.mrprona.dota2assitant.hero.api.Hero;
import com.mrprona.dota2assitant.hero.api.responses.HeroResponse;
import com.mrprona.dota2assitant.hero.api.responses.HeroResponsesSection;
import com.mrprona.dota2assitant.hero.service.HeroService;
import com.mrprona.dota2assitant.item.api.Item;
import com.mrprona.dota2assitant.item.service.ItemService;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ABadretdinov
 * 23.06.2015
 * 16:07
 */
public class ResponseLoadRequest extends TaskRequest<String> {
    HeroService heroService = BeanContainer.getInstance().getHeroService();
    ItemService itemService = BeanContainer.getInstance().getItemService();
    private Context mContext;

    public ResponseLoadRequest(Context context) {
        super(String.class);
        this.mContext = context;
    }

    @Override
    public String loadData() throws Exception {
        List<Hero> heroes = heroService.getAllHeroes(mContext);
        for (Hero hero : heroes) {
            if(hero.getId()==113||hero.getId()==114||hero.getId()==108){
                List<HeroResponsesSection> responses = loadHeroResponses(hero);
                FileUtils.saveJsonFile(Environment.getExternalStorageDirectory().getPath() + "/dota/" + hero.getDotaId() + "/responses.json", responses);
                Log.d("BINH", Environment.getExternalStorageDirectory().getPath() + "/dota/" + hero.getDotaId() + "/responses.json"+"saved");
            }
        }
        return "";
    }

    /*do not use /ru, otherwise you won't have items*/
    private List<HeroResponsesSection> loadHeroResponses(Hero hero) throws Exception {
        String heroName = hero.getLocalizedName().replace("'", "%27").replace(' ', '_');

        String url = MessageFormat.format(Constants.Heroes.DOTA2_WIKI_RESPONSES_URL, heroName);

        if(hero.getId()==114){
            url="http://dota2.gamepedia.com/Monkey_King/Responses";
        }

        System.out.println("hero url: " + url);
        Document doc = Jsoup.connect(url).get();
        Element content = doc.select("div#content").first();
        Elements spanList = content.select("span[class=mw-headline]");
        List<HeroResponsesSection> heroResponsesList = new ArrayList<>();
        String code=null;
        if (spanList != null && !spanList.isEmpty()) {
            Element firstH2 = spanList.first().parent(); //h2
            spanList=null;
            doc=null;
            if (firstH2 != null) {
                Elements contentChildren = content.children();
                int index = contentChildren.indexOf(firstH2);
                boolean isH2 = true;
                if(index<0){//mb change for while index<0 and change from first to get(i)
                    contentChildren=contentChildren.get(1).children();
                    contentChildren=contentChildren.get(0).children();
                    index=contentChildren.indexOf(firstH2);
                }
                content=null;
                for (int i = index, size = contentChildren.size() - 1; i < size && isH2; i++) {
                    Element h2 = contentChildren.get(i);
                    if ("h2".equals(h2.tagName())) {
                        Element ul = contentChildren.get(i+1).select("ul").first();
                        String sectionName=h2.child(1).text();
                        if(ul!=null) {
                            i++;
                            HeroResponsesSection section = new HeroResponsesSection();
                            section.setCode(code);
                            section.setName(sectionName);
                            Elements headerResponses = ul.select("li");
                            section.setResponses(new ArrayList<HeroResponse>());
                            for (Element elResponse : headerResponses) {
                                Elements alist = elResponse.select("a[title=Play]");
                                if (alist != null && !alist.isEmpty()) {
                                    Element aWithLink = alist.first();
                                    HeroResponse response2 = getHeroResponse2(aWithLink);
                                    if (response2 != null) {
                                        section.getResponses().add(response2);
                                    }
                                }
                            }
                            heroResponsesList.add(section);
                        }
                        else {
                            code=sectionName;
                        }
                    }
                    else if("h1".equals(h2.tagName())) { //h1 and other h2s are in the different div
                        code=h2.child(0).text();
                    }
                    else {
                        isH2 = false;
                    }
                }
            }
        } else {
            HeroResponsesSection section = new HeroResponsesSection();
            section.setName("All responses");
            section.setResponses(new ArrayList<HeroResponse>());
            Elements elements = doc.select("a[title=Play]");
            for(Element element:elements){
                HeroResponse response2 = getHeroResponse2(element);
                if(response2!=null){
                    section.getResponses().add(response2);
                }
            }
            heroResponsesList.add(section);
        }
        return heroResponsesList;
    }

    private HeroResponse getHeroResponse2(Element aWithLink) {
        HeroResponse response2 = null;
        if (aWithLink != null && aWithLink.hasAttr("href")) {
            Element elResponse = aWithLink.parent();
            response2 = new HeroResponse();
            response2.setUrl(aWithLink.attr("href"));
            String title = null;
            List<TextNode> nodes = elResponse.textNodes();
            if (nodes.size() > 0) {
                title = nodes.get(nodes.size() - 1).toString();
            }
            response2.setTitle(title);
            response2.setHeroes(new ArrayList<String>());
            response2.setItems(new ArrayList<String>());
            Elements children = elResponse.children();
            for (int j = 1, elResponseChildSize = children.size(); j < elResponseChildSize; j++) {
                Element child = children.get(j);
                String extraFieldName = child.attr("title");
                if (extraFieldName.contains("Runes")) {
                    Element img = child.select("img").first();
                    String imgAlt = img.attr("alt");
                    if (imgAlt.contains("Double Damage")) {
                        response2.setRune("dd");
                    } else if (imgAlt.contains("Haste")) {
                        response2.setRune("haste");
                    } else if (imgAlt.contains("Illusion")) {
                        response2.setRune("illus");
                    } else if (imgAlt.contains("Invisibility")) {
                        response2.setRune("invis");
                    } else if (imgAlt.contains("Regeneration")) {
                        response2.setRune("regen");
                    }
                } else {
                    Hero heroeWithThisName = heroService.getExactHeroByName(mContext, extraFieldName);
                    if (heroeWithThisName!= null) {
                        response2.getHeroes().add(heroeWithThisName.getDotaId());
                    } else {
                        List<Item> itemsWithThisName = itemService.getItemsByName(mContext, extraFieldName);
                        if (itemsWithThisName != null && itemsWithThisName.size() > 0) {
                            response2.getItems().add(itemsWithThisName.get(0).getDotaId());
                        }
                    }
                }
            }
        }
        return response2;
    }

}
