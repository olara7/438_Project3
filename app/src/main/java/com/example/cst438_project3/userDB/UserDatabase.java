package com.example.cst438_project3.userDB;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.cst438_project3.User;
import com.example.cst438_project3.userDB.DateTypeConverters.Converter;

@Database(entities = {User.class}, version = 1)
@TypeConverters(Converter.class)
public abstract class UserDatabase extends RoomDatabase {
    public static final String DB_NAME = "USER_DATABASE";
    public static final String USER_TABLE = "USER_TABLE";

    public abstract UserDAO getUserDAO();
}