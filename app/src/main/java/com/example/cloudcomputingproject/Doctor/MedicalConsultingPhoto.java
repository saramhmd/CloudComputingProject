package com.example.cloudcomputingproject.Doctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;

import com.example.cloudcomputingproject.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class MedicalConsultingPhoto extends AppCompatActivity {
    StorageReference storageRef;
    FirebaseStorage storage;
    ImageView personImage;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri uriImage;
    Button choosePhoto,uploadImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_consulting_photo);
        choosePhoto = findViewById(R.id.chooseImageButton);
        uploadImage = findViewById(R.id.uploadImage);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference imagesCollection = db.collection("images");
        choosePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


    }
