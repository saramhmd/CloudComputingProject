package com.example.cloudcomputingproject.Patient.adapter.model;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class SelectedTopics {
    private String advice;
//    private String image;
    private String topicName;

//    private Bitmap imageBitmap;

    public SelectedTopics(String advice, String topicName) {
        this.advice = advice;
//        this.image = image;
        this.topicName = topicName;
//        this.imageBitmap = imageBitmap;

    }

    public SelectedTopics() {
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

//    public String getImage() {
//        return image;
//    }
//
//    public void setImage(String image) {
//        this.image = image;
//    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

//    public Bitmap getImageBitmap() {
//        return imageBitmap;
//    }
//
//    public void setImageBitmap(Bitmap imageBitmap) {
//        this.imageBitmap = imageBitmap;
//    }
}
