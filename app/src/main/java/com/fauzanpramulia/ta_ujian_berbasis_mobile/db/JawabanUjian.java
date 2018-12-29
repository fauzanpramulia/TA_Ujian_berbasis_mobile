package com.fauzanpramulia.ta_ujian_berbasis_mobile.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

@Entity(tableName = "jawaban_ujian")
public class JawabanUjian {

    @ColumnInfo(name = "soal_ujian_id")
    public int soal_ujian_id;

    @ColumnInfo(name = "ujian_mahasiswa_id")
    public int ujian_mahasiswa_id;

    @ColumnInfo(name = "jawaban")
    public String jawaban;

}
