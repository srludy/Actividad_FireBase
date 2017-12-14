package com.example.jose.actividad_firebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Login_Activity extends AppCompatActivity {

    //Views
    Button btn_GoIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);
        getSupportActionBar().hide();

        //Initiations
        btn_GoIn = (Button) findViewById(R.id.btn_GoIn);


        //Listeners
        btn_GoIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               //setResult(RESULT_OK, getIntent());
            }
        });

    }
}
