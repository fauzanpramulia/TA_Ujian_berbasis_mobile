package com.fauzanpramulia.dosen_ta_ujian.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MahasiswaModel implements Parcelable {
    public int id;
    public String nama;
    public String nim;
    public int status;
    public int nilai;

    public MahasiswaModel(int id, String nama, String nim, int status, int nilai) {
        this.id = id;
        this.nama = nama;
        this.nim = nim;
        this.status = status;
        this.nilai = nilai;
    }

    public MahasiswaModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getNilai() {
        return nilai;
    }

    public void setNilai(int nilai) {
        this.nilai = nilai;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.nama);
        dest.writeString(this.nim);
        dest.writeInt(this.status);
        dest.writeInt(this.nilai);
    }

    protected MahasiswaModel(Parcel in) {
        this.id = in.readInt();
        this.nama = in.readString();
        this.nim = in.readString();
        this.status = in.readInt();
        this.nilai = in.readInt();
    }

    public static final Creator<MahasiswaModel> CREATOR = new Creator<MahasiswaModel>() {
        @Override
        public MahasiswaModel createFromParcel(Parcel source) {
            return new MahasiswaModel(source);
        }

        @Override
        public MahasiswaModel[] newArray(int size) {
            return new MahasiswaModel[size];
        }
    };
}
