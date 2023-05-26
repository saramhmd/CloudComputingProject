package com.example.cloudcomputingproject;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class DoctorOrPatient extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_or_patient);

        TextView login = findViewById(R.id.login);
        Button doctorButton = findViewById(R.id.doctorButton);
        Button patientButton = findViewById(R.id.patientButton);
        doctorButton.setOnClickListener(view -> {
            Intent intent = new Intent(DoctorOrPatient.this, SigninActivity.class);
            intent.putExtra("userType", 1); // userType=1 for doctors
            startActivity(intent);
        });

        patientButton.setOnClickListener(v -> {
            Intent intent = new Intent(DoctorOrPatient.this, SigninActivity.class);
            intent.putExtra("userType", 0); // userType=0 for patients
            startActivity(intent);
        });

        login.setOnClickListener(view -> {
            Intent intent = new Intent(DoctorOrPatient.this, SigninActivity.class);
            startActivity(intent);
        });


    }


}