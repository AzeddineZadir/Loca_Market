package com.example.loca_market.ui.userAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.installations.FirebaseInstallations;

import java.security.PrivateKey;

public class LoginActivity extends AppCompatActivity implements  LoginListner{
    public static final String TAG = "LoginActivity";
    public static final String  USER_SHARED_PREFS ="userSharedPrefs";
    public static final String  EMAIL ="email";
    public static final String  PWD ="pwd";
    public static final String  ROLE ="role";
    private  String user_email ,user_pwd,role;
    EditText et_email_login  , et_password_login ;
    ProgressBar pb_login ;
    FirebaseFirestore fdb;
    LoginListner loginListner ;
    FirebaseAuth mAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);



        loginListner = this ;
        mAuth = FirebaseAuth.getInstance();
        fdb = FirebaseFirestore.getInstance();
        et_email_login =(EditText)findViewById(R.id.et_email_login);
        et_password_login =(EditText)findViewById(R.id.et_password_login);
        pb_login =(ProgressBar)findViewById(R.id.pb_login);
        pb_login.setVisibility(View.INVISIBLE);

        //String token = FirebaseInstallations.getInstance().getToken(true).getResult().toString();
        String token2 = FirebaseInstanceId.getInstance().getToken() ;
     /*   Log.e(TAG, "token created "+token );*/
        Log.e(TAG, "token login "+token2 );


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
                            Log.e(TAG, "current user role : "+current_user.getUsername() );
                            // save the user to loghim auto
                            saveUserPref(current_user.getRole());
                            loginListner.onSuccess(current_user.getRole());

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

        startActivity(intent);
    }
    public void forgotPassword(View view){
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    public void saveUserPref(String useer_role){
        SharedPreferences sharedPreferences = getSharedPreferences(USER_SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EMAIL,et_email_login.getText().toString());
        editor.putString(PWD,et_password_login.getText().toString());
        editor.putString(ROLE,useer_role);
        editor.apply();
    }






    @Override
    public void onSuccess(String role) {
        if (role.equals("seller")){

            Intent intent = new Intent(LoginActivity.this, SellerHomeActivity.class);

            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            //Toast.makeText(getApplicationContext(),"juste connected as a Seller ", Toast.LENGTH_SHORT).show();
        }else if (role.equals("client")){

            Intent intent2 = new Intent(LoginActivity.this, ClientHomeActivity.class);
            //intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent2);
            //Toast.makeText(getApplicationContext(),"juste connected as a Clinet  ", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure() {

    }
}