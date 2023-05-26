package com.example.cloudcomputingproject.model;

public class Task {
    private String id;
    private String title;
    private String groupName;
    private boolean checked;

    public Task() {
        // Required empty public constructor
    }

    public Task(String id, String title, String groupName, boolean checked) {
        this.id = id;
        this.title = title;
        this.groupName = groupName;
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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
