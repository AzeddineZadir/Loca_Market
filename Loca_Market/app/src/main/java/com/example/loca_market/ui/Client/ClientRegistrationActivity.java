package com.example.loca_market.ui.Client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.loca_market.Models.User;
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

public class ClientRegistrationActivity extends AppCompatActivity {
    EditText et_email_client_registration,et_username_client_registration , et_password_client_registration ;
    ProgressBar pb_client_signe_up ;
    FirebaseAuth auth;
    FirebaseFirestore fdb ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_registration);
        auth = FirebaseAuth.getInstance();
        // Access a Firestore instance from your Activity
        fdb = FirebaseFirestore.getInstance();

        et_email_client_registration =(EditText)findViewById(R.id.et_email_client_regstration);
        et_password_client_registration =(EditText)findViewById(R.id.et_password_client_regstration);
        et_username_client_registration =(EditText)findViewById(R.id.et_username_client_regstration);
        pb_client_signe_up = (ProgressBar)findViewById(R.id.pb_client_signe_up);
        // massquer la bar de progression et l'afficher lors de l'operation d'inscription
        pb_client_signe_up.setVisibility(View.INVISIBLE);
    }

    public void click_b_register_client(View view) {
        registerClient();
    }

    private void registerClient() {
        String email = et_email_client_registration.getText().toString().trim();
        String password = et_password_client_registration.getText().toString().trim();
        String username = et_username_client_registration.getText().toString().trim();
        if (email.isEmpty()) {
            et_email_client_registration.setError("Email Obligatoire");
            et_email_client_registration.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            et_email_client_registration.setError("Inserez votre email SVP");
            et_email_client_registration.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            et_password_client_registration.setError("Le mot de passe est obligatoire");
            et_password_client_registration.requestFocus();
            return;
        }

        if (password.length() < 6) {
            et_password_client_registration.setError("La taille minimale du mot de passe est 6 caractère");
            et_password_client_registration.requestFocus();
            return;
        }

        pb_client_signe_up.setVisibility(View.VISIBLE);

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                CollectionReference usersRef  = fdb.collection("Users");
                // si la creation c'est correctement dérouler
                // on ajoute le username au compte que on viens de créer
                if (task.isSuccessful()) {

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        // Name, email address, and profile photo Url
                        String name = user.getDisplayName();
                        String email = user.getEmail();
                        Uri photoUrl = user.getPhotoUrl();
                        String uid = user.getUid();
                        String role = "Client";
                        User new_user = new User(username,email,role) ;


                        usersRef.document(uid).set(new_user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(), "inscription réussi", Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                    finish();
                    startActivity(new Intent(ClientRegistrationActivity.this, ClientLoginActivity.class));

                } else {

                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "vous ête déjà inscrit", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }

    public void clientLogin(View view) {
        Intent intent =new Intent(this,ClientLoginActivity.class);
        startActivity(intent);
    }
}