package com.example.cst438_project3;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textview.MaterialTextView;

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

    TextView quoteTextView;

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

        quoteTextView = (TextView)findViewById(R.id.quoteTextView);


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
                    return;
                }

                //check to see if there are multiple checked boxes
                if( (maleTwoCheckBox.isChecked() && femaleTwoCheckBox.isChecked()) || (maleTwoCheckBox.isChecked() && otherTwoCheckBox.isChecked()) || femaleTwoCheckBox.isChecked() && otherTwoCheckBox.isChecked()){
                    quoteTextView.setText("You have too many genders selected");
                    saveQuoteBtn.setVisibility(View.INVISIBLE);
                    return;
                }

                //check to see if there are no checked boxes
                if(!maleOneCheckBox.isChecked() && !femaleOneCheckBox.isChecked() && !otherOneCheckBox.isChecked()){
                    quoteTextView.setText("You have to select a gender");
                    saveQuoteBtn.setVisibility(View.INVISIBLE);
                    return;
                }

                //check to see if there are no checked boxes
                if(!maleTwoCheckBox.isChecked() && !femaleTwoCheckBox.isChecked() && !otherTwoCheckBox.isChecked()){
                    quoteTextView.setText("You have to select a gender");
                    saveQuoteBtn.setVisibility(View.INVISIBLE);
                    return;
                }

                String url = "https://love-calculator.p.rapidapi.com/getPercentage?fname=" + name1 + "&sname=" + name2;

                if(maleOneCheckBox.isChecked()){
                    quoteTextView.setText("MaleCheck1: " + name1 + " ");
                    saveQuoteBtn.setVisibility(View.VISIBLE);
                }else if(femaleOneCheckBox.isChecked()){
                    quoteTextView.setText("FemaleCheck1: " + name1 + " ");
                    saveQuoteBtn.setVisibility(View.VISIBLE);
                }else if(otherOneCheckBox.isChecked()){
                    quoteTextView.setText("OtherCheck1: " + name1 + " ");
                    saveQuoteBtn.setVisibility(View.VISIBLE);
                }else{
                    quoteTextView.setText("You have to select a gender Patawan");
                    saveQuoteBtn.setVisibility(View.INVISIBLE);
                }

                if(maleTwoCheckBox.isChecked()){
                    quoteTextView.setText(quoteTextView.getText().toString() + "MaleCheck2: " + name2);
                    saveQuoteBtn.setVisibility(View.VISIBLE);
                }else if(femaleTwoCheckBox.isChecked()){
                    quoteTextView.setText(quoteTextView.getText().toString() + "FemaleCheck2: " + name2);
                    saveQuoteBtn.setVisibility(View.VISIBLE);
                }else if(otherTwoCheckBox.isChecked()){
                    quoteTextView.setText(quoteTextView.getText().toString() + "OtherCheck2: " + name2);
                    saveQuoteBtn.setVisibility(View.VISIBLE);
                }else{
                    quoteTextView.setText("You have to select a gender Patawan");
                    saveQuoteBtn.setVisibility(View.INVISIBLE);
                }
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
    }
}