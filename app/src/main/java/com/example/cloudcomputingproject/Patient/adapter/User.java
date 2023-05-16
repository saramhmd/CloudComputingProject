package com.example.cloudcomputingproject.Patient.adapter;

public class User {

    public String uid;
    public String email;
    public String fullName;
    public String mobile;
    public String gender;
    public int userType;

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public User() {
    }

    public User(String uid, String email, String fullName, String mobile, String gender, int userType) {
        this.uid = uid;
        this.email = email;
        this.fullName = fullName;
        this.mobile = mobile;
        this.gender = gender;
        this.userType = userType;
    }
}
