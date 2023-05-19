package com.example.cloudcomputingproject.Doctor;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cloudcomputingproject.model.SelectedTopics;
import com.example.cloudcomputingproject.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class AddTopic extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText editTextTopicName, editTextAdvice;
    Button buttonAddV, buttonAddPH, addMedicalConsultingButton;
    private StorageTask mUploadTask;
    String Imageurl;
    String Videourl;
    StorageReference mstorageRef;
    DatabaseReference mdatabaseRef;
    private Uri uriImage;
    private int PICK_IMAGE_REQUEST = 1;
    private int PICK_VIDEO = 1;
    private Uri uriVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_topic);
        editTextTopicName = findViewById(R.id.editTextTextTopicName);
        editTextAdvice = findViewById(R.id.editTextAdvice);
        buttonAddV = findViewById(R.id.addVideo);
        buttonAddPH = findViewById(R.id.addPhoto);
        addMedicalConsultingButton = findViewById(R.id.addMedicalConsultingButton);
        mstorageRef = FirebaseStorage.getInstance().getReference("Uploads");
        mdatabaseRef = FirebaseDatabase.getInstance().getReference("Uploads");
        addMedicalConsultingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToFirebase();
            }
        });
        buttonAddPH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PICK_IMAGE_REQUEST=1;
                PICK_VIDEO = 0;
                openFileChooser();

            }
        });
        buttonAddV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PICK_IMAGE_REQUEST=0;
                PICK_VIDEO = 1;
                openFileChooserVideo();


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

                            Toast.makeText(AddTopic.this, "Upload video successful", Toast.LENGTH_SHORT).show();
                            taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri downloadUri) {
                                    String downloadUrl = downloadUri.toString();
                                    Videourl= downloadUrl;
                                    SelectedTopics selectedTopics = new SelectedTopics();
                                    selectedTopics.setvideoUri(downloadUrl);
                                    Log.e("Videourl", downloadUrl);
                                    String uploadId = mdatabaseRef.push().getKey();
                                    mdatabaseRef.child(uploadId).setValue(selectedTopics);
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddTopic.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(AddTopic.this, "There is no video selected", Toast.LENGTH_SHORT).show();
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


/////////////////////////////////////////////////////////////////////////////////

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uriImage = data.getData();
            uploadFile();
        }
         if (requestCode == PICK_VIDEO && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uriVideo = data.getData();
            uploadVideoFile(uriVideo);
        }
    }

        private String getFiLExtention(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
    private void uploadFile() {
        if (uriImage != null) {
            StorageReference fileReference = mstorageRef.child(System.currentTimeMillis() + "." + getFiLExtention(uriImage));
            mUploadTask = fileReference.putFile(uriImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(AddTopic.this, "Upload Image successful", Toast.LENGTH_SHORT).show();
                            taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri downloadUri) {
                                    String downloadUrl = downloadUri.toString();
                                    Imageurl = downloadUrl;
                                    SelectedTopics selectedTopics = new SelectedTopics(downloadUrl);
                                    Log.e("Imageurl0", downloadUrl);
                                    String uploadId = mdatabaseRef.push().getKey();
                                    mdatabaseRef.child(uploadId).setValue(selectedTopics);

                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddTopic.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(AddTopic.this, "There is no file selected", Toast.LENGTH_SHORT).show();
        }
    }


    public void addToFirebase() {

        String advice = editTextAdvice.getText().toString();
        String topicName = editTextTopicName.getText().toString();

        Map<String, Object> topicMap = new HashMap<>();
        if (!advice.isEmpty() && !topicName.isEmpty() && !Imageurl.isEmpty() && !Videourl.isEmpty()) {
            topicMap.put("advice", advice);
            topicMap.put("topicName", topicName);
            topicMap.put("image", Imageurl);
            topicMap.put("video", Videourl);
            db.collection("medical consulting")
                    .add(topicMap)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                              @Override
                                              public void onSuccess(DocumentReference documentReference) {
                                                  Toast.makeText(AddTopic.this, "Added Succeeded", Toast.LENGTH_SHORT).show();
                                                  Log.e("TAG", "Data added successfully to database");
                                              }
                                          }
                    )

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("TAG", "Failed to add database");

                        }
                    });

        } else {
            Toast.makeText(this, "Please Fill fields", Toast.LENGTH_SHORT).show();
        }

    }

}