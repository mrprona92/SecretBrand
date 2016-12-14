package com.badr.infodota.quiz.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.R;
import com.badr.infodota.base.util.SteamUtils;
import com.badr.infodota.hero.activity.HeroInfoActivity;
import com.badr.infodota.hero.api.Hero;
import com.badr.infodota.hero.api.abilities.Ability;
import com.badr.infodota.hero.service.HeroService;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * User: ABadretdinov
 * Date: 10.02.14
 * Time: 13:18
 */
public class HeroSkillsQuiz extends QuizFragment {
    public static final int SKILLS_NUM_TO_DISPLAY = 8;
    private Hero hero;
    private Ability realAbility;
    private List<Ability> fakeAbilities;

    public static HeroSkillsQuiz newInstance(OnQuizStateChangeListener listener, Random idRandom) {
        HeroSkillsQuiz fragment = new HeroSkillsQuiz();
        fragment.setListener(listener);
        fragment.setIdRandom(idRandom);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.quiz_hero_skills, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        HeroService heroService = BeanContainer.getInstance().getHeroService();
        Activity activity = getActivity();
        List<Hero> heroes = heroService.getAllHeroes(activity);

        hero = heroes.get(idRandom.nextInt(heroes.size()));
        List<Ability> heroAbilities = heroService.getHeroAbilities(activity, hero.getId());
        //Random random=new Random();
        //любой скил, кроме attribute_bonus
        realAbility = heroAbilities.get(idRandom.nextInt(heroAbilities.size() - 1));
        List<Ability> nonHeroAbilities = heroService.getNotThisHeroAbilities(activity, hero.getId());
        fakeAbilities = new ArrayList<>();
        fakeAbilities.add(realAbility);
        while (fakeAbilities.size() < SKILLS_NUM_TO_DISPLAY) {
            fakeAbilities.add(nonHeroAbilities.get(idRandom.nextInt(nonHeroAbilities.size())));
        }
        Collections.shuffle(fakeAbilities, idRandom);
        View root = getView();
        if (root != null) {
            initCoreHero(root);
            initFakeFlow(root);
        }
    }

    private void initFakeFlow(View root) {
        LinearLayout fakeFlow = (LinearLayout) root.findViewById(R.id.ability_fake_holder);
        LinearLayout fake1 = (LinearLayout) fakeFlow.findViewById(R.id.ability_fake_holder1);
        LinearLayout fake2 = (LinearLayout) fakeFlow.findViewById(R.id.ability_fake_holder2);
        fake1.removeAllViews();
        fake2.removeAllViews();
        Context context = root.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.weight = 1f;
        for (int i = 0; i < fakeAbilities.size(); i++) {
            final Ability ability = fakeAbilities.get(i);
            View view = inflater.inflate(R.layout.item_quiz_holder, null, false);
            view.setLayoutParams(layoutParams);
            ImageView imageView = (ImageView) view.findViewById(R.id.img);
            Glide.with(context).load(SteamUtils.getSkillImage(ability.getName())).into(imageView);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ability.equals(realAbility)) {
                        answered();
                    } else {
                        wrongAnswerChoose();
                    }
                }
            });
            if (i % 2 == 0) {
                fake1.addView(view);
            } else {
                fake2.addView(view);
            }
        }
    }

    private void initCoreHero(View root) {
        Glide.with(root.getContext()).load(SteamUtils.getHeroPortraitImage(hero.getDotaId())).into((ImageView) root.findViewById(R.id.hero_img));
    }

    @Override
    public void showLoosed() {
        Intent intent = new Intent(getActivity(), HeroInfoActivity.class);
        intent.putExtra("id", hero.getId());
        startActivity(intent);
        getActivity().finish();
    }
}
