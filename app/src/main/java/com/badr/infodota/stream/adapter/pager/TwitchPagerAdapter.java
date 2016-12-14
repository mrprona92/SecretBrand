package com.badr.infodota.stream.adapter.pager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.R;
import com.badr.infodota.stream.api.Stream;
import com.badr.infodota.stream.fragment.FavouriteStreamsList;
import com.badr.infodota.stream.fragment.StreamsList;
import com.badr.infodota.stream.fragment.TwitchGamesAdapter;
import com.badr.infodota.stream.fragment.TwitchMatchListHolder;
import com.badr.infodota.stream.service.TwitchService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * User: ABadretdinov
 * Date: 07.03.14
 * Time: 15:19
 */
public class TwitchPagerAdapter extends FragmentPagerAdapter implements TwitchGamesAdapter {
    private Context context;
    private Map<Integer, TwitchMatchListHolder> groupMap = new HashMap<Integer, TwitchMatchListHolder>();
    private List<Stream> channels;
    private TwitchService twitchService = BeanContainer.getInstance().getTwitchService();

    public TwitchPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;

        channels = twitchService.getFavouriteStreams(context);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                TwitchMatchListHolder live = groupMap.get(position);
                if (live == null) {
                    live = StreamsList.newInstance(this, channels);
                    groupMap.put(position, live);
                }
                return live;
            case 1:
            default:
                TwitchMatchListHolder favourite = groupMap.get(position);
                if (favourite == null) {
                    favourite = FavouriteStreamsList.newInstance(this, channels);
                    groupMap.put(position, favourite);
                }
                return favourite;
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        groupMap.remove(position);
        super.destroyItem(container, position, object);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return context.getResources().getStringArray(R.array.twitch_pages_title)[position];
    }

    //todo вынести отсюда
    @Override
    public void updateList() {

        channels = twitchService.getFavouriteStreams(context);
        Set<Integer> keySet = groupMap.keySet();
        for (Integer key : keySet) {
            groupMap.get(key).updateList(channels);
        }
    }
}
