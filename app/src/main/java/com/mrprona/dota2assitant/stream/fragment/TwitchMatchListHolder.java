package com.mrprona.dota2assitant.stream.fragment;

import com.mrprona.dota2assitant.base.fragment.UpdatableRecyclerFragment;
import com.mrprona.dota2assitant.stream.adapter.holder.StreamHolder;
import com.mrprona.dota2assitant.stream.api.Stream;

import java.util.List;

/**
 * User: ABadretdinov
 * Date: 07.03.14
 * Time: 15:28
 */
public abstract class TwitchMatchListHolder extends UpdatableRecyclerFragment<Stream, StreamHolder> {

    public abstract void updateList(List<Stream> channels);
}
