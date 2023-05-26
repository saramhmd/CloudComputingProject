package com.example.cloudcomputingproject.chatapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

import org.checkerframework.checker.nullness.qual.NonNull;

public class ProfileActivity2 extends AppCompatActivity {
    private String receiverId, currentUserId, currentState;
    private TextView visitName, visitStatus;
    private Button requestButton;
    private DatabaseReference chatRequestRef, contactsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile2);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();

        receiverId = getIntent().getStringExtra("visit_user_id");

        visitName = findViewById(R.id.visit_user_name);
        visitStatus = findViewById(R.id.visit_status);
        requestButton = findViewById(R.id.send_message_request_button);
        currentState = "new";

        chatRequestRef = FirebaseDatabase.getInstance().getReference().child("Chat Requests");
        contactsRef = FirebaseDatabase.getInstance().getReference().child("Contacts");

        retrieveUserInfo();

        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentState.equals("new")) {
                    sendChatRequest();
                } else if (currentState.equals("request_sent")) {
                    cancelChatRequest();
                }
            }
        });
    }

    private void retrieveUserInfo() {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(receiverId);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (dataSnapshot.child("name").getValue() != null) {
                        String username = dataSnapshot.child("name").getValue().toString();
                        visitName.setText(username);
                    }
                    if (dataSnapshot.child("status").getValue() != null) {
                        String userStatus = dataSnapshot.child("status").getValue().toString();
                        visitStatus.setText(userStatus);
                    }

                    manageChatRequest();
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
            }
        });
    }

    private void manageChatRequest() {
        chatRequestRef.child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(receiverId)) {
                    String requestType = dataSnapshot.child(receiverId).child("request_type").getValue().toString();
                    if (requestType.equals("sent")) {
                        currentState = "request_sent";
                        requestButton.setText("Cancel Chat Request");
                    }
                } else {
                    contactsRef.child(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.hasChild(receiverId)) {
                                currentState = "friends";
                                requestButton.setText("Remove Contact");
                            } else {
                                currentState = "new";
                                requestButton.setText("Send Message");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Handle database error
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
            }
        });
    }

    private void sendChatRequest() {
        chatRequestRef.child(currentUserId).child(receiverId).child("request_type").setValue("sent")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            chatRequestRef.child(receiverId).child(currentUserId).child("request_type").setValue("received")
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                currentState = "request_sent";
                                                requestButton.setText("Cancel Chat Request");
                                            }
                                        }
                                    });
                        }
                    }
                });
    }

    private void cancelChatRequest() {
        chatRequestRef.child(currentUserId).child(receiverId).removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            chatRequestRef.child(receiverId).child(currentUserId).removeValue()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                currentState = "new";
                                                requestButton.setText("Send Message");
                                            }
                                        }
                                    });
                        }
                    }
                });
    }
}
