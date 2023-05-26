package com.example.cloudcomputingproject.Doctor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cloudcomputingproject.R;
import com.example.cloudcomputingproject.model.SelectedTopics;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class TopicDetails extends AppCompatActivity {
    private SimpleExoPlayer player;
    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;
    Button editBtn,deleteBtn;
    private FirebaseFirestore db;
    private CollectionReference topicsCollection;
    private boolean dataVisible = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_details);
        editBtn = findViewById(R.id.editBtn);
        deleteBtn = findViewById(R.id.deleteBtn);

        db = FirebaseFirestore.getInstance();

        // Obtain reference to the collection
        topicsCollection = db.collection("medical consulting");

        PlayerView playerView = findViewById(R.id.playerView);
        RecyclerView recyclerView = findViewById(R.id.rvTopic);


        TextView topicNameTextView = findViewById(R.id.topicNameTextView);
        TextView adviceTextView = findViewById(R.id.adviceTextView);
        ImageView imageView = findViewById(R.id.imageView);
        TextView back = findViewById(R.id.back);

        back.setOnClickListener(view -> startActivity(new Intent(TopicDetails.this, DoctorHome.class)));

        Intent intent = getIntent();
        SelectedTopics selectedTopic = (SelectedTopics) intent.getSerializableExtra("selectedTopic");


        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(TopicDetails.this, Updates_Topic.class);
                        intent.putExtra("selectedTopic", selectedTopic);
                        startActivity(intent);
                    }
                });
            }
        });


        deleteBtn.setOnClickListener(view -> {
            // Delete the document from Firestore
            topicsCollection.document(selectedTopic.getId()).delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Remove the item from the RecyclerView
                            int position = getIntent().getIntExtra("position", -1);
                            if (position != -1) {
                                RecyclerView.Adapter adapter = recyclerView.getAdapter();
                                if (adapter != null) {
                                    adapter.notifyItemRemoved(position);
                                }
                            }
                            Toast.makeText(TopicDetails.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(TopicDetails.this, DoctorHome.class));
                            // Finish the activity or perform any other necessary actions
                            finish();
                        }
                    })
                    .addOnFailureListener(e -> {
                        // Handle the failure case
                        Toast.makeText(TopicDetails.this, "Failed to delete topic", Toast.LENGTH_SHORT).show();
                    });
        });

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        dataVisible = sharedPreferences.getBoolean("dataVisible", true);
        Button visibleBtn = findViewById(R.id.visibleBtn);

        // Update the visibility of the TextView based on the dataVisible state
        if (!dataVisible) {
            adviceTextView.setVisibility(View.GONE); // Hide the advice TextView
        } else {
            adviceTextView.setVisibility(View.VISIBLE); // Show the advice TextView
        }



//        visibleBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dataVisible = !dataVisible;
//
//                // Update the visibility of the TextView based on the dataVisible state
//                if (!dataVisible) {
//                    adviceTextView.setVisibility(View.GONE); // Hide the advice TextView
//                } else {
//                    adviceTextView.setVisibility(View.VISIBLE); // Show the advice TextView
//                }
//
//                // Save the visibility state to SharedPreferences
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putBoolean("dataVisible", dataVisible);
//                editor.apply();
//            }
//        });
//        visibleBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String documentId = selectedTopic.getId(); // Replace it with the actual document ID
//                Map<String, Object> updateData = new HashMap<>();
//                updateData.put("isHidden", true); // Update the "isHidden" field to true
//
//                topicsCollection.document(documentId).update(updateData)
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
////                                topicsCollection.document(documentId).update(updateData);
//
//                                Toast.makeText(TopicDetails.this, "Document hidden successfully!", Toast.LENGTH_SHORT).show();
//                                // Update the user interface or take any other action based on the updated state
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Toast.makeText(TopicDetails.this, "Failed to hide document", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//            }
//        });
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.show();

//        visibleBtn.setOnClickListener(view -> {
//            // Toggle the visibility state of the data
//            dataVisible = !dataVisible;
//
//            // Update the visibility of the RecyclerView or its items based on the dataVisible state
//            if (dataVisible) {
//                recyclerView.setVisibility(View.VISIBLE); // Show the RecyclerView or its items
//            } else {
//                recyclerView.setVisibility(View.GONE);
//                Toast.makeText(this, "Hided Successfully", Toast.LENGTH_SHORT).show();// Hide the RecyclerView or its items
//            }
//        });


        adviceTextView.setText(selectedTopic.getAdvice());
        topicNameTextView.setText(selectedTopic.getTopicName());
        Glide.with(this).load(selectedTopic.getImageUri()).into(imageView);
        player = new SimpleExoPlayer.Builder(this).build();
        String videoUri = selectedTopic.getvideoUri();
        if (videoUri != null && !videoUri.isEmpty()) {
            MediaItem mediaItem = MediaItem.fromUri(Uri.parse(videoUri));
            player.setMediaItem(mediaItem);
            player.prepare();
        } else {
            Toast.makeText(TopicDetails.this, "No Video", Toast.LENGTH_SHORT).show();
        }
        playerView.setPlayer(player);

        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player != null) {
            playWhenReady = player.getPlayWhenReady();
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            player.setPlayWhenReady(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (player != null) {
            player.setPlayWhenReady(playWhenReady);
            player.seekTo(currentWindow, playbackPosition);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
            player = null;
        }
    }

}