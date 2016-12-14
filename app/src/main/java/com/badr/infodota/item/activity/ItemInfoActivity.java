package com.badr.infodota.item.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.Html;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.R;
import com.badr.infodota.base.activity.BaseActivity;
import com.badr.infodota.base.util.FileUtils;
import com.badr.infodota.base.util.ResourceUtils;
import com.badr.infodota.base.view.FlowLayout;
import com.badr.infodota.item.api.Item;
import com.badr.infodota.item.service.ItemService;
import com.google.gson.Gson;

import java.util.List;

/**
 * User: Histler
 * Date: 18.01.14
 */
public class ItemInfoActivity extends BaseActivity {
    public static final int UP_REQUEST = 1;
    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_stats);
        changeOrientation();
        long id = getIntent().getExtras().getLong("id");
        ItemService itemService = BeanContainer.getInstance().getItemService();
        item = itemService.getItemById(this, id);
        String itemType = item.getType();
        List<Item> from = itemService.getItemsToThis(this, item);
        List<Item> to = itemService.getItemsFromThis(this, item);
        String entity = FileUtils
                .getTextFromAsset(this, "items/" + item.getDotaId() + "_" + getString(R.string.language) + ".json");
        item = new Gson().fromJson(entity, Item.class);
        item.setType(itemType);
        initItem(from, to);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(RESULT_CANCELED);
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initItem(List<Item> from, List<Item> to) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(item.getDotaName());
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        FileUtils.setDrawableFromAsset(((ImageView) findViewById(R.id.item_image)), "items/" + item.getDotaId() + ".png");
        ((TextView) findViewById(R.id.name)).setText(ResourceUtils.getLocalizedItemType(this, item.getType()));
        ((TextView) findViewById(R.id.cost)).setText(String.valueOf(item.getCost()));
        if (!(item.getManaCost() instanceof Boolean)) {
            TextView mc = (TextView) findViewById(R.id.mc);
            mc.setText(Html.fromHtml(
                    getString(R.string.mc) + " " +
                            ((item.getManaCost() instanceof Double) ?
                                    String.valueOf(((Double) item.getManaCost()).intValue())
                                    : item.getManaCost().toString())));
            mc.setCompoundDrawables(FileUtils.getDrawableFromAsset(this, "mana.png"), null, null, null);
            mc.setVisibility(View.VISIBLE);
        }
        if (!(item.getCooldown() instanceof Boolean)) {
            TextView cd = (TextView) findViewById(R.id.cd);
            cd.setText(Html.fromHtml(
                    getString(R.string.cd) + " " +
                            ((item.getCooldown() instanceof Double) ?
                                    String.valueOf(((Double) item.getCooldown()).intValue())
                                    : Html.fromHtml(item.getCooldown().toString()))));
            cd.setCompoundDrawables(FileUtils.getDrawableFromAsset(this, "cooldown.png"), null, null, null);
            cd.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(item.getAttrib())) {
            TextView attrib = (TextView) findViewById(R.id.attrib);
            attrib.setText(Html.fromHtml(item.getAttrib()));
            attrib.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(item.getDescription())) {
            TextView desc = (TextView) findViewById(R.id.desc);
            desc.setText(Html.fromHtml(item.getDescription()));
            desc.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(item.getLore())) {
            TextView lore = (TextView) findViewById(R.id.lore);
            lore.setText(Html.fromHtml(item.getLore()));
            lore.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(item.getNotes())) {
            TextView note = (TextView) findViewById(R.id.note);
            note.setText(Html.fromHtml(item.getNotes()));
            note.setVisibility(View.VISIBLE);
        }
        if (from != null && from.size() > 0) {
            findViewById(R.id.makes_from_title).setVisibility(View.VISIBLE);
            FlowLayout fromLayout = (FlowLayout) findViewById(R.id.makes_from);
            fromLayout.setVisibility(View.VISIBLE);
            fromLayout.removeAllViews();
            int currentCost = 0;
            for (Item fromItem : from) {
                LinearLayout row = (LinearLayout) getLayoutInflater().inflate(R.layout.item_recept_row, fromLayout, false);
                FileUtils.setDrawableFromAsset(((ImageView) row.findViewById(R.id.img)),
                        "items/" + fromItem.getDotaId() + ".png");
                ((TextView) row.findViewById(R.id.name)).setText(fromItem.getDotaName());
                ((TextView) row.findViewById(R.id.cost)).setText(String.valueOf(fromItem.getCost()));
                if (fromItem.getId() != 0) {
                    row.setOnClickListener(new OnDotaItemClickListener(fromItem.getId(), UP_REQUEST));
                }
                currentCost += fromItem.getCost();
                fromLayout.addView(row);
            }
            if (currentCost < item.getCost()) {
                LinearLayout row = (LinearLayout) getLayoutInflater().inflate(R.layout.item_recept_row, fromLayout, false);
                ((ImageView) row.findViewById(R.id.img)).setImageDrawable(FileUtils.getDrawableFromAsset(this, "items/recipe.png"));
                ((TextView) row.findViewById(R.id.name)).setText(getString(R.string.recipe));
                ((TextView) row.findViewById(R.id.cost)).setText(String.valueOf(item.getCost() - currentCost));
                fromLayout.addView(row);
            }
        }
        if (to != null && to.size() > 0) {
            findViewById(R.id.makes_to_title).setVisibility(View.VISIBLE);
            FlowLayout toLayout = (FlowLayout) findViewById(R.id.makes_to);
            toLayout.setVisibility(View.VISIBLE);
            toLayout.removeAllViews();
            for (Item toItem : to) {
                LinearLayout row = (LinearLayout) getLayoutInflater().inflate(R.layout.item_recept_row, toLayout, false);
                ((ImageView) row.findViewById(R.id.img)).setImageDrawable(FileUtils.getDrawableFromAsset(this, "items/" + toItem.getDotaId() + ".png"));
                ((TextView) row.findViewById(R.id.name)).setText(toItem.getDotaName());
                ((TextView) row.findViewById(R.id.cost)).setText(String.valueOf(toItem.getCost()));
                if (toItem.getId() != 0) {
                    row.setOnClickListener(new OnDotaItemClickListener(toItem.getId(), UP_REQUEST));
                }
                toLayout.addView(row);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == UP_REQUEST && resultCode == RESULT_CANCELED) {
            setResult(RESULT_CANCELED);
            finish();
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        changeOrientation();
    }

    private void changeOrientation() {
        if (getResources().getBoolean(R.bool.is_tablet)) {
            ((LinearLayout) findViewById(R.id.main_holder)).setOrientation(LinearLayout.HORIZONTAL);
        } else {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                ((LinearLayout) findViewById(R.id.main_holder)).setOrientation(LinearLayout.HORIZONTAL);
            } else {
                ((LinearLayout) findViewById(R.id.main_holder)).setOrientation(LinearLayout.VERTICAL);
            }
        }

    }

    public static class OnDotaItemClickListener implements View.OnClickListener {
        private long itemId;
        private boolean forResult = false;
        private int requestCode;

        public OnDotaItemClickListener(long itemId) {
            this.itemId = itemId;
        }

        public OnDotaItemClickListener(long itemId, int REQUEST_CODE) {
            this(itemId);
            forResult = true;
            requestCode = REQUEST_CODE;


        }

        @Override
        public void onClick(View v) {
            Context context = v.getContext();
            Intent intent = new Intent(context, ItemInfoActivity.class);
            intent.putExtra("id", itemId);
            if (forResult) {
                ((Activity) context).startActivityForResult(intent, requestCode);
            } else {
                context.startActivity(intent);
            }
        }
    }
}
