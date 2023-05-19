package com.example.cloudcomputingproject.Patient.adapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.cloudcomputingproject.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MsgActivity extends AppCompatActivity {

    private String mobile;
    private String email;
    private String name;
    private RecyclerView rvMsg;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg);

        rvMsg = findViewById(R.id.msgRv);




        rvMsg.setHasFixedSize(true);
        rvMsg.setLayoutManager(new LinearLayoutManager(this));

        BottomNavigationView nav = findViewById(R.id.bottomNavigationViewPatient);
        nav.setSelectedItemId(R.id.notificationPatient);

        nav.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.homePatient:
                    startActivity(new Intent(MsgActivity.this, HomePatientActivity.class));
                    return true;

                case R.id.profilePatient:
                    startActivity(new Intent(MsgActivity.this, ProfileActivity.class));
                    return true;

                case R.id.msgPatient:
                    return true;

                case R.id.notificationPatient:
                    startActivity(new Intent(MsgActivity.this, NotificationPatientActivity.class));
                    return true;

                default:
                    return false;
            }


        });
    }
}