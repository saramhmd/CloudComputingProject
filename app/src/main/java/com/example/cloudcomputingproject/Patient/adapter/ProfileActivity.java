package com.example.cloudcomputingproject.Patient.adapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class ProfileActivity extends AppCompatActivity {
    BottomNavigationView nav;
    String email, mobile, name, gender;
    TextView emailTv,phoneTv, nameTv, genderTv, updateDataTv;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        emailTv = findViewById(R.id.email);
        nameTv = findViewById(R.id.name);
        phoneTv = findViewById(R.id.phone);
        genderTv = findViewById(R.id.gender);

        updateDataTv = findViewById(R.id.tvUpdateData);

        imageView = findViewById(R.id.imageView);
        imageView.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileActivity.this, UploadProfilePicture.class);
            startActivity(intent);
        });

        updateDataTv.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileActivity.this, UpdateDataActivity.class);
            startActivity(intent);
        });


        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        if (firebaseUser == null){
            Toast.makeText(ProfileActivity.this, "something error", Toast.LENGTH_SHORT).show();
        }else {
            showUserProfile(firebaseUser);
        }




        //navigation code

        nav = findViewById(R.id.bottomNavigationViewPatient);
        nav.setSelectedItemId(R.id.profilePatient);

        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.homePatient:
                        startActivity(new Intent(ProfileActivity.this, HomePatientActivity.class));
                        return true;

                    case R.id.profilePatient:
                        return true;

                    case R.id.notificationPatient:
                        startActivity(new Intent(ProfileActivity.this, NotificationPatientActivity.class));
                        return true;

                    case R.id.msgPatient:
                        startActivity(new Intent(ProfileActivity.this, MsgActivity.class));
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

                 //   Picasso.with(ProfileActivity.this).load(uri).into(imageView);
                    Picasso.get().load(uri).into(imageView);

                }else {
                    Toast.makeText(ProfileActivity.this, "Something error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

