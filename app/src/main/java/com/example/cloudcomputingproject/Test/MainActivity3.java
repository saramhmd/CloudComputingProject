package com.example.cloudcomputingproject.Test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cloudcomputingproject.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity3 extends AppCompatActivity {

    FirebaseFirestore db;
    CollectionReference notesCollection;
    RecyclerView recyclerView;
    ArrayList<Note> notesItemArrayList;
    NotesRecyclerAdapter adapter;
    Button buttonAdd ;
    Button buttonHide;
    ViewDialogAdd viewDialogAdd; // Declare ViewDialogAdd object

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Objects.requireNonNull(getSupportActionBar()).hide();
        db = FirebaseFirestore.getInstance();
        notesCollection = db.collection("medical consulting");
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        notesItemArrayList = new ArrayList<>();
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonHide = findViewById(R.id.buttonHidden);

        adapter = new NotesRecyclerAdapter(MainActivity3.this, notesItemArrayList);
        recyclerView.setAdapter(adapter);

        viewDialogAdd = new ViewDialogAdd(); // Instantiate ViewDialogAdd object

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewDialogAdd.showDialog(MainActivity3.this); // Use the instantiated object to show the dialog
            }
        });




        readData();

    }

    private void readData() {
        notesCollection.orderBy("nameNote", Query.Direction.ASCENDING).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        notesItemArrayList.clear();
                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Note note = documentSnapshot.toObject(Note.class);
                            note.setIdNote(documentSnapshot.getId()); // Set the document ID in the Note object
                            notesItemArrayList.add(note);
                        }
                        adapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity3.this, "Failed to read data", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public class ViewDialogAdd {
        public void showDialog(Context context) {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.alert_dilog);
            EditText textName = dialog.findViewById(R.id.textName);
            Button buttonAdd = dialog.findViewById(R.id.buttonAdd);
            Button buttonCancel = dialog.findViewById(R.id.buttonCancel);
            Button buttonHide = dialog.findViewById(R.id.buttonHidden);

            buttonAdd.setText("ADD");
            buttonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            buttonAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String id = "note" + new Date().getTime();
                    String name = textName.getText().toString();
                    if (name.isEmpty()) {
                        Toast.makeText(context, "Please enter all data...", Toast.LENGTH_SHORT).show();
                    } else {
                        Note note = new Note(id, name);
                        notesCollection.document(id).set(note)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(context, "Note added successfully!", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context, "Failed to add note", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }
            });
            buttonHide.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String documentId = "documentId"; // Replace it with the actual document ID
                    Map<String, Object> updateData = new HashMap<>();
                    updateData.put("isHidden", true); // Update the "isHidden" field to true

                    notesCollection.document(documentId).update(updateData)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(MainActivity3.this, "Document hidden successfully!", Toast.LENGTH_SHORT).show();
                                    // Update the user interface or take any other action based on the updated state
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(MainActivity3.this, "Failed to hide document", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            });
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        }
    }
}