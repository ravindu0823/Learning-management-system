package com.example.lms;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class admin_shedule extends Fragment {
    Button excelButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shedule, container, false);


        Button buttonSchedule = view.findViewById(R.id.button1_shedule);
        Button buttonSchedule2 = view.findViewById(R.id.button2_shedule);
        buttonSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://nsbm365-my.sharepoint.com/:x:/g/personal/srdkumara_students_nsbm_ac_lk/Ecj_kLloKO1GmAqv6CHlQgUBu-7qW56V0WTPLuJCa70yDw?e=DeGjvs"; // Replace with your desired URL

                // Create an intent to open the URL
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));

                // Start the activity with the intent
                startActivity(intent);


            }
        });

        buttonSchedule2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://nsbm365-my.sharepoint.com/:x:/g/personal/srdkumara_students_nsbm_ac_lk/EelSQXlClpFEoE_vVhL44owBRW2oXcWcHjeyz8p4r-dpmQ?e=dmi2Jl"; // Replace with your desired URL

                // Create an intent to open the URL
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));

                // Start the activity with the intent
                startActivity(intent);
            }
        });


        return view;
    }

}