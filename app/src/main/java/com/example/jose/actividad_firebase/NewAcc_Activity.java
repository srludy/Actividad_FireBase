package com.example.jose.actividad_firebase;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jose.actividad_firebase.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NewAcc_Activity extends AppCompatActivity {

    //Views
    Button btn_Register, btn_Cancel;
    EditText txt_UserName, txt_Email, txt_Name, txt_Adress, txt_Pass;
    TextView userName_used, email_used;

    //BBDD
    DatabaseReference BBDD;
    FirebaseAuth firebaseAuth;

    //Variable
    boolean available_userName;
    boolean available_email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_acc_);
        getSupportActionBar().hide();

        //Initations
        btn_Cancel = (Button) findViewById(R.id.btn_Cancel_Register);
        btn_Register = (Button) findViewById(R.id.btn_Register);
        txt_UserName = (EditText) findViewById(R.id.txt_UserName);
        txt_Email = (EditText) findViewById(R.id.txt_Email);
        txt_Name = (EditText) findViewById(R.id.txt_Name);
        txt_Adress = (EditText) findViewById(R.id.txt_Adress) ;
        txt_Pass = (EditText) findViewById(R.id.txt_newUserPass);
        userName_used = (TextView) findViewById(R.id.invalid_userName);
        email_used = (TextView) findViewById(R.id.invalid_email);

        BBDD = FirebaseDatabase.getInstance().getReference("users");

        userName_used.setVisibility(View.INVISIBLE);
        email_used.setVisibility(View.INVISIBLE);

        //Listeners

        txt_UserName.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                txt_UserName.setTextColor(Color.parseColor("#FF141614"));
                userName_used.setVisibility(View.INVISIBLE);
                return false;
            }
        });
        txt_Email.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                txt_Email.setTextColor(Color.parseColor("#FF141614"));
                email_used.setVisibility(View.INVISIBLE);
                return false;
            }
        });
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
                if(data_Test){
                    test_UserNameAndEmail();
                }else{
                    Toast.makeText(getApplicationContext(),"¡ Rellena Todos los Campos Correctamente !", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean test_EdiText_Data(){
        boolean data_Test = true;
        if(txt_Adress.getText().toString().isEmpty() || txt_Name.getText().toString().isEmpty() || txt_UserName.getText().toString().isEmpty() || txt_Email.getText().toString().isEmpty() || txt_Pass.getText().toString().isEmpty()) {
            data_Test = false;
        }
        return data_Test;
    }
    //COMPROBACIONES
    private void test_UserNameAndEmail(){

        Query q = BBDD.orderByChild("userName").equalTo(txt_UserName.getText().toString());
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount() > 0){
                    available_userName = false;
                    userName_used.setVisibility(View.VISIBLE);
                    txt_UserName.setTextColor(Color.parseColor("#FFCA1D09"));
                }else{
                    available_userName = true;
                }

                Query q2 = BBDD.orderByChild("email").equalTo(txt_Email.getText().toString());
                q2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getChildrenCount() > 0){
                            available_email = false;
                            email_used.setVisibility(View.VISIBLE);
                            txt_Email.setTextColor(Color.parseColor("#FFCA1D09"));
                        }else{
                            available_email = true;
                        }

                        if(available_email && available_userName) {
                            addUser();
                        }else{
                            Toast.makeText(getApplicationContext(),"Error, Hay campos incorrectos",Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    //AÑADE USUARIO
    private void addUserToDataBase(String key){
        User u = new User(txt_UserName.getText().toString(), txt_Email.getText().toString(), txt_Name.getText().toString(), txt_Adress.getText().toString(), "user");
        BBDD.child(key).setValue(u);
    }

    //AÑADE UN AUTH
    private void addUser(){
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(txt_Email.getText().toString(), txt_Pass.getText().toString())
                .addOnCompleteListener(NewAcc_Activity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            addUserToDataBase(user.getUid());
                            getIntent().putExtra("userUID",user.getUid());
                            setResult(RESULT_OK,getIntent());
                            finish();

                        } else {
                            Toast.makeText(getApplication(), "Authentication failed. "+task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}
