package com.example.jose.actividad_firebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class AddItemActivity extends AppCompatActivity {

    //Views
    Spinner spinnerCategory;
    EditText txt_name, txt_price, txt_description;
    Button btn_addItem, btn_cancel;

    //BBDD

    //Variables


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        //Initiations
        txt_description = (EditText) findViewById(R.id.txt_newItemDescription);
        txt_description = (EditText) findViewById(R.id.txt_newItemDescription);
        txt_description = (EditText) findViewById(R.id.txt_newItemDescription);
        spinnerCategory = (Spinner) findViewById(R.id.spinnerNewItemCategory);
        btn_addItem = (Button) findViewById(R.id.btn_addNewItem);
        btn_cancel = (Button) findViewById(R.id.btn_cancelNewItem);


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
