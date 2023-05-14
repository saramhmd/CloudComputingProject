package com.example.cloudcomputingproject.Patient.adapter.model;

public class Task {
    private String id;
    private String title;
    private boolean checked;

    public Task() {
        // Required empty public constructor
    }

    public Task(String id, String title, boolean checked) {
        this.id = id;
        this.title = title;
        this.checked = checked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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