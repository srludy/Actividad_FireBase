package com.example.jose.actividad_firebase;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login_Activity extends AppCompatActivity {

    //Classes


    //Views
    Button btn_GoIn;
    EditText txt_Email, txt_Pass;

    //BBDD
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);
        getSupportActionBar().hide();

        //Initiations
        btn_GoIn = (Button) findViewById(R.id.btn_GoIn);
        txt_Pass = (EditText) findViewById(R.id.txt_Pass);
        txt_Email = (EditText) findViewById(R.id.txt_UserEmail);
        firebaseAuth = FirebaseAuth.getInstance();



        //Listeners
        btn_GoIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean correct_EdiText_Data = test_Editext_Data();
                if(correct_EdiText_Data){
                    test_auth_user();
                }else{
                    Toast.makeText(getApplicationContext(),"¡ Rellena Todos los campos Correctamente !", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void test_auth_user(){

        firebaseAuth.signInWithEmailAndPassword(txt_Email.getText().toString(), txt_Pass.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            getIntent().putExtra("userUID",user.getUid());
                            setResult(RESULT_OK,getIntent());
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
    private boolean test_Editext_Data() {
        boolean correctData = true;
        if(txt_Pass.getText().toString().isEmpty() || txt_Email.getText().toString().isEmpty()){
            correctData = false;
        }
        return correctData;
    }
}
