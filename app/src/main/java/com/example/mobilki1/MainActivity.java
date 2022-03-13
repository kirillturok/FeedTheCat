package com.example.mobilki1;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity {
    Animation animation;
    private TextView satiety_textView;
    private int clicks = 0;
    private String personName;
    public static ArrayList<Achievement> achievements = new ArrayList<>();
    final String FILENAME = "achievements";
    private Animation animation_cat;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        satiety_textView = findViewById(R.id.satiety_textView);
        animation = AnimationUtils.loadAnimation(this, R.anim.center_cat);
        imageView=findViewById(R.id.imageCat);
        Toolbar t = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(t);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            setTitle(personName);
        }
        generateAchievements();
        animation_cat = AnimationUtils.loadAnimation(this, R.anim.center_cat);
    }

    public void feed_button_click(View view) {
        clicks++;
        satiety_textView.setText(String.valueOf(clicks));
        if (clicks % 15 == 0) {
            imageView.startAnimation(animation_cat);
            for (int i = 0; i < achievements.size(); i++) {
                if (clicks == achievements.get(i).getScore_to_achieve()) {
                    GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
                    if (acct != null) {
                        personName = acct.getDisplayName();
                    }
                    achievements.get(i).setName(personName);
                    writeAchievements(i);
                }
            }
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_share:
                shareScore();
                return true;
            case R.id.item_aboutTheDeveloper:
                Intent intent = new Intent(this,AboutDeveloperActivity.class);
                startActivity(intent);
                return true;
            case R.id.item_results:
                Intent intent1 = new Intent(this,ActivityResult.class);
                startActivity(intent1);
                return true;
            case R.id.item_save:
                writeResults();
                showToast("Success!\nYour Score has been saved!");
                return true;
            case R.id.item_achievements:
                Intent intent2 =new Intent(this,AchievementsActivity.class);
                startActivity(intent2);
                return true;
            case R.id.item_exit:
                Intent intent3 =new Intent(this,SignOutActivity.class);
                startActivity(intent3);
                return true;
            case R.id.item_slide:
                Intent intent4 =new Intent(this,RulesActivity.class);
                startActivity(intent4);
                return true;
            case R.id.item_widget:
                GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
                if (acct != null) {
                    personName = acct.getDisplayName();
                }
                putVidgetInfo();
                return true;
            default:return super.onOptionsItemSelected(item);
        }

    }
    void writeFile(String filename,String text) {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                    openFileOutput(filename, MODE_APPEND)));
            bw.write(text);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void showToast(String text) {
        Toast toast = Toast.makeText(getApplicationContext(),
                text, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
    void putVidgetInfo(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("USER", personName);
        editor.putLong("LAST_SCORE", clicks);
        editor.apply();
        Intent intent5 = new Intent(this, AppWidgetProvider.class);
        intent5.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = AppWidgetManager.getInstance(getApplication())
                .getAppWidgetIds(new ComponentName(getApplication(), AppWidgetProvider.class));
        intent5.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        sendBroadcast(intent5);
    }

    void writeResults(){
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            personName = acct.getDisplayName();
        }
        Date date = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String format = formatter.format(date);
        writeFile("results",personName+"|"+format+"|" + clicks+"\n");
    }

    void writeAchievements(int index){
        if(noSuchAchievement(index)){
            showToast("Achieved achievement"+"\n"+"You clicked "+achievements.get(index).getScore_to_achieve()+" times"+"\n"+achievements.get(index).getTitle());
            writeFile("achievements",
                    achievements.get(index).getName()+"|"+achievements.get(index).getTitle()+"|"+achievements.get(index).getScore_to_achieve()+"\n");
        }
    }
    void generateAchievements(){
        achievements.add(new Achievement(R.drawable.third_place,null,"Cat", 15));
        achievements.add(new Achievement(R.drawable.second_place,null,"Boss!", 30));
        achievements.add(new Achievement(R.drawable.first_place,null,"GOD", 45));
    }

    boolean noSuchAchievement(int index) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    openFileInput(FILENAME)));
            String str = "";
            while ((str = br.readLine()) != null) {
                TableRow tableRow = new TableRow(this);
                tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                String[] strings = str.split("\\|");
                    if(strings[0].equals(achievements.get(index).getName())&&strings[1].equals(achievements.get(index).getTitle())){
                      return false;
                    }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
    void shareScore(){
        Intent myIntent = new Intent(Intent.ACTION_SEND);
        myIntent.setType("text/plain");
        String shareBody=String.valueOf(clicks);
        myIntent.putExtra(Intent.EXTRA_TEXT,shareBody);
        startActivity(Intent.createChooser(myIntent,"Share Using"));
    }

}