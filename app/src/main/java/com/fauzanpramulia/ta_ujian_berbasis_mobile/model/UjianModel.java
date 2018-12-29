package com.fauzanpramulia.ta_ujian_berbasis_mobile.model;

import android.os.Parcel;
import android.os.Parcelable;

public class UjianModel implements Parcelable {

    public int id;
    public String nama_mk;
    public String nama_kelas;
    public String nama_ujian;
    public String tanggal_mulai;
    public String tanggal_selesai;
    public String waktu_ujian;
    public String waktu_selesai;
    public String ruang_ujian;
    public String sifat_ujian;
    public String kode;
    public int nilai;
    public int status;
    public int ujian_id;
    public int krs_id;
    public int ujian_kelas_id;
    public int kelas_id;

    public UjianModel(int id, String nama_mk, String nama_kelas, String nama_ujian, String tanggal_mulai, String tanggal_selesai, String waktu_ujian, String waktu_selesai, String ruang_ujian, String sifat_ujian, String kode, int nilai, int status, int ujian_id, int krs_id, int ujian_kelas_id, int kelas_id) {
        this.id = id;
        this.nama_mk = nama_mk;
        this.nama_kelas = nama_kelas;
        this.nama_ujian = nama_ujian;
        this.tanggal_mulai = tanggal_mulai;
        this.tanggal_selesai = tanggal_selesai;
        this.waktu_ujian = waktu_ujian;
        this.waktu_selesai = waktu_selesai;
        this.ruang_ujian = ruang_ujian;
        this.sifat_ujian = sifat_ujian;
        this.kode = kode;
        this.nilai = nilai;
        this.status = status;
        this.ujian_id = ujian_id;
        this.krs_id = krs_id;
        this.ujian_kelas_id = ujian_kelas_id;
        this.kelas_id = kelas_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama_mk() {
        return nama_mk;
    }

    public void setNama_mk(String nama_mk) {
        this.nama_mk = nama_mk;
    }

    public String getNama_kelas() {
        return nama_kelas;
    }

    public void setNama_kelas(String nama_kelas) {
        this.nama_kelas = nama_kelas;
    }

    public String getNama_ujian() {
        return nama_ujian;
    }

    public void setNama_ujian(String nama_ujian) {
        this.nama_ujian = nama_ujian;
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

    public int getNilai() {
        return nilai;
    }

    public void setNilai(int nilai) {
        this.nilai = nilai;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUjian_id() {
        return ujian_id;
    }

    public void setUjian_id(int ujian_id) {
        this.ujian_id = ujian_id;
    }

    public int getKrs_id() {
        return krs_id;
    }

    public void setKrs_id(int krs_id) {
        this.krs_id = krs_id;
    }

    public int getUjian_kelas_id() {
        return ujian_kelas_id;
    }

    public void setUjian_kelas_id(int ujian_kelas_id) {
        this.ujian_kelas_id = ujian_kelas_id;
    }

    public int getKelas_id() {
        return kelas_id;
    }

    public void setKelas_id(int kelas_id) {
        this.kelas_id = kelas_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.nama_mk);
        dest.writeString(this.nama_kelas);
        dest.writeString(this.nama_ujian);
        dest.writeString(this.tanggal_mulai);
        dest.writeString(this.tanggal_selesai);
        dest.writeString(this.waktu_ujian);
        dest.writeString(this.waktu_selesai);
        dest.writeString(this.ruang_ujian);
        dest.writeString(this.sifat_ujian);
        dest.writeString(this.kode);
        dest.writeInt(this.nilai);
        dest.writeInt(this.status);
        dest.writeInt(this.ujian_id);
        dest.writeInt(this.krs_id);
        dest.writeInt(this.ujian_kelas_id);
        dest.writeInt(this.kelas_id);
    }

    protected UjianModel(Parcel in) {
        this.id = in.readInt();
        this.nama_mk = in.readString();
        this.nama_kelas = in.readString();
        this.nama_ujian = in.readString();
        this.tanggal_mulai = in.readString();
        this.tanggal_selesai = in.readString();
        this.waktu_ujian = in.readString();
        this.waktu_selesai = in.readString();
        this.ruang_ujian = in.readString();
        this.sifat_ujian = in.readString();
        this.kode = in.readString();
        this.nilai = in.readInt();
        this.status = in.readInt();
        this.ujian_id = in.readInt();
        this.krs_id = in.readInt();
        this.ujian_kelas_id = in.readInt();
        this.kelas_id = in.readInt();
    }

    public static final Parcelable.Creator<UjianModel> CREATOR = new Parcelable.Creator<UjianModel>() {
        @Override
        public UjianModel createFromParcel(Parcel source) {
            return new UjianModel(source);
        }

        @Override
        public UjianModel[] newArray(int size) {
            return new UjianModel[size];
        }
    };
}
