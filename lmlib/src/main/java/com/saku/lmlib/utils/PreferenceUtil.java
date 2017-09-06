package com.saku.lmlib.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;
import java.util.Set;

public class PreferenceUtil {
    private static String PREF_NAME = "pref_name";

    public static void putString(Context context, String key, String value) {
        final SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        preferences.edit().putString(key, value).apply();
    }

    public static void putBoolean(Context context, String key, boolean value) {
        final SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        preferences.edit().putBoolean(key, value).apply();
    }

    public static void putInt(Context context, String key, int value) {
        final SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        preferences.edit().putInt(key, value).apply();
    }

    public static void putFloat(Context context, String key, float value) {
        final SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        preferences.edit().putFloat(key, value).apply();
    }

    public static void putLong(Context context, String key, long value) {
        final SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        preferences.edit().putLong(key, value).apply();
    }

    public static void putStringSet(Context context, String key, Set<String> value) {
        final SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        preferences.edit().putStringSet(key, value).apply();
    }

    public static Map<String, ?> getAll(Context context) {
        final SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getAll();
    }

    public static boolean getBoolean(Context context, String key, boolean defValue) {
        final SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
       return preferences.getBoolean(key, defValue);
    }

    public static float getFloat(Context context, String key, float defValue) {
        final SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getFloat(key, defValue);
    }

    public static int getInt(Context context, String key, int defValue) {
        final SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getInt(key, defValue);
    }

    public static long getLong(Context context, String key, long defValue) {
        final SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getLong(key, defValue);
    }

    public static String getString(Context context, String key, String defValue) {
        final SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getString(key, defValue);
    }
    public static Set<String> getStringSet(Context context, String key, Set<String> defValue) {
        final SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getStringSet(key, defValue);
    }
}
