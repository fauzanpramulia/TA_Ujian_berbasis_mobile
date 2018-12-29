package com.fauzanpramulia.ta_ujian_berbasis_mobile;

import com.fauzanpramulia.ta_ujian_berbasis_mobile.model.LoginModel;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.model.SoalModel;
import com.fauzanpramulia.ta_ujian_berbasis_mobile.model.UjianModel;

import org.json.JSONObject;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface UjianClient {
//    @GET("absensi")
//    Call<List<UjianModel>> getUjian();
//
    //buat kelas interfacenya
    @FormUrlEncoded
    @POST("login/users")
    Call<LoginModel> login(@Field("username") String username,
                           @Field("password") String password);

    @FormUrlEncoded
    @POST("login/logout")
    Call<ResponseBody> logout(@Field("token") String token);

    @FormUrlEncoded
    @POST("ujian/index")
    Call<List<UjianModel>> getUjian(@Field("token") String token);

    @FormUrlEncoded
    @POST("ujian/quest")
    Call<ResponseBody> getSoal(@Field("token") String token,
                                  @Field("kode") String kode,
                                  @Field("ujian_id") int ujian_id);
    @FormUrlEncoded
    @POST("ujian/soal")
    Call<List<SoalModel>> daftarSoal(@Field("token") String token,
                                  @Field("kode") String kode,
                                  @Field("ujian_id") int ujian_id);
    //    @FormUrlEncoded
//    @Multipart
//    @POST("absensi/insert")
//    Call<ResponseBody> insertAbsen(
//            @Part("bp") RequestBody bp,
//            @Part("nama") RequestBody nama,
//            @Part("kelas") RequestBody kelas,
//            @Part("mata_kuliah") RequestBody mata_kuliah,
//            @Part MultipartBody.Part filePart,
//            @Part("name") RequestBody foto);
}
