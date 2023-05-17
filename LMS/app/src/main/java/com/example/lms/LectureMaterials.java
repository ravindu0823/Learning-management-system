package com.example.lms;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class LectureMaterials extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lecture_materials, container, false);

        SessionManager sessionManager = new SessionManager(getContext());
        String moduleName = sessionManager.getModuleName();

        Toast.makeText(getContext(), moduleName, Toast.LENGTH_SHORT).show();

        return view;

    }
}