package com.example.cloudcomputingproject.Doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cloudcomputingproject.Patient.adapter.HomePatientActivity;
import com.example.cloudcomputingproject.Patient.adapter.NotificationPatientActivity;
import com.example.cloudcomputingproject.Patient.adapter.ProfileActivity;
import com.example.cloudcomputingproject.Patient.adapter.UpdateDataActivity;
import com.example.cloudcomputingproject.Patient.adapter.UploadProfilePicture;
import com.example.cloudcomputingproject.Patient.adapter.User;
import com.example.cloudcomputingproject.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProfileDoctor extends AppCompatActivity {

    String email, mobile, name, gender;
    TextView emailTv,phoneTv, nameTv, genderTv, updateDataTv;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_doctor);

        emailTv = findViewById(R.id.email);
        nameTv = findViewById(R.id.name);
        phoneTv = findViewById(R.id.phone);
        genderTv = findViewById(R.id.gender);

        updateDataTv = findViewById(R.id.tvUpdateData);

        imageView = findViewById(R.id.imageView);
        imageView.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileDoctor.this, UploadProfilePicture.class);
            startActivity(intent);
        });

        updateDataTv.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileDoctor.this, UpdateDataActivity.class);
            startActivity(intent);
        });


        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        if (firebaseUser == null){
            Toast.makeText(ProfileDoctor.this, "something error", Toast.LENGTH_SHORT).show();
        }else {
            showUserProfile(firebaseUser);
        }

        //navigation code

        BottomNavigationView nav = findViewById(R.id.bottomNavigationView);
        nav.setSelectedItemId(R.id.profileDoctor);

        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.homeDoctor:
                        startActivity(new Intent(ProfileDoctor.this, DoctorHome.class));
                        return true;

                    case R.id.profileDoctor:
                        return true;

                    case R.id.notificationDoctor:
                        startActivity(new Intent(ProfileDoctor.this, NotificationDoctor.class));
                        return true;

                    case R.id.msgDoctor:
                        startActivity(new Intent(ProfileDoctor.this, TrackDoctor.class));
                        return true;

                    default:
                        return false;
                }
            }
        });
        //////////////////////////////////////////////////////////////////////////////
    }


    private void showUserProfile(FirebaseUser firebaseUser) {
        String userID = firebaseUser.getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User readUserDetails = snapshot.getValue(User.class);
                if (readUserDetails != null){
                    email = firebaseUser.getEmail();
                    name = firebaseUser.getDisplayName();
                    mobile = readUserDetails.mobile;
                    gender = readUserDetails.gender;

                    emailTv.setText(email);
                    nameTv.setText(name);
                    phoneTv.setText(mobile);
                    genderTv.setText(gender);

                    Uri uri = firebaseUser.getPhotoUrl();

                    Picasso.get().load(uri).into(imageView);
                }else {
                    Toast.makeText(ProfileDoctor.this, "Something error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileDoctor.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
