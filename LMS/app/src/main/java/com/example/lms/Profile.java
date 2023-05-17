package com.example.lms;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Profile extends Fragment {
    TextView viewProfile;
    EditText txtEmail, txtStudentId;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://learning-management-syst-24c6c-default-rtdb.firebaseio.com/");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        viewProfile = v.findViewById(R.id.profile_name);
        txtEmail = v.findViewById(R.id.txtUserEmail);
        txtStudentId = v.findViewById(R.id.txtStudentID);

        SessionManager sessionManager = new SessionManager(getActivity());
        int sessionManagerStudentId = sessionManager.getStudentId();
        String sessionManagerEmail = sessionManager.getEmail();
        String sessionManagerFullName = sessionManager.getFullName();

        viewProfile.setText(sessionManagerFullName);
        txtEmail.setText(sessionManagerEmail);
        txtStudentId.setText(String.valueOf(sessionManagerStudentId));


        return v;
    }
}

