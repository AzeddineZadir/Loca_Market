package com.example.loca_market.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.loca_market.R;
import com.example.loca_market.ui.userAuth.SellerLoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void sellerLogin(View view){
        Intent intent1 =new Intent(this, SellerLoginActivity.class);
        intent1.putExtra("role","seller");
        startActivity(intent1);
    }
}