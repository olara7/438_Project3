package com.example.cst438_project3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.cst438_project3.userDB.UserDAO;
import com.example.cst438_project3.userDB.UserDatabase;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class SavedQuotesDisplay extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener {

    RecyclerViewAdapter adapter;
    UserDAO mUserDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_quotes_display);

        getDatabase();

        Intent intent = getIntent();
        String username = intent.getStringExtra("userName");

        ArrayList<String> quotes = new ArrayList<>();

        List<User> allAccounts = mUserDAO.getUsers();

        for(int i = 0; i < allAccounts.size(); i++){
            if(allAccounts.get(i).getAppUsername().equals(username)){
                quotes = allAccounts.get(i).getQuotes();
                System.out.println(quotes);
                break;
            }
        }
        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvSavedQuotes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(this, quotes);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        //Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }

    private void getDatabase() {
        mUserDAO= Room.databaseBuilder(this, UserDatabase.class,"USER_TABLE")
                .allowMainThreadQueries()
                .build()
                .getUserDAO();
    }
}