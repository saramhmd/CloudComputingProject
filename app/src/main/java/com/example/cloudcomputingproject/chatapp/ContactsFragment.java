package com.example.cloudcomputingproject.chatapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloudcomputingproject.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.sql.Time;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsFragment extends Fragment {

    private View view;
    private RecyclerView myRecyclerView;
    private DatabaseReference contactsRef, userRef;
    private FirebaseAuth mAuth;
    private String currentUserId;

    public ContactsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_contacts, container, false);
        myRecyclerView = view.findViewById(R.id.contacts_recyclerview_list);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();

        contactsRef = FirebaseDatabase.getInstance().getReference().child("Contacts").child(currentUserId);
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Contacts> options = new FirebaseRecyclerOptions.Builder<Contacts>()
                .setQuery(contactsRef, Contacts.class)
                .build();

        FirebaseRecyclerAdapter<Contacts, ContactsViewHolder> adapter = new FirebaseRecyclerAdapter<Contacts, ContactsViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull final ContactsViewHolder holder, int position, @NonNull Contacts model) {
                String userId = getRef(position).getKey();
                userRef.child(userId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            if (dataSnapshot.child("userState").hasChild("state")) {
                                String state = dataSnapshot.child("userState").child("state").getValue().toString();
                                if (state.equals("online")) {
                                    holder.onlineIcon.setVisibility(View.VISIBLE);
                                } else if (state.equals("offline")) {
                                    holder.onlineIcon.setVisibility(View.INVISIBLE);
                                }
                            } else {
                                holder.onlineIcon.setVisibility(View.INVISIBLE);
                            }

                            String name = dataSnapshot.child("name").getValue().toString();
                            String status = dataSnapshot.child("status").getValue().toString();
//                            String date = dataSnapshot.child("date").getValue().toString();
//                            String time = dataSnapshot.child("time").getValue().toString();



//                            if (dataSnapshot.child("userState").hasChild("date")) {
//                                String date = dataSnapshot.child("userState").child("date").getValue().toString();
//                                holder.userdate.setText(date);
//                            }
//
//                            if (dataSnapshot.child("userState").hasChild("time")) {
//                                String time = dataSnapshot.child("userState").child("time").getValue().toString();
//                                holder.usertime.setText(time);
//                            }

                            holder.username.setText(name);
                            holder.userstatus.setText(status);
//                            holder.userdate.setText(date);
//                            holder.usertime.setText(time);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @NonNull
            @Override
            public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_display_layout, parent, false);
                ContactsViewHolder viewHolder = new ContactsViewHolder(view);
                return viewHolder;
            }
        };

        myRecyclerView.setAdapter(adapter);
        adapter.startListening();


    }

    public static class ContactsViewHolder extends RecyclerView.ViewHolder {
        TextView username, userstatus , userdate , usertime ;
        ImageView onlineIcon;

        public ContactsViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.users_profile_name);
            userstatus = itemView.findViewById(R.id.users_status);
            onlineIcon = itemView.findViewById(R.id.users_online_status);
            userdate = itemView.findViewById(R.id.userdate);
            usertime = itemView.findViewById(R.id.usertime);
        }
    }
}
