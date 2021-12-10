package com.example.a11_9project.ui.schedule.Adapter;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a11_9project.R;
import com.example.a11_9project.ui.schedule.AddNewTask;
import com.example.a11_9project.ui.schedule.Model.ToDoModel;
import com.example.a11_9project.ui.schedule.ScheduleFragment;
import com.example.a11_9project.ui.schedule.Utils.DatabaseHandler;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {

    //Declares lists of tasks
    private List<ToDoModel> todolist;
    private ScheduleFragment activity;
    private DatabaseHandler db;

    //Constructor
    public ToDoAdapter(DatabaseHandler db,ScheduleFragment activity){
        this.db = db;
        this.activity = activity;
    }

    //Method that inflates the task into the ViewHolder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_layout, parent, false);
        return new ViewHolder(itemview);
    }

    //Method that gets the position of the ViewHolder and gets the task located at that position
    //Sets the text and checks the box depending on what is in that position
    public void onBindViewHolder(ViewHolder holder, int position){
        db.openDatabase();
        ToDoModel item = todolist.get(position);
        holder.task.setText(item.getTask());
        holder.task.setChecked(toBoolean(item.getStatus()));
        holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    db.updateStatus(item.getId(), 1);
                }
                else{
                    db.updateStatus(item.getId(), 0);
                }
            }
        });
    }

    //Returns the size of our List
    public int getItemCount(){
        return todolist.size();
    }

    //Converts to boolean
    private boolean toBoolean(int n){
        return n != 0;
    }

    public void setTask(List<ToDoModel> todolist){
        this.todolist = todolist;
        notifyDataSetChanged();
    }

    public Context getContext(){
        return activity;
    }

    public void deleteItem(int position){
        ToDoModel item = todolist.get(position);
        db.deleteTask(item.getId());
        todolist.remove(position);
        notifyItemRemoved(position);
    }

    public void editItem(int position){
        ToDoModel item = todolist.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("task", item.getTask());
        AddNewTask fragment = new AddNewTask();
        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(), AddNewTask.TAG);
                //.getSupportFragmentManager()
    }

    //Declares ViewHolder class with checkboxes as tasks
    public static class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox task;

        //task declares itself as a checkbox
        ViewHolder(View view){
            super(view);
            task = view.findViewById(R.id.todoCheckBox);
        }
    }
}
