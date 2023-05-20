package com.example.cloudcomputingproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import com.bumptech.glide.Glide;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.cloudcomputingproject.R;
import com.example.cloudcomputingproject.model.SelectedTopics;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.ViewHolder> {
    private List<SelectedTopics> mmData;
    Context context;

    public MyAdapter2(Context context, List<SelectedTopics> mData) {
        this.context = context;
        if (mData == null){
            mmData = new ArrayList<>();
        }else {
            this.mmData = mData;
        }

    }

    @Override
    public int getItemCount() {
        return mmData.size();
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
        SelectedTopics selectedTopics = mmData.get(position);
        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        holder.advice.setText(selectedTopics.getAdvice());
        holder.topicName.setText(selectedTopics.getTopicName());
        Glide.with(context).load(selectedTopics.getImageUri())
                .into(holder.image);

    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView advice;
        TextView topicName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.advice = itemView.findViewById(R.id.advice);
            image = (ImageView) itemView.findViewById(R.id.imageView2);
            this.topicName = itemView.findViewById(R.id.topicName);
        }
    }
}
