package com.fauzanpramulia.ta_ujian_berbasis_mobile.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface JawabanUjianDao {

    @Query("SELECT * FROM jawaban_ujian")
    List<JawabanUjian> getAllMovies();

    @Query("SELECT * FROM jawaban_ujian WHERE soal_ujian_id = :id")
    JawabanUjian getSoalUjianID(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertJawabanUjian(JawabanUjian nowPlaying);

    @Query("DELETE FROM jawaban_ujian")
    void clear();

    @Delete
    void delete(JawabanUjian jawabanUjian);
}
