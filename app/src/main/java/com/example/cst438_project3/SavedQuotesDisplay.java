package com.example.cst438_project3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class SavedQuotesDisplay extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener {

    RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_quotes_display);

        ArrayList<String> quotes = new ArrayList<>();
        quotes.add("Yoda Quote 1");
        quotes.add("Yoda Quote 2");
        quotes.add("Yoda Quote 3");
        quotes.add("Yoda Quote 4");
        quotes.add("Yoda Quote 5");

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvSavedQuotes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(this, quotes);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }
}