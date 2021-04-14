package com.example.loca_market.ui.userAuth;

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
import com.example.loca_market.data.models.User;
import com.example.loca_market.ui.client.ClientHomeActivity;
import com.example.loca_market.ui.seller.SellerHomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {


    EditText et_email_login  , et_password_login ;
    ProgressBar pb_login ;
    FirebaseFirestore fdb;

    FirebaseAuth mAuth;
    public static String role ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mAuth = FirebaseAuth.getInstance();
        fdb = FirebaseFirestore.getInstance();
        et_email_login =(EditText)findViewById(R.id.et_email_login);
        et_password_login =(EditText)findViewById(R.id.et_password_login);
        pb_login =(ProgressBar)findViewById(R.id.pb_login);
        pb_login.setVisibility(View.INVISIBLE);




    }

    @Override
    protected void onStart() {
        super.onStart();

    }


    public void click_b_login(View view) {
        userLogin();
    }


    private void userLogin() {
        String email = et_email_login.getText().toString();
        String password = et_password_login.getText().toString();

        if (email.isEmpty()) {
            et_email_login.setError("Email is required");
            et_email_login.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            et_email_login.setError("Please enter a valid email");
            et_email_login.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            et_password_login.setError("Password is required");
            et_password_login.requestFocus();
            return;
        }

        if (password.length() < 6) {
            et_password_login.setError("Minimum lenght of password should be 6");
            et_password_login.requestFocus();
            return;
        }

        pb_login.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                pb_login.setVisibility(View.GONE);
                if (task.isSuccessful()) {

                    finish();
                    FirebaseUser connectedUser = FirebaseAuth.getInstance().getCurrentUser();

                    DocumentReference userdocRef = fdb.collection("users").document(connectedUser.getUid());
                    userdocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            User current_user = documentSnapshot.toObject(User.class);

                             if (current_user.getRole().equals("seller")){

                                Intent intent = new Intent(LoginActivity.this, SellerHomeActivity.class);
                                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                //Toast.makeText(getApplicationContext(),"juste connected as a Seller ", Toast.LENGTH_SHORT).show();
                            }else if (current_user.getRole().equals("client")){

                                Intent intent2 = new Intent(LoginActivity.this, ClientHomeActivity.class);
                                 //intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent2);
                                //Toast.makeText(getApplicationContext(),"juste connected as a Clinet  ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });


                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void registration(View view){
        Intent intent = new Intent(this, RegistrationActivity.class);
        intent.putExtra("role",role);
        startActivity(intent);
    }
    public void forgotPassword(View view){
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }


}