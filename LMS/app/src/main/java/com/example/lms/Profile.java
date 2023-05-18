package com.example.lms;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.UUID;


public class Profile extends Fragment {
    TextView viewProfile;
    EditText txtEmail, txtStudentId;
    RoundedImageView profileImage;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://learning-management-syst-24c6c-default-rtdb.firebaseio.com/");
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReferenceFromUrl("gs://learning-management-syst-24c6c.appspot.com/");

    Uri filePath;
    final int PICK_IMAGE_REQUEST = 22;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        viewProfile = v.findViewById(R.id.profile_name);
        txtEmail = v.findViewById(R.id.txtUserEmail);
        txtStudentId = v.findViewById(R.id.txtStudentID);
        profileImage = v.findViewById(R.id.imgUserProfile);

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage();
            }
        });

        SessionManager sessionManager = new SessionManager(getActivity());
        int sessionManagerStudentId = sessionManager.getStudentId();
        String sessionManagerEmail = sessionManager.getEmail();
        String sessionManagerFullName = sessionManager.getFullName();
        String sessionManagerUsername = sessionManager.getUsername();

        viewProfile.setText(sessionManagerFullName);
        txtEmail.setText(sessionManagerEmail);
        txtStudentId.setText(String.valueOf(sessionManagerStudentId));

        databaseReference.child("Users").child(sessionManagerUsername).child("filepath").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                String filepath = dataSnapshot.getValue(String.class);
                if (filepath != null) {
                    Picasso.get().load(filepath).into(profileImage);
                }
            }
        });


        return v;
    }

    private void UploadImage() {
        if (filePath != null) {
            ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
            ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            SessionManager sessionManager = new SessionManager(getActivity());
                            String sessionManagerUsername = sessionManager.getUsername();
                            databaseReference.child("Users").child(sessionManagerUsername).child("filepath").setValue(uri.toString());

                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Saved Changes", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded " + (int) progress + "%");
                }
            });
        }
    }

    private void SelectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            profileImage.setImageURI(filePath);

            UploadImage();
        }
    }
}

