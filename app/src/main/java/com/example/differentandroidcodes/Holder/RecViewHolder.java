package com.example.differentandroidcodes.Holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.differentandroidcodes.R;

public class RecViewHolder extends RecyclerView.ViewHolder {

    public TextView tvName;
    public ImageView imageView;
    public RelativeLayout container;

    public RecViewHolder(@NonNull  View itemView) {
        super(itemView);

        tvName = itemView.findViewById(R.id.txtName);
        container = itemView.findViewById(R.id.container);
        imageView = itemView.findViewById(R.id.menu_more);
    }
}
