package com.example.loca_market.ui.Client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.loca_market.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ClientLoginActivity extends AppCompatActivity {
    EditText et_email_client_login  , et_password_client_login ;
    ProgressBar pb_client_login ;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_login);
        auth = FirebaseAuth.getInstance();

        et_email_client_login =(EditText)findViewById(R.id.et_email_client_login);
        et_password_client_login =(EditText)findViewById(R.id.et_password_client_login);
        pb_client_login =(ProgressBar)findViewById(R.id.pb_client_login);
        pb_client_login.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void click_b_clientLogin(View view) {
        clientLogin();
    }
    private void clientLogin() {
        String email = et_email_client_login.getText().toString();
        String password = et_password_client_login.getText().toString();

        if (email.isEmpty()) {
            et_email_client_login.setError("Email Obligatoire");
            et_email_client_login.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            et_email_client_login.setError("Inserez votre email SVP");
            et_email_client_login.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            et_password_client_login.setError("Le mot de passe est obligatoire");
            et_password_client_login.requestFocus();
            return;
        }

        if (password.length() < 6) {
            et_password_client_login.setError("La taille minimale du mot de passe est 6 caractère");
            et_password_client_login.requestFocus();
            return;
        }
        pb_client_login.setVisibility(View.VISIBLE);

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                pb_client_login.setVisibility(View.GONE);
                if (task.isSuccessful()) {

                    finish();
                    Intent intent = new Intent(ClientLoginActivity.this, ClientHomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void forgotPasswordClientLogin(View view) {
        Intent intent = new Intent(this, ClientForgotPasswordActivity.class);
        startActivity(intent);
    }

    public void clientRegistration(View view) {
        Intent intent = new Intent(this, ClientRegistrationActivity.class);
        startActivity(intent);
    }
}