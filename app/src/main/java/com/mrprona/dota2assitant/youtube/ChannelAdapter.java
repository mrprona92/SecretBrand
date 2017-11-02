package com.mrprona.dota2assitant.youtube;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mrprona.dota2assitant.R;
import com.mrprona.dota2assitant.youtube.model.ChannelInfo;

import java.util.List;

/**
 * Created by Ravi Tamada on 18/05/16.
 */
public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.MyViewHolder> {

    private Context mContext;
    private List<ChannelInfo> channelInfos;

    private OnClickImageChannel mListener;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView lblTitle, lblDescription;
        public ImageView thumbnail;


        public MyViewHolder(View view) {
            super(view);
            lblTitle = (TextView) view.findViewById(R.id.lblTitle);
            lblDescription = (TextView) view.findViewById(R.id.lblDescription);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        }
    }


    public ChannelAdapter(Context mContext) {
        this.mContext = mContext;

    }

    public void setListAdapter(List<ChannelInfo> abilityInfos) {
        this.channelInfos = abilityInfos;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.channel_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        ChannelInfo channelInfo = channelInfos.get(position);
        holder.lblTitle.setText(channelInfo.getName());
        holder.lblDescription.setText(channelInfo.getDescription());
        Glide.with(mContext).load(channelInfo.getImageID()).into(holder.thumbnail);
        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.OnClickImage(v, holder.getAdapterPosition());
            }
        });
    }


    /**
     * Click listener for popup menu items
     */


    @Override
    public int getItemCount() {
        return channelInfos.size();
    }

    public interface OnClickImageChannel {
        void OnClickImage(View v, int position);
    }

    public void setOnItemClickListener(OnClickImageChannel onItemClickListener) {
        mListener = onItemClickListener;
    }


}
