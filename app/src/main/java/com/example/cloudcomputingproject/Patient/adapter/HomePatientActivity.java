package com.example.cloudcomputingproject.Patient.adapter;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.example.cloudcomputingproject.Patient.adapter.Adapter.MyAdapter2;
import com.example.cloudcomputingproject.model.SelectedTopics;
import com.example.cloudcomputingproject.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class HomePatientActivity extends AppCompatActivity implements MyAdapter2.ItemClickListener{

    private RecyclerView recyclerView;
    private List<SelectedTopics> mData;

    BottomNavigationView nav;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_patient);

        recyclerView = findViewById(R.id.rv2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyAdapter2 myAdapter2 = new MyAdapter2(this, mData);

        recyclerView.setAdapter(myAdapter2);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        nav = findViewById(R.id.bottomNavigationViewPatient);
        nav.setSelectedItemId(R.id.homePatient);

        nav.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.homePatient:
                    return true;
                case R.id.profilePatient:
                    startActivity(new Intent(HomePatientActivity.this, ProfileActivity.class));
                    return true;
                case R.id.notificationPatient:
                    startActivity(new Intent(HomePatientActivity.this, NotificationPatientActivity.class));
                    return true;
                case R.id.msgPatient:
                    startActivity(new Intent(HomePatientActivity.this, MsgActivity.class));
                    return true;
                default:
                    return false;
            }
        });

        getAllSelectedTopics();
    }

    private void getAllSelectedTopics() {
        CollectionReference topicsRef = FirebaseFirestore.getInstance().collection("topics");
        Query query = topicsRef.whereEqualTo("isChecked", true);
        query.get().addOnSuccessListener(queryDocumentSnapshots -> {
            List<String> topicNames = new ArrayList<>();

            for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                String topicName = document.getString("topicName");
                topicNames.add(topicName);
            }

                // Retrieve the advice from the "medical consulting" collection based on the topicId
                CollectionReference medicalConsultingRef = FirebaseFirestore.getInstance().collection("medical consulting");
                medicalConsultingRef.whereIn("topicName", topicNames).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<SelectedTopics> mData = new ArrayList<>();
                        for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {

                            String id = document.getId();
                            String advice = document.getString("advice");
                            String topicName = document.getString("topicName");
                            String image = document.getString("image");

                            SelectedTopics task1 = new SelectedTopics(id,advice,topicName,image);
                            mData.add(task1);
                        }
                        MyAdapter2 adapter = new MyAdapter2(HomePatientActivity.this, mData);
                        adapter.setClickListener(HomePatientActivity.this);

                        recyclerView.setAdapter(adapter);
                    }
                });

        });
    }

    @Override
    public void onItemClick(SelectedTopics selectedTopic, String id) {
        Intent intent = new Intent(HomePatientActivity.this, DetailsActivity.class);
        intent.putExtra("selectedTopic", selectedTopic);
        intent.putExtra("topicId", id);
        startActivity(intent);
        finish();
    }
}