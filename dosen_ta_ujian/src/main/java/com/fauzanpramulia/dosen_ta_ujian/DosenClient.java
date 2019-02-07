package com.fauzanpramulia.dosen_ta_ujian;

import com.fauzanpramulia.dosen_ta_ujian.model.BiodataModel;
import com.fauzanpramulia.dosen_ta_ujian.model.LoginModel;
import com.fauzanpramulia.dosen_ta_ujian.model.MahasiswaModel;
import com.fauzanpramulia.dosen_ta_ujian.model.UjianModel;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface DosenClient {
    //    @GET("absensi")
//    Call<List<UjianModel>> getUjian();
//
    //buat kelas interfacenya
    @FormUrlEncoded
    @POST("login/dosen")
    Call<LoginModel> login(@Field("username") String username,
                           @Field("password") String password);

    @FormUrlEncoded
    @POST("login/logout")
    Call<ResponseBody> logout(@Field("token") String token);

    @FormUrlEncoded
    @POST("dosen/index")
    Call<List<UjianModel>> getUjian(@Field("token") String token);

    @FormUrlEncoded
    @POST("dosen/daftar_mahasiswa")
    Call<List<MahasiswaModel>> getDaftarMhs(@Field("token") String token,
                                       @Field("ujian_kelas_id") int ujian_kelas_id);

    @FormUrlEncoded
    @POST("dosen/update_status_mhs")
    Call<ResponseBody> updateStatusMHS(@Field("token") String token,
                                       @Field("status_mhs") int status_mhs,
                                       @Field("ujian_mahasiswa_id") int ujian_mahasiswa_id);
    @FormUrlEncoded
    @POST("dosen/profil_dosen")
    Call<BiodataModel> getProfil(@Field("token") String token );
//    @FormUrlEncoded
//    @POST("ujian/soal")
//    Call<List<SoalModel>> daftarSoal(@Field("token") String token,
//                                     @Field("kode") String kode,
//                                     @Field("ujian_id") int ujian_id);
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
