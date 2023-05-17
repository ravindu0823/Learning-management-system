package com.example.lms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ModuleAdapter extends RecyclerView.Adapter<ModuleAdapter.MyViewHolder> {

    Context context;
    ArrayList<Module> list;

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

        holder.moduleName.setText(module.getModuleName());
        holder.degreeProgram.setText(module.getDegreeProgramme());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView moduleName, degreeProgram;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            moduleName = itemView.findViewById(R.id.module_name);
            degreeProgram = itemView.findViewById(R.id.degree_program);
        }
    }

}
