package com.badr.infodota.hero.fragment;

import android.app.Activity;

import com.badr.infodota.base.fragment.RecyclerFragment;
import com.badr.infodota.base.service.LocalSpiceService;
import com.badr.infodota.hero.adapter.HeroSkillsAdapter;
import com.badr.infodota.hero.adapter.SkillHttpImageGetter;
import com.badr.infodota.hero.adapter.holder.HeroSkillHolder;
import com.badr.infodota.hero.api.Hero;
import com.badr.infodota.hero.api.Skill;
import com.badr.infodota.hero.task.SkillsLoadRequest;
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
