package com.example.jose.actividad_firebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.jose.actividad_firebase.Model.Item;
import com.example.jose.actividad_firebase.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Admin_Main_Activity extends AppCompatActivity {


    //BDDD
    DatabaseReference reference;

    RecyclerView recyclerView;
    RecyclerAdapter_Users adapter_users;
    LinearLayoutManager linearLayoutManager;
    ArrayList<User> users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        users = new ArrayList();
        setContentView(R.layout.activity_admin__main_);
        getSupportActionBar().setTitle("Servipop(ADMIN)");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerUsers);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter_users = new RecyclerAdapter_Users(users, this);


    }

    private void inflateAdapterUsers() {

        reference = FirebaseDatabase.getInstance().getReference("users");
        users.clear();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    User user = dataSnapshot1.getValue(User.class);
                    users.add(user);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        adapter_users.updateAdapter(users);
        recyclerView.setAdapter(adapter_users);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu_admin_main, menu);
        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ShowUsers:
                inflateAdapterUsers();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
