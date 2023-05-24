package com.example.cloudcomputingproject.Doctor;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cloudcomputingproject.R;
import com.example.cloudcomputingproject.model.SelectedTopics;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

public class TopicDetails extends AppCompatActivity {
    private SimpleExoPlayer player;
    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_details);

        PlayerView playerView = findViewById(R.id.playerView);

        TextView topicNameTextView = findViewById(R.id.topicNameTextView);
        TextView adviceTextView = findViewById(R.id.adviceTextView);
        ImageView imageView = findViewById(R.id.imageView);
        TextView back = findViewById(R.id.back);
        back.setOnClickListener(view -> startActivity(new Intent(TopicDetails.this, DoctorHome.class)));

        Intent intent = getIntent();
        SelectedTopics selectedTopic = (SelectedTopics) intent.getSerializableExtra("selectedTopic");

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
            Toast.makeText(TopicDetails.this, "Upload successful00000000000000000", Toast.LENGTH_SHORT).show();
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