package com.example.loca_market.ui.userAuth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.loca_market.R;
import com.example.loca_market.ui.userAuth.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}