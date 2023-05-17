package com.example.lms;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MaterialAdapter extends RecyclerView.Adapter<MaterialAdapter.MyViewHolder> {

    Context context;
    ArrayList<Material> list;

    public MaterialAdapter(Context context, ArrayList<Material> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.material_item, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Material material = list.get(position);
        holder.materialName.setText(material.getMaterialName());
        holder.btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadFile(material.getMaterialUrl());
            }
        });

    }

    private void downloadFile(String url) {
        downloadFiles(context, "myFile", ".pdf", DIRECTORY_DOWNLOADS, url);
    }

    public void downloadFiles(Context context, String fileName, String fileExtension, String destinationDirectory, String url) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, destinationDirectory, fileName + fileExtension);

        downloadManager.enqueue(request);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView materialName;
        Button btnDownload;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            materialName = itemView.findViewById(R.id.material_name);
            btnDownload = itemView.findViewById(R.id.btnDownloadMaterial);
        }
    }
}
