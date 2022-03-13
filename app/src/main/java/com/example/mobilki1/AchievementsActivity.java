package com.example.mobilki1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class AchievementsActivity extends AppCompatActivity {
    final String FILENAME = "achievements";
    private TableLayout tableLayout;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);
        tableLayout = findViewById(R.id.achievementTable);
        readFile();
        Toolbar t = (Toolbar) findViewById(R.id.toolbar_achievement);
        setSupportActionBar(t);
        Objects.requireNonNull(getSupportActionBar()).setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Achievements");
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent1 = new Intent(this, MainActivity.class);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void readFile() {
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            name = acct.getDisplayName();
        }
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    openFileInput(FILENAME)));
            String str = "";
            int i = 1;
            while ((str = br.readLine()) != null) {
                String[] strings = str.split("\\|");
                if(!strings[0].equals(name)){
                    continue;
                }
                TableRow tableRow = new TableRow(this);
                tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                ImageView imageView = new ImageView(this);
                imageView.setImageResource(MainActivity.achievements.get(Integer.parseInt(strings[strings.length-1])/15-1).image);
                tableRow.addView(imageView, 0);
                int j=1;
                while(j<strings.length){
                    TextView textView = new TextView(this);
                    textView.setGravity(Gravity.CENTER);
                    textView.setText(strings[j-1]);
                    tableRow.addView(textView, j);
                    j++;
                }
                tableLayout.addView(tableRow, i);
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}