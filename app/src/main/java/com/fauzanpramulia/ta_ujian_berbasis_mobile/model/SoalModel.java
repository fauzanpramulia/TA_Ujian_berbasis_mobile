package com.fauzanpramulia.ta_ujian_berbasis_mobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class SoalModel implements Parcelable {


    private int id;

    private int soal_id;

    private String soal;

    private int tipe_soal;

    private int no_urut;

    private List<OptionModel> options = null;

    public SoalModel(int id, int soal_id, String soal, int tipe_soal, int no_urut, List<OptionModel> options) {
        this.id = id;
        this.soal_id = soal_id;
        this.soal = soal;
        this.tipe_soal = tipe_soal;
        this.no_urut = no_urut;
        this.options = options;
    }

    public SoalModel() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.soal_id);
        dest.writeString(this.soal);
        dest.writeInt(this.tipe_soal);
        dest.writeInt(this.no_urut);
        dest.writeTypedList(this.options);
    }

    protected SoalModel(Parcel in) {
        this.id = in.readInt();
        this.soal_id = in.readInt();
        this.soal = in.readString();
        this.tipe_soal = in.readInt();
        this.no_urut = in.readInt();
        this.options = in.createTypedArrayList(OptionModel.CREATOR);
    }

    public static final Creator<SoalModel> CREATOR = new Creator<SoalModel>() {
        @Override
        public SoalModel createFromParcel(Parcel source) {
            return new SoalModel(source);
        }

        @Override
        public SoalModel[] newArray(int size) {
            return new SoalModel[size];
        }
    };

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

    public String getSoal() {
        return soal;
    }

    public void setSoal(String soal) {
        this.soal = soal;
    }

    public int getTipe_soal() {
        return tipe_soal;
    }

    public void setTipe_soal(int tipe_soal) {
        this.tipe_soal = tipe_soal;
    }

    public int getNo_urut() {
        return no_urut;
    }

    public void setNo_urut(int no_urut) {
        this.no_urut = no_urut;
    }

    public List<OptionModel> getOptions() {
        return options;
    }

    public void setOptions(List<OptionModel> options) {
        this.options = options;
    }
}
