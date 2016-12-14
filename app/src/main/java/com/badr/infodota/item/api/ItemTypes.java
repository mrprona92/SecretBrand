package com.badr.infodota.item.api;

import java.util.List;

/**
 * User: ABadretdinov
 * Date: 28.01.14
 * Time: 19:05
 */
public class ItemTypes {
    //todo сделать Map<String,List<String>>. и удалять, когда находим.
    List<String> arcane;
    List<String> armaments;
    List<String> armor;
    List<String> artifacts;
    List<String> attributes;
    List<String> caster;
    List<String> common;
    List<String> consumable;
    List<String> support;
    List<String> secret_shop;
    List<String> weapons;

    public List<String> getArcane() {
        return arcane;
    }

    public void setArcane(List<String> arcane) {
        this.arcane = arcane;
    }

    public List<String> getArmaments() {
        return armaments;
    }

    public void setArmaments(List<String> armaments) {
        this.armaments = armaments;
    }

    public List<String> getArmor() {
        return armor;
    }

    public void setArmor(List<String> armor) {
        this.armor = armor;
    }

    public List<String> getArtifacts() {
        return artifacts;
    }

    public void setArtifacts(List<String> artifacts) {
        this.artifacts = artifacts;
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<String> attributes) {
        this.attributes = attributes;
    }

    public List<String> getCaster() {
        return caster;
    }

    public void setCaster(List<String> caster) {
        this.caster = caster;
    }

    public List<String> getCommon() {
        return common;
    }

    public void setCommon(List<String> common) {
        this.common = common;
    }

    public List<String> getConsumable() {
        return consumable;
    }

    public void setConsumable(List<String> consumable) {
        this.consumable = consumable;
    }

    public List<String> getSupport() {
        return support;
    }

    public void setSupport(List<String> support) {
        this.support = support;
    }

    public List<String> getSecret_shop() {
        return secret_shop;
    }

    public void setSecret_shop(List<String> secret_shop) {
        this.secret_shop = secret_shop;
    }

    public List<String> getWeapons() {
        return weapons;
    }

    public void setWeapons(List<String> weapons) {
        this.weapons = weapons;
    }
}
