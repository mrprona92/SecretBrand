package com.badr.infodota.player.api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * User: Histler
 * Date: 16.04.14
 */
public class Player implements Serializable {
    @SerializedName("steamid")
    private String steamId;
    @SerializedName("communityvisibilitystate")
    private Long communityVisibilityState;
    @SerializedName("profilestate")
    private Long profileState;
    @SerializedName("personaname")
    private String personaName;
    @SerializedName("lastlogoff")
    private Long lastLogOff;
    @SerializedName("commentpermission")
    private Long commentPermission;
    @SerializedName("profileurl")
    private String profileUrl;
    private String avatar;
    @SerializedName("avatarmedium")
    private String avatarMedium;
    @SerializedName("avatarfull")
    private String avatarFull;
    @SerializedName("personastate")
    private Long personaState;
    @SerializedName("primaryclanid")
    private String primaryClanId;
    @SerializedName("timecreated")
    private Long timeCreated;
    @SerializedName("personastateflags")
    private Long personaStateFlags;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatarFull() {
        return avatarFull;
    }

    public void setAvatarFull(String avatarFull) {
        this.avatarFull = avatarFull;
    }

    public String getAvatarMedium() {
        return avatarMedium;
    }

    public void setAvatarMedium(String avatarMedium) {
        this.avatarMedium = avatarMedium;
    }

    public Long getCommentPermission() {
        return commentPermission;
    }

    public void setCommentPermission(Long commentPermission) {
        this.commentPermission = commentPermission;
    }

    public Long getCommunityVisibilityState() {
        return communityVisibilityState;
    }

    public void setCommunityVisibilityState(Long communityVisibilityState) {
        this.communityVisibilityState = communityVisibilityState;
    }

    public Long getLastLogOff() {
        return lastLogOff;
    }

    public void setLastLogOff(Long lastLogOff) {
        this.lastLogOff = lastLogOff;
    }

    public String getPersonaName() {
        return personaName;
    }

    public void setPersonaName(String personaName) {
        this.personaName = personaName;
    }

    public Long getPersonaState() {
        return personaState;
    }

    public void setPersonaState(Long personaState) {
        this.personaState = personaState;
    }

    public Long getPersonaStateFlags() {
        return personaStateFlags;
    }

    public void setPersonaStateFlags(Long personaStateFlags) {
        this.personaStateFlags = personaStateFlags;
    }

    public String getPrimaryClanId() {
        return primaryClanId;
    }

    public void setPrimaryClanId(String primaryClanId) {
        this.primaryClanId = primaryClanId;
    }

    public Long getProfileState() {
        return profileState;
    }

    public void setProfileState(Long profileState) {
        this.profileState = profileState;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getSteamId() {
        return steamId;
    }

    public void setSteamId(String steamId) {
        this.steamId = steamId;
    }

    public Long getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Long timeCreated) {
        this.timeCreated = timeCreated;
    }
}
