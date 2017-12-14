package com.example.jose.actividad_firebase;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    //Final Declarations
    final static int LOGIN_ACTIVITY_CODE = 2;
    final static int NEWACC_ACTIVITY_CODE = 3;

    //Views
    Button btn_Login, btn_newAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        //Initiations
        btn_Login = (Button) findViewById(R.id.btn_Login);
        btn_newAccount = (Button) findViewById(R.id.btn_New_Account);


        //Listeners
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Login_Activity.class);
                startActivityForResult(i,LOGIN_ACTIVITY_CODE);
            }
        });

        btn_newAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), NewAcc_Activity.class);
                startActivityForResult(i,NEWACC_ACTIVITY_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case LOGIN_ACTIVITY_CODE:
                switch (resultCode){
                    case RESULT_OK:
                        break;
                    case RESULT_CANCELED:
                        break;
                }

                break;
            case NEWACC_ACTIVITY_CODE:
                switch (resultCode){
                    case RESULT_OK:
                        break;
                    case RESULT_CANCELED:
                        break;
                }
                break;
        }
    }
}
