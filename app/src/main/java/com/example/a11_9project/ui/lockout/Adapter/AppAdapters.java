package com.example.a11_9project.ui.lockout.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a11_9project.R;
import com.example.a11_9project.ui.lockout.Model.AppItem;
import com.example.a11_9project.ui.lockout.ViewHolder.AppViewHolder;

import java.util.List;

public class AppAdapters extends RecyclerView.Adapter<AppViewHolder> {

    private Context mContext;
    private List<AppItem> appItemList;

    public AppAdapters(Context mContext, List<AppItem> appItemList) {
        this.mContext = mContext;
        this.appItemList = appItemList;
    }

    @NonNull
    @Override
    public AppViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_apps, parent, false);

        return new AppViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewHolder holder, int position) {

        holder.name_app.setText(appItemList.get(position).getName());
        holder.icon_app.setImageDrawable(appItemList.get(position).getIcon());
    }

    @Override
    public int getItemCount() {
        return appItemList.size();
    }
}
