package com.badr.infodota.match.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import com.badr.infodota.base.fragment.RecyclerFragment;
import com.badr.infodota.base.util.Utils;
import com.badr.infodota.match.activity.MatchPlayerDetailsActivity;
import com.badr.infodota.match.adapter.MatchPlayersAdapter;
import com.badr.infodota.match.adapter.holder.MatchPlayerHolder;
import com.badr.infodota.match.api.Player;

import java.util.List;

/**
 * User: ABadretdinov
 * Date: 21.01.14
 * Time: 15:32
 */
public class MatchTeamPlayers extends RecyclerFragment<Player, MatchPlayerHolder> {
    private List<Player> players;
    private boolean randomSkills;

    public static MatchTeamPlayers newInstance(boolean isRandomSkills, List<Player> players) {
        MatchTeamPlayers fragment = new MatchTeamPlayers();
        fragment.setPlayers(players);
        fragment.setRandomSkills(isRandomSkills);
        return fragment;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void setRandomSkills(boolean randomSkills) {
        this.randomSkills = randomSkills;
    }

    public void setPlayersWithListUpdate(boolean randomSkills, List<Player> players) {
        this.players = players;
        this.randomSkills = randomSkills;
        Context context = getActivity();
        if (context != null) {
            setAdapter(new MatchPlayersAdapter(players, Utils.getDeviceState(context)));
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Player player = getAdapter().getItem(position);
        Intent intent = new Intent(getActivity(), MatchPlayerDetailsActivity.class);
        intent.putExtra("player", player);
        intent.putExtra("randomSkills", randomSkills);
        startActivity(intent);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Activity activity = getActivity();
        if (players != null && activity != null) {
            setAdapter(new MatchPlayersAdapter(players, Utils.getDeviceState(activity)));
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        MatchPlayersAdapter adapter = (MatchPlayersAdapter) getAdapter();
        Activity activity = getActivity();
        if (adapter != null && activity != null) {
            adapter.notifyStateChanged(Utils.getDeviceState(activity));
        }
    }

}
