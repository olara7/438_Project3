package com.example.cst438_project3.userDB.DateTypeConverters;

import androidx.room.TypeConverter;

import java.util.Date;

public class Converter {
    @TypeConverter
    public long convertLongToDate(Date date){
        return date.getTime();
    }

    @TypeConverter
    public Date convertLongtoDate(long time){
        return new Date(time);
    }
}
