package com.example.cloudcomputingproject.message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.recyclerview.widget.RecyclerView;
//;dcmskdmcklsd

import com.example.cloudcomputingproject.R;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {

    private final List<MessageList> messageLists;
    private final Context context;

    public MessageAdapter(List<MessageList> messageLists, Context context) {
        this.messageLists = messageLists;
        this.context = context;
    }

    @NonNull
    @Override
    public MessageAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item,null));
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return messageLists.size();
    }
    static class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView profilePic;
        private TextView name, lastMsg, unseenMsg;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePic = itemView.findViewById(R.id.profilePic);
            name = itemView.findViewById(R.id.name);
            lastMsg = itemView.findViewById(R.id.lastMsg);
            unseenMsg = itemView.findViewById(R.id.unseenMsg);
        }
    }
}
