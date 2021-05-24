package com.example.loca_market.ui.userAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.loca_market.R;
import com.example.loca_market.ui.client.ClientHomeActivity;
import com.example.loca_market.ui.seller.SellerHomeActivity;
import com.example.loca_market.ui.userAuth.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    public static final String  USER_SHARED_PREFS ="userSharedPrefs";
    public static final String  EMAIL ="email";
    public static final String  PWD ="pwd";
    public static final String  ROLE ="role";
    private  String user_email ,user_pwd,role;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        connectSavedUser();
        setContentView(R.layout.activity_splash);





    }
    public void sellerLogin(View view){
        Intent intent1 =new Intent(this, LoginActivity.class);
        intent1.putExtra("role","seller");
        startActivity(intent1);
    }

    public void clientLogin(View view) {
        Intent intent1 =new Intent(this, LoginActivity.class);
        intent1.putExtra("role","client");
        startActivity(intent1);
    }



    public void loadUserData(){
        SharedPreferences sharedPreferences = getSharedPreferences(USER_SHARED_PREFS,MODE_PRIVATE);
        user_email = sharedPreferences.getString(EMAIL,"");
        user_pwd = sharedPreferences.getString(PWD,"");
        role = sharedPreferences.getString(ROLE,"");
    }

    public void connectSavedUser(){
        loadUserData();

        if(!user_email.equals("") && !user_pwd.equals("") ) {
            mAuth.signInWithEmailAndPassword(user_email, user_pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (role.equals("seller")) {

                        Intent intent = new Intent(SplashActivity.this, SellerHomeActivity.class);
                        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        //Toast.makeText(getApplicationContext(),"juste connected as a Seller ", Toast.LENGTH_SHORT).show();
                    } else if (role.equals("client")) {

                        Intent intent2 = new Intent(SplashActivity.this, ClientHomeActivity.class);
                        //intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent2);
                        //Toast.makeText(getApplicationContext(),"juste connected as a Clinet  ", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);

            startActivity(intent);
        }
    }

}