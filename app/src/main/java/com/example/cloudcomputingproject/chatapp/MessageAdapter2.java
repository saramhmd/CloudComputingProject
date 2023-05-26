package com.example.cloudcomputingproject.chatapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloudcomputingproject.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class MessageAdapter2 extends RecyclerView.Adapter<MessageAdapter2.MessageViewHolder> {
    private List<Messages> userMessageList;
    private FirebaseAuth mAuth;

    public MessageAdapter2(List<Messages> userMessageList) {
        this.userMessageList = userMessageList;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_messages_layout, parent, false);
        MessageViewHolder messageViewHolder = new MessageViewHolder(view);
        mAuth = FirebaseAuth.getInstance();

        return messageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MessageViewHolder holder, final int position) {
        String messageSenderId = mAuth.getCurrentUser().getUid();
        final Messages messages = userMessageList.get(position);

        String fromMessageType = messages.getType();

        holder.receivermessagetext.setVisibility(View.GONE);
        holder.sendermessagetext.setVisibility(View.GONE);

        String fromUserId = messages.getFrom();
        if (fromUserId != null && fromUserId.equals(messageSenderId)) {
            holder.sendermessagetext.setVisibility(View.VISIBLE);
            holder.sendermessagetext.setBackgroundResource(R.drawable.sender_message_layout);
            holder.sendermessagetext.setText(messages.getMessage() + "\n \n" + messages.getTime() + " - " + messages.getDate());
        } else {
            holder.receivermessagetext.setVisibility(View.VISIBLE);
            holder.receivermessagetext.setBackgroundResource(R.drawable.receiver_message_layout);
            holder.receivermessagetext.setText(messages.getMessage() + "\n \n" + messages.getTime() + " - " + messages.getDate());
        }

    }

    @Override
    public int getItemCount() {
        return userMessageList.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        public TextView sendermessagetext, receivermessagetext;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            sendermessagetext = itemView.findViewById(R.id.sender_message_text);
            receivermessagetext = itemView.findViewById(R.id.receiver_message_text);
        }
    }
}
