package com.example.lms;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

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

public class AdminLectureMaterials extends Fragment {

    RecyclerView recyclerView;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://learning-management-syst-24c6c-default-rtdb.firebaseio.com/");
    MaterialAdapter materialAdapter;
    ArrayList<Material> list;
    Button btnMaterialUpload;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_admin_lecturematerials, container, false);




        // Toast.makeText(getContext(), moduleName + " " + degree, Toast.LENGTH_SHORT).show();

        recyclerView = v.findViewById(R.id.adminLectureMaterialsRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        list = new ArrayList<>();
        materialAdapter = new MaterialAdapter(getContext(), list);
        recyclerView.setAdapter(materialAdapter);



        btnMaterialUpload = v.findViewById(R.id.btnUploadLectureMaterials);

        btnMaterialUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminUploadLectureMaterials adminUploadLectureMaterials = new AdminUploadLectureMaterials();
                getFragmentManager().beginTransaction().replace(R.id.admin_fragment_container, adminUploadLectureMaterials).commit();
            }
        });


        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        SessionManager sessionManager = new SessionManager(getContext());
        String moduleName = sessionManager.getModuleName();
        String degree = sessionManager.getDegreeName();

        databaseReference.child("Module").child(degree).child(moduleName).child("Materials").addListenerForSingleValueEvent(new ValueEventListener() {
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

    }
}