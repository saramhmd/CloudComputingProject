package com.example.cloudcomputingproject.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloudcomputingproject.R;
import com.example.cloudcomputingproject.TopicsAvailableActivity;
import com.example.cloudcomputingproject.model.SelectedTopics;
import com.example.cloudcomputingproject.model.Task;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Task> tasks;
    ArrayList<Task> arrayListCopy;

    public MyAdapter(Context context, List<Task> tasks) {
        this.tasks = tasks;
        this.arrayListCopy = new ArrayList<>();
        arrayListCopy.addAll(tasks);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.checkBox.setText(task.getTitle());
        holder.checkBox.setChecked(task.isChecked());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CheckBox checkBox;

        public ViewHolder(View view) {
            super(view);
            checkBox = view.findViewById(R.id.checkBox);
        }
//        public void bind(Task task, AdapterView.OnItemClickListener listener) {
//            checkBox.setText(task.getTitle());
//            checkBox.setChecked(task.isChecked());
//
//            checkBox.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    boolean isChecked = checkBox.isChecked();
//                    listener.onItemClick(task, isChecked);
//                }
    }

    public void filter(CharSequence charSequence){
        ArrayList<Task> tempArrayList = new ArrayList<>();
        if (!TextUtils.isEmpty(charSequence)){
            for (Task task: tasks){
                if (task.getTitle().toLowerCase().contains(charSequence)){
                    tempArrayList.add(task);
                }
            }
        }else {
            tempArrayList.addAll(arrayListCopy);
        }
        tasks.clear();
        tasks.addAll(tempArrayList);
        notifyDataSetChanged();
        tempArrayList.clear();
    }
}

