package com.badr.infodota.base.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.ArrayAdapter;

import com.badr.infodota.R;
import com.badr.infodota.player.api.Unit;

import java.text.MessageFormat;
import java.util.Arrays;

/**
 * User: Histler
 * Date: 16.01.14
 */
public class SteamUtils {
    private static final long STEAM64ID = 76561197960265728L;

    public static long steam32to64(long steam32) {
        return STEAM64ID + steam32;
    }

    public static long steam64to32(long steam64) {
        return steam64 - STEAM64ID;
    }


    public static void addPlayerToListDialog(final Context context, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.add_player_title);
        String[] list = context.getResources().getStringArray(R.array.match_history_title);

        builder.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, android.R.id.text1, Arrays.copyOfRange(list, 1, 3)), listener);
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public static void deletePlayerFromListDialog(final Context context, Unit unit, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.delete_player_title);
        builder.setMessage(MessageFormat.format(context.getString(R.string.delete_player_msg), unit.getName()));
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton(R.string.delete, listener);
        builder.show();
    }

    public static String getItemImage(String itemDotaId) {
        return "file:///android_asset/items/" + itemDotaId + ".png";
    }

    public static String getHeroFullImage(String heroDotaId) {
        return "file:///android_asset/heroes/" + heroDotaId + "/full.png";
    }

    public static String getHeroPortraitImage(String heroDotaId) {
        return "file:///android_asset/heroes/" + heroDotaId + "/vert.jpg";
    }

    public static String getHeroMiniImage(String heroDotaId) {
        return "file:///android_asset/heroes/" + heroDotaId + "/mini.png";
    }

    public static String getSkillImage(String skillName) {
        return "file:///android_asset/skills/" + skillName + ".png";
    }
}
