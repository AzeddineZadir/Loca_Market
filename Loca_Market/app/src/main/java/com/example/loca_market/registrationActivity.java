package com.example.loca_market;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class registrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }
    public void sellerLogin(View view){
        Intent intent1 =new Intent(this,SellerLoginActivity.class);
        startActivity(intent1);
    }
}