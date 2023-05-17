package com.example.cloudcomputingproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cloudcomputingproject.Patient.adapter.SigninActivity;
import com.example.cloudcomputingproject.Patient.adapter.SignupActivity;

public class DoctorOrPatient extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_or_patient);

        TextView login = findViewById(R.id.login);
        Button doctorButton = findViewById(R.id.doctorButton);
        Button patientButton = findViewById(R.id.patientButton);
        doctorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoctorOrPatient.this, SignupActivity.class);
                intent.putExtra("userType", 1); // userType=1 for doctors
                startActivity(intent);
            }
        });

        patientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorOrPatient.this, SignupActivity.class);
                intent.putExtra("userType", 0); // userType=0 for patients
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoctorOrPatient.this, SigninActivity.class);
                startActivity(intent);
            }
        });


    }


}