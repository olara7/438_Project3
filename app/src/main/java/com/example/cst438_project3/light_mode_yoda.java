package com.example.cst438_project3;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import com.google.android.material.textview.MaterialTextView;
import com.parse.ParseUser;

public class light_mode_yoda extends AppCompatActivity {

    EditText nameOneEditText;
    EditText nameTwoEditText;

    //checkboxes for 1st person
    CheckBox maleOneCheckBox;
    CheckBox femaleOneCheckBox;
    CheckBox otherOneCheckBox;

    //checkboxes for 2nd person
    CheckBox maleTwoCheckBox;
    CheckBox femaleTwoCheckBox;
    CheckBox otherTwoCheckBox;

    Button darkModeBtn;
    Button submitBtn;
    Button saveQuoteBtn;
    Button savedQuotesBtn;
    Button logoutBtn;

    TextView quoteTextView;
    TextView percentTextView;

    ArrayList<String> quoteList = new ArrayList<>();
    ArrayList<String> nameOneList = new ArrayList<>();
    ArrayList<String> nameTwoList = new ArrayList<>();
    ArrayList<String> percentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_mode_yoda);

        nameOneEditText = findViewById(R.id.nameOneEditText);
        maleOneCheckBox = findViewById(R.id.maleOneCheckBox);
        femaleOneCheckBox = findViewById(R.id.femaleOneCheckBox);
        otherOneCheckBox = findViewById(R.id.otherOneCheckBox);

        nameTwoEditText = findViewById(R.id.nameTwoEditText);
        maleTwoCheckBox = findViewById(R.id.maleTwoCheckBox);
        femaleTwoCheckBox = findViewById(R.id.femaleTwoCheckBox);
        otherTwoCheckBox = findViewById(R.id.otherTwoCheckBox);

        darkModeBtn = findViewById(R.id.darkModeBtn);
        submitBtn = findViewById(R.id.submitBtn);
        saveQuoteBtn = findViewById(R.id.saveQuoteBtn);
        saveQuoteBtn.setVisibility(View.INVISIBLE);
        savedQuotesBtn = findViewById(R.id.savedQuotesBtn);
        logoutBtn = findViewById(R.id.logoutBtn);

        quoteTextView = (TextView)findViewById(R.id.quoteTextView);
        percentTextView = findViewById(R.id.percentageTextView);
        percentTextView.setVisibility(View.INVISIBLE);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                String name1 = nameOneEditText.getText().toString();
                String name2 = nameTwoEditText.getText().toString();

                //check to see if there are multiple checked boxes
                if( (maleOneCheckBox.isChecked() && femaleOneCheckBox.isChecked()) || (maleOneCheckBox.isChecked() && otherOneCheckBox.isChecked()) || femaleOneCheckBox.isChecked() && otherOneCheckBox.isChecked()){
                    quoteTextView.setText("You have too many genders selected");
                    saveQuoteBtn.setVisibility(View.INVISIBLE);
                    percentTextView.setVisibility(View.INVISIBLE);
                    return;
                }

                //check to see if there are multiple checked boxes
                if( (maleTwoCheckBox.isChecked() && femaleTwoCheckBox.isChecked()) || (maleTwoCheckBox.isChecked() && otherTwoCheckBox.isChecked()) || femaleTwoCheckBox.isChecked() && otherTwoCheckBox.isChecked()){
                    quoteTextView.setText("You have too many genders selected");
                    saveQuoteBtn.setVisibility(View.INVISIBLE);
                    percentTextView.setVisibility(View.INVISIBLE);
                    return;
                }

                //check to see if there are no checked boxes
                if(!maleOneCheckBox.isChecked() && !femaleOneCheckBox.isChecked() && !otherOneCheckBox.isChecked()){
                    quoteTextView.setText("You have to select a gender");
                    saveQuoteBtn.setVisibility(View.INVISIBLE);
                    percentTextView.setVisibility(View.INVISIBLE);
                    return;
                }

                //check to see if there are no checked boxes
                if(!maleTwoCheckBox.isChecked() && !femaleTwoCheckBox.isChecked() && !otherTwoCheckBox.isChecked()){
                    quoteTextView.setText("You have to select a gender");
                    saveQuoteBtn.setVisibility(View.INVISIBLE);
                    percentTextView.setVisibility(View.INVISIBLE);
                    return;
                }

                boolean check = true;
                //check to see if user has already inputted the same names
                for(int i = 0; i < nameOneList.size(); i++){
                    if(nameOneList.get(i).equals(nameOneEditText.getText().toString()) && nameTwoList.get(i).equals(nameTwoEditText.getText().toString())){
                        String percent = percentList.get(i);
                        get_json(percent);
                        quoteTextView.setText(quoteList.get(quoteList.size() - 1));
                        percentTextView.setText(percent + "%");
                        check = false;
                        break;
                    }
                }

                if(check){
                    int percent = get_percentage();
                    String p = String.valueOf(percent);
                    get_json(p);

                    nameOneList.add(nameOneEditText.getText().toString());
                    nameTwoList.add(nameTwoEditText.getText().toString());
                    percentList.add(p);

                    quoteTextView.setText(quoteList.get(quoteList.size() - 1));
                    percentTextView.setText(percentList.get(percentList.size() - 1) + "%");
                }

                percentTextView.setVisibility(View.VISIBLE);
            }
        });

        saveQuoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String quote = quoteTextView.getText().toString();

                //save the quote into the database to be accessed later LOOK UP DOCUMENTATION
            }
        });


        savedQuotesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(light_mode_yoda.this, SavedQuotesDisplay.class);
                startActivity(intent);
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                ParseUser currentUser = ParseUser.getCurrentUser(); // changed to null
                Intent intent = new Intent(light_mode_yoda.this, LoginActivity.class);
                Toast.makeText(getApplicationContext(),"Logout Successful",Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });
    }

    public void get_json(String percentage){

        String json;

        try {
            InputStream is = getAssets().open("Yoda.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);

            for(int i = 0; i < jsonArray.length(); i++){

                JSONObject obj = jsonArray.getJSONObject(i);

                if(obj.getString("Percentage").equals(percentage)){
                    quoteList.add(obj.getString("Jedi"));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int get_percentage(){
        int from = 0;
        int to = 100;
        int multi = 5;
        Random rand = new Random();
        return (multi*(Math.round(rand.nextInt((to+multi-from))+from)/multi));
    }
}