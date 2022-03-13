package com.example.mobilki1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class SliderAdapter extends PagerAdapter {

    private final Context context;
    private LayoutInflater layoutInflater;
    private final Integer[] images = { R.drawable.feed_button,
            R.drawable.achievements,
            R.drawable.share};
    private final String[] HEADERS = { "Feed the cat\nPress the 'Feed!' button",
            "Achievements\nWhen you reach 15 points you get achievements",
            "Share\nYou can share score with your friends!"};

    SliderAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE
        );
        @SuppressLint("InflateParams") View view = layoutInflater.inflate(R.layout.firstlayout, null);

        ImageView imageView = view.findViewById(R.id.image_view);
        TextView header = view.findViewById(R.id.header);
        imageView.setImageResource(images[position]);
        header.setText(HEADERS[position]);
        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view= (View) object;
        viewPager.removeView(view);
    }
}
