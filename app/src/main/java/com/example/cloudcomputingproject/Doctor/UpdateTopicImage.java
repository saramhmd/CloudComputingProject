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
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cloudcomputingproject.Patient.adapter.ProfileActivity;
import com.example.cloudcomputingproject.Patient.adapter.UploadProfilePicture;
import com.example.cloudcomputingproject.R;
import com.example.cloudcomputingproject.model.SelectedTopics;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class UpdateTopicImage extends AppCompatActivity {

    private ImageView imageView;
    private StorageReference storageReference;
    private static final int PICK_IMAGE_REQUEST = 101;
    private Uri uriImage;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_profile_picture);
        Button btnUploadPicChoose = findViewById(R.id.upload_pic_choose_button);
        Button btnUploadPic = findViewById(R.id.upload_pic_button);
        imageView = findViewById(R.id.imageView_profile_dp);
        storageReference = FirebaseStorage.getInstance().getReference("Uploads");

        Intent intent = getIntent();
        String selectedTopicImage = intent.getStringExtra("selectedTopicImage");
        Glide.with(this).load(selectedTopicImage).into(imageView);

        btnUploadPicChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        btnUploadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadPic();
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uriImage = data.getData();
            imageView.setImageURI(uriImage);
        }
    }

    private void uploadPic() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading File....");
        progressDialog.show();
        Intent intent = getIntent();
        String selectedTopicId = intent.getStringExtra("selectedTopicId");
        intent.putExtra("selectedTopicId", selectedTopicId);

        if (uriImage != null) {
            StorageReference fileReference = storageReference.child(selectedTopicId + "." + getFileExtension(uriImage));

            fileReference.putFile(uriImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Uri downloadUri = uri;

                            // Update the selected topic image URL in the database
                            updateSelectedTopicImage(downloadUri.toString());

                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            Toast.makeText(UpdateTopicImage.this, "Successfully", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(UpdateTopicImage.this, TopicDetails.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UpdateTopicImage.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            Toast.makeText(UpdateTopicImage.this, "No File Selected!", Toast.LENGTH_SHORT).show();
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void updateSelectedTopicImage(String imageUrl) {
        Intent intent = getIntent();
        String selectedTopicId = intent.getStringExtra("selectedTopicId");

        Toast.makeText(UpdateTopicImage.this, selectedTopicId, Toast.LENGTH_SHORT).show();

        CollectionReference topicsCollection = FirebaseFirestore.getInstance().collection("medical consulting");

        // Create a map to update the image URL
        Map<String, Object> updateData = new HashMap<>();
        updateData.put("image", imageUrl);

        // Update the document with the selectedTopicId
        topicsCollection.document(selectedTopicId)
                .update(updateData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // The image URL has been successfully updated
                        Toast.makeText(UpdateTopicImage.this, "Image updated successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Failed to update the image URL
                        Toast.makeText(UpdateTopicImage.this, "Failed to update image", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
