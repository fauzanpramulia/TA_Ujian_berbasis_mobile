package com.fauzanpramulia.ta_ujian_berbasis_mobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JawabanModel implements Parcelable {
    @SerializedName("no_urut")
    @Expose
    public int no_urut;
    @SerializedName("soal_id")
    @Expose
    public int soal_id;
    @SerializedName("ujian_mahasiswa_id")
    @Expose
    public int ujian_mahasiswa_id;
    @SerializedName("jawaban")
    @Expose
    public String jawaban;
    @SerializedName("tipe_soal")
    @Expose
    public int tipe_soal;

    public JawabanModel(int no_urut, int soal_id, int ujian_mahasiswa_id, String jawaban, int tipe_soal) {
        this.no_urut = no_urut;
        this.soal_id = soal_id;
        this.ujian_mahasiswa_id = ujian_mahasiswa_id;
        this.jawaban = jawaban;
        this.tipe_soal = tipe_soal;
    }

    public JawabanModel() {
    }

    public int getNo_urut() {
        return no_urut;
    }

    public void setNo_urut(int no_urut) {
        this.no_urut = no_urut;
    }

    public int getSoal_id() {
        return soal_id;
    }

    public void setSoal_id(int soal_id) {
        this.soal_id = soal_id;
    }

    public int getUjian_mahasiswa_id() {
        return ujian_mahasiswa_id;
    }

    public void setUjian_mahasiswa_id(int ujian_mahasiswa_id) {
        this.ujian_mahasiswa_id = ujian_mahasiswa_id;
    }

    public String getJawaban() {
        return jawaban;
    }

    public void setJawaban(String jawaban) {
        this.jawaban = jawaban;
    }

    public int getTipe_soal() {
        return tipe_soal;
    }

    public void setTipe_soal(int tipe_soal) {
        this.tipe_soal = tipe_soal;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.no_urut);
        dest.writeInt(this.soal_id);
        dest.writeInt(this.ujian_mahasiswa_id);
        dest.writeString(this.jawaban);
        dest.writeInt(this.tipe_soal);
    }

    protected JawabanModel(Parcel in) {
        this.no_urut = in.readInt();
        this.soal_id = in.readInt();
        this.ujian_mahasiswa_id = in.readInt();
        this.jawaban = in.readString();
        this.tipe_soal = in.readInt();
    }

    public static final Creator<JawabanModel> CREATOR = new Creator<JawabanModel>() {
        @Override
        public JawabanModel createFromParcel(Parcel source) {
            return new JawabanModel(source);
        }

        @Override
        public JawabanModel[] newArray(int size) {
            return new JawabanModel[size];
        }
    };
}
