package com.example.loca_market;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.loca_market.ui.userAuth.LoginActivity;

public class registrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }
    public void sellerLogin(View view){
        Intent intent1 =new Intent(this, LoginActivity.class);
        startActivity(intent1);
    }
}