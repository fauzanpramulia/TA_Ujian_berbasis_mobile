package com.fauzanpramulia.dosen_ta_ujian.model;

import android.os.Parcel;
import android.os.Parcelable;

public class UjianVeriDanValiModel implements Parcelable {
    public int id;
    public String nama_ujian;
    public int tipe_ujian;
    public String mata_kuliah_id;
    public String tanggal_mulai;
    public String tanggal_selesai;
    public String waktu_ujian;
    public String waktu_selesai;
    public String ruang_ujian;
    public String sifat_ujian;
    public String kode;
    public int tipe_jawaban_ganda;
    public String nama_mk;
    public String semester;
    public String sks;
    public String created_by;
    public int status_verifikasi;
    public String verified_by;
    public String verified_at;
    public String alasan_tolak_verifikasi;
    public int status_validasi;
    public String validated_by;
    public String validated_at;
    public String alasan_tolak_validasi;

    public UjianVeriDanValiModel(int id, String nama_ujian, int tipe_ujian, String mata_kuliah_id, String tanggal_mulai, String tanggal_selesai, String waktu_ujian, String waktu_selesai, String ruang_ujian, String sifat_ujian, String kode, int tipe_jawaban_ganda, String nama_mk, String semester, String sks, String created_by, int status_verifikasi, String verified_by, String verified_at, String alasan_tolak_verifikasi, int status_validasi, String validated_by, String validated_at, String alasan_tolak_validasi) {
        this.id = id;
        this.nama_ujian = nama_ujian;
        this.tipe_ujian = tipe_ujian;
        this.mata_kuliah_id = mata_kuliah_id;
        this.tanggal_mulai = tanggal_mulai;
        this.tanggal_selesai = tanggal_selesai;
        this.waktu_ujian = waktu_ujian;
        this.waktu_selesai = waktu_selesai;
        this.ruang_ujian = ruang_ujian;
        this.sifat_ujian = sifat_ujian;
        this.kode = kode;
        this.tipe_jawaban_ganda = tipe_jawaban_ganda;
        this.nama_mk = nama_mk;
        this.semester = semester;
        this.sks = sks;
        this.created_by = created_by;
        this.status_verifikasi = status_verifikasi;
        this.verified_by = verified_by;
        this.verified_at = verified_at;
        this.alasan_tolak_verifikasi = alasan_tolak_verifikasi;
        this.status_validasi = status_validasi;
        this.validated_by = validated_by;
        this.validated_at = validated_at;
        this.alasan_tolak_validasi = alasan_tolak_validasi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama_ujian() {
        return nama_ujian;
    }

    public void setNama_ujian(String nama_ujian) {
        this.nama_ujian = nama_ujian;
    }

    public int getTipe_ujian() {
        return tipe_ujian;
    }

    public void setTipe_ujian(int tipe_ujian) {
        this.tipe_ujian = tipe_ujian;
    }

    public String getMata_kuliah_id() {
        return mata_kuliah_id;
    }

    public void setMata_kuliah_id(String mata_kuliah_id) {
        this.mata_kuliah_id = mata_kuliah_id;
    }

    public String getTanggal_mulai() {
        return tanggal_mulai;
    }

    public void setTanggal_mulai(String tanggal_mulai) {
        this.tanggal_mulai = tanggal_mulai;
    }

    public String getTanggal_selesai() {
        return tanggal_selesai;
    }

    public void setTanggal_selesai(String tanggal_selesai) {
        this.tanggal_selesai = tanggal_selesai;
    }

    public String getWaktu_ujian() {
        return waktu_ujian;
    }

    public void setWaktu_ujian(String waktu_ujian) {
        this.waktu_ujian = waktu_ujian;
    }

    public String getWaktu_selesai() {
        return waktu_selesai;
    }

    public void setWaktu_selesai(String waktu_selesai) {
        this.waktu_selesai = waktu_selesai;
    }

    public String getRuang_ujian() {
        return ruang_ujian;
    }

    public void setRuang_ujian(String ruang_ujian) {
        this.ruang_ujian = ruang_ujian;
    }

    public String getSifat_ujian() {
        return sifat_ujian;
    }

    public void setSifat_ujian(String sifat_ujian) {
        this.sifat_ujian = sifat_ujian;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public int getTipe_jawaban_ganda() {
        return tipe_jawaban_ganda;
    }

    public void setTipe_jawaban_ganda(int tipe_jawaban_ganda) {
        this.tipe_jawaban_ganda = tipe_jawaban_ganda;
    }

    public String getNama_mk() {
        return nama_mk;
    }

    public void setNama_mk(String nama_mk) {
        this.nama_mk = nama_mk;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getSks() {
        return sks;
    }

    public void setSks(String sks) {
        this.sks = sks;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public int getStatus_verifikasi() {
        return status_verifikasi;
    }

    public void setStatus_verifikasi(int status_verifikasi) {
        this.status_verifikasi = status_verifikasi;
    }

    public String getVerified_by() {
        return verified_by;
    }

    public void setVerified_by(String verified_by) {
        this.verified_by = verified_by;
    }

    public String getVerified_at() {
        return verified_at;
    }

    public void setVerified_at(String verified_at) {
        this.verified_at = verified_at;
    }

    public String getAlasan_tolak_verifikasi() {
        return alasan_tolak_verifikasi;
    }

    public void setAlasan_tolak_verifikasi(String alasan_tolak_verifikasi) {
        this.alasan_tolak_verifikasi = alasan_tolak_verifikasi;
    }

    public int getStatus_validasi() {
        return status_validasi;
    }

    public void setStatus_validasi(int status_validasi) {
        this.status_validasi = status_validasi;
    }

    public String getValidated_by() {
        return validated_by;
    }

    public void setValidated_by(String validated_by) {
        this.validated_by = validated_by;
    }

    public String getValidated_at() {
        return validated_at;
    }

    public void setValidated_at(String validated_at) {
        this.validated_at = validated_at;
    }

    public String getAlasan_tolak_validasi() {
        return alasan_tolak_validasi;
    }

    public void setAlasan_tolak_validasi(String alasan_tolak_validasi) {
        this.alasan_tolak_validasi = alasan_tolak_validasi;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.nama_ujian);
        dest.writeInt(this.tipe_ujian);
        dest.writeString(this.mata_kuliah_id);
        dest.writeString(this.tanggal_mulai);
        dest.writeString(this.tanggal_selesai);
        dest.writeString(this.waktu_ujian);
        dest.writeString(this.waktu_selesai);
        dest.writeString(this.ruang_ujian);
        dest.writeString(this.sifat_ujian);
        dest.writeString(this.kode);
        dest.writeInt(this.tipe_jawaban_ganda);
        dest.writeString(this.nama_mk);
        dest.writeString(this.semester);
        dest.writeString(this.sks);
        dest.writeString(this.created_by);
        dest.writeInt(this.status_verifikasi);
        dest.writeString(this.verified_by);
        dest.writeString(this.verified_at);
        dest.writeString(this.alasan_tolak_verifikasi);
        dest.writeInt(this.status_validasi);
        dest.writeString(this.validated_by);
        dest.writeString(this.validated_at);
        dest.writeString(this.alasan_tolak_validasi);
    }

    protected UjianVeriDanValiModel(Parcel in) {
        this.id = in.readInt();
        this.nama_ujian = in.readString();
        this.tipe_ujian = in.readInt();
        this.mata_kuliah_id = in.readString();
        this.tanggal_mulai = in.readString();
        this.tanggal_selesai = in.readString();
        this.waktu_ujian = in.readString();
        this.waktu_selesai = in.readString();
        this.ruang_ujian = in.readString();
        this.sifat_ujian = in.readString();
        this.kode = in.readString();
        this.tipe_jawaban_ganda = in.readInt();
        this.nama_mk = in.readString();
        this.semester = in.readString();
        this.sks = in.readString();
        this.created_by = in.readString();
        this.status_verifikasi = in.readInt();
        this.verified_by = in.readString();
        this.verified_at = in.readString();
        this.alasan_tolak_verifikasi = in.readString();
        this.status_validasi = in.readInt();
        this.validated_by = in.readString();
        this.validated_at = in.readString();
        this.alasan_tolak_validasi = in.readString();
    }

    public static final Parcelable.Creator<UjianVeriDanValiModel> CREATOR = new Parcelable.Creator<UjianVeriDanValiModel>() {
        @Override
        public UjianVeriDanValiModel createFromParcel(Parcel source) {
            return new UjianVeriDanValiModel(source);
        }

        @Override
        public UjianVeriDanValiModel[] newArray(int size) {
            return new UjianVeriDanValiModel[size];
        }
    };
}
