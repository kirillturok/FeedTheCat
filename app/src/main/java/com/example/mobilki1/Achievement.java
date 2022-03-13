package com.example.mobilki1;

import android.widget.ImageView;

public class Achievement {
    public Integer image;
    public String name;
    public String title;
    public int score_to_achieve;

    public Achievement(Integer image, String name, String title,  int score_to_achieve){
        this.image=image;
        this.name=name;
        this.title=title;
        this.score_to_achieve=score_to_achieve;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getScore_to_achieve() {
        return score_to_achieve;
    }

    public void setScore_to_achieve(int score_to_achieve) {
        this.score_to_achieve = score_to_achieve;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }
}
