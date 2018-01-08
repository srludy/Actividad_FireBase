package com.example.jose.actividad_firebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.jose.actividad_firebase.Model.Item;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddItemActivity extends AppCompatActivity {

    //Views
    Spinner spinnerCategory;
    EditText txt_name, txt_price, txt_description;
    Button btn_addItem, btn_cancel;

    //BBDD
    DatabaseReference BBDD;
    //Variables


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        //Initiations
        txt_description = (EditText) findViewById(R.id.txt_newDescItem);
        txt_name = (EditText) findViewById(R.id.txt_newNameItem);
        txt_price = (EditText) findViewById(R.id.txt_newItemPrice);
        spinnerCategory = (Spinner) findViewById(R.id.spinnerNewItemCategory);
        btn_addItem = (Button) findViewById(R.id.btn_addItem);
        btn_cancel = (Button) findViewById(R.id.btn_cancelNewItem);

        BBDD = FirebaseDatabase.getInstance().getReference("items");


        //spinner inflate
        String[] arraySpinner = new String[] {
                 "Tecnologia", "Coches", "Hogar"
        };
        final ArrayAdapter<String> adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        spinnerCategory.setAdapter(adapter);



        //Listeners

        btn_addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               boolean correctEditextData  = test_editext_data();
                if(correctEditextData){
                    String key = BBDD.push().getKey();
                   // Toast.makeText(getApplicationContext(),key,Toast.LENGTH_LONG).show();
                    Item i = new Item(txt_name.getText().toString(), txt_description.getText().toString(), spinnerCategory.getSelectedItem().toString(), txt_price.getText().toString(), getIntent().getExtras().getString("userUID"), key);
                    BBDD.child(key).setValue(i);
                    setResult(RESULT_OK, getIntent());
                    finish();
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED, getIntent());
                finish();
            }
        });
    }

    private boolean test_editext_data() {
        boolean correctEditextData = true;
        if(txt_description.getText().toString().isEmpty() || txt_name.getText().toString().isEmpty() || txt_price.getText().toString().isEmpty()){
         correctEditextData = false;
        }
        return correctEditextData;
    }
}
