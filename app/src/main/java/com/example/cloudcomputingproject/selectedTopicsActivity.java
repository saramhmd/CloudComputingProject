package com.example.cloudcomputingproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cloudcomputingproject.adapter.MyAdapter2;
import com.example.cloudcomputingproject.model.SelectedTopics;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class selectedTopicsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapter2 myAdapter2;
    private FirebaseFirestore db;
    private List<SelectedTopics> mData;
    TextView advice;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_topics);

        myAdapter2 = new MyAdapter2(this, mData);
//        recyclerView.setAdapter(myAdapter2);

//        imageView = findViewById(R.id.imageView);
//        advice=findViewById(R.id.advice);

        recyclerView = findViewById(R.id.rv2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();

        getAllSelectedTopics();


    }

    private void getAllSelectedTopics() {
        db.collection("medical consulting").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<SelectedTopics> mData = new ArrayList<>();
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        if (documentSnapshot.exists()) {
                            SelectedTopics selectedTopics = documentSnapshot.toObject(SelectedTopics.class);
//                            String id = documentSnapshot.getId();
//                            ImageView = documentSnapshot.getString("image");
                            String advice = documentSnapshot.getString("advice");
                            String topicName = documentSnapshot.getString("topicName");

                            SelectedTopics task1 = new SelectedTopics(advice, topicName);
                            mData.add(task1);
                            myAdapter2 = new MyAdapter2(this, mData);
                            recyclerView.setAdapter(myAdapter2);
                            myAdapter2.notifyDataSetChanged();
                        }
                    }

//                    taskAdapter = new MyAdapter(this, tasks);
//                    recyclerView.setAdapter(taskAdapter);


                })
                .addOnFailureListener(e -> Toast.makeText(selectedTopicsActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());

    }
}