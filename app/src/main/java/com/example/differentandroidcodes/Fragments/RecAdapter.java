package com.example.differentandroidcodes.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.differentandroidcodes.R;
import com.example.differentandroidcodes.Holder.RecViewHolder;
import com.example.differentandroidcodes.Listener.onSelectListener;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.util.List;

public class RecAdapter extends RecyclerView.Adapter<RecViewHolder> {

    private final Context context;
    private final List<File> fileList;
    private final onSelectListener listener;

    public RecAdapter(Context context, List<File> fileList,onSelectListener listener) {
        this.context = context;
        this.fileList = fileList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        return new RecViewHolder(LayoutInflater.from(context).inflate(R.layout.custom_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull  RecViewHolder holder, int position) {
        holder.tvName.setText(fileList.get(position).getName());
        holder.tvName.setSelected(true);

        holder.container.setOnClickListener(v -> listener.onSelected(fileList.get(position)));
        holder.imageView.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(context, v);
            popupMenu.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
            popupMenu.show();

            popupMenu.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.delete) {
                    deleteFile(position, v);
                }
                if (item.getItemId() == R.id.share) {
                    Uri uri = Uri.parse(fileList.get(position).getAbsolutePath());
                    Intent share = new Intent(Intent.ACTION_SEND);
                    share.setType("audio/*");
                    share.putExtra(Intent.EXTRA_STREAM, uri);
                    context.startActivity(Intent.createChooser(share, "Share Sound File"));
                }
                return true;
            });
        });
    }

    @Override
    public int getItemCount() {
        return fileList.size();
    }

    private void deleteFile(int position, View view){
        Uri contentUri = FileProvider.getUriForFile(view.getContext(), view.getContext().getApplicationContext().getPackageName() + ".provider", fileList.get(position));
        File file = new File(fileList.get(position).getPath());
        boolean deleted = file.delete();
        if(deleted){
            context.getContentResolver().delete(contentUri,null,null);
            fileList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position,fileList.size());
            Snackbar.make(view,"File Deleted : ",Snackbar.LENGTH_LONG)
                    .show();
        }
        else{
            notifyItemRangeChanged(position,fileList.size());
            Snackbar.make(view,"File Can't be Deleted : ",Snackbar.LENGTH_LONG)
                    .show();
        }
    }

}
