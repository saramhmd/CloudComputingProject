package com.example.cloudcomputingproject.chat;

import static android.provider.ContactsContract.CommonDataKinds.Nickname.TYPE_DEFAULT;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.cloudcomputingproject.R;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChatAdapter extends ArrayAdapter<ChatMessage> {
        private List<ChatMessage> messageList;
        private LayoutInflater inflater;

        private static final int TYPE_SENDER = 0;
        private static final int TYPE_RECIPIENT = 1;

        public ChatAdapter(Context context, List<ChatMessage> messageList) {
                super(context, R.layout.item_message, messageList);
                this.inflater = LayoutInflater.from(context);
                this.messageList = messageList;
        }

        @Override
        public int getCount() {
                return messageList.size();
        }

        @Override
        public ChatMessage getItem(int position) {
                return messageList.get(position);
        }

        @Override
        public int getViewTypeCount() {
                return 2;
        }

        public int getItemViewType(int position) {
                ChatMessage message = messageList.get(position);
                if (message != null && message.getSenderId() != null && FirebaseAuth.getInstance().getCurrentUser() != null) {
                        if (message.getSenderId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                                return TYPE_SENDER;
                        } else {
                                return TYPE_RECIPIENT;
                        }
                } else {
                        // تعامل مع القيم الـ null أو الحالات الاستثنائية
                        return TYPE_DEFAULT;
                }
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder viewHolder;
                int viewType = getItemViewType(position);

                if (convertView == null) {
                        viewHolder = new ViewHolder();

                        if (viewType == TYPE_SENDER) {
                                convertView = inflater.inflate(R.layout.item_message_sender, parent, false);
                        } else {
                                convertView = inflater.inflate(R.layout.item_message_recipient, parent, false);
                        }

                        viewHolder.messageTextView = convertView.findViewById(R.id.text_view_message);
                        viewHolder.timeTextView = convertView.findViewById(R.id.text_view_time);

                        convertView.setTag(viewHolder);
                } else {
                        viewHolder = (ViewHolder) convertView.getTag();
                }

                ChatMessage message = messageList.get(position);
                viewHolder.messageTextView.setText(message.getMessageText());
                SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
                String time = dateFormat.format(new Date(message.getTimestamp()));
                viewHolder.timeTextView.setText(time);

                return convertView;
        }

        private static class ViewHolder {
                TextView messageTextView;
                TextView timeTextView;
        }
}
