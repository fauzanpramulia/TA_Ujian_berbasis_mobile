package com.fauzanpramulia.ta_ujian_berbasis_mobile.shared;

import com.fauzanpramulia.ta_ujian_berbasis_mobile.UjianClient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {

    //buat retrofitClientInstance nya
    //untuk deklarasi base url
    public static String BASE_URL = "http://10.44.7.79:8000/api/";
//    public static String BASE_URL = "http://192.168.42.224:8000/api/";
//    public static String BASE_URL = "http://10.234.63.32:8000/api/";

    public static Retrofit getRetrofitInstance(){


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
