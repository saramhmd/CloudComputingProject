package com.example.cloudcomputingproject.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cloudcomputingproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    private ListView listViewMessages;
    private EditText editTextMessage;
    private Button buttonSend;
    private TextView textViewRecipientEmail;
    private String recipientEmail;
    private String recipientId;
    private String senderId;
    private List<ChatMessage> messageList;
    private ChatAdapter messageAdapter;
    private DatabaseReference messagesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        listViewMessages = findViewById(R.id.list_view_messages);
        editTextMessage = findViewById(R.id.edit_text_message);
        buttonSend = findViewById(R.id.button_send);
        textViewRecipientEmail = findViewById(R.id.text_view_recipient_email);

        recipientEmail = getIntent().getStringExtra("accountEmail");
        recipientId = getIntent().getStringExtra("accountId");
        senderId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        textViewRecipientEmail.setText(recipientEmail);

        messageList = new ArrayList<>();
        messageAdapter = new ChatAdapter(this, messageList);
        listViewMessages.setAdapter(messageAdapter);

        messagesRef = FirebaseDatabase.getInstance().getReference().child("Messages")
                .child(senderId).child(recipientId);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        messagesRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                ChatMessage message = dataSnapshot.getValue(ChatMessage.class);
                messageList.add(message);
                messageAdapter.notifyDataSetChanged();
                scrollToBottom();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                // Not needed in this example
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                // Not needed in this example
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                // Not needed in this example
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Not needed in this example
            }
        });
    }

    private void sendMessage() {
        String messageText = editTextMessage.getText().toString().trim();

        if (!messageText.isEmpty()) {
            DatabaseReference senderRef = messagesRef.child(senderId).push();
            DatabaseReference recipientRef = messagesRef.child(recipientId).push();

            String senderMessageId = senderRef.getKey();
            String recipientMessageId = recipientRef.getKey();

            long timestamp = System.currentTimeMillis();

            ChatMessage senderMessage = new ChatMessage(senderId, recipientId, messageText, timestamp);
            ChatMessage recipientMessage = new ChatMessage(senderId, recipientId, messageText, timestamp);

            Map<String, Object> messageUpdates = new HashMap<>();
            messageUpdates.put("/" + senderId + "/" + senderMessageId, senderMessage);
            messageUpdates.put("/" + recipientId + "/" + recipientMessageId, recipientMessage);

            messagesRef.updateChildren(messageUpdates, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError == null) {
                        editTextMessage.setText("");
                        scrollToBottom();
                    } else {
                        Toast.makeText(ChatActivity.this, "Failed to send message", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void scrollToBottom() {
        listViewMessages.post(new Runnable() {
            @Override
            public void run() {
                listViewMessages.setSelection(listViewMessages.getAdapter().getCount() - 1);
            }
        });
    }
}
