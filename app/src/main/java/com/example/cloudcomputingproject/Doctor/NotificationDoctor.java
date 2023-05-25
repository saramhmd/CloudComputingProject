package com.example.cloudcomputingproject.Doctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.cloudcomputingproject.Patient.adapter.ProfileActivity;
import com.example.cloudcomputingproject.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NotificationDoctor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_doctor);

        BottomNavigationView nav = findViewById(R.id.bottomNavigationView);
        nav.setSelectedItemId(R.id.notificationDoctor);

        nav.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.homeDoctor:
                    startActivity(new Intent(NotificationDoctor.this, DoctorHome.class));
                    return true;
                case R.id.profileDoctor:
                    startActivity(new Intent(NotificationDoctor.this, ProfileDoctor.class));
                    return true;
                case R.id.notificationDoctor:
                    return true;
                case R.id.msgDoctor:
                    startActivity(new Intent(NotificationDoctor.this, TrackDoctor.class));
                    return true;
                default:
                    return false;
            }
        });
    }
}
