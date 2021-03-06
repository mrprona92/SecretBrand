package com.mrprona.dota2assitant.base.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.NativeExpressAdView;
import com.mrprona.dota2assitant.BeanContainer;
import com.mrprona.dota2assitant.R;
import com.mrprona.dota2assitant.base.configs.ScreenIDs;
import com.mrprona.dota2assitant.base.dao.DatabaseManager;
import com.mrprona.dota2assitant.base.dialog.SubmitBugDialog;
import com.mrprona.dota2assitant.base.fragment.AgreementFragment;
import com.mrprona.dota2assitant.base.fragment.ConfirmDialog;
import com.mrprona.dota2assitant.base.fragment.SCAlertDialog;
import com.mrprona.dota2assitant.base.fragment.SCBaseFragment;
import com.mrprona.dota2assitant.base.fragment.SearchableFragment;
import com.mrprona.dota2assitant.base.menu.fragment.MenuFragment;
import com.mrprona.dota2assitant.base.service.LocalSpiceService;
import com.mrprona.dota2assitant.base.util.AppRater;
import com.mrprona.dota2assitant.base.util.UpdateUtils;
import com.mrprona.dota2assitant.cosmetic.fragment.CosmeticItemsList;
import com.mrprona.dota2assitant.counter.fragment.CounterPickFilter;
import com.mrprona.dota2assitant.hero.fragment.HeroesList;
import com.mrprona.dota2assitant.hero.service.HeroService;
import com.mrprona.dota2assitant.item.fragment.ItemsList;
import com.mrprona.dota2assitant.joindota.fragment.LeaguesGamesList;
import com.mrprona.dota2assitant.news.fragment.NewsList;
import com.mrprona.dota2assitant.player.fragment.PlayerGroupsHolder;
import com.mrprona.dota2assitant.quiz.activity.HighscoreActivity;
import com.mrprona.dota2assitant.quiz.dialog.SubmitHighscoreDialog;
import com.mrprona.dota2assitant.quiz.fragment.GameOverFragment;
import com.mrprona.dota2assitant.quiz.fragment.QuizTypeSelect;
import com.mrprona.dota2assitant.ranking.fragment.RankingMainFragment;
import com.mrprona.dota2assitant.stream.fragment.TwitchHolder;
import com.mrprona.dota2assitant.trackdota.fragment.TrackdotaMain;
import com.mrprona.dota2assitant.youtube.YouTubeRecyclerPlaylistFragment;
import com.mrprona.dota2assitant.youtube.YoutubeFragment;
import com.octo.android.robospice.SpiceManager;
import com.util.TypefaceUtil;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import devlight.io.library.ntb.NavigationTabBar;

import static android.view.View.VISIBLE;

//import android.support.v7.widget.ActionMenuPresenter;

/**
 * User: ABadretdinov
 * Date: 15.01.14
 * Time: 14:27
 */
public class ListHolderActivity extends BaseActivity implements SearchView.OnQueryTextListener, AdapterView.OnItemSelectedListener, SubmitHighscoreDialog.ConfirmDialogListener {
    int lastSelected = -1;

    HeroService heroService = BeanContainer.getInstance().getHeroService();
    private SpiceManager mSpiceManager = new SpiceManager(LocalSpiceService.class);
    private boolean doubleBackToExitPressedOnce = false;
    protected final String TAG = getClass().getSimpleName();
    private Spinner navSpinner;

    public static int CODE_WRITE_SETTINGS_PERMISSION = 1001;

    @Nullable
    @BindView(R.id.btnBack)
    ImageView btnBack;

    @Nullable
    @BindView(R.id.lblToolbarTitle)
    TextView lblToolbarTitle;

    private ScreenIDs.ScreenTab mCurrentTab;

    private SCBaseFragment mCurrentFragment;

    @BindColor(R.color.cmn_white)
    int tabHighLightTextColor;


    @BindColor(R.color.ranking_bgr_row)
    int tabNormalTextColor;

    private FragmentManager mFragmentManager;

    private static Context appContext;


    private void initControl() {
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Log.e(TAG, "onBackStackChanged() called");
                Fragment fr = mFragmentManager.findFragmentById(R.id.details);
                if (fr != null) {
                    mCurrentFragment = (SCBaseFragment) fr;
                    updateUI();
                    Log.e(TAG, "Current fragment = " + fr.getClass().getSimpleName());
                }
            }
        });
    }


    public static Map<Long, Integer> mMapTypeHero;


    public static Map<Long, Integer> getMapTypeHero() {
        return mMapTypeHero;
    }

    @Override
    protected void onStart() {
        ButterKnife.bind(this);
        Log.d("BINH", "onStart() called");

        mMapTypeHero = DatabaseManager.getInstance(this).mHelper.getAllStatsHero();
        /*
        if (!mSpiceManager.isStarted()) {
            mSpiceManager.start(getApplicationContext());
            final int currentVersion = localUpdateService.getVersion(this);
            if (currentVersion != Helper.DATABASE_VERSION) {
                mSpiceManager.execute(new UpdateLoadRequest(getApplicationContext()), this);
            }
        }*/

        //Chartboost.onStart(this);
        super.onStart();
    }


    @Override
    protected void onResume() {
        super.onResume();
        //Chartboost.onResume(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Chartboost.onStop(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Chartboost.onDestroy(this);
    }

    NativeExpressAdView adView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate() called with: " + "savedInstanceState = [" + this.getDatabasePath("dota2.db") + "]");
        setTheme(R.style.Infodota);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_fragment_holder);

        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/future.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf

        //mActionMenuView.setPresenter(new ActionMenuPresenter(this));

        FacebookSdk.sdkInitialize(getApplicationContext());

        // Initialize the Mobile Ads SDK.§

        MobileAds.initialize(this, getString(R.string.banner_ad_unit_id));


       /* Chartboost.startWithAppId(this, getString(R.string.appIDChartboost), getString(R.string.appSignature));
        Chartboost.onCreate(this);*/

        AppEventsLogger.activateApp(this);

        MenuFragment.updateActivity(this);

        Log.d(TAG, "onCreate() called with: " + "savedInstanceState = [" + savedInstanceState + "]");

        ActionBar bar = getSupportActionBar();
        bar.setDisplayShowTitleEnabled(false);
        bar.setDisplayShowHomeEnabled(false);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.edit().remove("mainMenuLastSelected").commit();

        // BinhTran comment 23/03 for calling redundant
        // UpdateUtils.checkNewVersion(this, false);


        navSpinner = (Spinner) mToolbar.findViewById(R.id.nav_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, getResources().getStringArray(R.array.main_menu));
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        navSpinner.setAdapter(adapter);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int selected = prefs.getInt("mainMenuLastSelected", 0);

        navSpinner.setOnItemSelectedListener(this);
        navSpinner.setSelection(Math.min(selected, adapter.getCount() - 1));
        // navSpinner.setVisibility(View.GONE);

        initUI();

        initControl();

        UpdateUtils.checkNewVersion(this, false);

        appContext = this;

        AppRater.showRate(appContext);

        adView = (NativeExpressAdView) findViewById(R.id.adView);

        AdRequest request = new AdRequest.Builder()
                .addTestDevice("121EC3F83A2EAFBD46DB00F1773A13A0")
                .build();
        adView.loadAd(request);
        visiableAdview(8);
        //не нужен AppRater.onAppLaunched(this);
    }

    public void visiableAdview(int hide) {
        adView.setVisibility(hide);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        /*if (mCurrentFragment instanceof MenuFragment) {
            navSpinner.setVisibility(View.VISIBLE);
        } else {*/
        navSpinner.setVisibility(View.GONE);
        //}

        getMenuInflater().inflate(R.menu.search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint(getString(android.R.string.search_go));
        searchView.setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);
    }

    //public boolean isSearchViewVisible=false;
    public boolean onQueryTextChange(String textNew) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.details);
        if (fragment instanceof SearchableFragment) {
            ((SearchableFragment) fragment).onTextSearching(textNew);
        }
        if (fragment instanceof RankingMainFragment) {
            ((SearchableFragment) ((RankingMainFragment) fragment).getActiveFragment()).onTextSearching(textNew);
        }
        return true;
    }

    public boolean onQueryTextSubmit(String text) {

        return true;
    }


    @Override
    public void onBackPressed() {
        // If an interstitial is on screen, close it.
      /*  if (Chartboost.onBackPressed())
            return;*/

        /*if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getString(R.string.back_toast), Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, Constants.MILLIS_FOR_EXIT);*/
        if (mFragmentManager.getBackStackEntryCount() >= 1) {
            Log.d(TAG, "onBackPressed() called. More than 0 fragment in back stack");
           /* clearBackStack();
            openScreen(ScreenIDs.ScreenTab.MENU, MenuFragment.class, null, false, false);*/
            super.onBackPressed();
            return;
        } else {
            Log.d(TAG, "onBackPressed() called. Finish()");
            ConfirmDialog confirmDialog = new ConfirmDialog(ListHolderActivity.this);
            confirmDialog.setMessageId(R.string.ask_quit);
            confirmDialog.setTitleId(R.string.app_name);

            confirmDialog.setOnConfirmDialogListener(new ConfirmDialog.ConfirmDialogListener() {
                @Override
                public void onSelect(int indexButton) {
                    if (indexButton == 1) {
                        ListHolderActivity.super.onBackPressed();
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
                    }
                }
            });
            confirmDialog.show();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (!getResources().getBoolean(R.bool.is_tablet)) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                ((ViewGroup) mActionMenuView.getParent()).removeView(mActionMenuView);
                ((ViewGroup) findViewById(R.id.top_container)).addView(mActionMenuView);
                ((LinearLayout.LayoutParams) mActionMenuView.getLayoutParams()).weight = 1;
                ((LinearLayout.LayoutParams) mToolbar.getLayoutParams()).weight = 1;
            } else {
                ((ViewGroup) mActionMenuView.getParent()).removeView(mActionMenuView);
                ((ViewGroup) findViewById(R.id.main_view)).addView(mActionMenuView);
                ((LinearLayout.LayoutParams) mActionMenuView.getLayoutParams()).weight = 0;
                ((LinearLayout.LayoutParams) mToolbar.getLayoutParams()).weight = 0;
            }
        }
        mActionMenuView.onConfigurationChanged(newConfig);
        super.onConfigurationChanged(newConfig);
    }

    public void replaceFragment(Fragment details) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.details, details);
        ft.commitAllowingStateLoss();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            default:
            case 0:
                mCurrentFragment = new MenuFragment();
                break;
            case 1:
                mCurrentFragment = new ItemsList();
                break;
            case 2:
                mCurrentFragment = new PlayerGroupsHolder();
                break;
            case 3:
                mCurrentFragment = new CounterPickFilter();
                break;
            case 4:
                mCurrentFragment = new CosmeticItemsList();
                break;
            case 5:
                mCurrentFragment = new QuizTypeSelect();
                break;
            case 6:
                mCurrentFragment = new TwitchHolder();
                break;
            case 7:
                // mCurrentFragment = new NewsList();
                break;
            case 8:
                mCurrentFragment = LeaguesGamesList.newInstance(null);
                break;
            case 9:
                mCurrentFragment = new TrackdotaMain();
            case 10:
                mCurrentFragment = new AgreementFragment();
                break;
                    /*
                case 9:
					details=new LeaguesGamesList.newInstance("&c2=7057&c1=2390");
                    break;*/
        }
        replaceFragment(mCurrentFragment);
        lastSelected = position;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.edit().putInt("mainMenuLastSelected", lastSelected).commit();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void initUI() {
        ActionBar bar = getSupportActionBar();
        bar.setDisplayShowTitleEnabled(false);
        bar.setDisplayShowHomeEnabled(false);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.edit().remove("mainMenuLastSelected").commit();

        final String[] colors = getResources().getStringArray(R.array.default_preview);

        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb_horizontal);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_tab4_menu),
                        Color.parseColor(colors[4]))
                        .selectedIcon(getResources().getDrawable(R.drawable.ic_tab4_menu))
                        .title("MENU")
                        .badgeTitle("777")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_tab1_live),
                        Color.parseColor(colors[0]))
                        .selectedIcon(getResources().getDrawable(R.drawable.ic_tab1_live))
                        .title("LIVE")
                        .badgeTitle("NTB")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_tab3_quiz),
                        Color.parseColor(colors[1]))
//                        .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .title("QUIZ")
                        .badgeTitle("with")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_tab4_youtube),
                        Color.parseColor(colors[2]))
                        .selectedIcon(getResources().getDrawable(R.drawable.ic_tab4_youtube))
                        .title("CLIP")
                        .badgeTitle("state")
                        .build()
        );
        navigationTabBar.setModels(models);
        navigationTabBar.setModelIndex(0);
        navigationTabBar.setOnTabBarSelectedIndexListener(new NavigationTabBar.OnTabBarSelectedIndexListener() {
            @Override
            public void onStartTabSelected(NavigationTabBar.Model model, int index) {
                clearBackStack();
                visiableAdview(0);
                if (index == 0) {
                    visiableAdview(8);
                    openScreen(ScreenIDs.ScreenTab.MENU, MenuFragment.class, null, true, false);
                } else if (index == 1) {
                    openScreen(ScreenIDs.ScreenTab.LIVE, TrackdotaMain.class, null, true, false);
                } else if (index == 2) {
                    openScreen(ScreenIDs.ScreenTab.QUIZ, QuizTypeSelect.class, null, true, false);
                } else if (index == 3) {
                    visiableAdview(8);
                    openScreen(ScreenIDs.ScreenTab.CLIP, YoutubeFragment.class, null, true, false);
                }
            }

            @Override
            public void onEndTabSelected(NavigationTabBar.Model model, int index) {

            }
        });


        navigationTabBar.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < navigationTabBar.getModels().size(); i++) {
                    final NavigationTabBar.Model model = navigationTabBar.getModels().get(i);
                    navigationTabBar.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            model.showBadge();
                        }
                    }, i * 100);
                }
            }
        }, 500);

        /*navSpinner = (Spinner) mToolbar.findViewById(R.id.nav_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, getResources().getStringArray(R.array.main_menu));
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        navSpinner.setAdapter(adapter);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int selected = prefs.getInt("mainMenuLastSelected", 0);

        navSpinner.setOnItemSelectedListener(this);
        navSpinner.setSelection(Math.min(selected, adapter.getCount() - 1));*/
        // navSpinner.setVisibility(View.GONE);
    }


    public void onChangeFragment(int position) {
        switch (position) {
            default:
            case 0:
                //When want open link  to display
                /*Bundle bundle = new Bundle();
                bundle.putString(AgreementFragment.ARG_URL, "http://www.gosugamers.net/dota2/rankings");
                openScreen(ScreenIDs.ScreenTab.MENU, AgreementFragment.class, bundle, true, true);
                */
                openScreen(ScreenIDs.ScreenTab.MENU, RankingMainFragment.class, null, true, true);
                break;
            case 1:
                visiableAdview(0);
                openScreen(ScreenIDs.ScreenTab.MENU, HeroesList.class, null, true, true);
                break;
            case 2:
                //mFragmentDetails = new ItemsList();
                visiableAdview(0);
                openScreen(ScreenIDs.ScreenTab.MENU, ItemsList.class, null, true, true);
                break;
            case 3:
                openScreen(ScreenIDs.ScreenTab.MENU, NewsList.class, null, true, true);
                break;
            case 4:
                openScreen(ScreenIDs.ScreenTab.MENU, LeaguesGamesList.class, null, true, true);
                break;
            case 5:
                openScreen(ScreenIDs.ScreenTab.MENU, TwitchHolder.class, null, true, true);
                break;
            case 6:
                SubmitBugDialog mSubmit = new SubmitBugDialog(this);
                mSubmit.show();
                break;
            case 7:
                UpdateUtils.checkNewVersion(ListHolderActivity.this, true);
                break;
            case 8:
                startActivity(new Intent(ListHolderActivity.this, AboutActivity.class));
                break;
            case 9:
                //TODO quit app
                AlertDialog alertDialog = new AlertDialog.Builder(ListHolderActivity.this).create();
                alertDialog.setTitle("Dota Assitant");
                alertDialog.setMessage("Do you want quit application?");
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
                    }
                });
                alertDialog.show();
                break;
        }
        replaceFragment(mCurrentFragment);
    }


    /**
     * Returns the shared progress dialog.
     *
     * @author Binh.TH
     */
    @SuppressLint("InflateParams")
    private Dialog getProgressDialog() {
        if (mProgressDialog == null) {         // Create if null
            mProgressDialog = new Dialog(this, android.R.style.Theme_Black);
            View view = LayoutInflater.from(this).inflate(R.layout.cmn_process, null);
            mProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mProgressDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
            mProgressDialog.setContentView(view);
        }
        return mProgressDialog;
    }


    private Dialog mProgressDialog;

    public void showProgressDialog(final String message) {
        showProgressDialog(message, false);
    }

    private int sDialogCount = 0;

    /**
     * Show progress dialog.
     *
     * @param message
     * @param cancelable
     */

    public void showProgressDialog(final String message, final boolean cancelable) {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Dialog dlg = getProgressDialog();
                        sDialogCount++;
                        if (!dlg.isShowing()) {
                            dlg.setCancelable(cancelable);
                            dlg.show();
                        }
                    } catch (Exception ex) {
                        // Do nothing
                    }
                }
            });
        } catch (Exception e) {
        }
    }


    /**
     * Hide progress dialog.
     */
    public void hideProgressDialog() {
        hideProgressDialog(true);
    }

    public void hideProgressDialog(final boolean isWait) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Dialog dlg = getProgressDialog();
                    sDialogCount--;
                    if (dlg != null && dlg.isShowing()) {
                        if (!isWait) {
                            sDialogCount = 0;
                            dlg.dismiss();
                        } else {
                            if (sDialogCount <= 0) dlg.dismiss();
                        }
                    }
                } catch (Exception ex) {
                    // Do nothing
                }
            }
        });
    }


    private SCAlertDialog mAlertDialog;

    public void showAlertDialog(final int message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mAlertDialog != null && mAlertDialog.isShowing()) {
                    mAlertDialog.hide();
                }
                mAlertDialog = new SCAlertDialog(ListHolderActivity.this, message, R.string.cmn_ok);
                mAlertDialog.show();
            }
        });
    }

    public void showAlertDialog(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mAlertDialog != null && mAlertDialog.isShowing()) {
                    mAlertDialog.hide();
                }
                mAlertDialog = new SCAlertDialog(ListHolderActivity.this, message, R.string.cmn_ok);
                mAlertDialog.show();
            }
        });
    }


    public void updateUI() {
        Log.d(TAG, "updateUI: called");

        if (mCurrentFragment instanceof HeroesList ||
                mCurrentFragment instanceof YouTubeRecyclerPlaylistFragment) {
            lblToolbarTitle.setVisibility(View.GONE);
            mActionMenuView.setVisibility(View.GONE);
        } else {
            lblToolbarTitle.setVisibility(VISIBLE);
        }

        if (mCurrentFragment != null) {
            if (mCurrentFragment.getToolbarTitle() != -99) {
                if (lblToolbarTitle != null && mCurrentFragment.getToolbarTitle() > 0) {
                    lblToolbarTitle.setText(mCurrentFragment.getToolbarTitle());
                }
            } else {
                lblToolbarTitle.setText(mCurrentFragment.getToolbarTitleString());
            }
        }

        //btnBack.setVisibility(mFragmentManager.getBackStackEntryCount() >= 1 ? View.VISIBLE : GONE);
       /* if (mCurrentFragment instanceof Coun)
            setHighLightTab(ScreenIDs.ScreenTab.RANKING);
        if (mCurrentFragment instanceof TradeSettingFragment)
            setHighLightTab(ScreenIDs.ScreenTab.TRADE_SETTING);
        if (mCurrentFragment instanceof TradeStatusFragment)
            setHighLightTab(ScreenIDs.ScreenTab.TRADE_SETTING);
        if (mCurrentFragment instanceof MenuFragment) setHighLightTab(ScreenIDs.ScreenTab.MENU);*/
    }


    private void openScreen2(ScreenIDs.ScreenTab tab, Class<? extends SCBaseFragment> fragmentClass, Bundle bundles, boolean isAnimate,
                             boolean shouldAddToBackStack) {

        //if (tab != ScreenIDs.ScreenTab.NOT_HIGHLIGHT) clearBackStack();
        if (getBaseContext() == null) return;

        this.mCurrentTab = tab;

        FragmentManager manager = getSupportFragmentManager();
        String tag = fragmentClass.getName();
        try {
            FragmentTransaction transaction = manager.beginTransaction();
            if (isAnimate)
                transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            //if (!manager.popBackStackImmediate(tag, 0) && manager.findFragmentByTag(tag) == null) {
            Log.e(TAG, "openScreen: fragment " + fragmentClass.getSimpleName() + " is NOT from back stack");
            mCurrentFragment = fragmentClass.newInstance();
            mCurrentFragment.setRetainInstance(true);
            if (bundles == null) bundles = new Bundle();
            mCurrentFragment.setArguments(bundles);
            if (shouldAddToBackStack) {
                transaction.addToBackStack(tag);
                Log.e(TAG, "openScreen: add " + tag + " to back stack");
            }
            transaction.replace(R.id.details, mCurrentFragment, tag);
            transaction.commitAllowingStateLoss();
//            } else {
//                Log.e(TAG, "openScreen: popped " + tag + " from back stack");
//                mCurrentFragment = (SCBaseFragment) manager.findFragmentByTag(tag);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openScreen(final ScreenIDs.ScreenTab tab, final Class<? extends SCBaseFragment> fragmentClass, final Bundle bundles, final boolean isAnimate,
                           final boolean shouldAddToBackStack) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                openScreen2(tab, fragmentClass, bundles, isAnimate, shouldAddToBackStack);
            }
        });
    }

    public void clearBackStack() {
        Log.e(TAG, "clearBackStack() called:" + mFragmentManager.getBackStackEntryCount());
        if (mFragmentManager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = mFragmentManager.getBackStackEntryAt(0);
            boolean didPop = mFragmentManager.popBackStackImmediate(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
            Log.d(TAG, "clearBackStack: didPop " + didPop);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static Context getAppContext() {
        return appContext;
    }

    @Override
    public void onSelect(int indexButton, int mode) {
        if ((indexButton == 1) || (mCurrentFragment instanceof GameOverFragment)) {
            Intent intent = new Intent(this, HighscoreActivity.class);
            Bundle sendData = new Bundle();
            sendData.putInt("mode", mode);
            intent.putExtras(sendData);
            startActivity(intent);
        }
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB) // API 11
    public void startMyTask(AsyncTask asyncTask) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        else
            asyncTask.execute();
    }

}
