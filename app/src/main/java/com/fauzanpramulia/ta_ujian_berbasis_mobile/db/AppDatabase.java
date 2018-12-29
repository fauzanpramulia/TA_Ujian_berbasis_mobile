package com.fauzanpramulia.ta_ujian_berbasis_mobile.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {JawabanUjian.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract JawabanUjianDao soalUjianDao();

}
