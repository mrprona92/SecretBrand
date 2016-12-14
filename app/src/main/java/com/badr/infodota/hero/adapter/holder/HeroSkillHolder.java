package com.badr.infodota.hero.adapter.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.badr.infodota.R;
import com.badr.infodota.base.adapter.holder.BaseViewHolder;

/**
 * Created by ABadretdinov
 * 24.08.2015
 * 13:02
 */
public class HeroSkillHolder extends BaseViewHolder {
    public ImageView image;
    public TextView name;
    public TextView affects;
    public LinearLayout loreHolder;
    public LinearLayout paramsHolder;
    public TextView youtube;

    public HeroSkillHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void initView(View itemView) {
        image = (ImageView) itemView.findViewById(R.id.skill_img);
        name = (TextView) itemView.findViewById(R.id.skill_name);
        affects = (TextView) itemView.findViewById(R.id.skill_affects);

        loreHolder = (LinearLayout) itemView.findViewById(R.id.skill_lore_layout);
        paramsHolder = (LinearLayout) itemView.findViewById(R.id.skill_params_layout);
        youtube = (TextView) itemView.findViewById(R.id.youtube);
    }
}
