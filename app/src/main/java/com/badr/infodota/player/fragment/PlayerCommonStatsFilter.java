package com.badr.infodota.player.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.badr.infodota.R;
import com.badr.infodota.player.activity.PlayerCommonStatsActivity;
import com.badr.infodota.player.api.Unit;

/**
 * User: ABadretdinov
 * Date: 18.02.14
 * Time: 17:32
 */
public class PlayerCommonStatsFilter extends Fragment {
    private static final String[] DATE = new String[]{
            "",
            "week",
            "month",
            "3month",
            "6month",
            "year",
            "patch_6.84",
            "patch_6.84b",
            "patch_6.84c"
    };
    private static final String[] GAME_MODE = new String[]{
            "",
            "ability_draft",
            "all_pick",
            "all_random",
            "captains_draft",
            "captains_mode",
            "least_player",
            "pool1",
            "random_draft",
            "single_draft",
            "all_random_deathmatch",
            "1v1_solo_mid"
    };
    private static final String[] LOBBY_TYPE = new String[]{
            "",
            "normal_matchmaking",
            "ranked_matchmaking",
            "team_matchmaking",
            "solo_matchmaking"
    };
    private static final String[] RESULT = new String[]{
            "",
            "won",
            "lost"
    };

    private Unit account;
    private Bundle args;

    public static PlayerCommonStatsFilter newInstance(Unit account) {
        PlayerCommonStatsFilter fragment = new PlayerCommonStatsFilter();
        fragment.account = account;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.player_common_stats_filters, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        args = new Bundle();
        args.putString("duration", "");
        View root = getView();
        Spinner byDateSpinner = (Spinner) root.findViewById(R.id.date);
        Spinner byGameModeSpinner = (Spinner) root.findViewById(R.id.game_mode);
        Spinner byLobbyTypeSpinner = (Spinner) root.findViewById(R.id.lobby_type);
        Spinner resultSpinner = (Spinner) root.findViewById(R.id.result);
        Button submit = (Button) root.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PlayerCommonStatsActivity.class);
                intent.putExtra("account", account);
                intent.putExtra("args", args);
                startActivity(intent);
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.statsCommon_date));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        byDateSpinner.setAdapter(adapter);
        byDateSpinner.setSelection(0);
        byDateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                args.putString("date", DATE[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.statsCommon_game_mode));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        byGameModeSpinner.setAdapter(adapter);
        byGameModeSpinner.setSelection(0);
        byGameModeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                args.putString("game_mode", GAME_MODE[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.statsCommon_lobby_type));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        byLobbyTypeSpinner.setAdapter(adapter);
        byLobbyTypeSpinner.setSelection(0);
        byLobbyTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                args.putString("lobby_type", LOBBY_TYPE[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.statsCommon_result));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        resultSpinner.setAdapter(adapter);
        resultSpinner.setSelection(0);
        resultSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                args.putString("result", RESULT[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}