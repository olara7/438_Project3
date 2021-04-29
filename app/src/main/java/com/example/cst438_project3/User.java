package com.example.cst438_project3;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.cst438_project3.userDB.UserDatabase;

import java.util.Date;
import java.util.Objects;

@Entity(tableName = UserDatabase.USER_TABLE)
public class User {

    @PrimaryKey(autoGenerate = true)
    private int appId;

    private String appUsername;
    private String appPassword;
    private Date getDate;

    public User(String appUsername, String appPassword) {
        this.appUsername = appUsername;
        this.appPassword = appPassword;

        getDate = new Date();
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public String getAppUsername() {
        return appUsername;
    }

    public void setAppUsername(String appUsername) {
        this.appUsername = appUsername;
    }

    public String getAppPassword() {
        return appPassword;
    }

    public void setAppPassword(String appPassword) {
        this.appPassword = appPassword;
    }

    public Date getGetDate() {
        return getDate;
    }

    public void setGetDate(Date getDate) {
        this.getDate = getDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return appUsername.equals(user.appUsername) &&
                appPassword.equals(user.appPassword);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(appUsername, appPassword);
    }

    @Override
    public String toString() {
        String output;
        output = appUsername;
        output +=  "\n";
        output += appPassword;
        output +=  "\n";
        output += getDate;
        output +=  "\n";

        return output;
    }
}