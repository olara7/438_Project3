package com.example.cst438_project3.userDB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cst438_project3.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert
    void insert(User... users);

    @Update
    void update(User... users);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM " + UserDatabase.USER_TABLE)
    List<User> getUsers();

    @Query("SELECT * FROM " + UserDatabase.USER_TABLE + " WHERE appId =:id")
    User getUserById(int id);




}