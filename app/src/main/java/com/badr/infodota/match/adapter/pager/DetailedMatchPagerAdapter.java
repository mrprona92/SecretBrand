package com.badr.infodota.match.adapter.pager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.badr.infodota.R;
import com.badr.infodota.match.api.Player;
import com.badr.infodota.match.api.detailed.DetailedMatch;
import com.badr.infodota.match.fragment.MatchSummary;
import com.badr.infodota.match.fragment.MatchTeamPlayers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: ABadretdinov
 * Date: 21.01.14
 * Time: 15:05
 */
public class DetailedMatchPagerAdapter extends FragmentPagerAdapter {
    private Context context;
    //private Match match;
    private Map<Integer, MatchTeamPlayers> matchDetailsMap = new HashMap<Integer, MatchTeamPlayers>();
    private MatchSummary matchSummary;
    private DetailedMatch match = null;

    public DetailedMatchPagerAdapter(FragmentManager fragmentManager, Context context/*,Match match*/) {
        super(fragmentManager);
        /*this.match=match;*/
        this.context = context;
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;
        switch (i) {
            case 2:
                matchSummary = MatchSummary.newInstance(match);
                /*if(match!=null){
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("match",match);
                    matchSummary.setArguments(bundle);
                }*/
                fragment = matchSummary;
                break;
            case 0:
            case 1:
                fragment = MatchTeamPlayers.newInstance(match != null && match.getGame_mode() == 18,
                        match != null && match.getPlayers().size() == 10 ? match.getPlayers()
                                .subList(i * 5, i * 5 + 5) : null);
                matchDetailsMap.put(i, (MatchTeamPlayers) fragment);
                break;
        }
        return fragment;
    }

    /*@Override*/
    public int getIconResId(int index) {
        switch (index) {
            case 0:
                return R.drawable.radiant;
            case 1:
                return R.drawable.dire;
        }
        return 0;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.radiant);
            case 1:
                return context.getString(R.string.dire);
            case 2:
                return context.getString(R.string.common);
            default:
                return "";
        }
    }

    public void updateMatchDetails(DetailedMatch match) {
        this.match = match;
        for (Integer mdId : matchDetailsMap.keySet()) {
            MatchTeamPlayers matchDetails = matchDetailsMap.get(mdId);
            List<Player> currentPlayers;
            if (match.getPlayers().size() == 10) {
                currentPlayers = match.getPlayers().subList(mdId * 5, mdId * 5 + 5);
            } else {
                currentPlayers = new ArrayList<Player>();
                for (Player player : match.getPlayers()) {
                    if (player.getPlayerSlot() < 128 * (mdId + 1) && player.getPlayerSlot() >= 128 * mdId) {
                        currentPlayers.add(player);
                    }
                }
            }
            matchDetails.setPlayersWithListUpdate(match.getGame_mode() == 18, currentPlayers);
        }
        if (matchSummary != null) {
            matchSummary.updateWithMatchInfo(match);
        }
    }
}
