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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LectureMaterials extends Fragment {

    RecyclerView recyclerView;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://learning-management-syst-24c6c-default-rtdb.firebaseio.com/");
    MaterialAdapter materialAdapter;
    ArrayList<Material> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lecture_materials, container, false);

        SessionManager sessionManager = new SessionManager(getContext());
        String moduleName = sessionManager.getModuleName();

        /*Toast.makeText(getContext(), moduleName, Toast.LENGTH_SHORT).show();*/

        recyclerView = view.findViewById(R.id.lecture_materials_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        list = new ArrayList<>();
        materialAdapter = new MaterialAdapter(getContext(), list);
        recyclerView.setAdapter(materialAdapter);

        String degree = sessionManager.getDegree();

        databaseReference.child("Module").child(degree).child(moduleName).child("Materials").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Material material = new Material();
                    material.setMaterialName(dataSnapshot.child("name").getValue().toString());
                    material.setMaterialUrl(dataSnapshot.child("url").getValue().toString());
                    list.add(material);

                    Log.d("TAG", "onDataChange: " + dataSnapshot.child("name").getValue().toString());
                    Log.d("TAG", "onDataChange material url: " + dataSnapshot.child("url").getValue().toString());

                }
                materialAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;

    }
}