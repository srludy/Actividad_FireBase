package com.example.jose.actividad_firebase;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

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
        /*btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Login_Activity.class);
                startActivityForResult(i,LOGIN_ACTIVITY_CODE);
            }
        });*/

        btn_Login.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                DatabaseReference bbdd = FirebaseDatabase.getInstance().getReference("users");

                Toast.makeText(MainActivity.this, "Antes de hacer la consulta "+bbdd.orderByKey(), Toast.LENGTH_LONG).show();
                Query q=bbdd.orderByChild("userName");

                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int cont=0;
                        for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                            cont++;
                            Toast.makeText(MainActivity.this, "He encontrado "+cont, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

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
                        Toast.makeText(getApplicationContext(),"¡ Registro De Usuario Completado !",Toast.LENGTH_LONG).show();
                        break;
                    case RESULT_CANCELED:
                        break;
                }
                break;
        }
    }
}
