package com.fauzanpramulia.ta_ujian_berbasis_mobile.shared;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {

    public SharedPreferences loginToken;
    public SharedPreferences ujianMahasiswaID;

    public Session(Context cntx) {
        loginToken = PreferenceManager.getDefaultSharedPreferences(cntx);
        ujianMahasiswaID = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setToken(String token) {
        loginToken.edit().putString("token", token).commit();
    }

    public String getToken() {
        String usename = loginToken.getString("token","");
        return usename;
    }
    public void setUjianMahasiswaID(int id){
        ujianMahasiswaID.edit().putInt("id",id).commit();
    }
    public int getUjianMahasiswaID(){
        int id = ujianMahasiswaID.getInt("id",0);
        return id;
    }
}