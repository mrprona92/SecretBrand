package com.mrprona.dota2assitant.hero.fragment;

import android.app.Activity;

import com.mrprona.dota2assitant.base.fragment.RecyclerFragment;
import com.mrprona.dota2assitant.base.service.LocalSpiceService;
import com.mrprona.dota2assitant.hero.adapter.HeroSkillsAdapter;
import com.mrprona.dota2assitant.hero.adapter.SkillHttpImageGetter;
import com.mrprona.dota2assitant.hero.adapter.holder.HeroSkillHolder;
import com.mrprona.dota2assitant.hero.api.Hero;
import com.mrprona.dota2assitant.hero.api.Skill;
import com.mrprona.dota2assitant.hero.task.SkillsLoadRequest;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

/**
 * User: ABadretdinov
 * Date: 16.01.14
 * Time: 15:57
 */
public class HeroSkills extends RecyclerFragment<Skill, HeroSkillHolder> implements RequestListener<Skill.List> {
    private SpiceManager mSpiceManager = new SpiceManager(LocalSpiceService.class);

    private Hero mHero;

    public static HeroSkills newInstance(Hero hero) {
        HeroSkills fragment = new HeroSkills();
        fragment.mHero = hero;
        return fragment;
    }

    @Override
    public void onStart() {
        if (!mSpiceManager.isStarted()) {
            Activity activity = getActivity();
            if (activity != null) {
                mSpiceManager.start(activity);
                mSpiceManager.execute(new SkillsLoadRequest(activity.getApplicationContext(), mHero.getDotaId()), this);
            }
        }
        super.onStart();
    }

    @Override
    public void onDestroy() {
        if (mSpiceManager.isStarted()) {
            mSpiceManager.shouldStop();
        }
        super.onDestroy();
    }

    @Override
    public void onRequestFailure(SpiceException spiceException) {
    }

    @Override
    public void onRequestSuccess(Skill.List skills) {
        setAdapter(new HeroSkillsAdapter(skills, new SkillHttpImageGetter(getActivity())));
    }
}
