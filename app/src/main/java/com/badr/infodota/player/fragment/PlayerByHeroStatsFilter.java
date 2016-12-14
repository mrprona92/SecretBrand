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
import com.badr.infodota.player.activity.PlayerByHeroStatsActivity;
import com.badr.infodota.player.api.Unit;

/**
 * User: ABadretdinov
 * Date: 19.02.14
 * Time: 14:06
 */
public class PlayerByHeroStatsFilter extends Fragment {
    private static final String[] DATE = new String[]{
            "",
            "month",
            "week",
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
    /* private static final String[] MATCH_TYPE=new String[]{
             "",
             "real",
             "unreal"
     };*/
    private static final String[] METRIC = new String[]{
            "played",
            "winning",
            "impact",
            "economy"
    };
    private Unit account;
    private Intent intent;

    public static PlayerByHeroStatsFilter newInstance(Unit account) {
        PlayerByHeroStatsFilter fragment = new PlayerByHeroStatsFilter();
        fragment.account = account;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.player_by_hero_stats_filters, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        intent = new Intent(getActivity(), PlayerByHeroStatsActivity.class);
        intent.putExtra("account", account);
        View root = getView();
        Spinner byDateSpinner = (Spinner) root.findViewById(R.id.date);
        Spinner byGameModeSpinner = (Spinner) root.findViewById(R.id.game_mode);
        //Spinner byMatchTypeSpinner= (Spinner)root.findViewById(R.id.match_type);
        Spinner byMetricSpinner = (Spinner) root.findViewById(R.id.metric);
        Button submit = (Button) root.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.statsByHero_date));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        byDateSpinner.setAdapter(adapter);
        byDateSpinner.setSelection(0);
        byDateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                intent.putExtra("date", DATE[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.statsByHero_game_mode));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        byGameModeSpinner.setAdapter(adapter);
        byGameModeSpinner.setSelection(0);
        byGameModeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                intent.putExtra("game_mode", GAME_MODE[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

		/*adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.statsByHero_match_type));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		byMatchTypeSpinner.setAdapter(adapter);
		byMatchTypeSpinner.setSelection(0);
		byMatchTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
			{
				intent.putExtra("match_type",MATCH_TYPE[position]);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{

			}
		});*/

        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.statsByHero_metric));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        byMetricSpinner.setAdapter(adapter);
        byMetricSpinner.setSelection(0);
        byMetricSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                intent.putExtra("metric", METRIC[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
