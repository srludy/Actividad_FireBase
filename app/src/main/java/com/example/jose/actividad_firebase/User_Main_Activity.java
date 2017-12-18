package com.example.jose.actividad_firebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jose.actividad_firebase.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class User_Main_Activity extends AppCompatActivity {

    //Views
    Button btn_addItem;
    CheckBox checkBox_MyItems;
    Spinner categorySpinner;
    TextView userNameTitle;
    //BBDD
    DatabaseReference BBDD;
    String userUID ;

    //class
    User currentUser;

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
        BBDD = FirebaseDatabase.getInstance().getReference("users/"+userUID);

        //spinner inflate
        String[] arraySpinner = new String[] {
                "Todo", "Tecnologia", "Coches", "Hogar"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        categorySpinner.setAdapter(adapter);


        //Listeners
        BBDD.addListenerForSingleValueEvent(new ValueEventListener() {
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

            }
        });


        checkBox_MyItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





    }
}
