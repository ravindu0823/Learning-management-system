package com.example.lms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Random;

public class LectureModuleAdapter extends RecyclerView.Adapter<LectureModuleAdapter.LectureModuleViewHolder> {

    Context context;
    ArrayList<Module> list;
    int[] myImageList = new int[]{R.drawable.artificialinterlligence, R.drawable.big_data_analytics, R.drawable.datbase_management_system, R.drawable.object_oriented, R.drawable.web_development};

    public LectureModuleAdapter(Context context, ArrayList<Module> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public LectureModuleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.module_item, parent, false);

        return new LectureModuleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LectureModuleViewHolder holder, int position) {
        Module module = list.get(position);

        holder.moduleName.setText(module.getModuleDescription());
        holder.degreeProgram.setText(module.getDegreeProgramme());

        /*Random ran = new Random();
        int randomNumber = ran.nextInt(myImageList.length);
        holder.moduleImage.setImageResource(myImageList[randomNumber]);*/

        holder.moduleImage.setImageResource(myImageList[position]);

        holder.btnLectureMaterials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManager sessionManager = new SessionManager(context);
                sessionManager.saveModuleName(module);

                AdminLectureMaterials adminLectureMaterials = new AdminLectureMaterials();
                ((activity_main_admin) context).getSupportFragmentManager().beginTransaction().replace(R.id.admin_fragment_container, adminLectureMaterials).commit();
            }
        });

        holder.btnAssignments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManager sessionManager = new SessionManager(context);
                sessionManager.saveModuleName(module);

                AdminCourseWorks adminCourseWorks = new AdminCourseWorks();
                ((activity_main_admin) context).getSupportFragmentManager().beginTransaction().replace(R.id.admin_fragment_container, adminCourseWorks).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class LectureModuleViewHolder extends RecyclerView.ViewHolder {

        TextView moduleName, degreeProgram;
        Button btnLectureMaterials, btnAssignments;
        ImageView moduleImage;

        public LectureModuleViewHolder(@NonNull View itemView) {
            super(itemView);

            moduleName = itemView.findViewById(R.id.module_name);
            degreeProgram = itemView.findViewById(R.id.degree_program);
            btnLectureMaterials = itemView.findViewById(R.id.btn_Lecture_Materials);
            btnAssignments = itemView.findViewById(R.id.btn_Assignments);
            moduleImage = itemView.findViewById(R.id.module_image);
        }
    }
}
