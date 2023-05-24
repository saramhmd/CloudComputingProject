package com.example.cloudcomputingproject.Patient.adapter.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.cloudcomputingproject.R;
import com.example.cloudcomputingproject.model.SelectedTopics;
import java.util.ArrayList;
import java.util.List;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.ViewHolder> {
    private List<SelectedTopics> mmData;
    private ItemClickListener mClickListener;

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(SelectedTopics selectedTopic, String id);
    }


    Context context;

    public MyAdapter2(Context context, List<SelectedTopics> mData) {
        this.context = context;
        if (mData == null){
            mmData = new ArrayList<>();
        }else {
            this.mmData = mData;
        }
    }

    @NonNull
    @Override
    public MyAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.selected_topic_item, parent, false);
        return new MyAdapter2.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter2.ViewHolder holder,  @SuppressLint("RecyclerView") final int position) {
        SelectedTopics selectedTopics = mmData.get(position);
        holder.advice.setText(selectedTopics.getAdvice());
        holder.topicName.setText(selectedTopics.getTopicName());
        Glide.with(context).load(selectedTopics.getImageUri())
                .into(holder.image);
        holder.setId(selectedTopics.getId());
    }

    @Override
    public int getItemCount() {
        return mmData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private String id;
        ImageView image;
        TextView advice;
        TextView topicName;
        public CardView card;
        ImageButton playButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.advice = itemView.findViewById(R.id.advice);
            image = (ImageView) itemView.findViewById(R.id.imageView2);
            this.topicName = itemView.findViewById(R.id.topicName);
            playButton = itemView.findViewById(R.id.playerView);
            this.card = itemView.findViewById(R.id.card);

        }
        public void setId(String id) {
            this.id = id;
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mClickListener.onItemClick(mmData.get(position), id);
                }
            }
        }
    }
}
