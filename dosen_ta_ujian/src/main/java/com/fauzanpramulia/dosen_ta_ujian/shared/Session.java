package com.fauzanpramulia.dosen_ta_ujian.shared;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {

    public SharedPreferences loginToken;

    public Session(Context cntx) {
        loginToken = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setToken(String token) {
        loginToken.edit().putString("token", token).commit();
    }

    public String getToken() {
        String usename = loginToken.getString("token","");
        return usename;
    }
}