package com.example.cloudcomputingproject.Doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cloudcomputingproject.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddTopic extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText editTextTopicName,editTextAdvice;
    Button buttonAddV,buttonAddPH,addMedicalConsultingButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_topic);
        editTextTopicName = findViewById(R.id.editTextTextTopicName);
        editTextAdvice = findViewById(R.id.editTextAdvice);
        buttonAddV = findViewById(R.id.addVideo);
        buttonAddPH = findViewById(R.id.addPhoto);
        addMedicalConsultingButton = findViewById(R.id.addMedicalConsultingButton);
        addMedicalConsultingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToFirebase();
            }
        });

    }

    public void addToFirebase() {

        String advice = editTextAdvice.getText().toString();
        String topicName = editTextTopicName.getText().toString();

        Map<String, Object> topicMap = new HashMap<>();
        if (!advice.isEmpty() && !topicName.isEmpty() ) {
            topicMap.put("advice", advice);
            topicMap.put("topicName", topicName);
            db.collection("medical consulting")
                    .add(topicMap)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                              @Override
                                              public void onSuccess(DocumentReference documentReference) {
                                                  Toast.makeText(AddTopic.this, "Added Succeeded", Toast.LENGTH_SHORT).show();
                                                  Log.e("TAG", "Data added successfully to database");
                                              }
                                          }
                    )

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("TAG", "Failed to add database");

                        }
                    });

        } else {
            Toast.makeText(this, "Please Fill fields", Toast.LENGTH_SHORT).show();
        }

    }

}