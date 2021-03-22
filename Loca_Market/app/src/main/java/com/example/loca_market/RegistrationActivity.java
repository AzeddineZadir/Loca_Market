package com.example.loca_market;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class RegistrationActivity extends AppCompatActivity {

    EditText et_email_registration ,et_username_registration , et_password_registration ;
    ProgressBar pb_signe_up ;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mAuth = FirebaseAuth.getInstance();
        et_email_registration =(EditText)findViewById(R.id.et_email_registration);
        et_password_registration =(EditText)findViewById(R.id.et_password_registration);
        et_username_registration =(EditText)findViewById(R.id.et_email_login);
        pb_signe_up = (ProgressBar)findViewById(R.id.pb_signe_up);
        // massquer la bar de progression et l'afficher lors de l'operation d'inscription
        pb_signe_up.setVisibility(View.INVISIBLE);

    }



    public void click_b_register_user(View view) {
        registerUser();
    }
    private void registerUser() {
        String email = et_email_registration.getText().toString().trim();
        String password = et_password_registration.getText().toString().trim();

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

        pb_signe_up.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                pb_signe_up.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    finish();
                    startActivity(new Intent(RegistrationActivity.this, SellerLoginActivity.class));
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
        Intent intent1 =new Intent(this,SellerLoginActivity.class);
        startActivity(intent1);
    }


}