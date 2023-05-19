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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class MedicalConsultingPhoto extends AppCompatActivity {
    StorageReference mstorageRef;
    DatabaseReference mdatabaseRef;
    ImageView topicImage;
    ProgressBar mprogressBar;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri uriImage;
    Button choosePhoto, uploadImage;
    private StorageTask mUploadTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_consulting_photo);
        choosePhoto = findViewById(R.id.chooseImageButton);
        uploadImage = findViewById(R.id.uploadImage);
        topicImage = findViewById(R.id.imageView);
        mprogressBar= findViewById(R.id.progressBar);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        mstorageRef = FirebaseStorage.getInstance().getReference("Uploads");
        mdatabaseRef = FirebaseDatabase.getInstance().getReference("Uploads");
        FirebaseAppCheck firebaseAppCheck = FirebaseAppCheck.getInstance();
        firebaseAppCheck.installAppCheckProviderFactory(DebugAppCheckProviderFactory.getInstance());
        choosePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mUploadTask != null && mUploadTask.isInProgress()){
                    Toast.makeText(MedicalConsultingPhoto.this, "Upload In Progress", Toast.LENGTH_SHORT).show();
                }else {
                    uploadFile();
                }
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uriImage = data.getData();

            Picasso.get().load(uriImage).into(topicImage);
//            personImage.setImageURI(uriImage);
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
                    mprogressBar.setProgress(0);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mprogressBar.setProgress(0);
                        }
                    },5000);
                    Toast.makeText(MedicalConsultingPhoto.this, "Upload successful", Toast.LENGTH_SHORT).show();
                    taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri downloadUri) {
                            String downloadUrl = downloadUri.toString();
                            SelectedTopics selectedTopics = new SelectedTopics(downloadUrl);
                            Log.e("sara1", downloadUrl);
//                            selectedTopics.setImageUri(downloadUrl);
                            String uploadId = mdatabaseRef.push().getKey();
                            mdatabaseRef.child(uploadId).setValue(selectedTopics);
                            Intent intent = new Intent(MedicalConsultingPhoto.this, AddTopic.class);
                            intent.putExtra("imageUri",downloadUrl);
                            startActivity(intent);
                        }
                    });

                }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MedicalConsultingPhoto.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            double progress = (100.0* snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                            mprogressBar.setProgress((int)progress);
                        }
                    });
        } else {
            Toast.makeText(MedicalConsultingPhoto.this, "There is no file selected", Toast.LENGTH_SHORT).show();
        }
    }

//    public void uploadImage() {
//        mstorageRef = FirebaseStorage.getInstance().getReference("images/");
//        mstorageRef.putFile(uriImage)
//                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        mprogressBar.setVisibility(View.GONE);
//
//                        topicImage.setImageURI(null);
//                        Toast.makeText(MedicalConsultingPhoto.this, "Uploaded", Toast.LENGTH_LONG).show();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(MedicalConsultingPhoto.this, "Failed", Toast.LENGTH_LONG).show();
//
//                    }
//                });
//    }
}
