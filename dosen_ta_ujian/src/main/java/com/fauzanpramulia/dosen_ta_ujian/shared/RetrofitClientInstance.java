package com.fauzanpramulia.dosen_ta_ujian.shared;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {

    //buat retrofitClientInstance nya
    //untuk deklarasi base url
//    public static String BASE_URL = "http://192.168.42.97:8000/api/";
    public static String BASE_URL = "http://10.44.7.92:8000/api/";

    public static Retrofit getRetrofitInstance(){


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}