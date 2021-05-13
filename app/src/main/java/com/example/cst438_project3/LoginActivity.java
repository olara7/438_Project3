package com.example.cst438_project3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cst438_project3.userDB.UserDAO;
import com.example.cst438_project3.userDB.UserDatabase;
import com.parse.LogInCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Database;
import androidx.room.Room;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {


    private EditText etUsername;
    private EditText etPassword;

    private String username;
    private String password;
    private Button btnLogin;
    private Button btnSignup;

    User addUser;
    UserDAO mUserDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ParseInstallation.getCurrentInstallation().saveInBackground();
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.loginBtn);
        btnSignup = findViewById(R.id.btnSignup);

        getDatabase();

        btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!etUsername.getText().toString().isEmpty() && !etPassword.getText().toString().isEmpty()){
                        ParseUser.logInInBackground(etUsername.getText().toString(), etPassword.getText().toString(), new LogInCallback() {
                            @Override
                            public void done(ParseUser user, ParseException e) {
                                if (user!=null){
                                    Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_LONG).show();
                                    startUserActivity(etUsername.getText().toString());
                                }else {
                                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                }
            });

        btnSignup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!etUsername.getText().toString().isEmpty() && !etPassword.getText().toString().isEmpty()){
                        ParseUser user = new ParseUser();
                        user.setUsername(etUsername.getText().toString());
                        user.setPassword(etPassword.getText().toString());

                        user.signUpInBackground(new SignUpCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e==null){
                                    Toast.makeText(getApplicationContext(),"Sign Up Successful",Toast.LENGTH_LONG).show();
                                    addUser = new User(etUsername.getText().toString(), etPassword.getText().toString());
                                    mUserDAO.insert(addUser);

                                    startUserActivity(etUsername.getText().toString());
                                }else {
                                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                }
            });
    }

    private void startUserActivity(String username) {
        Intent intent = new Intent(LoginActivity.this, light_mode_yoda.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    private void getDatabase() {
        mUserDAO= Room.databaseBuilder(this, UserDatabase.class,"USER_TABLE")
                .allowMainThreadQueries()
                .build()
                .getUserDAO();
    }
}