package com.example.loca_market.ui.userAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.loca_market.data.models.User;
import com.example.loca_market.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegistrationActivity extends AppCompatActivity {

    EditText et_email_registration ,et_username_registration , et_password_registration ;
    ProgressBar pb_signe_up ;
    FirebaseAuth mAuth;
    FirebaseFirestore fdb ;
    CheckBox cb_client , cb_seller ;
    public static  String role ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

       /* Intent intent  = getIntent() ;
        if (intent!= null){
            role = intent.getStringExtra("role");

        }*/


        mAuth = FirebaseAuth.getInstance();
        // Access a Firestore instance from your Activity
        fdb = FirebaseFirestore.getInstance();
        // composants
        et_email_registration =(EditText)findViewById(R.id.et_email_registration);
        et_password_registration =(EditText)findViewById(R.id.et_password_registration);
        et_username_registration =(EditText)findViewById(R.id.et_username_registration);
        pb_signe_up = (ProgressBar)findViewById(R.id.pb_signe_up);
        cb_client = (CheckBox)findViewById(R.id.cb_client);

        cb_client.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){cb_seller.setVisibility(View.INVISIBLE);}
                if (!isChecked){cb_seller.setVisibility(View.VISIBLE);}
            }
        });
        cb_seller = (CheckBox)findViewById(R.id.cb_seller);
        cb_seller.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){cb_client.setVisibility(View.INVISIBLE);}
                if (!isChecked){cb_client.setVisibility(View.VISIBLE);}
            }
        });

        // massquer la bar de progression et l'afficher lors de l'operation d'inscription
        pb_signe_up.setVisibility(View.INVISIBLE);

    }



    public void click_b_register_user(View view) {
        registerUser();
    }
    private void registerUser() {
        String email = et_email_registration.getText().toString().trim();
        String password = et_password_registration.getText().toString().trim();
        String username = et_username_registration.getText().toString().trim();
        if (email.isEmpty()) {
            et_email_registration.setError("Email is required");
            et_email_registration.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            et_email_registration.setError("Please enter a valid email");
            et_email_registration.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            et_password_registration.setError("Password is required");
            et_password_registration.requestFocus();
            return;
        }

        if (password.length() < 6) {
            et_password_registration.setError("Minimum lenght of password should be 6");
            et_password_registration.requestFocus();
            return;
        }

        // gestion des check box
        if (!cb_client.isChecked() && !cb_seller.isChecked()){
            cb_seller.setError("please check one ");
            cb_client.setError("please check one ");
            cb_client.requestFocus();
            return;
        }

        pb_signe_up.setVisibility(View.VISIBLE);
        // initalisation du role
        role=getRole();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                CollectionReference usersRef  = fdb.collection("users");
                // si la creation c'est correctement dérouler
                // on ajoute le username au compte que on viens de crrer
                if (task.isSuccessful()) {

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null && role != null) {
                        // Name, email address, and profile photo Url
                        String name = user.getDisplayName();
                        String email = user.getEmail();
                        Uri photoUrl = user.getPhotoUrl();
                        String uid = user.getUid();
                        User new_user = new User(username,email,role) ;

                        Log.d("silver","user uid "+ uid);
                        usersRef.document(uid).set(new_user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(), "user correctly registered", Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                    finish();
                    startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));

                } else {

                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }

    public void sellerLogin(View view){
        Intent intent1 =new Intent(this, LoginActivity.class);
        startActivity(intent1);
    }
     public String getRole(){
        if (cb_seller.isChecked()){
            return "seller";
        }else {
            return "client";
        }
     }

}