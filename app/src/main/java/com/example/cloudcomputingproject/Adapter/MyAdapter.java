package com.example.cloudcomputingproject.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloudcomputingproject.R;
import com.example.cloudcomputingproject.model.Task;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Task> tasks;
    ArrayList<Task> arrayListCopy;
    Context context;

    public MyAdapter(Context context, List<Task> tasks) {
        this.tasks = tasks;
        this.arrayListCopy = new ArrayList<>();
        this.context = context;
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
        DocumentReference docRef = FirebaseFirestore.getInstance().collection("topics").document(task.getId());
        holder.checkBox.setText(task.getTitle());
        holder.checkBox.setChecked(task.isChecked());

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                docRef.update("isChecked", isChecked);
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void unused) {
//                                Toast.makeText(context, "Boolean value updated successfully", Toast.LENGTH_SHORT).show();
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Toast.makeText(context, "Boolean value cannot updated", Toast.LENGTH_SHORT).show();
//
//                            }
//                        });
            }
        });

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

