package com.example.jose.actividad_firebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.example.jose.actividad_firebase.Model.ModifyUserInfo;
import com.example.jose.actividad_firebase.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.TooManyListenersException;

public class User_Main_Activity extends AppCompatActivity {


    //Variables
    int itemPosition;
    boolean filtered;

    //Finals
    private final static int ADD_ITEM_ACTIVITY = 2;
    private final static int MODIFY_ITEM_ACTIVITY = 3;
    private final static int MODIFY_USER_ACTIVITY = 4;

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
    DatabaseReference itemReference;
    FirebaseUser firebaseUser;
    String userUID ;

    //class
    User currentUser;

    //ArrayList
    ArrayList<Item> items;
    ArrayList <Item> filteredItems;
    ArrayList<Item> userItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__main_);
        getSupportActionBar().setTitle("Servipop");


        //Initiations
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        btn_addItem = (Button) findViewById(R.id.btnAddItem);
        checkBox_MyItems = (CheckBox) findViewById(R.id.checkBoxMyItems);
        categorySpinner = (Spinner) findViewById(R.id.categorySpinner);
        userUID = getIntent().getExtras().getString("userUID");
        userNameTitle = (TextView) findViewById(R.id.userNameTitle);
        userReference = FirebaseDatabase.getInstance().getReference("users/"+userUID);
        allItems = FirebaseDatabase.getInstance().getReference("items");
        recyclerView = (RecyclerView) findViewById(R.id.recycler_userItems);
        items = new ArrayList();
        userItems = new ArrayList();
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
                        userItems.clear();
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
                        filtered = false;
                        allItems.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                items.clear();
                                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    Item item = dataSnapshot1.getValue(Item.class);
                                    items.add(item);
                                }
                                if(checkBox_MyItems.isChecked()){
                                    userItems.clear();
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
                        filteredItems.clear();
                        filtered = true;
                        if(checkBox_MyItems.isChecked()){
                            for (int i = 0 ; i < userItems.size() ; i++){
                                if(userItems.get(i).getCategory().equals("Coches")){
                                    filteredItems.add(userItems.get(i));
                                }
                            }
                            updateAdapter(filteredItems);
                        }else{
                            for (int i = 0 ; i < items.size() ; i++){
                                if(items.get(i).getCategory().equals("Coches")){
                                    filteredItems.add(items.get(i));
                                }
                            }
                            updateAdapter(filteredItems);
                        }
                        break;

                    case "Hogar":
                        filteredItems.clear();
                        filtered = true;
                        if(checkBox_MyItems.isChecked()){
                            for (int i = 0 ; i < userItems.size() ; i++){
                                if(userItems.get(i).getCategory().equals("Hogar")){
                                    filteredItems.add(userItems.get(i));
                                }
                            }
                            updateAdapter(filteredItems);
                        }else{
                            for (int i = 0 ; i < items.size() ; i++){
                                if(items.get(i).getCategory().equals("Hogar")){
                                    filteredItems.add(items.get(i));
                                }
                            }
                            updateAdapter(filteredItems);
                        }
                        break;

                    case "Tecnologia":
                        filteredItems.clear();
                        filtered = true;
                        if(checkBox_MyItems.isChecked()){
                            for (int i = 0 ; i < userItems.size() ; i++){
                                if(userItems.get(i).getCategory().equals("Tecnologia")){
                                    filteredItems.add(userItems.get(i));
                                }
                            }
                            updateAdapter(filteredItems);
                        }else{
                            for (int i = 0 ; i < items.size() ; i++){
                                if(items.get(i).getCategory().equals("Tecnologia")){
                                    filteredItems.add(items.get(i));
                                }
                            }
                            updateAdapter(filteredItems);
                        }
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
                                        userItems.clear();
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
                        Toast.makeText(getApplicationContext(),"Se ha Cancelado el nuevo Item.",Toast.LENGTH_LONG).show();
                        break;
                }
            break;
            case MODIFY_ITEM_ACTIVITY:
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
                                        userItems.clear();
                                        for (int i = 0 ; i < items.size() ; i++){
                                            if(items.get(i).getUserUID().equals(userUID)) {
                                                userItems.add(items.get(i));
                                            }
                                        }
                                        updateAdapter(userItems);
                                    }else{
                                        updateAdapter(items);
                                    }
                                    Toast.makeText(getApplicationContext(),"El Item se ha modificado con exito.",Toast.LENGTH_LONG).show();
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

            case MODIFY_USER_ACTIVITY:
                switch (resultCode){
                    case RESULT_OK:
                        Toast.makeText(getApplicationContext(),"Informacion modificada correctamente", Toast.LENGTH_LONG).show();
                        break;
                    case RESULT_CANCELED:
                        Toast.makeText(getApplicationContext(),"Proceso de modificacion cancelado.", Toast.LENGTH_LONG).show();
                        break;
                }
            break;
        }
    }

    public void getItemPosition(int itemPosition){
        this.itemPosition = itemPosition;
    }

    private void inflateRecyclerView(){
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerAdapter_Items(items, this);
        recyclerView.setAdapter(adapter);

    }
    private void updateAdapter(ArrayList<Item>items){
        adapter.updateAdapter(items);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if(checkBox_MyItems.isChecked()){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.context_menu_user, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.modifyItem:
                Intent i = new Intent(this, ModifyItem_Activity.class);
                if (filtered){
                    i.putExtra("itemName", filteredItems.get(itemPosition).getName());
                    i.putExtra("itemDesc", filteredItems.get(itemPosition).getDescription());
                    i.putExtra("itemPrice", filteredItems.get(itemPosition).getPrice());
                    i.putExtra("itemKey", filteredItems.get(itemPosition).getKey());

                    startActivityForResult(i,MODIFY_ITEM_ACTIVITY);
                }else{

                    i.putExtra("itemName", userItems.get(itemPosition).getName());
                    i.putExtra("itemDesc", userItems.get(itemPosition).getDescription());
                    i.putExtra("itemPrice", userItems.get(itemPosition).getPrice());
                    i.putExtra("itemKey", userItems.get(itemPosition).getKey());

                    startActivityForResult(i,MODIFY_ITEM_ACTIVITY);
                }
                break;

            case R.id.deleteItem:
                if(filtered){
                    itemReference = FirebaseDatabase.getInstance().getReference("items/"+filteredItems.get(itemPosition).getKey());
                    itemReference.removeValue();
                    filteredItems.remove(itemPosition);
                    adapter.deleteItem(filteredItems, itemPosition);
                    Toast.makeText(getApplicationContext(),"Se ha eliminado el item.",Toast.LENGTH_LONG).show();

                }else{
                    itemReference = FirebaseDatabase.getInstance().getReference("items/"+userItems.get(itemPosition).getKey());
                    itemReference.removeValue();
                    userItems.remove(itemPosition);
                    adapter.deleteItem(userItems, itemPosition);
                    items.clear();
                    allItems.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                Item item = dataSnapshot1.getValue(Item.class);
                                items.add(item);
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    Toast.makeText(getApplicationContext(),"Se ha eliminado el item.",Toast.LENGTH_LONG).show();

                }
                break;

        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ModifyUserInfo:
                Intent i = new Intent(this, ModifyUserInfo.class);
                startActivityForResult(i, MODIFY_USER_ACTIVITY);

                return true;
            case R.id.logout:
                Toast.makeText(getApplicationContext(), "722", Toast.LENGTH_LONG).show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
