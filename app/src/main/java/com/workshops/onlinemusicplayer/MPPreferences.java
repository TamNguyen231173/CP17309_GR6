package com.workshops.onlinemusicplayer;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public class MPPreferences {
    private static SharedPreferences.Editor getEditor(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                MPConstants.PACKAGE_NAME, Context.MODE_PRIVATE
        );
        return sharedPreferences.edit();
    }

    private static SharedPreferences getSharedPref(Context context) {
        return context.getSharedPreferences(
                MPConstants.PACKAGE_NAME, Context.MODE_PRIVATE
        );
    }

    public static void storeAlbumRequest(Context context, boolean val) {
        getEditor(context).putBoolean(MPConstants.SETTINGS_ALBUM_REQUEST, val).apply();
    }

    public static boolean getAlbumRequest(Context context) {
        return getSharedPref(context).getBoolean(MPConstants.SETTINGS_ALBUM_REQUEST, false);
    }


    public static Set<String> getExcludedFolders(Context context) {
        return getSharedPref(context).getStringSet(MPConstants.SETTINGS_EXCLUDED_FOLDER, new HashSet<>());
    }
}
