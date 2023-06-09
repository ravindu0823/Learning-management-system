package com.example.lms;

import static android.app.Activity.RESULT_OK;
import static android.os.Environment.DIRECTORY_DOWNLOADS;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AdminCourseWorks extends Fragment {
    TextView txtDueDate;
    EditText txtInputDueDate;
    RelativeLayout relativeLayout;
    Button btnDownload, btnUpload;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://learning-management-syst-24c6c-default-rtdb.firebaseio.com/");
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReferenceFromUrl("gs://learning-management-syst-24c6c.appspot.com/");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_coursework2, container, false);

        SessionManager sessionManager = new SessionManager(getContext());
        String moduleName = sessionManager.getModuleName();
        String degree = sessionManager.getDegreeName();

        Toast.makeText(getContext(), moduleName + " " + degree, Toast.LENGTH_SHORT).show();

        relativeLayout = view.findViewById(R.id.relativeLayoutCoursework);
        txtDueDate = view.findViewById(R.id.txtLectureDueDate);
        txtInputDueDate = view.findViewById(R.id.txtCourseWorkDueDate);
        btnDownload = view.findViewById(R.id.btnLectureDownloadCourseWork);
        btnUpload = view.findViewById(R.id.btnUploadCourseWork);

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFiles();
            }
        });

        databaseReference.child("Module").child(degree).child(moduleName).child("coursework").child("final").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                txtDueDate.setText("Due Date: " + snapshot.child("due_date").getValue().toString());

                sessionManager.saveDueDate(snapshot.child("due_date").getValue().toString());

                btnDownload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        downloadFile(snapshot.child("file_path").getValue().toString());
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


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
            btnUpload.setOnClickListener(new View.OnClickListener() {
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
        String degree = sessionManager.getDegreeName();

        String dueDate = this.txtInputDueDate.getText().toString();

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
                databaseReference.child("Module").child(degree).child(moduleName).child("coursework").child("final").child("due_date").setValue(dueDate);
                databaseReference.child("Module").child(degree).child(moduleName).child("coursework").child("final").child("file_path").setValue(url.toString());

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

    private void downloadFile(String url) {
        downloadFiles(getContext(), "myFile", ".pdf", DIRECTORY_DOWNLOADS, url);
    }

    public void downloadFiles(Context context, String fileName, String fileExtension, String destinationDirectory, String url) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, destinationDirectory, fileName + fileExtension);

        downloadManager.enqueue(request);
    }
}