package com.example.jose.actividad_firebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login_Activity extends AppCompatActivity {

    //Classes
    private FirebaseAuth mAuth;

    //Views
    Button btn_GoIn;
    EditText txt_Email, txt_Pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);
        getSupportActionBar().hide();

        //Initiations
        btn_GoIn = (Button) findViewById(R.id.btn_GoIn);
        txt_Pass = (EditText) findViewById(R.id.txt_Pass);
        txt_Email = (EditText) findViewById(R.id.txt_UserEmail);
        mAuth = FirebaseAuth.getInstance();



        //Listeners
        btn_GoIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean correct_EdiText_Data = test_Editext_Data();
                boolean correct_BBDD_Data = test_BBDD_Data();
                if(correct_EdiText_Data){
                    if(correct_BBDD_Data){

                        //setResult(RESULT_OK, getIntent());
                    }else{
                        Toast.makeText(getApplicationContext(),"¡ Usuario o Contraseña no coinciden !", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"¡ Rellena Todos los campos Correctamente !", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean test_BBDD_Data(){
        boolean correct_BBDD_Data = false;

        return correct_BBDD_Data;
    }
    private boolean test_Editext_Data() {
        boolean correctData = true;
        if(txt_Pass.getText().toString().isEmpty() || txt_Email.getText().toString().isEmpty()){
            correctData = false;
        }
        return correctData;
    }
}
