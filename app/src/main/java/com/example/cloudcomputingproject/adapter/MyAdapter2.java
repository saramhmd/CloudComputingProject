package com.example.cloudcomputingproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloudcomputingproject.R;
import com.example.cloudcomputingproject.model.SelectedTopics;
import java.util.List;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.ViewHolder> {
    private List<SelectedTopics> mData;
    Context context;

    public MyAdapter2(Context context, List<SelectedTopics> mData) {
        this.context = context;
        this.mData = mData;

    }

    @NonNull
    @Override
    public MyAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.selected_topic_item, parent, false);
        return new MyAdapter2.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter2.ViewHolder holder, int position) {
        SelectedTopics selectedTopics = mData.get(position);
//        holder.image.setText(selectedTopics.getImage());
        holder.advice.setText(selectedTopics.getAdvice());
        holder.topicName.setText(selectedTopics.getTopicName());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView advice;
        TextView topicName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.advice = itemView.findViewById(R.id.advice);
//            this.image = itemView.findViewById(R.id.imageView);
            this.topicName = itemView.findViewById(R.id.topicName);
        }
    }
}
