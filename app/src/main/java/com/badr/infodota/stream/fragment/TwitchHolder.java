package com.badr.infodota.stream.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.PopupMenu;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.R;
import com.badr.infodota.base.activity.ListHolderActivity;
import com.badr.infodota.stream.adapter.pager.TwitchPagerAdapter;
import com.badr.infodota.stream.api.Stream;
import com.badr.infodota.stream.service.TwitchService;

/**
 * User: ABadretdinov
 * Date: 07.03.14
 * Time: 15:18
 */
public class TwitchHolder extends Fragment { //pullToRefresh + FloatingActionButton.

    public static final int REFRESH = 6001;
    public static final int ADD_CHANNEL = 322;
    public static final int PLAYER_TYPE = 1403;
    private TwitchPagerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.twitch_holder, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        ActionMenuView actionMenuView = ((ListHolderActivity) getActivity()).getActionMenuView();
        actionMenuView.getMenu().clear();

        MenuItem add = menu.add(1, ADD_CHANNEL, 0, R.string.add_channel);
        add.setIcon(R.drawable.ic_menu_add);
        MenuItemCompat.setShowAsAction(add, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if (Build.VERSION.SDK_INT < 14) {
            preferences.edit().putInt("player_type", 1).commit();
            actionMenuView.setVisibility(View.GONE);
        } else {
            actionMenuView.setVisibility(View.VISIBLE);
            int currentPlayer = preferences.getInt("player_type", 0);
            Menu actionMenu = actionMenuView.getMenu();
            MenuItem player = actionMenu.add(1, PLAYER_TYPE, 1, getResources().getStringArray(R.array.player_types)[currentPlayer]);
            MenuItemCompat.setShowAsAction(player, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
            actionMenuView.setOnMenuItemClickListener(new ActionMenuView.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    return onOptionsItemSelected(menuItem);
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == ADD_CHANNEL) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            final EditText textView = new EditText(getActivity());
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            textView.setLayoutParams(lp);
            textView.setHint(R.string.channel_name);
            builder.setTitle(R.string.add_channel);
            builder.setView(textView);
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String channelName = textView.getText().toString();
                    if (!TextUtils.isEmpty(channelName)) {
                        TwitchService twitchService = BeanContainer.getInstance().getTwitchService();
                        Stream stream = new Stream();
                        stream.setChannel(channelName);
                        twitchService.addStream(getActivity(), stream);
                    }
                    dialog.dismiss();
                    adapter.updateList();
                }
            });
            builder.show();
            return true;
        } else if (item.getItemId() == PLAYER_TYPE) {
            final MenuItem player = item;
            PopupMenu popup = new PopupMenu(getActivity(), getActivity().findViewById(item.getItemId()));
            final Menu menu = popup.getMenu();
            String[] playerTypes = getResources().getStringArray(R.array.player_types);
            for (int i = 0; i < playerTypes.length; i++) {
                menu.add(2, i, 0, playerTypes[i]);
            }
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    player.setTitle(menuItem.getTitle());
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    preferences.edit().putInt("player_type", menuItem.getItemId()).commit();
                    return true;
                }
            });
            popup.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if (!prefs.getBoolean("twitchDialogShowed", false)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.attention);
            builder.setMessage(R.string.twitch_attention);
            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("twitchDialogShowed", true);
                    editor.commit();
                    dialog.dismiss();
                }
            });
            builder.show();
        }
        initPager();
    }

    public TwitchPagerAdapter getAdapter() {
        return adapter;
    }

    private void initPager() {

        adapter = new TwitchPagerAdapter(getChildFragmentManager(), getActivity());

        ViewPager pager = (ViewPager) getView().findViewById(R.id.pager);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(1);


        TabLayout tabLayout = (TabLayout) getView().findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);
    }
}