package com.badr.infodota.hero.adapter;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.badr.infodota.R;
import com.badr.infodota.base.adapter.BaseRecyclerAdapter;
import com.badr.infodota.base.util.SteamUtils;
import com.badr.infodota.hero.OnYoutubeClickListener;
import com.badr.infodota.hero.adapter.holder.HeroSkillHolder;
import com.badr.infodota.hero.api.Skill;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by ABadretdinov
 * 24.08.2015
 * 13:15
 */
public class HeroSkillsAdapter extends BaseRecyclerAdapter<Skill, HeroSkillHolder> {

    private SkillHttpImageGetter mImageGetter;

    public HeroSkillsAdapter(List<Skill> data, SkillHttpImageGetter imageGetter) {
        super(data);
        this.mImageGetter = imageGetter;
    }

    @Override
    public HeroSkillHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hero_skill_row, parent, false);
        return new HeroSkillHolder(view);
    }

    @Override
    public void onBindViewHolder(HeroSkillHolder holder, int position) {
        Skill skill = getItem(position);
        Context context = holder.name.getContext();
        holder.name.setText(Html.fromHtml(skill.getDotaName()));
        holder.affects.setText(Html.fromHtml(skill.getAffects()));
        Glide.with(context)
                .load(SteamUtils.getSkillImage(skill.getName()))
                .into(holder.image);
        holder.loreHolder.removeAllViews();
        holder.paramsHolder.removeAllViews();
        if (!TextUtils.isEmpty(skill.getCmb())) {
            TextView tv = new TextView(context);
            tv.setText(Html.fromHtml(skill.getCmb(), mImageGetter, null));
            holder.paramsHolder.addView(tv);
        }

        if (!TextUtils.isEmpty(skill.getDmg())) {
            TextView tv = new TextView(context);
            tv.setText(Html.fromHtml(skill.getDmg(), mImageGetter, null));
            holder.paramsHolder.addView(tv);
        }

        if (!TextUtils.isEmpty(skill.getAttrib())) {
            TextView tv = new TextView(context);
            tv.setText(Html.fromHtml(skill.getAttrib(), mImageGetter, null));
            holder.paramsHolder.addView(tv);
        }
        if (!TextUtils.isEmpty(skill.getDesc())) {
            TextView tv = new TextView(context);
            tv.setText(Html.fromHtml(skill.getDesc(), mImageGetter, null));
            holder.loreHolder.addView(tv);
        }

        if (!TextUtils.isEmpty(skill.getNotes())) {
            TextView tv = new TextView(context);
            tv.setText(Html.fromHtml(skill.getNotes(), mImageGetter, null));
            holder.loreHolder.addView(tv);
        }
        if (TextUtils.isEmpty(skill.getYoutube())) {
            holder.youtube.setVisibility(View.GONE);
            holder.youtube.setOnClickListener(null);
        } else {
            holder.youtube.setVisibility(View.VISIBLE);
            String youtubeUrl = skill.getYoutube();
            holder.youtube.setOnClickListener(new OnYoutubeClickListener(youtubeUrl));
        }
    }
}
