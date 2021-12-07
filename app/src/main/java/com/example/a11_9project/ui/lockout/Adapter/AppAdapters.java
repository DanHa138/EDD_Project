package com.example.a11_9project.ui.lockout.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a11_9project.R;
import com.example.a11_9project.ui.lockout.Interfaces.AppOnClickListener;
import com.example.a11_9project.ui.lockout.Model.AppItem;
import com.example.a11_9project.ui.lockout.Utils.Utils;
import com.example.a11_9project.ui.lockout.ViewHolder.AppViewHolder;

import java.util.List;

public class AppAdapters extends RecyclerView.Adapter<AppViewHolder> {

    private Context mContext;
    private List<AppItem> appItemList;
    private Utils utils;

    public AppAdapters(Context mContext, List<AppItem> appItemList) {
        this.mContext = mContext;
        this.appItemList = appItemList;
        this.utils = new Utils(mContext);
    }

    @NonNull
    @Override
    public AppViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_apps, parent, false);

        return new AppViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AppViewHolder holder, int position) {

        holder.name_app.setText(appItemList.get(position).getName());
        holder.icon_app.setImageDrawable(appItemList.get(position).getIcon());

        String pk = appItemList.get(position).getPackageName();

        if(utils.isBlock(pk)){
            holder.lock_app.setImageResource(R.drawable.ic_outline_lock);
        }
        else{
            holder.lock_app.setImageResource(R.drawable.ic_baseline_lock_open);
        }

        holder.setListener(new AppOnClickListener() {
            @Override
            public void selectApp(int pos) {
                if(utils.isBlock(pk)){
                    holder.lock_app.setImageResource(R.drawable.ic_baseline_lock_open);
                    utils.unlock(pk);
                }
                else{
                    holder.lock_app.setImageResource(R.drawable.ic_outline_lock);
                    utils.lock(pk);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return appItemList.size();
    }
}
