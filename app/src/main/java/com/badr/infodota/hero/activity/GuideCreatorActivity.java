package com.badr.infodota.hero.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.R;
import com.badr.infodota.base.activity.BaseActivity;
import com.badr.infodota.base.util.FileUtils;
import com.badr.infodota.hero.adapter.pager.GuideCreatorPagerAdapter;
import com.badr.infodota.hero.api.Hero;
import com.badr.infodota.hero.api.guide.custom.AbilityBuild;
import com.badr.infodota.hero.api.guide.custom.Guide;
import com.badr.infodota.hero.api.guide.custom.ItemBuild;
import com.badr.infodota.hero.service.HeroService;
import com.google.gson.Gson;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;

/**
 * User: Histler
 * Date: 14.02.14
 */
public class GuideCreatorActivity extends BaseActivity {
    public static final int SAVE_ID = 1001;
    GuideCreatorPagerAdapter pagerAdapter;
    private Hero hero;
    private String guideName;
    private String guidePath;
    private Guide guide;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem saveItem = menu.add(1, SAVE_ID, 1, R.string.save);
        saveItem.setIcon(R.drawable.ic_menu_save);
        MenuItemCompat.setShowAsAction(saveItem, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == SAVE_ID) {
            guide.setTitle(((TextView) findViewById(R.id.guide_name)).getText().toString());
            guide.setHero(hero.getDotaId());
            pagerAdapter.saveGuide();
            String entity = new Gson().toJson(guide);

            try {
                if (TextUtils.isEmpty(guidePath)) {
                    File externalFilesDir = FileUtils.externalFileDir(GuideCreatorActivity.this);
                    guidePath = externalFilesDir.getAbsolutePath()
                            + File.separator + "guides"
                            + File.separator + hero.getDotaId();
                    File pathFile = new File(guidePath);
                    guidePath += File.separator + "userguide_";
                    if (pathFile.exists() && pathFile.isDirectory()) {
                        guidePath += String.valueOf(pathFile.list().length);
                    } else {
                        guidePath += "0";
                    }
                    guidePath += ".json";
                }
                FileUtils.saveFile(guidePath,
                        new ByteArrayInputStream(entity.getBytes("UTF-8")));
                Toast.makeText(this, R.string.guide_saved, Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                setResult(RESULT_CANCELED);
            } finally {
                finish();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide_creator_holder);
        if (getSharedPreferences("guides", MODE_PRIVATE).getBoolean("showDialog", true)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.attention);
            builder.setMessage(R.string.guide_creator_message);
            builder.setPositiveButton(R.string.guide_next, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    getSharedPreferences("guides", MODE_PRIVATE).edit().putBoolean("showDialog", false).commit();
                    dialog.dismiss();
                }
            });
            builder.show();
        }
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey("id")) {
            HeroService heroService = BeanContainer.getInstance().getHeroService();
            hero = heroService.getHeroById(this, bundle.getLong("id"));
            final TypedArray styledAttributes = getTheme()
                    .obtainStyledAttributes(new int[]{R.attr.actionBarSize});
            int mActionBarSize = (int) styledAttributes.getDimension(0, 40) / 2;
            styledAttributes.recycle();
            ActionBar actionBar = getSupportActionBar();
            Bitmap icon = FileUtils.getBitmapFromAsset(this, "heroes/" + hero.getDotaId() + "/mini.png");
            if (icon != null) {
                icon = Bitmap.createScaledBitmap(icon, mActionBarSize, mActionBarSize, false);
                Drawable iconDrawable = new BitmapDrawable(getResources(), icon);
                //actionBar.setDisplayShowHomeEnabled(true);
                //actionBar.setIcon(iconDrawable);
                mToolbar.setNavigationIcon(iconDrawable);
            }
            actionBar.setTitle(hero.getLocalizedName());

            guideName = bundle.getString("guideName");
            guidePath = bundle.getString("guidePath");
            if (guidePath != null) {
                String entity = FileUtils.getTextFromFile(guidePath);
                guide = new Gson().fromJson(entity, Guide.class);
                ((TextView) findViewById(R.id.guide_name)).setText(guideName);
            }
            if (guide == null) {
                guide = new Guide();
                guide.setItemBuild(new ItemBuild());
                guide.setAbilityBuild(new AbilityBuild());
            }
            initPager();
        } else {
            finish();
        }
    }

    private void updatePager() {
        if (pagerAdapter != null) {
            pagerAdapter.updateWith(guide);
        }
    }

    private void initPager() {
        pagerAdapter = new GuideCreatorPagerAdapter(getSupportFragmentManager(), this, hero.getId(), guide);

        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(pagerAdapter);
        pager.setOffscreenPageLimit(2);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);
    }
}
