package com.fauzanpramulia.ta_ujian_berbasis_mobile.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.fauzanpramulia.ta_ujian_berbasis_mobile.model.OptionModel;

import java.util.List;

@Entity(tableName = "soal_ujian")
@TypeConverters(OptionTipeConverter.class)
public class SoalUjian {

    @PrimaryKey
    @ColumnInfo(name = "no_urut")
    public int no_urut;

    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "soal_id")
    public int soal_id;

    @ColumnInfo(name = "soal")
    public String soal;

    @ColumnInfo(name= "tipe_soal")
    public int tipeSoal;

    @ColumnInfo(name = "options")
    public List<OptionModel> options;

}