package com.example.cloudcomputingproject.model;

import java.io.Serializable;

public class SelectedTopics implements Serializable {
    private String id;
    private String advice;
    private String imageUri;
    private String topicName;
    private String videoUri;
    public SelectedTopics() {
    }



    public SelectedTopics(String id, String advice, String topicName, String imageUri ) {
        this.id = id;
        this.advice = advice;
        this.topicName = topicName;
        this.imageUri = imageUri;
    }

    public SelectedTopics(String id, String advice, String topicName, String imageUri, String videoUri ) {
        this.id = id;
        this.advice = advice;
        this.topicName = topicName;
        this.imageUri = imageUri;
        this.videoUri = videoUri;
    }

    public void setVideoUri(String videoUri) {
        this.videoUri = videoUri;
    }

    public String getVideoUri() {
        return videoUri;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public SelectedTopics(String imageUri) {
        this.imageUri = imageUri;
    }


    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public String getvideoUri() {
        return videoUri;
    }

    public void setvideoUri(String videoUri) {
        this.videoUri = videoUri;
    }



    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

}
