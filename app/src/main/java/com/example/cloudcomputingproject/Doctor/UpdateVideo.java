package com.example.cloudcomputingproject.Doctor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UpdateVideo extends AppCompatActivity {
    private StorageReference mStorageRef;
    private ProgressDialog progressDialog;
    private static final int PICK_VIDEO = 1;
    private Uri videoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_video);

        Button uploadVideoChooseButton = findViewById(R.id.upload_video_choose_button);
        Button uploadVideoButton = findViewById(R.id.upload_video_button);
        PlayerView playerView = findViewById(R.id.playerView);
        mStorageRef = FirebaseStorage.getInstance().getReference("Uploads");

        Intent intent = getIntent();
        String selectedTopicVideo = intent.getStringExtra("selectedTopicVideo");

        SimpleExoPlayer player = new SimpleExoPlayer.Builder(this).build();
        playerView.setPlayer(player);

        if (selectedTopicVideo != null && !selectedTopicVideo.isEmpty()) {
            MediaItem mediaItem = MediaItem.fromUri(Uri.parse(selectedTopicVideo));
            player.setMediaItem(mediaItem);
            player.prepare();
        } else {
            Toast.makeText(UpdateVideo.this, "No Video", Toast.LENGTH_SHORT).show();
        }

        uploadVideoChooseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooserVideo();
            }
        });

        uploadVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadVideoFile(videoUri);
                startActivity(new Intent(UpdateVideo.this, DoctorHome.class));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_VIDEO && resultCode == RESULT_OK && data != null && data.getData() != null) {
            videoUri = data.getData();
        }
    }

    private void uploadVideoFile(Uri videoUri) {
        if (videoUri != null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading Video....");
            progressDialog.show();

            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtensionVideo(videoUri));

            fileReference.putFile(videoUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();

                            Toast.makeText(UpdateVideo.this, "Upload video successful", Toast.LENGTH_SHORT).show();

                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri downloadUri) {
                                    String downloadUrl = downloadUri.toString();
                                    updateSelectedTopicVideo(downloadUrl);
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(UpdateVideo.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(UpdateVideo.this, "No video selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateSelectedTopicVideo(String videoUrl) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        CollectionReference topicsCollectionRef = firestore.collection("medical consulting");
        Intent intent = getIntent();
        String selectedTopicVideo = intent.getStringExtra("selectedTopicId");
        String selectedTopicId =selectedTopicVideo;

        topicsCollectionRef.document(selectedTopicId)
                .update("video", videoUrl)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(UpdateVideo.this, "Video URL updated successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateVideo.this, "Failed to update video URL: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void openFileChooserVideo() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_VIDEO);
    }

    private String getFileExtensionVideo(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
}
