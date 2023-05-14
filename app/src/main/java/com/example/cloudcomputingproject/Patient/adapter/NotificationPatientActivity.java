package com.example.cloudcomputingproject.Patient.adapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.cloudcomputingproject.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class NotificationPatientActivity extends AppCompatActivity {
    BottomNavigationView nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_patient);

        nav = findViewById(R.id.bottomNavigationViewPatient);
        nav.setSelectedItemId(R.id.notificationPatient);

        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.homePatient:
                        startActivity(new Intent(NotificationPatientActivity.this, HomePatientActivity.class));
                        return true;

                    case R.id.profilePatient:
                        startActivity(new Intent(NotificationPatientActivity.this, ProfilePatientActivity.class));
                        return true;

                    case R.id.notificationPatient:
                        return true;

                    default:
                        return false;
                }


            }
        });
    }
}