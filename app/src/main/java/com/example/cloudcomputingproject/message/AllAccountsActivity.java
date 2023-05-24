package com.example.cloudcomputingproject.message;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.cloudcomputingproject.Patient.adapter.HomePatientActivity;
import com.example.cloudcomputingproject.Patient.adapter.MsgActivity;
import com.example.cloudcomputingproject.Patient.adapter.NotificationPatientActivity;
import com.example.cloudcomputingproject.Patient.adapter.ProfileActivity;
import com.example.cloudcomputingproject.Patient.adapter.User;
import com.example.cloudcomputingproject.R;
import com.example.cloudcomputingproject.chat.ChatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AllAccountsActivity extends AppCompatActivity {

    private ListView listViewAccounts;
    private List<User> userList;
    private ArrayAdapter<String> arrayAdapter;
    private List<String> accountsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_list);

        BottomNavigationView nav = findViewById(R.id.bottomNavigationViewPatient);
        nav.setSelectedItemId(R.id.msgPatient);

        nav.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.homePatient:
                    startActivity(new Intent(AllAccountsActivity.this, HomePatientActivity.class));
                    return true;

                case R.id.profilePatient:
                    startActivity(new Intent(AllAccountsActivity.this, ProfileActivity.class));
                    return true;

                case R.id.msgPatient:
                    return true;

                case R.id.notificationPatient:
                    startActivity(new Intent(AllAccountsActivity.this, NotificationPatientActivity.class));
                    return true;

                default:
                    return false;
            }


        });

        listViewAccounts = findViewById(R.id.list_view_accounts);
        userList = new ArrayList<>();
        accountsList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, accountsList);

        listViewAccounts.setAdapter(arrayAdapter);
        listViewAccounts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // استرجاع معلومات الحساب المحدد
                User selectedUser = userList.get(position);
                String selectedEmail = selectedUser.getFullName();
                String selectedId = selectedUser.getUid();

                // إنشاء Intent لتمرير بيانات الحساب إلى ChatActivity
                Intent intent = new Intent(AllAccountsActivity.this, ChatActivity.class);
                intent.putExtra("accountEmail", selectedEmail);
                intent.putExtra("accountId", selectedId);
                startActivity(intent);
            }
        });

        // استعلام قاعدة البيانات للحصول على جميع الحسابات
        Query query = FirebaseDatabase.getInstance().getReference().child("Users");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        User user = snapshot.getValue(User.class);
                        userList.add(user);
                        accountsList.add(user.getFullName());
                    }
                    arrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // معالجة خطأ قاعدة البيانات هنا
            }
        });
    }
}
