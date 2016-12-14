package com.badr.infodota.match.fragment;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.R;
import com.badr.infodota.base.util.SteamUtils;
import com.badr.infodota.item.activity.ItemInfoActivity;
import com.badr.infodota.item.api.Item;
import com.badr.infodota.item.service.ItemService;
import com.badr.infodota.match.api.Player;
import com.badr.infodota.match.api.detailed.AdditionalUnit;
import com.bumptech.glide.Glide;

/**
 * User: Histler
 * Date: 23.01.14
 */
public class MatchPlayerSummary extends Fragment {
    private static final int PHONE = 0;
    private static final int TABLET_PORTRAIT = 1;
    private static final int TABLET_LANDSCAPE = 2;
    private Player player;

    public static MatchPlayerSummary newInstance(Player player) {
        MatchPlayerSummary fragment = new MatchPlayerSummary();
        fragment.setPlayer(player);
        return fragment;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.match_player_summary, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        orientationChanged();
        initBasicInfo();
    }

    private void initBasicInfo() {
        View root = getView();
        final Activity activity = getActivity();
        if (root != null && activity != null) {
            ItemService itemService = BeanContainer.getInstance().getItemService();
            Item current = itemService.getItemById(activity, player.getItem0());
            ImageView item0 = (ImageView) root.findViewById(R.id.item0);
            if (current != null) {
                Glide.with(activity).load(SteamUtils.getItemImage(current.getDotaId())).placeholder(R.drawable.default_img).into(item0);
                item0.setOnClickListener(new ItemInfoActivity.OnDotaItemClickListener(current.getId()));
            } else {
                item0.setImageResource(R.drawable.emptyitembg);
            }
            current = itemService.getItemById(activity, player.getItem1());
            ImageView item1 = (ImageView) root.findViewById(R.id.item1);
            if (current != null) {
                Glide.with(activity).load(SteamUtils.getItemImage(current.getDotaId())).placeholder(R.drawable.default_img).into(item1);
                item1.setOnClickListener(new ItemInfoActivity.OnDotaItemClickListener(current.getId()));
            } else {
                item1.setImageResource(R.drawable.emptyitembg);
            }
            current = itemService.getItemById(activity, player.getItem2());
            ImageView item2 = (ImageView) root.findViewById(R.id.item2);
            if (current != null) {
                Glide.with(activity).load(SteamUtils.getItemImage(current.getDotaId())).placeholder(R.drawable.default_img).into(item2);
                item2.setOnClickListener(new ItemInfoActivity.OnDotaItemClickListener(current.getId()));
            } else {
                item2.setImageResource(R.drawable.emptyitembg);
            }
            current = itemService.getItemById(activity, player.getItem3());
            ImageView item3 = (ImageView) root.findViewById(R.id.item3);
            if (current != null) {
                Glide.with(activity).load(SteamUtils.getItemImage(current.getDotaId())).placeholder(R.drawable.default_img).into(item3);
                item3.setOnClickListener(new ItemInfoActivity.OnDotaItemClickListener(current.getId()));
            } else {
                item3.setImageResource(R.drawable.emptyitembg);
            }
            current = itemService.getItemById(activity, player.getItem4());
            ImageView item4 = (ImageView) root.findViewById(R.id.item4);
            if (current != null) {
                Glide.with(activity).load(SteamUtils.getItemImage(current.getDotaId())).placeholder(R.drawable.default_img).into(item4);
                item4.setOnClickListener(new ItemInfoActivity.OnDotaItemClickListener(current.getId()));
            } else {
                item4.setImageResource(R.drawable.emptyitembg);
            }
            current = itemService.getItemById(activity, player.getItem5());
            ImageView item5 = (ImageView) root.findViewById(R.id.item5);
            if (current != null) {
                Glide.with(activity).load(SteamUtils.getItemImage(current.getDotaId())).placeholder(R.drawable.default_img).into(item5);
                item5.setOnClickListener(new ItemInfoActivity.OnDotaItemClickListener(current.getId()));
            } else {
                item5.setImageResource(R.drawable.emptyitembg);
            }
            View additionalUnitHolder = root.findViewById(R.id.additional_unit_holder);
            if (player.getAdditionalUnits() != null && player.getAdditionalUnits().size() > 0) {
                AdditionalUnit unit = player.getAdditionalUnits().get(0);
                additionalUnitHolder.setVisibility(View.VISIBLE);
                current = itemService.getItemById(activity, unit.getItem0());
                ImageView additionalUnitItem0 = (ImageView) root.findViewById(R.id.additional_unit_item0);
                if (current != null) {
                    Glide.with(activity).load(SteamUtils.getItemImage(current.getDotaId())).placeholder(R.drawable.default_img).into(additionalUnitItem0);
                    additionalUnitItem0.setOnClickListener(new ItemInfoActivity.OnDotaItemClickListener(current.getId()));
                } else {
                    additionalUnitItem0.setImageResource(R.drawable.emptyitembg);
                }
                current = itemService.getItemById(activity, unit.getItem1());
                ImageView additionalUnitItem1 = (ImageView) root.findViewById(R.id.additional_unit_item1);
                if (current != null) {
                    Glide.with(activity).load(SteamUtils.getItemImage(current.getDotaId())).placeholder(R.drawable.default_img).into(additionalUnitItem1);
                    additionalUnitItem1.setOnClickListener(new ItemInfoActivity.OnDotaItemClickListener(current.getId()));
                } else {
                    additionalUnitItem1.setImageResource(R.drawable.emptyitembg);
                }
                current = itemService.getItemById(activity, unit.getItem2());
                ImageView additionalUnitItem2 = (ImageView) root.findViewById(R.id.additional_unit_item2);
                if (current != null) {
                    Glide.with(activity).load(SteamUtils.getItemImage(current.getDotaId())).placeholder(R.drawable.default_img).into(additionalUnitItem2);
                    additionalUnitItem2.setOnClickListener(new ItemInfoActivity.OnDotaItemClickListener(current.getId()));
                } else {
                    additionalUnitItem2.setImageResource(R.drawable.emptyitembg);
                }
                current = itemService.getItemById(activity, unit.getItem3());
                ImageView additionalUnitItem3 = (ImageView) root.findViewById(R.id.additional_unit_item3);
                if (current != null) {
                    Glide.with(activity).load(SteamUtils.getItemImage(current.getDotaId())).placeholder(R.drawable.default_img).into(additionalUnitItem3);
                    additionalUnitItem3.setOnClickListener(new ItemInfoActivity.OnDotaItemClickListener(current.getId()));
                } else {
                    additionalUnitItem3.setImageResource(R.drawable.emptyitembg);
                }
                current = itemService.getItemById(activity, unit.getItem4());
                ImageView additionalUnitItem4 = (ImageView) root.findViewById(R.id.additional_unit_item4);
                if (current != null) {
                    Glide.with(activity).load(SteamUtils.getItemImage(current.getDotaId())).placeholder(R.drawable.default_img).into(additionalUnitItem4);
                    additionalUnitItem4.setOnClickListener(new ItemInfoActivity.OnDotaItemClickListener(current.getId()));
                } else {
                    additionalUnitItem4.setImageResource(R.drawable.emptyitembg);
                }
                current = itemService.getItemById(activity, unit.getItem5());
                ImageView additionalUnitItem5 = (ImageView) root.findViewById(R.id.additional_unit_item5);
                if (current != null) {
                    Glide.with(activity).load(SteamUtils.getItemImage(current.getDotaId())).placeholder(R.drawable.default_img).into(additionalUnitItem5);
                    additionalUnitItem5.setOnClickListener(new ItemInfoActivity.OnDotaItemClickListener(current.getId()));
                } else {
                    additionalUnitItem5.setImageResource(R.drawable.emptyitembg);
                }
            } else {
                additionalUnitHolder.setVisibility(View.GONE);
            }

            ((TextView) root.findViewById(R.id.kills)).setText(Html.fromHtml(getString(R.string.kills) + " " + player.getKills()));
            ((TextView) root.findViewById(R.id.death)).setText(Html.fromHtml(getString(R.string.deaths) + " " + player.getDeaths()));
            ((TextView) root.findViewById(R.id.assists)).setText(
                    Html.fromHtml(getString(R.string.assists) + " " + player.getAssists()));
            ((TextView) root.findViewById(R.id.gold)).setText(Html.fromHtml(getString(R.string.gold) + " " + player.getGold()));
            ((TextView) root.findViewById(R.id.last_hits)).setText(
                    Html.fromHtml(getString(R.string.last_hits) + " " + player.getLastHits()));
            ((TextView) root.findViewById(R.id.denies)).setText(
                    Html.fromHtml(getString(R.string.denies) + " " + player.getDenies()));
            ((TextView) root.findViewById(R.id.gpm)).setText(
                    Html.fromHtml(getString(R.string.gpm) + " " + player.getGoldPerMin()));
            ((TextView) root.findViewById(R.id.xpm)).setText(
                    Html.fromHtml(getString(R.string.xpm) + " " + player.getXpPerMin()));
            if (player.getNetWorth() == null) {
                ((TextView) root.findViewById(R.id.hero_damage)).setText(
                        Html.fromHtml(getString(R.string.hero_damage) + " " + player.getHeroDamage()));
                ((TextView) root.findViewById(R.id.hero_healing)).setText(
                        Html.fromHtml(getString(R.string.hero_healing) + " " + player.getHeroHealing()));
                ((TextView) root.findViewById(R.id.tower_damage)).setText(
                        Html.fromHtml(getString(R.string.tower_damage) + " " + player.getTowerDamage()));
            } else {
                TextView netWorth = (TextView) root.findViewById(R.id.net_worth);
                netWorth.setVisibility(View.VISIBLE);
                netWorth.setText(Html.fromHtml(getString(R.string.net_worth) + " " + player.getNetWorth()));
                root.findViewById(R.id.hero_damage).setVisibility(View.GONE);
                root.findViewById(R.id.hero_healing).setVisibility(View.GONE);
                root.findViewById(R.id.tower_damage).setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        orientationChanged();
    }

    private int getState() {
        Resources resources = getResources();
        if (!resources.getBoolean(R.bool.is_tablet)) {
            if (resources.getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                return TABLET_PORTRAIT;
            } else {
                return PHONE;
            }
        } else {
            if (resources.getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                return TABLET_LANDSCAPE;
            } else {
                return TABLET_PORTRAIT;
            }
        }
    }

    private void orientationChanged() {
        View root = getView();
        if (root != null) {
            switch (getState()) {
                case TABLET_LANDSCAPE:
                    ((LinearLayout) root.findViewById(R.id.unit_holder)).setOrientation(LinearLayout.VERTICAL);
                    ((LinearLayout) root.findViewById(R.id.additional_unit_holder)).setOrientation(LinearLayout.VERTICAL);
                    ((LinearLayout) root.findViewById(R.id.item_holder)).setOrientation(LinearLayout.HORIZONTAL);
                    ((LinearLayout) root.findViewById(R.id.main_holder)).setOrientation(LinearLayout.HORIZONTAL);
                    break;
                case TABLET_PORTRAIT:
                    ((LinearLayout) root.findViewById(R.id.unit_holder)).setOrientation(LinearLayout.VERTICAL);
                    ((LinearLayout) root.findViewById(R.id.additional_unit_holder)).setOrientation(LinearLayout.VERTICAL);
                    ((LinearLayout) root.findViewById(R.id.item_holder)).setOrientation(LinearLayout.VERTICAL);
                    ((LinearLayout) root.findViewById(R.id.main_holder)).setOrientation(LinearLayout.HORIZONTAL);
                    break;
                case PHONE:
                    ((LinearLayout) root.findViewById(R.id.unit_holder)).setOrientation(LinearLayout.HORIZONTAL);
                    ((LinearLayout) root.findViewById(R.id.additional_unit_holder)).setOrientation(LinearLayout.HORIZONTAL);
                    ((LinearLayout) root.findViewById(R.id.item_holder)).setOrientation(LinearLayout.VERTICAL);
                    ((LinearLayout) root.findViewById(R.id.main_holder)).setOrientation(LinearLayout.VERTICAL);
                    break;
            }
        }
    }
}
