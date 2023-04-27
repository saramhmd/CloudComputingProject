package com.example.cloudcomputingproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cloudcomputingproject.adapter.MyAdapter;
import com.example.cloudcomputingproject.model.Task;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class TopicsAvailableActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapter taskAdapter;
    private FirebaseFirestore db;

    private List<Task> tasks;
    private FloatingActionButton floatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics_available);

        recyclerView = findViewById(R.id.rv);
        floatingActionButton = findViewById(R.id.check);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();

        EditText searchBar = findViewById(R.id.searchBar);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                taskAdapter.filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TopicsAvailableActivity.this, selectedTopicsActivity.class);
                startActivity(intent);
            }
        });

        getAllTopics();

    }

    private void getAllTopics() {
        db.collection("topics").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Task> tasks = new ArrayList<>();
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        if (documentSnapshot.exists()) {
                            Task task = documentSnapshot.toObject(Task.class);
                            String id = documentSnapshot.getId();
                            String topicName = documentSnapshot.getString("topicName");
                            Boolean isChecked = documentSnapshot.getBoolean("isChecked");
                            Task task1 = new Task(id, topicName, Boolean.TRUE.equals(isChecked));
                            tasks.add(task1);
                            taskAdapter = new MyAdapter(this, tasks);
                            recyclerView.setAdapter(taskAdapter);
                            taskAdapter.notifyDataSetChanged();
                        }
                    }

//                    taskAdapter = new MyAdapter(this, tasks);
//                    recyclerView.setAdapter(taskAdapter);


                })
                .addOnFailureListener(e -> Toast.makeText(TopicsAvailableActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());


    }

//    public void onCheckboxChanged(View view) {
//        CheckBox checkBox = (CheckBox) view;
//        boolean isChecked = checkBox.isChecked();
//
//        // Get a reference to the Firestore document
//        DocumentReference docRef = FirebaseFirestore.getInstance().collection("topics")
//                .document("4JOD3slHrfQieDFmFpyY");
//
//        // Update the document field
//        docRef.update("isChecked", isChecked)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Log.d("TAG", "Document successfully updated!");
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w("TAG", "Error updating document", e);
//                    }
//                });
//    }

}
