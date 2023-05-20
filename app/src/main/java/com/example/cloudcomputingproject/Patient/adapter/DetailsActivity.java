package com.example.cloudcomputingproject.Patient.adapter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.cloudcomputingproject.R;
import com.example.cloudcomputingproject.model.SelectedTopics;

public class DetailsActivity extends AppCompatActivity {
    private TextView adviceTextView;
    private TextView topicNameTextView;
    private ImageView topicImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        SelectedTopics selectedTopic = (SelectedTopics) intent.getSerializableExtra("selectedTopic");
//        SelectedTopics selectedTopic = getIntent().getParcelableExtra("selectedTopic");

        // Initialize the views
        adviceTextView = findViewById(R.id.adviceTextView);
        topicNameTextView = findViewById(R.id.topicNameTextView);
        topicImageView = findViewById(R.id.imageView);

        adviceTextView.setText(selectedTopic.getAdvice());
        topicNameTextView.setText(selectedTopic.getTopicName());

        Glide.with(this).load(selectedTopic.getImageUri()).into(topicImageView);

    }
}