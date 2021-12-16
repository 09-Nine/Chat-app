package com.example.chatapp;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefs {
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    public SharedPrefs(Context mContext) {
        sharedPreferences = mContext.getSharedPreferences(Constants.MODE_SHARED_PREFERENCE, mContext.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setBooleanValue(String key, Boolean value) {
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getBooleanValue(String key) {
        return sharedPreferences.getBoolean(key, false);
    }
}
