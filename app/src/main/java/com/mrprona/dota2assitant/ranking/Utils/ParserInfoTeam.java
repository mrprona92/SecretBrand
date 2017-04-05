package com.mrprona.dota2assitant.ranking.Utils;

import com.mrprona.dota2assitant.ranking.PlayerRanking;
import com.mrprona.dota2assitant.ranking.TeamRanking;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by BinhTran on 3/15/17.
 */

public class ParserInfoTeam {

    public static List<TeamRanking> teamRankingListCached = new ArrayList<>();

    public static List<PlayerRanking> playerRankingListCached = new ArrayList<>();


    public static List<TeamRanking> getAllTeamRankCached() {
        return teamRankingListCached;
    }

    public static List<PlayerRanking> getAllPlayerRankCached() {
        return playerRankingListCached;
    }

    public static List<TeamRanking> getAllTeamRanked(String url) {

        List<TeamRanking> mTeamRankingList = new ArrayList<>();

        teamRankingListCached.clear();

        Document doc = null;
        try {
            doc = Jsoup.connect(url).userAgent("Mozilla/5.0 AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.4 Safari/537.36").timeout(10*1000).get();
            Elements links = doc.select("a[href]");
            Elements media = doc.select("[src]");
            Elements imports = doc.select("link[href]");
            Element table = doc.select("table").get(0); //select the first table.
            Elements rows = table.select("tr");

            for (int i = 0; i < rows.size() - 1; i++) { //first row is the col names so skip it.
                TeamRanking tempTeamRanking = new TeamRanking();

                Element row = rows.get(i);
                Elements cols = row.select("td");
                String tempString = doc.select("h4").get(i).getElementsByAttribute("title").attr("class");
                int indexSpace = 0;
                for (int j = 0; j < tempString.length(); j++) {
                    if (tempString.charAt(j) == ' ') {
                        indexSpace = j;
                        break;
                    }
                }
                tempTeamRanking.setFlagID(tempString.substring(indexSpace + 1, tempString.length()).toLowerCase());

                for (int j = 0; j <= 3; j++) {
                    if (j == 0) {
                        //Substring team ranking
                        String tmpTeamRanking= cols.get(j).getElementsByAttribute("class").get(1).className();
                        tempTeamRanking.setRankCurrent(tmpTeamRanking.substring(12,tmpTeamRanking.length()));
                    } else if (j == 1) {
                        //Team name parser
                        tempTeamRanking.setTeamName(cols.get(j).text());
                    } else if (j == 2) {
                        //Number ranking
                        tempTeamRanking.setNumberRanking(cols.get(j).text());
                    } else if (j == 3) {
                        //Status change ranking
                        tempTeamRanking.setChangeRanking(cols.get(j).getElementsByAttribute("title").attr("title"));
                    }
                }
                mTeamRankingList.add(tempTeamRanking);
                teamRankingListCached.add(tempTeamRanking);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mTeamRankingList;
    }



    //TODO
    public static List<PlayerRanking> getAllPlayerRanked(String url) {

        List<PlayerRanking> mPlayerRankingList = new ArrayList<>();

        playerRankingListCached.clear();

        Document doc = null;
        try {
            doc = Jsoup.connect(url).timeout(10*1000).get();
            Element table = doc.select("table").get(0); //select the first table.
            Elements rows = table.select("tr");
            Elements eles = table.select("tbody > tr > td");
            for (Element ele : eles) {
                System.out.println(ele.toString());

            }
            for (int i = 0; i < rows.size() - 1; i++) { //first row is the col names so skip it.
                PlayerRanking tempPlayerRanking = new PlayerRanking();

                Element row = rows.get(i);
                Elements cols = row.select("td");
                String tempString = doc.select("h4").get(i).getElementsByAttribute("title").attr("class");
                int indexSpace = 0;
                for (int j = 0; j < tempString.length(); j++) {
                    if (tempString.charAt(j) == ' ') {
                        indexSpace = j;
                        break;
                    }
                }
                tempPlayerRanking.setFlagID(tempString.substring(indexSpace + 1, tempString.length()).toLowerCase());

                for (int j = 0; j <= 3; j++) {
                    if (j == 0) {
                        //Substring team ranking
                        String tmpTeamRanking= cols.get(j).getElementsByAttribute("class").get(1).className();
                        tempPlayerRanking.setRankCurrent(tmpTeamRanking.substring(12,tmpTeamRanking.length()));
                    } else if (j == 1) {
                        //Player name parser
                        tempPlayerRanking.setPlayerName(cols.get(j).text());
                    } else if (j == 2) {
                        //Number ranking
                        tempPlayerRanking.setNumberRanking(cols.get(j).text());
                    }
                }
                mPlayerRankingList.add(tempPlayerRanking);
                playerRankingListCached.add(tempPlayerRanking);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mPlayerRankingList;
    }


}
