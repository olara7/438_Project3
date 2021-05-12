/**@Contributors: Timothy Johnson, Jim O. Cabrera
 * Description:
 * Date_of_Submission: May 14, 2021
 * Comments:
 * */


package com.example.cst438_project3;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class light_mode_yoda extends AppCompatActivity {

    int light_or_dark = 0;

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
    Button screenBtn;

    TextView quoteTextView;
    TextView percentTextView;

    View relativeLayout;
    ImageView yoda;

    ArrayList<String> quoteList = new ArrayList<>();
    ArrayList<String> nameOneList = new ArrayList<>();
    ArrayList<String> nameTwoList = new ArrayList<>();
    ArrayList<Integer> percentList = new ArrayList<>();

    ArrayList<String> quoteListSith = new ArrayList<>();
    ArrayList<String> nameOneListSith = new ArrayList<>();
    ArrayList<String> nameTwoListSith = new ArrayList<>();
    ArrayList<Integer> percentListSith = new ArrayList<>();

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

        relativeLayout = findViewById(R.id.relativeLayout);
        yoda = findViewById(R.id.imageView5);

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

                        if(light_or_dark == 0){
                            Integer percent = percentList.get(i);
                            String p = Integer.toString(percent);
                            get_json(p);
                            //getAPIInfo(percent);
                            quoteTextView.setText(quoteList.get(quoteList.size() - 1));
                            percentTextView.setText(percent + "%");
                            saveQuoteBtn.setVisibility(View.VISIBLE);
                        }else{
                            Integer percent = percentListSith.get(i);
                            String p = Integer.toString(percent);
                            get_json(p);
                            //getAPIInfo(percent);
                            quoteTextView.setText(quoteListSith.get(quoteListSith.size() - 1));
                            percentTextView.setText(percent + "%");
                            saveQuoteBtn.setVisibility(View.VISIBLE);
                        }

                        check = false;
                        break;
                    }
                }

                if(check){
                    Integer percent = get_percentage();
                    //getAPIInfo(percent);

                    String p = Integer.toString(percent);
                    get_json(p);

                    nameOneList.add(nameOneEditText.getText().toString());
                    nameTwoList.add(nameTwoEditText.getText().toString());
                    percentList.add(percent);

                    nameOneListSith.add(nameOneEditText.getText().toString());
                    nameTwoListSith.add(nameTwoEditText.getText().toString());
                    percentListSith.add(percent);

                    if(light_or_dark == 0){
                        quoteTextView.setText(quoteList.get(quoteList.size() - 1));
                        percentTextView.setText(percentList.get(percentList.size() - 1) + "%");
                        saveQuoteBtn.setVisibility(View.VISIBLE);
                    }else{
                        quoteTextView.setText(quoteListSith.get(quoteListSith.size() - 1));
                        percentTextView.setText(percentListSith.get(percentListSith.size() - 1) + "%");
                        saveQuoteBtn.setVisibility(View.VISIBLE);
                    }

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

        darkModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(light_or_dark == 0){
                    relativeLayout.setBackgroundColor(Color.parseColor("#656565")); //sets background to gray
                    yoda.setImageResource(R.drawable.dark_yoda);

                    light_or_dark = 1;

                    if(quoteListSith.size() != 0){
                        quoteTextView.setText(quoteListSith.get(quoteListSith.size() - 1));
                    }
                }else{
                    relativeLayout.setBackgroundColor(Color.parseColor("#ffffff")); //sets background to white
                    yoda.setImageResource(R.drawable.light_yoda);

                    light_or_dark = 0;

                    if(quoteList.size() != 0){
                        quoteTextView.setText(quoteList.get(quoteList.size() - 1));
                    }
                }

            }
        });

        /**@Contributor: Jim Cabrera
         *  1. The verify() function and uses activity_light_mode_yoda as a parameter.
         *
         *  2. screenBtn is linked to screenshotButton on the activity_light_mode_yoda.xml
         *  then, once the image is clicked, getScreenshot is called and takes the screenshot
         *  by using the parameters by getting the background drawable  then finds the
         *  topmost view, and a string for the file name.
         *
         *  3. Toast message informs user image has been saved.
         *
         * * */
        verify(this);
        screenBtn = findViewById(R.id.screenshotButton);

        screenBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                getScreenShot(getWindow().getDecorView().getRootView(), "result");
                Toast.makeText(getApplicationContext(),"Screenshot saved to Google Photos",Toast.LENGTH_LONG).show();
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
                        quoteListSith.add(obj.getString("Sith"));
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

    public void getAPIInfo(Integer percentage) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Yoda");
        query.whereEqualTo("Percentage", percentage);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject result, ParseException e) {
                if (e == null) {
                    String quoteJedi = result.getString("Jedi");
                    String quoteSith = result.getString("Sith");

                    quoteList.add(quoteJedi);
                    quoteListSith.add(quoteSith);
                } else {
                    // Something is wrong
                }
            }
        });
    }

    /**
     *
     * */
    protected static File getScreenShot(View view, String fileName){
        Date dateTaken = new Date();
        CharSequence formatOfDate = DateFormat.format("yyyy-MM-dd_hh:mm:ss", dateTaken);

        try{
            String dirPath = Environment.getExternalStorageDirectory().toString();
            File fileDir =  new File(dirPath);
            if(!fileDir.exists()){
                boolean mkdir = fileDir.mkdir();

            }

            String pathToGallery = dirPath + "/" + fileName + "-" + formatOfDate + ".jpeg";

            view.setDrawingCacheEnabled(true);
            Bitmap screenshot = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);

            File yodaQuote = new File(pathToGallery);

            FileOutputStream toStorage = new FileOutputStream(yodaQuote);
            int imQuality = 100;

            screenshot.compress(Bitmap.CompressFormat.JPEG, imQuality, toStorage);
            toStorage.flush();
            toStorage.close();
            return yodaQuote;



        }catch(FileNotFoundException e){
            e.printStackTrace();

        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }


    /**
     * */
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSION_STORAGE = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    public static void verify(Activity activity){
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    PERMISSION_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }




}