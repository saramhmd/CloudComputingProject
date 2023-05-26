package com.example.cloudcomputingproject.Doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.example.cloudcomputingproject.Adapter.HomeAdapter;
import com.example.cloudcomputingproject.Patient.adapter.DetailsActivity;
import com.example.cloudcomputingproject.R;
import com.example.cloudcomputingproject.model.SelectedTopics;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class DoctorHome extends AppCompatActivity implements HomeAdapter.ItemClickListener {
    BottomNavigationView nav;
    Boolean dataVisible = true;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    List<SelectedTopics> mData = new ArrayList<>();
    HomeAdapter adapter;
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//    RecyclerView recyclerView = ;

    RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_home);
        FloatingActionButton fab = findViewById(R.id.fab);
        rv = findViewById(R.id.rvTopic);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HomeAdapter(this, mData);
        rv.setAdapter(adapter);
        getAllSelectedTopics();

        if (!dataVisible) {
            rv.setVisibility(View.GONE); // Hide the RecyclerView or its items
        }


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoctorHome.this, AddTopic.class);
                startActivity(intent);
            }
        });

        nav = findViewById(R.id.bottomNavigationView);
        nav.setSelectedItemId(R.id.homeDoctor);

        nav.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.homeDoctor:
                    return true;
                case R.id.profileDoctor:
                    startActivity(new Intent(DoctorHome.this, ProfileDoctor.class));
                    return true;
                case R.id.notificationDoctor:
                    startActivity(new Intent(DoctorHome.this, NotificationDoctor.class));
                    return true;
                case R.id.msgDoctor:
                    startActivity(new Intent(DoctorHome.this, TrackDoctor.class));
                    return true;
                default:
                    return false;
            }
        });
    }

    private void getAllSelectedTopics() {

        db.collection("medical consulting").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                        String id = document.getId();
                        String advice = document.getString("advice");
                        String topicName = document.getString("topicName");
                        String image = document.getString("image");
                        String videoUri = document.getString("video");


                        SelectedTopics task1 = new SelectedTopics(id,advice,topicName,image,videoUri);

                        mData.add(task1);
                        HomeAdapter adapter = new HomeAdapter(DoctorHome.this, mData);
                        adapter.setClickListener(DoctorHome.this);
                        rv.setLayoutManager(layoutManager);
                        rv.setHasFixedSize(true);
                        rv.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        Log.e("LogDATA", task1.toString());
                    }
//                    rv.setAdapter(adapter);
                    Log.e("Sara", " onSuccess  ");

                }
            }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("Sara", " onFailure  ");

            }
        });
    }

    public void onItemClick(SelectedTopics selectedTopic, String id) {
        Intent intent = new Intent(DoctorHome.this, TopicDetails.class);
        intent.putExtra("selectedTopic", selectedTopic);
        intent.putExtra("topicId", id);
//        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
//        boolean dataVisible = sharedPreferences.getBoolean("dataVisible", true);
//
//        // Update the visibility of the RecyclerView or its items based on the dataVisible state
//        if (!dataVisible) {
//            rv.setVisibility(View.GONE); // Hide the RecyclerView or its items
//        } else {
//            rv.setVisibility(View.VISIBLE); // Show the RecyclerView or its items
//        }

        startActivity(intent);
        finish();
    }


//    protected void onResume() {
//        super.onResume();
//
//        // Retrieve the visibility state from SharedPreferences or Intent
//        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
//        dataVisible = sharedPreferences.getBoolean("dataVisible", true);
//
//        // Update the visibility of the RecyclerView or its items based on the dataVisible state
//        if (!dataVisible) {
//            rv.setVisibility(View.GONE); // Hide the RecyclerView or its items
//        } else {
//            rv.setVisibility(View.VISIBLE); // Show the RecyclerView or its items
//        }
//    }


//    public void onItemClick1(SelectedTopics selectedTopic, String id) {
//        Intent intent = new Intent(DoctorHome.this, TopicDetails.class);
//        intent.putExtra("selectedTopic", selectedTopic);
//        intent.putExtra("topicId", id);
//
//        // Retrieve the visibility state from SharedPreferences or Intent
//        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
//        boolean dataVisible = sharedPreferences.getBoolean("dataVisible", true);
//
//        // Update the visibility of the RecyclerView or its items based on the dataVisible state
//        if (!dataVisible) {
//            rv.setVisibility(View.GONE); // Hide the RecyclerView or its items
//        } else {
//            rv.setVisibility(View.VISIBLE); // Show the RecyclerView or its items
//        }
//
//        startActivity(intent);
//        finish();
//    }

}