package com.badr.infodota.match.adapter.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.badr.infodota.R;
import com.badr.infodota.base.adapter.OnItemClickListener;
import com.badr.infodota.base.adapter.holder.BaseViewHolder;

/**
 * Created by ABadretdinov
 * 24.08.2015
 * 13:54
 */
public class MatchPlayerHolder extends BaseViewHolder {
    public ImageView heroImg;
    public TextView heroName;
    public TextView playerLvl;
    public TextView leaver;
    public TextView nick;
    public TextView kills;
    public TextView deaths;
    public TextView assists;
    public TextView gold;
    public TextView lastHits;
    public TextView denies;
    public TextView gpm;
    public TextView xpm;

    public LinearLayout itemHolder;

    public ImageView item0;
    public ImageView item1;
    public ImageView item2;
    public ImageView item3;
    public ImageView item4;
    public ImageView item5;
    public LinearLayout unitHolder;

    public ImageView additionalUnitItem0;
    public ImageView additionalUnitItem1;
    public ImageView additionalUnitItem2;
    public ImageView additionalUnitItem3;
    public ImageView additionalUnitItem4;
    public ImageView additionalUnitItem5;
    public LinearLayout additionalUnitHolder;


    public MatchPlayerHolder(View itemView, OnItemClickListener listener) {
        super(itemView, listener);
    }

    @Override
    protected void initView(View itemView) {
        heroImg = (ImageView) itemView.findViewById(R.id.hero_img);
        heroName = (TextView) itemView.findViewById(R.id.hero_name);
        nick = (TextView) itemView.findViewById(R.id.nick);
        playerLvl = (TextView) itemView.findViewById(R.id.player_lvl);
        leaver = (TextView) itemView.findViewById(R.id.leaver);
        kills = (TextView) itemView.findViewById(R.id.kills);
        deaths = (TextView) itemView.findViewById(R.id.death);
        assists = (TextView) itemView.findViewById(R.id.assists);
        gold = (TextView) itemView.findViewById(R.id.gold);
        lastHits = (TextView) itemView.findViewById(R.id.last_hits);
        denies = (TextView) itemView.findViewById(R.id.denies);
        gpm = (TextView) itemView.findViewById(R.id.gpm);
        xpm = (TextView) itemView.findViewById(R.id.xpm);

        item0 = (ImageView) itemView.findViewById(R.id.item0);
        item1 = (ImageView) itemView.findViewById(R.id.item1);
        item2 = (ImageView) itemView.findViewById(R.id.item2);
        item3 = (ImageView) itemView.findViewById(R.id.item3);
        item4 = (ImageView) itemView.findViewById(R.id.item4);
        item5 = (ImageView) itemView.findViewById(R.id.item5);

        additionalUnitItem0 = (ImageView) itemView.findViewById(R.id.additional_unit_item0);
        additionalUnitItem1 = (ImageView) itemView.findViewById(R.id.additional_unit_item1);
        additionalUnitItem2 = (ImageView) itemView.findViewById(R.id.additional_unit_item2);
        additionalUnitItem3 = (ImageView) itemView.findViewById(R.id.additional_unit_item3);
        additionalUnitItem4 = (ImageView) itemView.findViewById(R.id.additional_unit_item4);
        additionalUnitItem5 = (ImageView) itemView.findViewById(R.id.additional_unit_item5);

        additionalUnitHolder = (LinearLayout) itemView.findViewById(R.id.additional_unit_holder);
        unitHolder = (LinearLayout) itemView.findViewById(R.id.unit_holder);
        itemHolder = (LinearLayout) itemView.findViewById(R.id.item_holder);

    }
}
