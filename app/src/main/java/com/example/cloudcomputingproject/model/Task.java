package com.example.cloudcomputingproject.model;

public class Task {
    private String title;
    private boolean checked;

    public Task() {
        // Required empty public constructor
    }

    public Task(String title, boolean checked) {
        this.title = title;
        this.checked = checked;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean completed) {
        this.checked = checked;
    }
}