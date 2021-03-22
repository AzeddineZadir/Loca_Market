package com.example.loca_market;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SellerLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_login);
    }
    public void registration(View view){
        Intent intent = new Intent(this,registrationActivity.class);
        startActivity(intent);
    }
    public void forgotPassword(View view){
        Intent intent = new Intent(this,forgotPasswordActivity.class);
        startActivity(intent);
    }
}