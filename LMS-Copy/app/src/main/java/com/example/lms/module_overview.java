package com.example.lms;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.FragmentTransaction;


public class module_overview extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_module_overview, container, false);

        Button button1 = view.findViewById(R.id.button1);
        Button button2 = view.findViewById(R.id.button2);

        // Set a click listener for the button
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an instance of the fragment you want to replace


                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.remove(module_overview.this);

                // Add the new fragment
                Fragment lecture_material = new lecture_materials();
               /* transaction.add(R.id.drawer_layout, lecture_material);
                transaction.addToBackStack(null); // Optional: Add the transaction to the back stack
                transaction.commit();*/

                transaction.replace(container.getId(), lecture_material);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an instance of the fragment you want to replace


                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.remove(module_overview.this);

                // Add the new fragment
                Fragment course_works2 = new courceWork2();
               /* transaction.add(R.id.drawer_layout, course_works2);
                transaction.addToBackStack(null); // Optional: Add the transaction to the back stack
                transaction.commit();*/

                transaction.replace(container.getId(), course_works2);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        return view;

    }
}