package com.example.cloudcomputingproject.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloudcomputingproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatsFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private DatabaseReference chatRef, userRef;
    private FirebaseAuth mAuth;
    private String userID;

    public ChatsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_chats, container, false);
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        chatRef = FirebaseDatabase.getInstance().getReference().child("Contacts").child(userID);
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");

        recyclerView = view.findViewById(R.id.chats_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Contacts> options = new FirebaseRecyclerOptions.Builder<Contacts>()
                .setQuery(chatRef, Contacts.class).build();

        FirebaseRecyclerAdapter<Contacts, ChatViewHolder> adapter = new FirebaseRecyclerAdapter<Contacts, ChatViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final ChatViewHolder holder, int position, @NonNull Contacts model) {
                final String userid = getRef(position).getKey();
                userRef.child(userid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            final String name = dataSnapshot.child("name").getValue().toString();
                            final String status = dataSnapshot.child("status").getValue().toString();
//                            final String date = dataSnapshot.child("date").getValue().toString();
//                            final String time = dataSnapshot.child("time").getValue().toString();

                            holder.username.setText(name);
                            holder.userstatus.setText(status);
//                            holder.userdate.setText(date);
//                            holder.usertime.setText(time);

                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent chatIntent = new Intent(getContext(), ChatActivity.class);
                                    chatIntent.putExtra("visit_user_id", userid);
                                    chatIntent.putExtra("visit_user_name", name);
//                                    chatIntent.putExtra("visit_user_name", date);
//                                    chatIntent.putExtra("visit_user_name", time);
                                    startActivity(chatIntent);
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @NonNull
            @Override
            public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(getContext()).inflate(R.layout.users_display_layout, parent, false);
                ChatViewHolder chatViewHolder = new ChatViewHolder(view);
                return chatViewHolder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {

        TextView username, userstatus , userdate , usertime ;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.users_profile_name);
            userstatus = itemView.findViewById(R.id.users_status);
            userdate = itemView.findViewById(R.id.userdate);
            usertime = itemView.findViewById(R.id.usertime);
        }
    }
}
