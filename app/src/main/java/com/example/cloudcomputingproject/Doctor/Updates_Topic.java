package com.example.cloudcomputingproject.Doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cloudcomputingproject.R;
import com.example.cloudcomputingproject.model.SelectedTopics;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Updates_Topic extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
//    EditText editTextUpdateTopicName, editTextUpdateAdvice;
    Button UpdatePhoto, UpdateVideo, UpdateTopic;


    private EditText topicNameEditText;
    private EditText adviceEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updates_topic);

        UpdateTopic= findViewById(R.id.UpdateTopic);
        UpdatePhoto= findViewById(R.id.UpdatePhoto);
        UpdateVideo= findViewById(R.id.UpdateVideo);

        topicNameEditText = findViewById(R.id.editTextUpdateTopicName);
        adviceEditText = findViewById(R.id.editTextUpdateAdvice);

        Intent intent = getIntent();
        SelectedTopics selectedTopic = (SelectedTopics) intent.getSerializableExtra("selectedTopic");

        topicNameEditText.setText(selectedTopic.getTopicName());
        adviceEditText.setText(selectedTopic.getAdvice());
        UpdateTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateTopic(selectedTopic);
            }
        });
        UpdatePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Updates_Topic.this, UpdateTopicImage.class);
                intent.putExtra("selectedTopicImage", selectedTopic.getImageUri());
                intent.putExtra("selectedTopicId", selectedTopic.getId());
                Log.e("selectedTopicId", selectedTopic.getId());

                startActivity(intent);
            }
        });

        UpdateVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Updates_Topic.this, UpdateVideo.class);
                intent.putExtra("selectedTopicVideo", selectedTopic.getvideoUri());
                intent.putExtra("selectedTopicId", selectedTopic.getId());
                Log.e("selectedTopicId", selectedTopic.getId());

                startActivity(intent);
            }
        });
    }
    public void UpdateTopic(SelectedTopics selectedTopics) {

                        db.collection("medical consulting").document(selectedTopics.getId()).update("topicName", topicNameEditText.getText().toString(),
                                "advice", adviceEditText.getText().toString())
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("sara", "DocumentSnapshot successfully updated!");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("sara", "Error updating document", e);
                                    }
                                });

    }


}
