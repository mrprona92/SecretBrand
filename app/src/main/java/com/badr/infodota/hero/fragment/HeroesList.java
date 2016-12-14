package com.badr.infodota.hero.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.badr.infodota.R;
import com.badr.infodota.base.activity.AboutActivity;
import com.badr.infodota.base.activity.ListHolderActivity;
import com.badr.infodota.base.adapter.OnItemClickListener;
import com.badr.infodota.base.fragment.SCBaseFragment;
import com.badr.infodota.base.fragment.SearchableFragment;
import com.badr.infodota.base.service.LocalSpiceService;
import com.badr.infodota.base.util.CarouselPageTransformer;
import com.badr.infodota.base.util.ResourceUtils;
import com.badr.infodota.base.util.SteamUtils;
import com.badr.infodota.base.util.UpdateUtils;
import com.badr.infodota.base.view.PagerContainer;
import com.badr.infodota.base.view.TransformableViewPager;
import com.badr.infodota.hero.activity.HeroInfoActivity;
import com.badr.infodota.hero.adapter.HeroesAdapter;
import com.badr.infodota.hero.api.CarouselHero;
import com.badr.infodota.hero.api.Hero;
import com.badr.infodota.hero.task.CarouselHeroesLoadRequest;
import com.badr.infodota.hero.task.HeroLoadRequest;
import com.bumptech.glide.Glide;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * User: ABadretdinov
 * Date: 15.01.14
 * Time: 14:14
 * https://github.com/florent37/MaterialLeanBack
 */
public class HeroesList extends SCBaseFragment implements SearchableFragment, RequestListener {
    RecyclerView gridView;
    PagerContainer mContainer;
    TransformableViewPager pager;
    boolean carousel;
    RecyclerView.LayoutManager layoutManager;
    private SpiceManager mSpiceManager = new SpiceManager(LocalSpiceService.class);
    private String mSearch = null;
    private String mFilter = null;

    @Override
    public void onStart() {
        if (!mSpiceManager.isStarted()) {
            mSpiceManager.start(getActivity());
            if (carousel) {
                loadHeroesForCarousel();
            } else {
                loadHeroesForGridView();
            }
        }
        super.onStart();
    }

    @Override
    public void onDestroy() {
        if (mSpiceManager.isStarted()) {
            mSpiceManager.shouldStop();
        }
        super.onDestroy();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        ActionMenuView actionMenuView = ((ListHolderActivity) getActivity()).getActionMenuView();
        Menu actionMenu = actionMenuView.getMenu();
        actionMenu.clear();
        actionMenuView.setVisibility(View.GONE);
        inflater.inflate(R.menu.main, menu);
        MenuItem item = menu.add(1, 1001, 1, getString(R.string.grid_carousel));
        if (carousel) {
            item.setIcon(R.drawable.gridview);
        } else {
            item.setIcon(R.drawable.carousel);
        }

        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
        actionMenuView.setOnMenuItemClickListener(new ActionMenuView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return onOptionsItemSelected(menuItem);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case 1001:
                SharedPreferences preferences = getActivity().getSharedPreferences("settings", 1);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("carousel", !preferences.getBoolean("carousel", false));
                editor.commit();
                ((ListHolderActivity) getActivity()).replaceFragment(new HeroesList());
                return true;
            case R.id.filter:
                PopupMenu popup = new PopupMenu(getActivity(), getActivity().findViewById(item.getItemId()));
                final Menu menu = popup.getMenu();
                String[] heroRoles = getResources().getStringArray(R.array.hero_roles);
                for (int i = 0; i < heroRoles.length; i++) {
                    menu.add(2, i, 0, heroRoles[i]);
                }
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == 0) {
                            item.setTitle(R.string.filter);
                            mFilter = null;
                        } else {
                            item.setTitle(menuItem.getTitle());
                            mFilter = ResourceUtils.getHeroRole(menuItem.getItemId());
                        }
                        if (carousel) {
                            loadHeroesForCarousel();
                        } else {
                            loadHeroesForGridView();
                        }
                        return true;
                    }
                });
                popup.show();
                return true;
           /* case R.id.about:
                startActivity(new Intent(getActivity(), AboutActivity.class));
                return true;
            case R.id.locale:
                showLanguageDialog();
                return true;
            case R.id.new_version:
                UpdateUtils.checkNewVersion(getActivity(), true);
                return true;*/
        }
        return super.onOptionsItemSelected(item);
    }

    private void showLanguageDialog() {
        final Activity activity = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(getString(R.string.change_language));
        final String[] localeCodes = new String[]{"en-us", "ru"};
        String[] locales = new String[]{"English", "Русский"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item, android.R.id.text1, locales);
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                SharedPreferences localPrefs = activity.getSharedPreferences("locale", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = localPrefs.edit();
                String localeSelected = localeCodes[which];
                editor.putString("current", localeSelected);
                editor.commit();
                Locale locale = new Locale(localeSelected);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                activity.getApplicationContext().getResources().updateConfiguration(config, null);
                restartActivity(activity);
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void restartActivity(Activity activity) {
        Intent intent = activity.getIntent();
        activity.finish();
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SharedPreferences preferences = getActivity().getSharedPreferences("settings", 1);
        carousel = preferences.getBoolean("carousel", false);
        if (!carousel) {
            return inflater.inflate(R.layout.hero_list, container, false);
        } else {
            return inflater.inflate(R.layout.hero_list_carousel, container, false);
        }
    }

    @Override
    public int getToolbarTitle() {
        return 0;
    }

    @Override
    public String getToolbarTitleString() {
        return null;
    }

    @Override
    protected int getViewContent() {
        return 0;
    }

    @Override
    protected void initUI() {

    }

    @Override
    protected void initControls() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void hideInformation() {

    }

    @Override
    protected void registerListeners() {

    }

    @Override
    protected void unregisterListener() {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        //View view=getActivity().findViewById(R.id.actionMenuView);
        View root = getView();
        if (root == null) {
            return;
        }
        if (carousel) {
            mContainer = (PagerContainer) root.findViewById(R.id.pager_container);

            pager = mContainer.getViewPager();


            //final HeroCarouselPagerAdapter adapter = new HeroCarouselPagerAdapter(heroes);
            //pager.setAdapter(adapter);
            pager.setOffscreenPageLimit(5);
            pager.setPageTransformer(true, new CarouselPageTransformer());

            //If hardware acceleration is enabled, you should also remove
            // clipping on the pager for its children.
            pager.setClipChildren(false);
            pager.setPageMargin(0);
        } else {
            gridView = (RecyclerView) root.findViewById(R.id.gridView);
            gridView.setHasFixedSize(true);
            layoutManager = new GridLayoutManager(getActivity(), 4);
            //layoutManager.setReverseLayout(true);
            gridView.setLayoutManager(layoutManager);
            setColumnSize();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setColumnSize();
    }

    private void setColumnSize() {
        if (gridView != null && layoutManager instanceof GridLayoutManager) {
            if (getResources().getBoolean(R.bool.is_tablet)) {
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    ((GridLayoutManager) layoutManager).setSpanCount(6);

                } else {
                    ((GridLayoutManager) layoutManager).setSpanCount(4);
                }
            } else {
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    ((GridLayoutManager) layoutManager).setSpanCount(4);
                } else {
                    ((GridLayoutManager) layoutManager).setSpanCount(3);
                }
            }
        }
    }

    private void openHeroInfo(long id) {
        Intent intent = new Intent(getActivity(), HeroInfoActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    @SuppressWarnings("unchecked")
    private void loadHeroesForGridView() {
        Activity activity = getActivity();
        if (activity != null) {
            mSpiceManager.execute(new HeroLoadRequest(activity.getApplicationContext(), mFilter), this);
        }
    }

    @Override
    public void onTextSearching(final String text) {
        //if we searched before, or searching now
        if (!TextUtils.isEmpty(mSearch) || !TextUtils.isEmpty(text)) {
            this.mSearch = text;
            if (carousel) {
                loadHeroesForCarousel();
            } else {
                loadHeroesForGridView();
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void loadHeroesForCarousel() {
        Activity activity = getActivity();
        if (activity != null) {
            mSpiceManager.execute(new CarouselHeroesLoadRequest(activity.getApplicationContext(), mFilter, mSearch), this);
        }
    }

    @Override
    public void onRequestFailure(SpiceException spiceException) {
    }

    @Override
    public void onRequestSuccess(Object o) {
        if (o instanceof CarouselHero.List) {
            CarouselHero.List result = (CarouselHero.List) o;
            HeroCarouselPagerAdapter adapter = new HeroCarouselPagerAdapter(result);
            pager.setAdapter(adapter);
        } else if (o instanceof Hero.List) {
            Hero.List result = (Hero.List) o;
            final HeroesAdapter adapter = new HeroesAdapter(result);
            Filter filter = adapter.getFilter();
            filter.filter(mSearch);
            gridView.setAdapter(adapter);
            adapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Hero hero = adapter.getItem(position);
                    if (hero != null) {
                        openHeroInfo(hero.getId());
                    }
                }
            });
        }
    }

    public class HeroCarouselPagerAdapter extends PagerAdapter {
        private List<CarouselHero> mData;
        private String dir;

        public HeroCarouselPagerAdapter(List<CarouselHero> heroes) {
            mData = heroes != null ? heroes : new ArrayList<CarouselHero>();
            File externalFilesDir;
            externalFilesDir = Environment.getExternalStorageDirectory();
            if (externalFilesDir == null) {
                externalFilesDir = new File(getActivity().getFilesDir().getPath() + getActivity().getPackageName() + "/files");
            } else {
                externalFilesDir = new File(externalFilesDir, "/Android/data/" + getActivity().getPackageName() + "/files");
            }
            if (!externalFilesDir.exists()) {
                externalFilesDir.mkdirs();
            }
            dir = externalFilesDir.getAbsolutePath();
            dir += File.separator + "anim" + File.separator;
        }

        public CarouselHero getItem(int position) {
            return mData.get(position);
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Context context = container.getContext();
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            final ViewGroup view = (ViewGroup) layoutInflater.inflate(R.layout.hero_carousel_row, container, false);
            final CarouselHero hero = getItem(position);

            File gifFile = new File(dir + hero.getDotaId() + File.separator, "anim.gif");
            GifImageView imageView = (GifImageView) view.findViewById(R.id.gifHero);
            if (gifFile.exists()) {
                try {
                    GifDrawable gifFromFile = new GifDrawable(gifFile);
                    imageView.setImageDrawable(gifFromFile);
                } catch (IOException e) {
                    //ignored
                }
            } else {
                Glide.with(context).load(SteamUtils.getHeroPortraitImage(hero.getDotaId())).into(imageView);
            }


            String[] skills = hero.getSkills();
            LinearLayout skillsHolder = (LinearLayout) view.findViewById(R.id.skills_holder);
            if (skills != null) {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
                for (String skill : skills) {
                    ViewGroup skillView = (ViewGroup) layoutInflater.inflate(R.layout.skill_carousel_holder, skillsHolder, false);
                    skillView.setLayoutParams(layoutParams);
                    Glide.with(context).load(SteamUtils.getSkillImage(skill)).into((ImageView) skillView.findViewById(R.id.skill_img));
                    skillsHolder.addView(skillView);
                }
            }
            ImageView mainAttr = (ImageView) view.findViewById(R.id.main_attr);
            switch (hero.getPrimaryStat()) {
                case 0:
                    mainAttr.setImageResource(R.drawable.pip_str);
                    break;
                case 1:
                    mainAttr.setImageResource(R.drawable.pip_agi);
                    break;
                default:
                    mainAttr.setImageResource(R.drawable.pip_int);
                    break;
            }
            ((TextView) view.findViewById(R.id.hero_name)).setText(hero.getLocalizedName().toUpperCase());
            final int finalPosition = position;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (finalPosition == pager.getCurrentItem()) {
                        openHeroInfo(hero.getId());
                    }
                }
            });

            container.addView(view);
            Log.d("TEST", "instantiateItem() called with: " + "container = [" + container + "], position = [" + position + "]");
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            GifImageView gifView = (GifImageView) ((View) object).findViewById(R.id.gifHero);
            if (gifView.getDrawable() instanceof GifDrawable) {
                ((GifDrawable) gifView.getDrawable()).recycle();
            }
            gifView.setImageDrawable(null);
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view == object);
        }
    }
}
