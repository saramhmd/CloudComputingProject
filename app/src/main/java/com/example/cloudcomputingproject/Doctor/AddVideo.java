package com.example.cloudcomputingproject.Doctor;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cloudcomputingproject.model.SelectedTopics;
import com.example.cloudcomputingproject.R;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class AddVideo extends AppCompatActivity {
    StorageReference mstorageRef;
    DatabaseReference mdatabaseRef;
    ImageView topicImage;
    ProgressBar mprogressBar;
    private StorageTask mUploadTask;
    private static final int PICK_VIDEO = 1;
    Button chooseVideo, uploadVideo;
    String url;
    private Uri uriVideo;
    PlayerView playerView;
    SimpleExoPlayer player;
    int currentWindow = 0;
    boolean playWhenReady = true;
    long playBackPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_video);
        chooseVideo = findViewById(R.id.chooseVideoButton);
        uploadVideo = findViewById(R.id.uploadVideo);
        playerView = findViewById(R.id.videa_view1);
        mprogressBar = findViewById(R.id.progressBar);
        mstorageRef = FirebaseStorage.getInstance().getReference("UploadsVideo");
        mdatabaseRef = FirebaseDatabase.getInstance().getReference("UploadsVideo");
        chooseVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooserVideo();
            }
        });

        uploadVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(AddVideo.this, "Upload In Progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploadVideoFile(uriVideo);
                }
            }
        });

    }


    private void uploadVideoFile(Uri videoUri) {
        if (videoUri != null) {
            StorageReference fileReference = mstorageRef.child(System.currentTimeMillis() + "." + getFiLExtentionVideo(videoUri));
            fileReference.putFile(videoUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            mprogressBar.setProgress(0);
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mprogressBar.setProgress(0);
                                }
                            }, 5000);
                            Toast.makeText(AddVideo.this, "Upload successful", Toast.LENGTH_SHORT).show();
                            taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri downloadUri) {
                                    String downloadUrl = downloadUri.toString();
                                    SelectedTopics selectedTopics = new SelectedTopics();
                                    selectedTopics.setvideoUri(downloadUrl);
                                    Log.e("sara1", downloadUrl);
                                    String uploadId = mdatabaseRef.push().getKey();
                                    mdatabaseRef.child(uploadId).setValue(selectedTopics);
                                    Intent intent = new Intent(AddVideo.this, AddTopic.class);
                                    intent.putExtra("videoUri",downloadUrl);
                                    startActivity(intent);
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddVideo.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                            mprogressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(AddVideo.this, "There is no video selected", Toast.LENGTH_SHORT).show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_VIDEO && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uriVideo = data.getData();
            SimpleExoPlayer player = new SimpleExoPlayer.Builder(this).build();
            playerView.setPlayer(player);
            MediaItem mediaItem = MediaItem.fromUri(uriVideo);
            player.setMediaItem(mediaItem);
            player.prepare();
            player.play();
        }
    }


    private void openFileChooserVideo() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_VIDEO);
    }

    private String getFiLExtentionVideo(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

}