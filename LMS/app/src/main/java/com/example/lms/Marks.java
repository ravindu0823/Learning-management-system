package com.example.lms;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Marks extends Fragment {

    RecyclerView recyclerView;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://learning-management-syst-24c6c-default-rtdb.firebaseio.com/");
    MarksAdapter marksAdapter;
    ArrayList<MarksModel> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_marks, container, false);

        recyclerView = view.findViewById(R.id.marksRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        list = new ArrayList<>();
        MarksAdapter marksAdapter = new MarksAdapter(getContext(), list);
        recyclerView.setAdapter(marksAdapter);

        SessionManager sessionManager = new SessionManager(getContext());
        int studentID = sessionManager.getStudentId();

        databaseReference.child("Marks").child(String.valueOf(studentID)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    MarksModel marksModel = new MarksModel();
                    marksModel.setGrade(dataSnapshot.child("grade").getValue().toString());
                    marksModel.setMarks(dataSnapshot.child("marks").getValue().toString());
                    marksModel.setModuleName(dataSnapshot.getKey());
                    list.add(marksModel);

                    Log.d("TAG", "Module Name: " + dataSnapshot.getKey());
                    Log.d("TAG", "onDataChange Module Grade: " + dataSnapshot.child("grade").getValue().toString());
                    Log.d("TAG", "onDataChange Module Marks: " + dataSnapshot.child("marks").getValue().toString());
                }
                marksAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}