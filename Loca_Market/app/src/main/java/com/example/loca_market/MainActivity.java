package com.example.loca_market;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.loca_market.Client.ClientLoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void sellerLogin(View view){
        Intent intent1 =new Intent(this,SellerLoginActivity.class);
        startActivity(intent1);
    }

    public void clientLogin(View view) {
        Intent intent1 =new Intent(this, ClientLoginActivity.class);
        startActivity(intent1);
    }
}