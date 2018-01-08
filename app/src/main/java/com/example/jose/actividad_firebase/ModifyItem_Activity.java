package com.example.jose.actividad_firebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jose.actividad_firebase.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ModifyItem_Activity extends AppCompatActivity {


    //Views
    Spinner spinnerCategory;
    EditText txt_name, txt_price, txt_description;
    Button btn_modifyItem, btn_cancelModify;

    //BBDD
    DatabaseReference itemReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_item_);
        getSupportActionBar().setTitle("Servipop");

        //Initiations
        txt_description = (EditText) findViewById(R.id.txt_modifyDesc);
        txt_name = (EditText) findViewById(R.id.txt_modifyName);
        txt_price = (EditText) findViewById(R.id.txt_modifyPrice);
        spinnerCategory = (Spinner) findViewById(R.id.spinnerModifyCategory);
        btn_modifyItem = (Button) findViewById(R.id.btn_modifyItem);
        btn_cancelModify = (Button) findViewById(R.id.btn_cancelModifyItem);
        itemReference = FirebaseDatabase.getInstance().getReference("items/"+getIntent().getExtras().getString("itemKey"));




        //spinner inflate
        String[] arraySpinner = new String[] {
                "Tecnologia", "Coches", "Hogar"
        };
        final ArrayAdapter<String> adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        spinnerCategory.setAdapter(adapter);


        txt_name.setText(getIntent().getExtras().getString("itemName").toString());
        txt_description.setText(getIntent().getExtras().getString("itemDesc").toString());
        txt_price.setText(getIntent().getExtras().getString("itemPrice").toString());

        btn_modifyItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txt_name.getText().toString().isEmpty() || txt_price.getText().toString().isEmpty() || txt_description.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Rellena todos los campos.",Toast.LENGTH_LONG).show();
                }else{
                    itemReference.child("name").setValue(txt_name.getText().toString());
                    itemReference.child("description").setValue(txt_description.getText().toString());
                    itemReference.child("price").setValue(txt_price.getText().toString());
                    itemReference.child("category").setValue(spinnerCategory.getSelectedItem().toString());
                    setResult(RESULT_OK,getIntent());
                    finish();

                }
            }
        });

        btn_cancelModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED, getIntent());
                finish();
            }
        });
    }
}
