package com.badr.infodota.hero.fragment.guide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.R;
import com.badr.infodota.base.util.FileUtils;
import com.badr.infodota.hero.api.guide.custom.AbilityBuild;
import com.badr.infodota.hero.api.guide.custom.Guide;
import com.badr.infodota.hero.api.guide.custom.ItemBuild;
import com.badr.infodota.item.activity.ItemInfoActivity;
import com.badr.infodota.item.api.Item;
import com.badr.infodota.item.service.ItemService;

import java.util.Map;

/**
 * User: ABadretdinov
 * Date: 28.01.14
 * Time: 16:21
 */
public class TooltipPart extends Fragment implements GuideHolder {
    private Guide guide;

    public static TooltipPart newInstance(long heroId, Guide guide) {
        TooltipPart fragment = new TooltipPart();
        Bundle bundle = new Bundle();
        bundle.putLong("id", heroId);
        bundle.putSerializable("guide", guide);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void updateWith(Guide guide) {
        this.guide = guide;
        getArguments().putSerializable("guide", guide);
        if (getActivity() != null) {
            initTooltips();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.guide_tooltips, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        guide = (Guide) getArguments().get("guide");
        initTooltips();
    }

    private void initTooltips() {
        View root = getView();
        if (guide != null && root != null) {
            AbilityBuild abilityBuild = guide.getAbilityBuild();
            LinearLayout abilitiesHolder = (LinearLayout) root.findViewById(R.id.abilities_holder);
            abilitiesHolder.removeAllViews();
            LayoutInflater inflater = getActivity().getLayoutInflater();
            if (abilityBuild != null && abilityBuild.getTooltips() != null) {
                root.findViewById(R.id.abilities_holder_header).setVisibility(View.VISIBLE);
                abilitiesHolder.setVisibility(View.VISIBLE);
                Map<String, String> abilitiesTooltips = abilityBuild.getTooltips();

                for (String ability : abilitiesTooltips.keySet()) {
                    ViewGroup tooltipRow = (ViewGroup) inflater.inflate(R.layout.tooltip_row, abilitiesHolder, false);
                    FileUtils.setDrawableFromAsset(((ImageView) tooltipRow.findViewById(R.id.tooltip_img)),
                            "skills/" + ability + ".png");
                    ((TextView) tooltipRow.findViewById(R.id.tooltip)).setText(abilitiesTooltips.get(ability));
                    abilitiesHolder.addView(tooltipRow);
                }
            } else {
                root.findViewById(R.id.abilities_holder_header).setVisibility(View.GONE);
                abilitiesHolder.setVisibility(View.GONE);
            }
            LinearLayout itemsHolder = (LinearLayout) root.findViewById(R.id.items_holder);
            itemsHolder.removeAllViews();
            ItemBuild itemBuild = guide.getItemBuild();
            if (itemBuild != null && itemBuild.getTooltips() != null) {
                root.findViewById(R.id.items_holder_header).setVisibility(View.VISIBLE);
                itemsHolder.setVisibility(View.VISIBLE);
                Map<String, String> itemsTooltips = itemBuild.getTooltips();

                for (final String item : itemsTooltips.keySet()) {
                    ViewGroup tooltipRow = (ViewGroup) inflater.inflate(R.layout.tooltip_row, itemsHolder, false);
                    FileUtils.setDrawableFromAsset(((ImageView) tooltipRow.findViewById(R.id.tooltip_img)), "items/" + item + ".png");
                    ((TextView) tooltipRow.findViewById(R.id.tooltip)).setText(itemsTooltips.get(item));
                    tooltipRow.findViewById(R.id.tooltip_img).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Activity activity = getActivity();
                            ItemService itemService = BeanContainer.getInstance().getItemService();
                            Item itemToOpen = itemService.getItemByDotaId(activity, item);
                            Intent intent = new Intent(activity, ItemInfoActivity.class);
                            intent.putExtra("id", itemToOpen.getId());
                            startActivityForResult(intent, ItemInfoActivity.UP_REQUEST);

                        }
                    });
                    itemsHolder.addView(tooltipRow);
                }
            } else {
                root.findViewById(R.id.items_holder_header).setVisibility(View.GONE);
                itemsHolder.setVisibility(View.GONE);
            }
        }
    }
}
