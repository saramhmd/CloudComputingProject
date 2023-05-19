package com.example.cloudcomputingproject.model;

public class SelectedTopics {
    private String advice;
    private String imageUri;
    private String topicName;
    private String videoUri;
    public SelectedTopics() {
    }



    public SelectedTopics(String advice, String topicName ) {
        this.advice = advice;
        this.topicName = topicName;
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
//    public SelectedTopics(String videoUri) {
//        this.videoUri = videoUri;
//    }

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
