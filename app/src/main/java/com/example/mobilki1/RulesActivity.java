package com.example.mobilki1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

public class RulesActivity extends AppCompatActivity {
    ViewPager viewPager;
    DotsIndicator dots;
    SliderAdapter sliderAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);

        viewPager = findViewById(R.id.view_pager);
        dots = findViewById(R.id.dots);

        sliderAdapter = new SliderAdapter(this);
        viewPager.setAdapter(sliderAdapter);
        dots.setViewPager(viewPager);
    }
}