package com.example.a11_9project.ui.lockout.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a11_9project.R;
import com.example.a11_9project.ui.lockout.Interfaces.AppOnClickListener;

public class AppViewHolder extends RecyclerView.ViewHolder {

    public ImageView icon_app, lock_app;
    public TextView name_app;

    private AppOnClickListener listener;

    public void setListener(AppOnClickListener listener){
        this.listener = listener;
    }

    public AppViewHolder(@NonNull View itemView) {
        super(itemView);

        icon_app = itemView.findViewById(R.id.icon_app);
        lock_app = itemView.findViewById(R.id.lock_app);
        name_app = itemView.findViewById(R.id.name_app);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.selectApp(getAdapterPosition());
            }
        });
    }
}
