package com.example.cloudcomputingproject.Doctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.cloudcomputingproject.Patient.adapter.ProfileActivity;
import com.example.cloudcomputingproject.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TrackDoctor extends AppCompatActivity {

    BottomNavigationView nav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_doctor);

        nav = findViewById(R.id.bottomNavigationView);
        nav.setSelectedItemId(R.id.trackDoctor);

        nav.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.homeDoctor:
                    startActivity(new Intent(TrackDoctor.this, DoctorHome.class));
                    return true;
                case R.id.profileDoctor:
                    startActivity(new Intent(TrackDoctor.this, ProfileDoctor.class));
                    return true;
                case R.id.notificationDoctor:
                    startActivity(new Intent(TrackDoctor.this, NotificationDoctor.class));
                    return true;
                case R.id.trackDoctor:
                    return true;
                default:
                    return false;
            }
        });
    }
}
