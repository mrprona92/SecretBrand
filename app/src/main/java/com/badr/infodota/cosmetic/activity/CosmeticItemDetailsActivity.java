package com.badr.infodota.cosmetic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.badr.infodota.R;
import com.badr.infodota.base.activity.BaseActivity;
import com.badr.infodota.base.util.ResourceUtils;
import com.badr.infodota.base.view.FlowLayout;
import com.badr.infodota.cosmetic.api.store.CosmeticItem;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * User: ABadretdinov
 * Date: 01.04.14
 * Time: 14:21
 */
public class CosmeticItemDetailsActivity extends BaseActivity {

    private CosmeticItem item;
    private CosmeticItem set;
    private ArrayList<CosmeticItem> setItems;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cosmetic_item_info);
        Bundle bundle = getIntent().getExtras();
        if (bundle.containsKey("set")) {
            set = (CosmeticItem) bundle.getSerializable("set");
        }
        if (bundle.containsKey("setItems")) {
            setItems = (ArrayList<CosmeticItem>) bundle.getSerializable("setItems");
        }
        if (bundle.containsKey("item")) {
            item = (CosmeticItem) bundle.getSerializable("item");
            initItem();
        }
    }

    @SuppressWarnings("deprecation")
    private void initItem() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(item.getName());
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(item.getName());
        title.setTextColor(getResources().getColor(ResourceUtils.COSMETIC_ITEM_QUALITY_IDS[item.getQuality()]));

        Glide.with(this).load(item.getImageUrlLarge()).placeholder(R.drawable.emptyitembg).into((ImageView) findViewById(R.id.image));

        TextView type = (TextView) findViewById(R.id.type);
        type.setText(item.getItemTypeName());

        TextView description = (TextView) findViewById(R.id.description);
        if (!TextUtils.isEmpty(item.getDescription())) {
            description.setVisibility(View.VISIBLE);
            description.setText(Html.fromHtml(item.getDescription()));
        } else {
            description.setVisibility(View.GONE);
        }
        TextView prices = (TextView) findViewById(R.id.prices);
        if (item.getPrices() != null && item.getPrices().size() > 0) {
            prices.setVisibility(View.VISIBLE);
            Map<String, Long> pricesMap = item.getPrices();
            Set<String> keySet = pricesMap.keySet();
            StringBuilder builder = new StringBuilder("\n");
            int index = 1;
            int size = keySet.size();
            Iterator<String> iterator = keySet.iterator();
            for (int i = 0; iterator.hasNext(); i++) {
                String key = iterator.next();
                float realPrice = pricesMap.get(key).floatValue() / 100;
                builder.append(String.valueOf(realPrice));
                builder.append(symbolForKey(key));
                if (index % 4 == 0) {
                    builder.append("\n");
                    index = 0;
                } else if (i != size - 1) {
                    builder.append(" / ");
                }
                index++;
            }
            prices.setText(builder.toString());

        } else {
            prices.setVisibility(View.GONE);
        }

        if ("bundle".equals(item.getItemClass())) {
            setSetsItemsInfo();
        } else {
            setSetInfo();
        }
    }

    private void setSetsItemsInfo() {
        FlowLayout flowLayout = (FlowLayout) findViewById(R.id.flow_set);
        findViewById(R.id.set).setVisibility(View.GONE);
        findViewById(R.id.set_title).setVisibility(View.GONE);
        if (setItems != null && setItems.size() > 0) {
            flowLayout.setVisibility(View.VISIBLE);
            flowLayout.removeAllViews();
            LayoutInflater inflater = getLayoutInflater();
            for (final CosmeticItem cosmeticItem : setItems) {
                View view = inflater.inflate(R.layout.cosmetic_item_row, null, false);

                Glide.with(this).load(cosmeticItem.getImageUrl()).placeholder(R.drawable.emptyitembg).into((ImageView) findViewById(R.id.img));
                TextView name = (TextView) view.findViewById(R.id.name);
                name.setText(cosmeticItem.getName());
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        recreateForItem(cosmeticItem);

                    }
                });
                flowLayout.addView(view);
            }
        } else {
            flowLayout.setVisibility(View.GONE);
        }

    }

    private void recreateForItem(CosmeticItem cosmeticItem) {
        Intent intent = new Intent(CosmeticItemDetailsActivity.this, CosmeticItemDetailsActivity.class);
        intent.putExtra("item", cosmeticItem);
        intent.putExtra("set", set);
        intent.putExtra("setItems", setItems);
        startActivityForResult(intent, 1);
    }

    private void setSetInfo() {
        View setLayout = findViewById(R.id.set);
        View setTitle = findViewById(R.id.set_title);
        if (set == null) {
            setLayout.setVisibility(View.GONE);
            setTitle.setVisibility(View.GONE);
        } else {
            findViewById(R.id.flow_set).setVisibility(View.GONE);
            setLayout.setVisibility(View.VISIBLE);
            setTitle.setVisibility(View.VISIBLE);
            Glide.with(this).load(set.getImageUrl()).placeholder(R.drawable.emptyitembg).into((ImageView) findViewById(R.id.set_img));
            ((TextView) setLayout.findViewById(R.id.set_name)).setText(set.getName());
            setLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recreateForItem(set);
                }
            });
        }
    }

    private String symbolForKey(String key) {
        if ("USD".equals(key)) {
            return "$";
        }
        if ("GBP".equals(key)) {
            return "£";
        }
        if ("EUR".equals(key)) {
            return "€";
        }
        if ("RUB".equals(key)) {
            return "руб.";
        }
        return key;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_CANCELED) {
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
}
