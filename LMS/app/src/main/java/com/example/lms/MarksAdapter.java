package com.example.lms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MarksAdapter extends RecyclerView.Adapter<MarksAdapter.MarksViewHolder>{

    Context context;
    ArrayList<MarksModel> list;

    public MarksAdapter(Context context, ArrayList<MarksModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MarksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.mark_item, parent, false);

        return new MarksViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MarksViewHolder holder, int position) {
        MarksModel marksModel = list.get(position);

        holder.moduleName.setText(marksModel.getModuleName());
        holder.grade.setText(marksModel.getGrade());
        holder.marks.setText(marksModel.getMarks());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MarksViewHolder extends RecyclerView.ViewHolder {

        TextView grade, moduleName, marks;

        public MarksViewHolder(@NonNull View itemView) {
            super(itemView);

            moduleName = itemView.findViewById(R.id.marksModuleName);
            grade = itemView.findViewById(R.id.marksModuleGrade);
            marks = itemView.findViewById(R.id.marksModuleMarks);


        }
    }
}
