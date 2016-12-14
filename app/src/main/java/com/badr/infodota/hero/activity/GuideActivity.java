package com.badr.infodota.hero.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.R;
import com.badr.infodota.base.activity.BaseActivity;
import com.badr.infodota.base.service.LocalSpiceService;
import com.badr.infodota.base.util.FileUtils;
import com.badr.infodota.hero.adapter.pager.GuidePagerAdapter;
import com.badr.infodota.hero.api.Hero;
import com.badr.infodota.hero.api.guide.custom.Guide;
import com.badr.infodota.hero.service.HeroService;
import com.badr.infodota.hero.task.GuidesLoadRequest;
import com.google.gson.Gson;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * User: ABadretdinov
 * Date: 28.01.14
 * Time: 12:31
 */
public class GuideActivity extends BaseActivity implements RequestListener<List> {
    public static final int ADD = 1001;
    public static final int EDIT = 1002;
    public static final int REMOVE = 1003;
    public static final int SEND = 1004;
    GuidePagerAdapter pagerAdapter;
    private Hero hero;
    private Spinner spinner;
    private Map<String, String> guideNameMap = new LinkedHashMap<String, String>();
    private Guide guide;
    private Menu menu;
    private int selected;
    private SpiceManager mSpiceManager = new SpiceManager(LocalSpiceService.class);

    @Override
    protected void onStart() {
        super.onStart();
        if (!mSpiceManager.isStarted()) {
            mSpiceManager.start(this);
            updateGuides();
        }
    }

    @Override
    protected void onDestroy() {
        if (mSpiceManager.isStarted()) {
            mSpiceManager.shouldStop();
        }
        super.onDestroy();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        addGuideMenuItem();

        return super.onCreateOptionsMenu(menu);
    }

    private void addGuideMenuItem() {
        if (menu != null) {
            menu.clear();
            MenuItem addGuide = menu.add(0, ADD, 1, R.string.add_guide);
            addGuide.setIcon(R.drawable.ic_menu_add);
            MenuItemCompat.setShowAsAction(addGuide, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
        }
    }

    private void addEditMenuItem() {
        addGuideMenuItem();
        MenuItem edit = menu.add(0, EDIT, 1, R.string.edit_guide);
        edit.setIcon(R.drawable.ic_menu_edit);
        MenuItemCompat.setShowAsAction(edit, MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItem remove = menu.add(0, REMOVE, 1, R.string.delete_guide);
        remove.setIcon(R.drawable.ic_menu_delete);
        MenuItemCompat.setShowAsAction(remove, MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        /*MenuItem send=menu.add(0, SEND, 1, R.string.send_guide);
        send.setIcon(R.drawable.ic_menu_send);
        MenuItemCompat.setShowAsAction(send,MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == ADD) {
            Intent intent = new Intent(this, GuideCreatorActivity.class);
            intent.putExtra("id", hero.getId());
            //intent.putExtra("guideName","test");
            startActivityForResult(intent, 1001);
            return true;
        } else if (id == EDIT) {
            String selectedGuideKey = guideNameMap.keySet().toArray(new String[guideNameMap.keySet().size()])[selected];
            Intent intent = new Intent(this, GuideCreatorActivity.class);
            intent.putExtra("id", hero.getId());
            intent.putExtra("guidePath", selectedGuideKey);
            intent.putExtra("guideName", guide.getTitle());
            startActivityForResult(intent, 1001);
            return true;
        } else if (id == REMOVE) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.attention);
            builder.setMessage(getString(R.string.sure_delete_Guide));
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    File file = new File(getFileName(guide.getTitle()));
                    if (file.exists()) {
                        if (file.delete()) {
                            Toast.makeText(GuideActivity.this, getString(R.string.guide_deleted), Toast.LENGTH_SHORT).show();
                            updateGuides();
                        } else {
                            Toast.makeText(GuideActivity.this, getString(R.string.guide_deletion_error), Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
            builder.show();
        }
        // NO MORE GUIDES AT MY EMAIL!!!
        /*else if(id==SEND){
            Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
            emailIntent.setType("application/json");
            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]
                    {"***@gmail.com"});
            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                    hero.getDotaId());
            *//*emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                    "go on read the emails");*//*
            //Log.v(getClass().getSimpleName(), "sPhotoUri=" + Uri.parse("file:/"+ sPhotoFileName));
            emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" +getFileName(guide.getTitle())));
            startActivity(Intent.createChooser(emailIntent, getString(R.string.send_mail)));
        }*/
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide_holder);
        Bundle bundle = getIntent().getExtras();
        spinner = (Spinner) findViewById(R.id.guide_spinner);
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
            initPager();
        } else {
            finish();
        }
    }

    private void updateGuides() {
        mSpiceManager.execute(new GuidesLoadRequest(getApplicationContext(), hero.getDotaId(), guideNameMap), this);
    }

    private String getFileName(String guideTitle) {
        Set<String> fileNames = guideNameMap.keySet();
        for (String tekFile : fileNames) {
            if (guideTitle.equals(guideNameMap.get(tekFile))) {
                return tekFile;
            }
        }
        return null;
    }

    private void updatePager() {
        if (pagerAdapter != null) {
            pagerAdapter.updateWith(guide);
        }
    }

    private void initPager() {
        pagerAdapter = new GuidePagerAdapter(getSupportFragmentManager(), this, hero.getId(), guide);

        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(pagerAdapter);
        pager.setOffscreenPageLimit(2);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            updateGuides();
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestFailure(SpiceException spiceException) {
        Toast.makeText(this, spiceException.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestSuccess(List list) {
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        final SharedPreferences prefs = getSharedPreferences("last_watched_guide", MODE_PRIVATE);
        spinner.setSelection(Math.min(adapter.getCount() - 1, prefs.getInt(hero.getDotaId(), 0)));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                selected = position;
                String guideTitle = adapter.getItem(position);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt(hero.getDotaId(), position);
                editor.commit();
                String fileName = getFileName(guideTitle);
                String entity;
                if (fileName.startsWith("guides")) {
                    entity = FileUtils.getTextFromAsset(GuideActivity.this, fileName);
                    addGuideMenuItem();
                } else {
                    entity = FileUtils.getTextFromFile(fileName);
                    addEditMenuItem();
                }
                guide = new Gson().fromJson(entity, Guide.class);
                updatePager();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
