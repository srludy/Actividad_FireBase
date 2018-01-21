package com.example.jose.actividad_firebase.Model;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jose.actividad_firebase.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

import static com.example.jose.actividad_firebase.R.id.userNameTitle;

public class ModifyUserInfo extends AppCompatActivity {

    //BBDD
    FirebaseUser user;
    DatabaseReference dbAllUsers;
    DatabaseReference dbUser;

    //Views
    Button btn_modify;
    EditText txt_email, txt_name;

    //class
    User currentUser;
    String nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_user_info);

        //Initiations
        dbAllUsers = FirebaseDatabase.getInstance().getReference("users");
        user = FirebaseAuth.getInstance().getCurrentUser();
        dbUser = FirebaseDatabase.getInstance().getReference("users/"+user.getUid());


        btn_modify = (Button) findViewById(R.id.btn_getModifyUserInfo);
        txt_email = (EditText) findViewById(R.id.modifyEmail);
        txt_name = (EditText) findViewById(R.id.modify_Name);

        nombre = txt_name.getText().toString();


        dbUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentUser = dataSnapshot.getValue(User.class);
                txt_name.setText(currentUser.getName().toString());
                txt_email.setText(currentUser.getEmail().toString());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Error adquiriendo informacion del usuario",Toast.LENGTH_LONG).show();
            }
        });

        btn_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txt_email.getText().toString().isEmpty() || txt_name.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Rellena todos los campos", Toast.LENGTH_LONG).show();
                }else{
                    boolean correctMail = ValidateMail(txt_email.getText().toString());
                    if(correctMail){
                        Query q2 = dbAllUsers.orderByChild("email").equalTo(txt_email.getText().toString());
                        q2.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(dataSnapshot.getChildrenCount() > 0){
                                    Toast.makeText(getApplicationContext(),"Este Email ya esta en uso.",Toast.LENGTH_LONG).show();
                                }else{
                                    modifyEmails();
                                    if(!nombre.equals(txt_name.getText().toString())){
                                        modifyName();
                                    }
                                    setResult(RESULT_OK);
                                    finish();
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Toast.makeText(getApplicationContext(),"Error modificando la informacion del usuario",Toast.LENGTH_LONG).show();
                            }
                        });
                    }else{
                        Toast.makeText(getApplicationContext(),"Email no valido",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }

    private void modifyName() {
        dbUser.child("name").setValue(txt_name.getText().toString());
    }

    private void modifyEmails() {
        user.updateEmail(txt_email.getText().toString());
        dbUser.child("email").setValue(txt_email.getText().toString());
    }

    private boolean ValidateMail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }


}
