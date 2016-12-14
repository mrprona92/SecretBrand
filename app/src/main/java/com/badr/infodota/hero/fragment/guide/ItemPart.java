package com.badr.infodota.hero.fragment.guide;

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
import com.badr.infodota.hero.api.guide.custom.Guide;
import com.badr.infodota.hero.api.guide.custom.ItemBuild;
import com.badr.infodota.item.activity.ItemInfoActivity;
import com.badr.infodota.item.api.Item;
import com.badr.infodota.item.service.ItemService;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * User: Histler
 * Date: 28.01.14
 */
public class ItemPart extends Fragment implements GuideHolder {

    private ItemBuild itemBuild;

    public static ItemPart newInstance(long heroId, ItemBuild itemBuild) {
        ItemPart fragment = new ItemPart();
        Bundle bundle = new Bundle();
        bundle.putLong("id", heroId);
        bundle.putSerializable("itemBuild", itemBuild);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.guide_item, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        itemBuild = (ItemBuild) getArguments().get("itemBuild");
        initGuide();
    }

    private void initGuide() {
        View fragmentView = getView();
        if (itemBuild != null && fragmentView != null) {
            LinearLayout flowHolder = (LinearLayout) fragmentView.findViewById(R.id.flowHolder);
            flowHolder.removeAllViews();
            Map<String, List<String>> guideItems = itemBuild.getItems();
            if (guideItems != null) {
                Activity activity = getActivity();
                ItemService itemService = BeanContainer.getInstance().getItemService();
                Set<String> guideStateSet = guideItems.keySet();
                LayoutInflater inflater = getActivity().getLayoutInflater();
                for (String guideState : guideStateSet) {
                    ViewGroup flowRow = (ViewGroup) inflater.inflate(R.layout.guide_item_flow_row, null, false);
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
                    } else {
                        header.setText(guideState);
                    }
                    List<String> items = guideItems.get(guideState);
                    for (String itemName : items) {
                        Item item = itemService.getItemByDotaId(activity, itemName);
                        if (item == null) {
                            Log.d(ItemPart.class.getName(), "error loading item: " + itemName);
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

    @Override
    public void updateWith(Guide guide) {
        if (guide != null) {
            itemBuild = guide.getItemBuild();
            getArguments().putSerializable("itemBuild", itemBuild);
            if (getActivity() != null) {
                initGuide();
            }
        }
    }
}
