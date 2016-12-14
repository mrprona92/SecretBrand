package com.badr.infodota.hero.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.R;
import com.badr.infodota.base.util.FileUtils;
import com.badr.infodota.base.view.FlowLayout;
import com.badr.infodota.hero.api.Hero;
import com.badr.infodota.hero.api.guide.custom.ItemBuild;
import com.badr.infodota.item.activity.ItemInfoActivity;
import com.badr.infodota.item.api.Item;
import com.badr.infodota.item.service.ItemService;
import com.google.gson.Gson;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * User: Histler
 * Date: 19.01.14
 */
public class HeroDefaultItemBuild extends Fragment {
    private ItemBuild guide;
    private Hero hero;

    public static HeroDefaultItemBuild newInstance(Hero hero) {
        HeroDefaultItemBuild fragment = new HeroDefaultItemBuild();
        fragment.hero = hero;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.hero_default_itembuild, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String guideEntity = FileUtils.getTextFromAsset(getActivity(), "heroes/" + hero.getDotaId() + "/default.json");
        guide = new Gson().fromJson(guideEntity, ItemBuild.class);
        initGuide();
    }


    private void initGuide() {
        View fragmentView = getView();
        if (guide != null && fragmentView != null) {
            LinearLayout flowHolder = (LinearLayout) fragmentView.findViewById(R.id.flowHolder);
            flowHolder.removeAllViews();
            Map<String, List<String>> guideItems = guide.getItems();
            if (guideItems != null) {
                Activity activity = getActivity();
                ItemService itemService = BeanContainer.getInstance().getItemService();
                Set<String> guideStateSet = guideItems.keySet();
                LayoutInflater inflater = getActivity().getLayoutInflater();
                for (String guideState : guideStateSet) {
                    ViewGroup flowRow = (ViewGroup) inflater.inflate(R.layout.guide_item_flow_row, flowHolder, false);
                    FlowLayout flowLayout = (FlowLayout) flowRow.findViewById(R.id.items);
                    TextView header = (TextView) flowRow.findViewById(R.id.header);

                    if ("startingItems".equals(guideState)) {
                        header.setText(R.string.starting_items);
                    } else if ("earlyGame".equals(guideState)) {
                        header.setText(R.string.early_game);

                    } else if ("coreItems".equals(guideState)) {
                        header.setText(R.string.core_items);

                    } else if ("luxury".equals(guideState)) {
                        header.setText(R.string.luxury_items);
                    } else if ("startingItems_Secondary".equals(guideState)) {
                        header.setText(R.string.starting_items_secondary);
                    } else if ("earlyGame_Secondary".equals(guideState)) {
                        header.setText(R.string.early_game_secondary);
                    } else if ("coreItems_Secondary".equals(guideState)) {
                        header.setText(R.string.core_items_secondary);
                    } else {
                        header.setText(guideState);
                    }
                    List<String> items = guideItems.get(guideState);
                    for (String itemName : items) {
                        Item item = itemService.getItemByDotaId(activity, itemName);
                        if (item == null) {
                            Log.d(getClass().getName(), "error loading item: " + itemName);
                        } else {
                            LinearLayout row = (LinearLayout) inflater.inflate(R.layout.item_recept_row, flowLayout, false);
                            FileUtils.setDrawableFromAsset((ImageView) row.findViewById(R.id.img),
                                    "items/" + item.getDotaId() + ".png");
                            //().setImageDrawable(Utils.getDrawableFromAsset(getActivity(), ));
                            ((TextView) row.findViewById(R.id.name)).setText(item.getDotaName());
                            ((TextView) row.findViewById(R.id.cost)).setText(String.valueOf(item.getCost()));
                            row.setOnClickListener(new ItemInfoActivity.OnDotaItemClickListener(item.getId(), ItemInfoActivity.UP_REQUEST));
                            flowLayout.addView(row);
                        }
                    }
                    if (items.size() > 0) {
                        flowHolder.addView(flowRow);
                    }
                }
            }
        }
    }
}
