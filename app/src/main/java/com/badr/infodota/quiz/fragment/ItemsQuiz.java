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
import com.badr.infodota.base.view.FlowLayout;
import com.badr.infodota.item.activity.ItemInfoActivity;
import com.badr.infodota.item.api.Item;
import com.badr.infodota.item.service.ItemService;
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
public class ItemsQuiz extends QuizFragment {
    public static final int ITEMS_FROM_SIZE = 8;
    private Item item;
    private List<Item> itemsFrom;
    private List<Item> fakeFrom;
    private List<Item> userSelectedFrom;
    private List<View> notChoosed;
    private LinearLayout fakeFlowLayout;

    public static ItemsQuiz newInstance(OnQuizStateChangeListener listener, Random idRandom) {
        ItemsQuiz fragment = new ItemsQuiz();
        fragment.setListener(listener);
        fragment.setIdRandom(idRandom);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.quiz_items, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ItemService itemService = BeanContainer.getInstance().getItemService();
        Activity activity = getActivity();
        List<Item> items = itemService.getComplexItems(activity);
        int position = idRandom.nextInt(items.size());
        item = items.get(position);
        itemsFrom = itemService.getItemsToThis(activity, item);
        long currentCost = 0;
        for (Item item1 : itemsFrom) {
            currentCost += item1.getCost();
        }
        if (currentCost < item.getCost()) {
            Item recipe = new Item();
            recipe.setDotaId("recipe");
            recipe.setId(0);
            itemsFrom.add(recipe);
        }
        fakeFrom = new ArrayList<Item>(itemsFrom);
        List<Item> allItems = itemService.getAllItems(getActivity());
        allItems.remove(item);
        //Random random=new Random();
        while (fakeFrom.size() < ITEMS_FROM_SIZE) {
            fakeFrom.add(allItems.get(idRandom.nextInt(allItems.size())));
        }
        Collections.shuffle(fakeFrom, idRandom);
        View root = getView();
        if (root != null) {
            initCoreItem(root);
            initFromFlow(root);
            initFakeFlow(root);
            userSelectedFrom = new ArrayList<Item>();
        }

    }

    private void initCoreItem(View root) {
        Glide.with(root.getContext()).load(SteamUtils.getItemImage(item.getDotaId())).into((ImageView) root.findViewById(R.id.request_item));

    }

    private void initFakeFlow(View root) {
        fakeFlowLayout = (LinearLayout) root.findViewById(R.id.recipe_fake_holder);
        LinearLayout fake1 = (LinearLayout) fakeFlowLayout.findViewById(R.id.recipe_fake_holder1);
        LinearLayout fake2 = (LinearLayout) fakeFlowLayout.findViewById(R.id.recipe_fake_holder2);
        fake1.removeAllViews();
        fake2.removeAllViews();
        Context context = root.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.weight = 1f;
        for (int i = 0; i < fakeFrom.size(); i++) {
            final Item fakeItem = fakeFrom.get(i);
            View view = inflater.inflate(R.layout.item_quiz_holder, null, false);
            view.setTag(i);
            view.setLayoutParams(layoutParams);
            ImageView imageView = (ImageView) view.findViewById(R.id.img);
            Glide.with(context).load(SteamUtils.getItemImage(fakeItem.getDotaId())).into(imageView);
            view.setEnabled(true);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (notChoosed.size() > 0) {
                        View view1 = notChoosed.get(0);
                        notChoosed.remove(0);
                        Glide.with(v.getContext()).load(SteamUtils.getItemImage(fakeItem.getDotaId())).into((ImageView) view1.findViewById(R.id.img));
                        view1.setTag(v.getTag());
                        view1.setEnabled(true);
                        v.setEnabled(false);
                        userSelectedFrom.add(fakeItem);
                        if (userSelectedFrom.size() == itemsFrom.size()) {
                            checkUserSelection();
                        }
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

    private void initFromFlow(View root) {
        notChoosed = new ArrayList<View>();
        FlowLayout flowLayout = (FlowLayout) root.findViewById(R.id.recipe_holder);
        flowLayout.removeAllViews();
        int size = itemsFrom.size();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        for (int i = 0; i < size; i++) {
            View view = inflater.inflate(R.layout.item_quiz_holder, null, false);
            view.setTag(i);
            ImageView imageView = (ImageView) view.findViewById(R.id.img);
            imageView.setImageResource(R.drawable.emptyitembg);
            view.setEnabled(false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notChoosed.add(v);
                    ((ImageView) v.findViewById(R.id.img)).setImageResource(R.drawable.emptyitembg);
                    Integer tag = (Integer) v.getTag();
                    View fakeView = fakeFlowLayout.findViewWithTag(tag);
                    v.setTag(-1);
                    fakeView.setEnabled(true);
                    v.setEnabled(false);
                    int toRemove = userSelectedFrom.indexOf(fakeFrom.get(tag));
                    userSelectedFrom.remove(toRemove);
                }
            });
            flowLayout.addView(view);
            notChoosed.add(view);
        }
    }

    private void checkUserSelection() {
        boolean right = true;
        List<Item> checkingSelectionList = new ArrayList<Item>(userSelectedFrom);
        for (int i = 0; i < itemsFrom.size() && right; i++) {
            Item itemFrom = itemsFrom.get(i);
            int index = checkingSelectionList.indexOf(itemFrom);
            if (index == -1) {
                right = false;
            } else {
                checkingSelectionList.remove(index);
            }
        }
        if (right) {
            answered();
        } else {
            wrongAnswerChoose();
        }
    }

    @Override
    public void showLoosed() {
        Intent intent = new Intent(getActivity(), ItemInfoActivity.class);
        intent.putExtra("id", item.getId());
        startActivity(intent);
        getActivity().finish();
    }
}
