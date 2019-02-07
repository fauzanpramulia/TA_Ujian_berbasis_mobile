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
    List<JawabanUjian> getJawaban();

    @Query("SELECT COUNT(*) FROM jawaban_ujian WHERE soal_ujian_id = :id AND ujian_mahasiswa_id = :uID AND jawaban = :pID")
    int checkUjianSOal(int id, int uID, String pID);

    @Query("SELECT COUNT(*) FROM jawaban_ujian WHERE soal_ujian_id = :id AND ujian_mahasiswa_id = :uID")
    int checkJawabanJS(int id, int uID);

    @Query("SELECT COUNT(*) FROM jawaban_ujian")
    int checkJumlah();

    @Query("DELETE FROM jawaban_ujian WHERE  jawaban = :pID")
    void deleteByItem(String pID);

    @Query("UPDATE jawaban_ujian SET jawaban = :jawab WHERE soal_ujian_id = :id AND ujian_mahasiswa_id = :uID")
    void updateJawaban(int id, int uID, String jawab);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertJawabanUjian(JawabanUjian jawabanUjian);

    @Query("SELECT * FROM jawaban_ujian WHERE soal_ujian_id = :id AND ujian_mahasiswa_id = :uID")
    JawabanUjian getJawabanByID(int id, int uID);

    @Query("DELETE FROM jawaban_ujian")
    void clear();

    @Delete
    void delete(JawabanUjian jawabanUjian);
}
