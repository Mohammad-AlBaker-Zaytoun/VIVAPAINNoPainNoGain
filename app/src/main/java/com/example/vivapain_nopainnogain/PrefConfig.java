package com.example.vivapain_nopainnogain;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class PrefConfig {
    private static final String LIST_KEY = "list_key";

    public static <gymDetailsModule> void writeListInPref(Context context, ArrayList<gymDetailsModule> list) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(list);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(LIST_KEY, jsonString);
        editor.apply();
    }

    public static ArrayList<gymDetailsModule> readListFromPref(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = pref.getString(LIST_KEY, "");
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<gymDetailsModule>>() {
        }.getType();
        return gson.fromJson(jsonString, type);
    }
}
