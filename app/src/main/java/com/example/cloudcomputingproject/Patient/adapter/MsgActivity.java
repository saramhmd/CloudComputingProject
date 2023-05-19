package com.example.cloudcomputingproject.Patient.adapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.cloudcomputingproject.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


import de.hdodenhof.circleimageview.CircleImageView;

public class MsgActivity extends AppCompatActivity {

    private String mobile;
    private String email;
    private String name;
    private RecyclerView rvMsg;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().getDatabase().getReferenceFromUrl("https://mobile-cloud-application-default-rtdb.firebaseio.com/");

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg);

        final CircleImageView userProfilePic = findViewById(R.id.userProfilePic);

        rvMsg = findViewById(R.id.msgRv);

        mobile = getIntent().getStringExtra("mobile");
        email = getIntent().getStringExtra("email");
        name = getIntent().getStringExtra("name");



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


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final String profilePicUrl = snapshot.child("Users").child(mobile).child("profile_pic").getValue(String.class);

                Picasso.get().load(profilePicUrl).into(userProfilePic);
//                userProfilePic.
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}