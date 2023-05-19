package com.example.cloudcomputingproject.Patient.adapter;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.cloudcomputingproject.Adapter.MyAdapter;
import com.example.cloudcomputingproject.model.Task;
import com.example.cloudcomputingproject.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
                TextView titleView = new TextView(TopicsAvailableActivity.this);
                titleView.setText("الاشعارات");
                titleView.setPadding(40, 40, 40, 40);
                AlertDialog.Builder builder = new AlertDialog.Builder(TopicsAvailableActivity.this);

                builder.setCustomTitle(titleView);
                builder.setMessage("هل تريد ان يُرسل لك اشعارات بالمواضيع التي قمت باختيارها؟");

                builder.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Do something when OK button is clicked
                        Intent intent = new Intent(TopicsAvailableActivity.this, HomePatientActivity.class);
                        startActivity(intent);
                    }
                });

                builder.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Do something when Cancel button is clicked
                        Intent intent = new Intent(TopicsAvailableActivity.this, HomePatientActivity.class);
                        startActivity(intent);
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();






//                Intent intent = new Intent(TopicsAvailableActivity.this, HomePatientActivity.class);
////                getCheckedTopics();
//                startActivity(intent);
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

                })
                .addOnFailureListener(e -> Toast.makeText(TopicsAvailableActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());


    }
}
