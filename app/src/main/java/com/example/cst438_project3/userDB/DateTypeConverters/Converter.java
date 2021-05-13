package com.example.cst438_project3.userDB.DateTypeConverters;

import androidx.room.TypeConverter;

import com.example.cst438_project3.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Converter {
    @TypeConverter
    public long convertLongToDate(Date date){
        return date.getTime();
    }

    @TypeConverter
    public Date convertLongtoDate(long time){
        return new Date(time);
    }

    @TypeConverter
    public ArrayList<String> fromString(String value) {
        Type listType = new TypeToken<ArrayList<String>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public String fromArrayList(ArrayList<String> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
