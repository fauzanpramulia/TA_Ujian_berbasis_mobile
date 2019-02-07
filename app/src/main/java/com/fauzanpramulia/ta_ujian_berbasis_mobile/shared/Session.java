package com.fauzanpramulia.ta_ujian_berbasis_mobile.shared;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {

    public SharedPreferences loginToken;
    public SharedPreferences ujianMahasiswaID;
    public SharedPreferences soalUjianID;
    public SharedPreferences noUrut;

    public Session(Context cntx) {
        loginToken = PreferenceManager.getDefaultSharedPreferences(cntx);
        ujianMahasiswaID = PreferenceManager.getDefaultSharedPreferences(cntx);
        soalUjianID = PreferenceManager.getDefaultSharedPreferences(cntx);
        noUrut = PreferenceManager.getDefaultSharedPreferences(cntx);
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

    public void setSoalUjianID(int id){
        soalUjianID.edit().putInt("soal_ujian_id",id).commit();
    }
    public int getSoalUjianID(){
        int id = soalUjianID.getInt("soal_ujian_id",0);
        return id;
    }

    public void setNoUrut(int urut){
        noUrut.edit().putInt("urut",urut).commit();
    }
    public int getNoUrut(){
        int urut = noUrut.getInt("urut",0);
        return urut;
    }

    public void setTipeSoal(int id){
        soalUjianID.edit().putInt("tipe_soal",id).commit();
    }
    public int getTipeSoal(){
        int tipe_soal = soalUjianID.getInt("tipe_soal",0);
        return tipe_soal;
    }
}