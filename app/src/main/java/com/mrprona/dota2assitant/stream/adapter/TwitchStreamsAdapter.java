package com.mrprona.dota2assitant.stream.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mrprona.dota2assitant.BeanContainer;
import com.mrprona.dota2assitant.R;
import com.mrprona.dota2assitant.base.adapter.BaseRecyclerAdapter;
import com.mrprona.dota2assitant.base.api.Constants;
import com.mrprona.dota2assitant.stream.adapter.holder.StreamHolder;
import com.mrprona.dota2assitant.stream.api.Stream;
import com.mrprona.dota2assitant.stream.fragment.TwitchGamesAdapter;
import com.mrprona.dota2assitant.stream.service.TwitchService;
import com.bumptech.glide.Glide;

import java.text.MessageFormat;

/**
 * User: Histler
 * Date: 28.02.14
 */
public class TwitchStreamsAdapter extends BaseRecyclerAdapter<Stream, StreamHolder> {
    private TwitchGamesAdapter holderAdapter;

    public TwitchStreamsAdapter(TwitchGamesAdapter holderAdapter, Stream.List streams) {
        super(streams);
        this.holderAdapter = holderAdapter;
    }

    public void addStream(Stream stream) {
        if (!mData.contains(stream)) {
            mData.add(0, stream);
        }
        notifyDataSetChanged();
    }

    @Override
    public StreamHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.twitch_stream_row, parent, false);
        return new StreamHolder(view, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(StreamHolder holder, int position) {
        final Stream stream = getItem(position);
        Context context = holder.img.getContext();
        if (TextUtils.isEmpty(stream.getImage())) {
            Glide.with(context).load(MessageFormat.format(Constants.TwitchTV.PREVIEW_URL, stream.getChannel())).placeholder(R.drawable.default_game).into(holder.img);
        } else {
            Glide.with(context).load(stream.getImage()).placeholder(R.drawable.default_game).into(holder.img);
        }
        holder.channel.setText(stream.getChannel());
        holder.status.setText(stream.getTitle());
        holder.viewers.setText(String.valueOf(stream.getViewers()));
        if (stream.isFavourite()) {
            holder.favourite.setImageResource(R.drawable.favourite_on);
            holder.favourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TwitchService twitchService = BeanContainer.getInstance().getTwitchService();
                    twitchService.deleteStream(v.getContext(), stream);
//                    notifyDataSetChanged();
                    holderAdapter.updateList();
                }
            });
        } else {
            holder.favourite.setImageResource(R.drawable.favourite_off);
            holder.favourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TwitchService twitchService = BeanContainer.getInstance().getTwitchService();
                    twitchService.addStream(v.getContext(), stream);
                    //notifyDataSetChanged();
                    holderAdapter.updateList();
                }
            });
        }
        if ("douyu".equals(stream.getProvider())) {
            holder.provider.setImageResource(R.drawable.douyu);
        } else {
            holder.provider.setImageResource(R.drawable.twitch);
        }
    }

    @Override
    public long getItemId(int position) {
        return mData.get(position).getId();
    }
}
