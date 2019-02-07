package com.fauzanpramulia.ta_ujian_berbasis_mobile.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import javax.crypto.KeyGenerator;

@Entity(tableName = "jawaban_ujian")
public class JawabanUjian {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "soal_ujian_id")
    public int soal_ujian_id;

    @ColumnInfo(name = "ujian_mahasiswa_id")
    public int ujian_mahasiswa_id;

    @ColumnInfo(name = "no_urut")
    public int no_urut;

    @ColumnInfo(name = "jawaban")
    public String jawaban;

    @ColumnInfo(name = "tipe_soal")
    public int tipe_soal;

}
