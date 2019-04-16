package com.fauzanpramulia.dosen_ta_ujian.shared;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {

    public SharedPreferences loginToken;
    public SharedPreferences ujianMahasiswaID;
    public SharedPreferences ujianID;
    public SharedPreferences status_rol;

    public Session(Context cntx) {
        loginToken = PreferenceManager.getDefaultSharedPreferences(cntx);
        ujianMahasiswaID = PreferenceManager.getDefaultSharedPreferences(cntx);
        ujianID = PreferenceManager.getDefaultSharedPreferences(cntx);
        status_rol = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setToken(String token) {
        loginToken.edit().putString("token", token).commit();
    }

    public String getToken() {
        String usename = loginToken.getString("token","");
        return usename;
    }

    public void setStatus_rol(int status) {
        status_rol.edit().putInt("status", status).commit();
    }

    public int getStatusRol() {
        int usename = status_rol.getInt("status",0);
        return usename;
    }

    public void setUjianMahasiswaID(int id){
        ujianMahasiswaID.edit().putInt("id",id).commit();
    }
    public int getUjianMahasiswaID(){
        int id = ujianMahasiswaID.getInt("id",0);
        return id;
    }

    public void setUjianID(int id){
        ujianID.edit().putInt("ujian_id",id).commit();
    }
    public int getUjianID(){
        int id = ujianID.getInt("ujian_id",0);
        return id;
    }

}