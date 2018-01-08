package com.example.jose.actividad_firebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class ModifyItem_Activity extends AppCompatActivity {


    //Views
    Spinner spinnerCategory;
    EditText txt_name, txt_price, txt_description;
    Button btn_modifyItem, btn_cancelModify;


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


    }
}
