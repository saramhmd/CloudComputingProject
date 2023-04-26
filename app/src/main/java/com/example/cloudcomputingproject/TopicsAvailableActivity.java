package com.example.cloudcomputingproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.cloudcomputingproject.adapter.MyAdapter;
import com.example.cloudcomputingproject.model.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class TopicsAvailableActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapter taskAdapter;
    private FirebaseFirestore db;

//    private SearchView searchView;
    private List<Task> taskList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics_available);


        recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();



        getAllTopics();
    }

    private void getAllTopics() {
        db.collection("topics").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Task> tasks = new ArrayList<>();
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        if (documentSnapshot.exists()) {
                            Task task = documentSnapshot.toObject(Task.class);
//                            String id = documentSnapshot.getId();
                            String topicName = documentSnapshot.getString("topicName");
                            Boolean isChecked = documentSnapshot.getBoolean("isChecked");
                            Task task1 = new Task(topicName, Boolean.TRUE.equals(isChecked));
                            tasks.add(task1);
//                                tasks.add(task);
                            taskAdapter = new MyAdapter(this, tasks);
                            recyclerView.setAdapter(taskAdapter);
                        }
                    }

                })
                .addOnFailureListener(e -> Toast.makeText(TopicsAvailableActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());

    }


}