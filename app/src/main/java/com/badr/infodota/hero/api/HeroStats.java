package com.badr.infodota.hero.api;

import com.badr.infodota.base.entity.HasId;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * User: ABadretdinov
 * Date: 29.08.13
 * Time: 15:12
 */
public class HeroStats implements HasId, Serializable {

    private long id;
    @SerializedName("Alignment")
    private int alignment;
    @SerializedName("Patch")
    private String patch;
    @SerializedName("Movespeed")
    private int movespeed;
    @SerializedName("MaxDmg")
    private int maxDmg;
    @SerializedName("MinDmg")
    private int minDmg;
    @SerializedName("HP")
    private int hp;
    @SerializedName("Mana")
    private int mana;
    @SerializedName("HPRegen")
    private float hpRegen;
    @SerializedName("ManaRegen")
    private float manaRegen;
    @SerializedName("Armor")
    private float armor;
    @SerializedName("Range")
    private int range;
    @SerializedName("ProjectileSpeed")
    private int projectileSpeed;
    @SerializedName("BaseStr")
    private int baseStr;
    @SerializedName("BaseAgi")
    private int baseAgi;
    @SerializedName("BaseInt")
    private int baseInt;
    @SerializedName("StrGain")
    private float strGain;
    @SerializedName("AgiGain")
    private float agiGain;
    @SerializedName("IntGain")
    private float intGain;
    /*
    * 0-str
	* 1-agi
	* 2-int
	* */
    @SerializedName("PrimaryStat")
    private int primaryStat;
    @SerializedName("BaseAttackTime")
    private float baseAttackTime;
    @SerializedName("DayVision")
    private int dayVision;
    @SerializedName("NightVision")
    private int nightVision;
    @SerializedName("AttackPoint")
    private float attackPoint;
    @SerializedName("AttackSwing")
    private float attackSwing;
    @SerializedName("CastPoint")
    private float castPoint;
    @SerializedName("CastSwing")
    private float castSwing;
    @SerializedName("Turnrate")
    private float turnRate;
    //Who needs legs, when you got wings?
    @SerializedName("Legs")
    private int legs;
    private String[] roles;

    public String getPatch() {
        return patch;
    }

    public void setPatch(String patch) {
        this.patch = patch;
    }

    public int getAlignment() {
        return alignment;
    }

    public void setAlignment(int alignment) {
        this.alignment = alignment;
    }

    public int getMovespeed() {
        return movespeed;
    }

    public void setMovespeed(int movespeed) {
        this.movespeed = movespeed;
    }

    public int getMaxDmg() {
        return maxDmg;
    }

    public void setMaxDmg(int maxDmg) {
        this.maxDmg = maxDmg;
    }

    public int getMinDmg() {
        return minDmg;
    }

    public void setMinDmg(int minDmg) {
        this.minDmg = minDmg;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public float getHpRegen() {
        return hpRegen;
    }

    public void setHpRegen(float hpRegen) {
        this.hpRegen = hpRegen;
    }

    public float getManaRegen() {
        return manaRegen;
    }

    public void setManaRegen(float manaRegen) {
        this.manaRegen = manaRegen;
    }

    public float getArmor() {
        return armor;
    }

    public void setArmor(float armor) {
        this.armor = armor;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getProjectileSpeed() {
        return projectileSpeed;
    }

    public void setProjectileSpeed(int projectileSpeed) {
        this.projectileSpeed = projectileSpeed;
    }

    public int getBaseStr() {
        return baseStr;
    }

    public void setBaseStr(int baseStr) {
        this.baseStr = baseStr;
    }

    public int getBaseAgi() {
        return baseAgi;
    }

    public void setBaseAgi(int baseAgi) {
        this.baseAgi = baseAgi;
    }

    public int getBaseInt() {
        return baseInt;
    }

    public void setBaseInt(int baseInt) {
        this.baseInt = baseInt;
    }

    public float getStrGain() {
        return strGain;
    }

    public void setStrGain(float strGain) {
        this.strGain = strGain;
    }

    public float getAgiGain() {
        return agiGain;
    }

    public void setAgiGain(float agiGain) {
        this.agiGain = agiGain;
    }

    public float getIntGain() {
        return intGain;
    }

    public void setIntGain(float intGain) {
        this.intGain = intGain;
    }

    public int getPrimaryStat() {
        return primaryStat;
    }

    public void setPrimaryStat(int primaryStat) {
        this.primaryStat = primaryStat;
    }

    public float getBaseAttackTime() {
        return baseAttackTime;
    }

    public void setBaseAttackTime(float baseAttackTime) {
        this.baseAttackTime = baseAttackTime;
    }

    public int getDayVision() {
        return dayVision;
    }

    public void setDayVision(int dayVision) {
        this.dayVision = dayVision;
    }

    public int getNightVision() {
        return nightVision;
    }

    public void setNightVision(int nightVision) {
        this.nightVision = nightVision;
    }

    public float getAttackPoint() {
        return attackPoint;
    }

    public void setAttackPoint(float attackPoint) {
        this.attackPoint = attackPoint;
    }

    public float getAttackSwing() {
        return attackSwing;
    }

    public void setAttackSwing(float attackSwing) {
        this.attackSwing = attackSwing;
    }

    public float getCastPoint() {
        return castPoint;
    }

    public void setCastPoint(float castPoint) {
        this.castPoint = castPoint;
    }

    public float getCastSwing() {
        return castSwing;
    }

    public void setCastSwing(float castSwing) {
        this.castSwing = castSwing;
    }

    public float getTurnRate() {
        return turnRate;
    }

    public void setTurnRate(float turnRate) {
        this.turnRate = turnRate;
    }

    public int getLegs() {
        return legs;
    }

    public void setLegs(int legs) {
        this.legs = legs;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }
}
