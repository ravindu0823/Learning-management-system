package com.example.lms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Random;

public class ModuleAdapter extends RecyclerView.Adapter<ModuleAdapter.MyViewHolder> {

    Context context;
    ArrayList<Module> list;
    int[] myImageList = new int[]{R.drawable.pic01, R.drawable.pic02, R.drawable.pic03};

    public ModuleAdapter(Context context, ArrayList<Module> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.module_item, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Module module = list.get(position);

        holder.moduleName.setText(module.getModuleDescription());
        holder.degreeProgram.setText(module.getDegreeProgramme());

        Random ran = new Random();
        int randomNumber = ran.nextInt(myImageList.length);
        holder.moduleImage.setImageResource(myImageList[randomNumber]);

        holder.btnLectureMaterials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManager sessionManager = new SessionManager(context);
                sessionManager.saveModuleName(module);

                LectureMaterials lectureMaterials = new LectureMaterials();
                ((MainActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, lectureMaterials).commit();
            }
        });

        holder.btnAssignments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CourseWorks courseWorks = new CourseWorks();
                ((MainActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, courseWorks).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView moduleName, degreeProgram;
        Button btnLectureMaterials, btnAssignments;
        ImageView moduleImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            moduleName = itemView.findViewById(R.id.module_name);
            degreeProgram = itemView.findViewById(R.id.degree_program);
            btnLectureMaterials = itemView.findViewById(R.id.btn_Lecture_Materials);
            btnAssignments = itemView.findViewById(R.id.btn_Assignments);
            moduleImage = itemView.findViewById(R.id.module_image);
        }
    }

}
