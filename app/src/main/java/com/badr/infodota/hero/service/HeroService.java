package com.badr.infodota.hero.service;

import android.content.Context;

import com.badr.infodota.InitializingBean;
import com.badr.infodota.hero.api.CarouselHero;
import com.badr.infodota.hero.api.Hero;
import com.badr.infodota.hero.api.TalentTree;
import com.badr.infodota.hero.api.abilities.Ability;

import java.util.List;

/**
 * Created by ABadretdinov
 * 25.12.2014
 * 14:35
 */
public interface HeroService extends InitializingBean {

    List<Hero> getAllHeroes(Context context);

    TalentTree getTalentTreeByID(Context context, int id);


    Hero.List getFilteredHeroes(Context context, String filter);

    List<Hero> getHeroesByName(Context context, String name);

    Hero getExactHeroByName(Context context, String name);

    List<CarouselHero> getCarouselHeroes(Context context, String filter);

    CarouselHero.List getCarouselHeroes(Context context, String filter, String name);

    Hero getHeroById(Context context, long id);

    Hero getHeroWithStatsById(Context context, long id);

    void saveHero(Context context, Hero hero);

    List<Ability> getHeroAbilities(Context context, long heroId);

    List<Ability> getNotThisHeroAbilities(Context context, long heroId);

    List<Ability> getAbilitiesByList(Context context, List<Long> inGameList);

    String getAbilityPath(Context context, long id);

    void saveAbility(Context context, Ability ability);

    void saveTalentsTree(Context context, List<TalentTree> talentTrees);

}
