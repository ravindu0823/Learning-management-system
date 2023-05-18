package com.example.lms;

import static android.app.Activity.RESULT_OK;

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
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class UploadCourseWorks extends Fragment {
    RelativeLayout relativeLayout;
    Button upload_btn;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://learning-management-syst-24c6c-default-rtdb.firebaseio.com/");
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReferenceFromUrl("gs://learning-management-syst-24c6c.appspot.com/");
    TextView tableDueDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_courcework, container, false);

        relativeLayout = view.findViewById(R.id.relativeLayoutFileUpload);
        upload_btn = view.findViewById(R.id.btnSubmit);
        tableDueDate = view.findViewById(R.id.table_due_date);

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFiles();
            }
        });

        SessionManager sessionManager = new SessionManager(getContext());
        tableDueDate.setText(sessionManager.getDueDate());

        return view;
    }

    private void selectFiles() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select PDF Files..."), 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            upload_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UploadFiles(data.getData());
                }
            });
        }
    }

    private void UploadFiles(Uri data) {
        SessionManager sessionManager = new SessionManager(getContext());
        String moduleName = sessionManager.getModuleName();
        String degree = sessionManager.getDegree();

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Uploading......");

        StorageReference reference = storageReference.child("Upload/" + System.currentTimeMillis() + ".pdf");
        reference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete()) ;
                Uri url = uriTask.getResult();

                // PDFClass pdfClass = new PDFClass(pdf_name.getText().toString(), url.toString());
                String key = databaseReference.push().getKey();
                assert key != null;
                databaseReference.child("Module").child(degree).child(moduleName).child("Submitted_Courseworks").child(key).child("student_id").setValue(sessionManager.getStudentId());
                databaseReference.child("Module").child(degree).child(moduleName).child("Submitted_Courseworks").child(key).child("file_path").setValue(url.toString());

                Toast.makeText(getContext(), "File Uploded!!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                progressDialog.setMessage("Uploaded: " + (int) progress + "%");
            }
        });
    }
}