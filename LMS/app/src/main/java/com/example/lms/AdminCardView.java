package com.example.lms;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class AdminCardView extends Fragment {
    RecyclerView recyclerView;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://learning-management-syst-24c6c-default-rtdb.firebaseio.com/");
    LectureModuleAdapter moduleAdapter;
    ArrayList<Module> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_admin_cardview, container, false);

        recyclerView = v.findViewById(R.id.lectureRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        list = new ArrayList<>();
        moduleAdapter = new LectureModuleAdapter(getContext(), list);
        recyclerView.setAdapter(moduleAdapter);

        SessionManager sessionManager = new SessionManager(getContext());
        String degree = sessionManager.getDegreeName();

        databaseReference.child("Module").child(degree).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Module module = new Module();
                    module.setModuleDescription(dataSnapshot.child("name").getValue().toString());
                    module.setDegreeProgramme(degree);
                    module.setModuleName(dataSnapshot.getKey());
                    list.add(module);

                    Log.d("TAG", "onDataChange: " + dataSnapshot.getKey());
                    Log.d("TAG", "onDataChange Module Name: " + dataSnapshot.child("name").getValue().toString());
                }
                moduleAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return v;
    }
}