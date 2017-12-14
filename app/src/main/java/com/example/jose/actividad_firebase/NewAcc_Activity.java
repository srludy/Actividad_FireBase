package com.example.jose.actividad_firebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewAcc_Activity extends AppCompatActivity {

    //Views
    Button btn_Register, btn_Cancel;
    EditText txt_UserName, txt_Email, txt_Name, txt_Adress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_acc_);
        getSupportActionBar().hide();

        //Initations
        btn_Cancel = (Button) findViewById(R.id.btn_Cancel_Register);
        btn_Register = (Button) findViewById(R.id.btn_Register);
        txt_UserName = (EditText) findViewById(R.id.txt_NameUser);
        txt_Email = (EditText) findViewById(R.id.txt_Email);
        txt_Name = (EditText) findViewById(R.id.txt_Name);
        txt_Adress = (EditText) findViewById(R.id.txt_Adress) ;


        //Listeners
        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED, getIntent());
                finish();
            }
        });

        btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean data_Test = test_EdiText_Data();
                boolean available_UserName = test_UserName();

                if(data_Test){
                    if(available_UserName){

                    }else{
                        Toast.makeText(getApplicationContext(),"¡ El Nombre De Usuario Ya Esta Utilizado, Porfavor Elija Otro !", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"¡ Rellena Todos los Campos Correctamente !", Toast.LENGTH_LONG).show();
                }
             //   setResult(RESULT_OK, getIntent());
            }
        });
    }

    private boolean test_EdiText_Data(){
        boolean data_Test = true;
        if(txt_Adress.getText().toString().isEmpty() || txt_Name.getText().toString().isEmpty() || txt_UserName.getText().toString().isEmpty() || txt_Email.getText().toString().isEmpty()) {
            data_Test = false;
        }
        return data_Test;
    }
    private boolean test_UserName(){
        boolean available_UserName = false;


     return available_UserName;
    }
}
