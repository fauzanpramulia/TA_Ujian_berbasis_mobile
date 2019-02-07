package com.fauzanpramulia.ta_ujian_berbasis_mobile.db;

import android.arch.persistence.room.TypeConverter;

import com.fauzanpramulia.ta_ujian_berbasis_mobile.model.OptionModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class OptionTipeConverter {
    
    @TypeConverter
    public static List<OptionModel> stringToOptions(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<OptionModel>>() {
        }.getType();
        List<OptionModel> options = gson.fromJson(json, type);
        return options;
    }

    @TypeConverter
    public static String optionsToString(List<OptionModel> list) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<OptionModel>>() {
        }.getType();
        String json = gson.toJson(list, type);
        return json;
    }

}
