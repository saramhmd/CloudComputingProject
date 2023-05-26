package com.example.cloudcomputingproject.chatapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cloudcomputingproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.HashMap;

public class SettingsActivity extends AppCompatActivity {

    private Button UpdateAccountSettings;
    private EditText username, userstatus;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference Refdatabase;
    private String currentUserrID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        firebaseAuth = FirebaseAuth.getInstance();
        currentUserrID = firebaseAuth.getCurrentUser().getUid();
        Refdatabase = FirebaseDatabase.getInstance().getReference();

        UpdateAccountSettings = findViewById(R.id.update_settings_button);
        username = findViewById(R.id.set_user_name);
        userstatus = findViewById(R.id.set_profile_status);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setTitle("Account Settings");

        UpdateAccountSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateSettings();
            }
        });

        RetrieveUserInfo();
    }

    private void UpdateSettings() {
        String setusername = username.getText().toString();
        String setuserstatus = userstatus.getText().toString();

        if (TextUtils.isEmpty(setusername)) {
            Toast.makeText(SettingsActivity.this, "Please write your user name first...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(setuserstatus)) {
            Toast.makeText(SettingsActivity.this, "Please write your status first...", Toast.LENGTH_SHORT).show();
        } else {
            HashMap<String, Object> profileMap = new HashMap<>();
            profileMap.put("uid", currentUserrID);
            profileMap.put("name", setusername);
            profileMap.put("status", setuserstatus);

            Refdatabase.child("Users").child(currentUserrID).updateChildren(profileMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(SettingsActivity.this, "Your profile has been updated...", Toast.LENGTH_SHORT).show();
                        sendUserToMainActivity();
                    } else {
                        String errormessage = task.getException().toString();
                        Toast.makeText(SettingsActivity.this, "Error: " + errormessage, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendUserToMainActivity() {
        // يمكنك تعديل هذا النشاط حسب احتياجاتك
    }

    private void RetrieveUserInfo() {
        Refdatabase.child("Users").child(currentUserrID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.hasChild("name")) {
                    String retrieveusername = dataSnapshot.child("name").getValue().toString();
                    String retrieveuserstatus = dataSnapshot.child("status").getValue().toString();

                    username.setText(retrieveusername);
                    userstatus.setText(retrieveuserstatus);
                } else {
                    Toast.makeText(SettingsActivity.this, "Please set and update your profile information...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
