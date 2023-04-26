package com.example.cloudcomputingproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
//    TextView noteTtext;
//    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        GetTopic();
    }

//    private void getAllTopics() {
//        db.collection("topics").get()
//                .addOnSuccessListener(queryDocumentSnapshots -> {
//                    List<Task> tasks = new ArrayList<>();
//                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
//                        if (documentSnapshot.exists()) {
//                            Task task = documentSnapshot.toObject(Task.class);
////                            String id = documentSnapshot.getId();
//                            String topicName = documentSnapshot.getString("topicName");
//                            Boolean isChecked = documentSnapshot.getBoolean("isChecked");
//                            Task task1 = new Task(topicName, Boolean.TRUE.equals(isChecked));
//                            tasks.add(task1);
////                                tasks.add(task);
//                            taskAdapter = new MyAdapter(tasks);
//                            recyclerView.setAdapter(taskAdapter);
//                        }
//                    }
//
//                })
//                .addOnFailureListener(e -> Toast.makeText(TopicsAvailableActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
//
//    }
}
