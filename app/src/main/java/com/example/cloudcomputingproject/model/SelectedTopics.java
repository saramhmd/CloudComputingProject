package com.example.cloudcomputingproject.model;

import android.widget.ImageView;

public class SelectedTopics {
    private String advice;
    private ImageView image;
    private String topicName;

    public SelectedTopics(String advice,  String topicName) {
        this.advice = advice;
//        this.image = image;
        this.topicName = topicName;
    }
    public SelectedTopics() {
        // Required empty public constructor
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
}
