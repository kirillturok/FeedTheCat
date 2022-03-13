package com.example.mobilki1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class StartActivity extends AppCompatActivity {

    private GifImageView loadGif;

    private Timer timer = new Timer();
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        setTitle("Loading...");

        loadGif = findViewById(R.id.LoadImage);
        ((GifDrawable)loadGif.getDrawable()).setLoopCount(1);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        checkAnimationEnding();
                    }
                });
            }
        }, 0, 20);
    }

    private void checkAnimationEnding(){
        if ( ((GifDrawable)loadGif.getDrawable()).isAnimationCompleted() ){
            timer.cancel();
            timer = null;

            Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() { }
}