package com.fauzanpramulia.ta_ujian_berbasis_mobile.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface SoalUjianDao {
    @Query("SELECT * FROM soal_ujian")
    List<SoalUjian> getSoal();

    @Query("SELECT COUNT(*) FROM soal_ujian WHERE id = :id AND soal_id = :sID AND  soal = :pID")
    int checkSoal(int id, int sID, String pID);

//    @Query("DELETE FROM soal_ujian WHERE soal_ujian_id = :id AND ujian_mahasiswa_id = :uID AND jawaban = :pID")
//    void deleteByItem(int id, int uID, String pID);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSoal(SoalUjian soalUjian);

    @Query("DELETE FROM soal_ujian")
    void clear();

    @Delete
    void delete(SoalUjian soalUjian);
}
