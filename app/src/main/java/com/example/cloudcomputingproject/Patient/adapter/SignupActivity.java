package com.example.cloudcomputingproject.Patient.adapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cloudcomputingproject.MainActivity;
import com.example.cloudcomputingproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        EditText editTextSignupEmail = findViewById(R.id.signup_email);
        EditText editTextSignupPassword = findViewById(R.id.signup_password);
        EditText editTextFullName = findViewById(R.id.signup_fullName);
        EditText editTextMobile = findViewById(R.id.signup_mobile);
        EditText editTextGender = findViewById(R.id.signup_gender);
        Button signupBtn = findViewById(R.id.signup_btn);
        TextView loginTxt = findViewById(R.id.loginText);
        String uid = null;

        loginTxt.setOnClickListener(view1 -> startActivity(new Intent(SignupActivity.this, SigninActivity.class)));

        signupBtn.setOnClickListener(view -> {
            String email = editTextSignupEmail.getText().toString().trim();
            String pass = editTextSignupPassword.getText().toString().trim();
            String name = editTextFullName.getText().toString().trim();
            String mobile = editTextMobile.getText().toString().trim();
            String gender = editTextGender.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                editTextSignupEmail.setError("Fill in the email field");
            } else if (TextUtils.isEmpty(name)) {
                editTextFullName.setError("Fill in the fullName field");
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                editTextSignupEmail.setError("Valid email is required");
                editTextFullName.requestFocus();
            } else if (TextUtils.isEmpty(pass)) {
                editTextSignupPassword.setError("Password is required");

            } else if (TextUtils.isEmpty(mobile)) {
                editTextMobile.setError("Fill in the mobile field");
            } else {
                registerUser(uid, name, email, pass, mobile, gender);


            }
        });

}
    private void registerUser(String uid, String name, String email, String pass, String mobile, String gender) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(SignupActivity.this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            String uid = task.getResult().getUser().getUid();
                            User user = new User(uid,email,name,mobile,gender,0);
                            firebaseDatabase.getReference().child("Users").child(uid).setValue(user);

                            Toast.makeText(SignupActivity.this, "User Register Successfully", Toast.LENGTH_SHORT).show();

                            FirebaseUser firebaseUser = auth.getCurrentUser();

                            UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
                            firebaseUser.updateProfile(profileChangeRequest);

//                            User user = new User(uid,email,name,mobile,gender,0);
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

                            reference.child(firebaseUser.getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()){

                                        firebaseUser.sendEmailVerification();
                                        Toast.makeText(SignupActivity.this, "User registered successfully, PLease verify your email", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }else {
                                        Toast.makeText(SignupActivity.this, "User registered failed", Toast.LENGTH_SHORT).show();

                                    }

                                }
                            });
                        }
                    }
                });
    }
}