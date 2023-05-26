package com.example.cloudcomputingproject.Patient.adapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.cloudcomputingproject.R;
import com.example.cloudcomputingproject.chatapp.MainActivity5;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class NotificationPatientActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_patient);

        BottomNavigationView nav = findViewById(R.id.bottomNavigationViewPatient);
        nav.setSelectedItemId(R.id.notificationPatient);

        nav.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.homePatient:
                    startActivity(new Intent(NotificationPatientActivity.this, HomePatientActivity.class));
                    return true;

                case R.id.profilePatient:
                    startActivity(new Intent(NotificationPatientActivity.this, ProfileActivity.class));
                    return true;

                case R.id.msgPatient:
                    startActivity(new Intent(NotificationPatientActivity.this, MainActivity5.class));
                    return true;

                case R.id.notificationPatient:
                    return true;

                default:
                    return false;
            }


        });
    }
}