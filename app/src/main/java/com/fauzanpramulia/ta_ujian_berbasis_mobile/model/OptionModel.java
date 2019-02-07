package com.fauzanpramulia.ta_ujian_berbasis_mobile.model;

import android.os.Parcel;
import android.os.Parcelable;

public class OptionModel implements Parcelable {
    public int id;
    public int soal_id;
    public String pilihan;
    public String jawaban_pg;
    public boolean isSelected;

    public OptionModel(int id, int soal_id, String pilihan, String jawaban_pg, boolean isSelected) {

        this.id = id;
        this.soal_id = soal_id;
        this.pilihan = pilihan;
        this.jawaban_pg = jawaban_pg;
        this.isSelected = isSelected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSoal_id() {
        return soal_id;
    }

    public void setSoal_id(int soal_id) {
        this.soal_id = soal_id;
    }

    public String getPilihan() {
        return pilihan;
    }

    public void setPilihan(String pilihan) {
        this.pilihan = pilihan;
    }

    public String getJawaban_pg() {
        return jawaban_pg;
    }

    public void setJawaban_pg(String jawaban_pg) {
        this.jawaban_pg = jawaban_pg;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.soal_id);
        dest.writeString(this.pilihan);
        dest.writeString(this.jawaban_pg);
        dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
    }

    protected OptionModel(Parcel in) {
        this.id = in.readInt();
        this.soal_id = in.readInt();
        this.pilihan = in.readString();
        this.jawaban_pg = in.readString();
        this.isSelected = in.readByte() != 0;
    }

    public static final Creator<OptionModel> CREATOR = new Creator<OptionModel>() {
        @Override
        public OptionModel createFromParcel(Parcel source) {
            return new OptionModel(source);
        }

        @Override
        public OptionModel[] newArray(int size) {
            return new OptionModel[size];
        }
    };
}
