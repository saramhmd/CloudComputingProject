package com.example.cloudcomputingproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cloudcomputingproject.Doctor.DoctorHome;
import com.example.cloudcomputingproject.Patient.adapter.TopicsAvailableActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SigninActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        EditText loginEmail = findViewById(R.id.login_email);
        EditText loginPassword = findViewById(R.id.login_password);
        Button loginBtn = findViewById(R.id.login_btn);
        TextView signupTxt = findViewById(R.id.signupText);

        loginBtn.setOnClickListener(view -> {
            String email = loginEmail.getText().toString().trim();
            String pass = loginPassword.getText().toString().trim();

            if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                if (!pass.isEmpty()){
                    firebaseAuth.signInWithEmailAndPassword(email, pass)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = firebaseAuth.getCurrentUser();

                                    String uid = task.getResult().getUser().getUid();
                                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                    firebaseDatabase.getReference().child("Users").child(uid).child("userType")
                                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    if (snapshot.exists()) {

                                                        int userType = snapshot.getValue(Integer.class);

                                                        if (userType == 0) {
                                                            Intent intent = new Intent(SigninActivity.this, TopicsAvailableActivity.class);
                                                            assert user != null;
                                                            intent.putExtra("email", user.getEmail());
                                                            startActivity(intent);
                                                            finish();

                                                        }
                                                        if (userType == 1) {
                                                            Intent intent = new Intent(SigninActivity.this, DoctorHome.class);
                                                            assert user != null;
                                                            intent.putExtra("email", user.getEmail());
                                                            startActivity(intent);
                                                            finish();
                                                        }
                                                    }else {
                                                        Toast.makeText(SigninActivity.this, "errrrrrrrrorrrrrrrrrrr", Toast.LENGTH_SHORT).show();
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });

//                                    FirebaseUser user1 = firebaseAuth.getCurrentUser();
//                                    Intent intent = new Intent(SigninActivity.this, MainActivity.class);
//                                    assert user != null;
//                                    intent.putExtra("email", user.getEmail());
//                                    startActivity(intent);
//                                    finish();
                                } else {
                                    Toast.makeText(SigninActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                }else {
                    loginPassword.setError("Fill the pass field");
                }
            } else if (email.isEmpty()) {
                loginEmail.setError("Fill the email field");
            }else {
                loginEmail.setError("Please enter valid email");
            }
        });

        signupTxt.setOnClickListener(view -> startActivity(new Intent(SigninActivity.this, com.example.cloudcomputingproject.SignupActivity.class)));
    }
}

