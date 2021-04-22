package com.example.cst438_project3;

import android.content.Context;

import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.cst438_project3.userDB.UserDAO;
import com.example.cst438_project3.userDB.UserDatabase;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        UserDAO myTestDB = Room.databaseBuilder(appContext, UserDatabase.class, UserDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getUserDAO();

        User users = new User("UserIsNew", "PasswordIsAgain");
        myTestDB.delete(users);
        myTestDB.insert(users);

        User users2 = myTestDB.getUserById(users.getAppUsername());

        assertEquals(users2, users);
        assertEquals("com.example.cst438_project3", appContext.getPackageName());
    }
}