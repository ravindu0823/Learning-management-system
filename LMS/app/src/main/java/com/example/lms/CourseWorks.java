package com.example.lms;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class CourseWorks extends Fragment {

    TextView textView, txtDueDate;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://learning-management-syst-24c6c-default-rtdb.firebaseio.com/");
    Button btnDownload;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cource_work2, container, false);

        textView = view.findViewById(R.id.txtFinalReportSubmission);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadCourseWorks uploadCourseWorks = new UploadCourseWorks();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, uploadCourseWorks).commit();
            }
        });

        txtDueDate = view.findViewById(R.id.txtDueDate);
        btnDownload = view.findViewById(R.id.btnDownloadCourseWork);

        SessionManager sessionManager = new SessionManager(getContext());
        String moduleName = sessionManager.getModuleName();
        String degree = sessionManager.getDegree();

        Toast.makeText(getContext(), moduleName + " " + degree, Toast.LENGTH_SHORT).show();

        databaseReference.child("Module").child(degree).child(moduleName).child("coursework").child("final").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                txtDueDate.setText("Due Date: " + snapshot.child("due_date").getValue().toString());

                btnDownload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        downloadFile(snapshot.child("file_path").getValue().toString());
                    }
                });

                // Log.d("TAG", "onDataChange: " + snapshot.child("file_path").getValue().toString());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
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