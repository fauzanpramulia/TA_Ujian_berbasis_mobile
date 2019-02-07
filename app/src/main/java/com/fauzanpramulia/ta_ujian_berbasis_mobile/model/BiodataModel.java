package com.fauzanpramulia.ta_ujian_berbasis_mobile.model;

import android.os.Parcel;
import android.os.Parcelable;

public class BiodataModel implements Parcelable {
    public int id;
    public String username;
    public String email;
    public String nama;
    public String nim;
    public String alamat;
    public String agama;

    public BiodataModel(int id, String username, String email, String nama, String nim, String alamat, String agama) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.nama = nama;
        this.nim = nim;
        this.alamat = alamat;
        this.agama = agama;
    }

    public BiodataModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getAgama() {
        return agama;
    }

    public void setAgama(String agama) {
        this.agama = agama;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.username);
        dest.writeString(this.email);
        dest.writeString(this.nama);
        dest.writeString(this.nim);
        dest.writeString(this.alamat);
        dest.writeString(this.agama);
    }

    protected BiodataModel(Parcel in) {
        this.id = in.readInt();
        this.username = in.readString();
        this.email = in.readString();
        this.nama = in.readString();
        this.nim = in.readString();
        this.alamat = in.readString();
        this.agama = in.readString();
    }

    public static final Parcelable.Creator<BiodataModel> CREATOR = new Parcelable.Creator<BiodataModel>() {
        @Override
        public BiodataModel createFromParcel(Parcel source) {
            return new BiodataModel(source);
        }

        @Override
        public BiodataModel[] newArray(int size) {
            return new BiodataModel[size];
        }
    };
}
