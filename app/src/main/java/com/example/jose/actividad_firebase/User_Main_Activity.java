package com.example.jose.actividad_firebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jose.actividad_firebase.Model.Item;
import com.example.jose.actividad_firebase.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class User_Main_Activity extends AppCompatActivity {

    //Finals
    private final static int ADD_ITEM_ACTIVITY = 2;

    //Views
    Button btn_addItem;
    CheckBox checkBox_MyItems;
    Spinner categorySpinner;
    TextView userNameTitle;
    RecyclerView recyclerView;
    RecyclerAdapter_Items adapter;
    LinearLayoutManager linearLayoutManager;

    //BBDD
    DatabaseReference userReference;
    DatabaseReference allItems;
    String userUID ;

    //class
    User currentUser;

    //ArrayList
    ArrayList<Item> items;
    ArrayList <Item> filteredItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__main_);
        getSupportActionBar().setTitle("Servipop");

        //Initiations

        btn_addItem = (Button) findViewById(R.id.btnAddItem);
        checkBox_MyItems = (CheckBox) findViewById(R.id.checkBoxMyItems);
        categorySpinner = (Spinner) findViewById(R.id.categorySpinner);
        userUID = getIntent().getExtras().getString("userUID");
        userNameTitle = (TextView) findViewById(R.id.userNameTitle);
        userReference = FirebaseDatabase.getInstance().getReference("users/"+userUID);
        allItems = FirebaseDatabase.getInstance().getReference("items");
        recyclerView = (RecyclerView) findViewById(R.id.recycler_userItems);
        items = new ArrayList();
        filteredItems = new ArrayList();
        inflateRecyclerView();



        //spinner inflate
        String[] arraySpinner = new String[] {
                "Todo", "Tecnologia", "Coches", "Hogar"
        };
        final ArrayAdapter<String> adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        categorySpinner.setAdapter(adapter);


        //Listeners


        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentUser = dataSnapshot.getValue(User.class);
                userNameTitle.setText(currentUser.getUserName());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        btn_addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AddItemActivity.class);
                i.putExtra("userUID", userUID);
                startActivityForResult(i,ADD_ITEM_ACTIVITY);

            }
        });


        checkBox_MyItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(categorySpinner.getSelectedItemPosition() != 0){
                    categorySpinner.setSelection(0);
                }else{
                    if(checkBox_MyItems.isChecked()){
                        ArrayList<Item> userItems = new ArrayList();
                        for (int i = 0 ; i < items.size() ; i++){
                            if(items.get(i).getUserUID().equals(userUID)) {
                                userItems.add(items.get(i));
                            }
                        }
                        updateAdapter(userItems);
                    }else{
                        updateAdapter(items);
                    }
                }
            }
        });


        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (categorySpinner.getItemAtPosition(position).toString()){

                    case "Todo":
                        allItems.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                items.clear();
                                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    Item item = dataSnapshot1.getValue(Item.class);
                                    items.add(item);
                                }
                                if(checkBox_MyItems.isChecked()){
                                    ArrayList<Item> userItems = new ArrayList();
                                    for (int i = 0 ; i < items.size() ; i++){
                                        if(items.get(i).getUserUID().equals(userUID)) {
                                            userItems.add(items.get(i));
                                        }
                                    }
                                    updateAdapter(userItems);
                                }else{
                                    updateAdapter(items);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        break;

                    case "Coches":
                        for (int i = 0 ; i < items.size() ; i++){
                            if(items.get(i).getCategory().equals("Coches")){
                                filteredItems.add(items.get(i));
                            }
                        }
                        updateAdapter(filteredItems);
                        break;

                    case "Hogar":
                        for (int i = 0 ; i < items.size() ; i++){
                            if(items.get(i).getCategory().equals("Hogar")){
                                filteredItems.add(items.get(i));
                            }
                        }
                        updateAdapter(filteredItems);
                        break;

                    case "Tecnologia":
                        for (int i = 0 ; i < items.size() ; i++){
                            if(items.get(i).getCategory().equals("Tecnologia")){
                                filteredItems.add(items.get(i));
                            }
                        }
                        updateAdapter(filteredItems);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case ADD_ITEM_ACTIVITY:
                switch (resultCode){
                    case RESULT_OK:
                        if(categorySpinner.getSelectedItemPosition() != 0){
                            categorySpinner.setSelection(0);
                        }else{
                            items.clear();
                            allItems.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                        Item item = dataSnapshot1.getValue(Item.class);
                                        items.add(item);
                                    }
                                    if(checkBox_MyItems.isChecked()){
                                        ArrayList<Item> userItems = new ArrayList();
                                        for (int i = 0 ; i < items.size() ; i++){
                                            if(items.get(i).getUserUID().equals(userUID)) {
                                                userItems.add(items.get(i));
                                            }
                                        }
                                        updateAdapter(userItems);
                                    }else{
                                        updateAdapter(items);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }

                        break;
                    case RESULT_CANCELED:

                        break;
                }
            break;
        }
    }

    private void userItemsFiltered(){

    }

    private void inflateRecyclerView(){
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerAdapter_Items(items);
        recyclerView.setAdapter(adapter);

    }
    private void updateAdapter(ArrayList<Item>items){
        adapter.updateAdapter(items);
    }

}
