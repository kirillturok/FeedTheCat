package com.example.mobilki1;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class ActivityResult extends AppCompatActivity {
    private TableLayout tableLayout;
    final String FILENAME = "results";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        tableLayout = findViewById(R.id.tableLayout);
        readFile();
        Toolbar t = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(t);
        Objects.requireNonNull(getSupportActionBar()).setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Result information");
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent1  =new Intent(this,MainActivity.class);
                startActivity(intent1);
                return true;
            default:return super.onOptionsItemSelected(item);
        }
    }

    void readFile() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    openFileInput(FILENAME)));
            String str = "";
            int i = 1;
            while ((str = br.readLine()) != null) {
                TableRow tableRow = new TableRow(this);
                tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                String[] strings = str.split("\\|");
                for (int j = 0; j < strings.length; j++) {
                    TextView textView = new TextView(this);
                    textView.setGravity(Gravity.CENTER);
                    textView.setText(strings[j]);
                    tableRow.addView(textView, j);
                }
                tableLayout.addView(tableRow, i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}