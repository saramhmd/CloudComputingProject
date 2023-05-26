package com.example.cloudcomputingproject.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cloudcomputingproject.Doctor.DoctorHome;
import com.example.cloudcomputingproject.R;
import com.example.cloudcomputingproject.model.SelectedTopics;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {


    private List<SelectedTopics> mData;
    private LayoutInflater mInflater;
    private HomeAdapter.ItemClickListener mClickListener;

    public void setClickListener(DoctorHome itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    Context context;
    public interface ItemClickListener {
        void onItemClick(SelectedTopics selectedTopic, String id);
    }

    public HomeAdapter(Context context, List<SelectedTopics> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;

        this.context = context;
        if (mData == null){
            mData = new ArrayList<>();
        }else {
            this.mData = mData;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.topic_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        SelectedTopics selectedTopics = mData.get(position);
//        RequestOptions requestOptions = new RequestOptions()
//                .diskCacheStrategy(DiskCacheStrategy.ALL);
//        holder.tvTopicDetailes.setText(selectedTopics.getAdvice());
        holder.tvTopicName.setText(selectedTopics.getTopicName());
        Glide.with(context).load(selectedTopics.getImageUri())
                .into(holder.topicPhoto);
//        holder.edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mClickListener.onItemClick(holder.getAdapterPosition());
//
//            }
//        });
//        holder.visible.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                mClickListener.onItemClick(holder.getAdapterPosition(), mData.get(position).id);
//
//            }
//        });
//        holder.delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                mClickListener.onItemClick(holder.getAdapterPosition(), mData.get(position).id);
//
//            }
//        });

//        holder.card.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                itemClickListener2.onItemClick2(holder.getAdapterPosition());
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvTopicName,tvTopicDetailes;
        public ImageView topicPhoto;
        public CardView card;
        private String id;

        ViewHolder(View itemView) {
            super(itemView);
            this.tvTopicName = itemView.findViewById(R.id.tvTopicName);
//            this.tvTopicDetailes = itemView.findViewById(R.id.tvTopicDetailes);
//            this.delete = itemView.findViewById(R.id.delete);
//            this.edit = itemView.findViewById(R.id.edit);
//            this.visible = itemView.findViewById(R.id.visibility);
            this.topicPhoto = itemView.findViewById(R.id.topicImage);
            this.card = itemView.findViewById(R.id.card);
            itemView.setOnClickListener(this);
        }
        public void setId(String id) {
            this.id = id;
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mClickListener.onItemClick(mData.get(position), id);
                }
            }
        }

    }

    SelectedTopics getItem(int id) {
        return mData.get(id);
    }

//    void setClickListener(ItemClickListener itemClickListener) {
//        this.mClickListener = itemClickListener;
//    }
//
//    public interface ItemClickListener {
//        void onItemClick(int position);
//    }
//
//    public interface ItemClickListener2{
//        void onItemClick2(int position);
//}
}