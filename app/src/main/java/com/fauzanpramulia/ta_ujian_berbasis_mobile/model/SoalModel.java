package com.fauzanpramulia.ta_ujian_berbasis_mobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class SoalModel implements Parcelable {


    private int id;

    private String soal;

    private int tipeSoal;

    private List<OptionModel> options = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSoal() {
        return soal;
    }

    public void setSoal(String soal) {
        this.soal = soal;
    }

    public Integer getTipeSoal() {
        return tipeSoal;
    }

    public void setTipeSoal(Integer tipeSoal) {
        this.tipeSoal = tipeSoal;
    }

    public List<OptionModel> getOptions() {
        return options;
    }

    public void setOptions(List<OptionModel> options) {
        this.options = options;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.soal);
        dest.writeInt(this.tipeSoal);
        dest.writeTypedList(this.options);
    }

    public SoalModel() {
    }

    protected SoalModel(Parcel in) {
        this.id = in.readInt();
        this.soal = in.readString();
        this.tipeSoal = in.readInt();
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
}
