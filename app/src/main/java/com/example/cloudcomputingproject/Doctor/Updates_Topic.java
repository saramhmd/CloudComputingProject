package com.example.cloudcomputingproject.Doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.example.cloudcomputingproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Updates_Topic extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText editTextUpdateTopicName, editTextUpdateAdvice;
    Button UpdatePhoto, UpdateVideo, UpdateTopic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updates_topic);

        UpdatePhoto = findViewById(R.id.UpdatePhoto);
        UpdateVideo = findViewById(R.id.UpdateVideo);
        UpdateTopic = findViewById(R.id.UpdateTopic);
        GetSingleTopics();

    }

    private void GetSingleTopics() {

        db.collection("medical consulting").document(getIntent().getStringExtra("id")).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            Log.d("TAG", "58888888888888");

                            if (document.exists()) {
                                String topicName = document.getString("topicName");
                                String advice = document.getString("advice");

                                editTextUpdateTopicName = findViewById(R.id.editTextUpdateTopicName);
                                editTextUpdateAdvice = findViewById(R.id.editTextUpdateAdvice);

                                editTextUpdateTopicName.setText(topicName);
                                editTextUpdateAdvice.setText(advice);
                                Log.d("TAG", topicName);

                            } else {
                                Log.d("TAG", "No such document555555555555555555555555555555");
                            }
                        } else {
                            Log.d("TAG", "get failed with ", task.getException());
                        }
                    }
                });
    }
}