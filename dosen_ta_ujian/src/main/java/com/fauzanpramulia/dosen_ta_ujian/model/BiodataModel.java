package com.fauzanpramulia.dosen_ta_ujian.model;

import android.os.Parcel;
import android.os.Parcelable;

public class BiodataModel implements Parcelable {
    public int id;
    public String username;
    public String email;
    public String nama;
    public String nip;

    public BiodataModel(int id, String username, String email, String nama, String nip) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.nama = nama;
        this.nip = nip;
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

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
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
        dest.writeString(this.nip);
    }

    protected BiodataModel(Parcel in) {
        this.id = in.readInt();
        this.username = in.readString();
        this.email = in.readString();
        this.nama = in.readString();
        this.nip = in.readString();
    }

    public static final Creator<BiodataModel> CREATOR = new Creator<BiodataModel>() {
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
