package com.example.loca_market.ui.client.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.loca_market.R;
import com.example.loca_market.ui.client.ClientHomeActivity;

public class ClientOrderConfirmedActivity extends AppCompatActivity {
    Button b_Home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_order_confirmed);
        b_Home = findViewById(R.id.b_home);
        b_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(ClientOrderConfirmedActivity.this, ClientHomeActivity.class);
                startActivity(intent);
            }
        });
    }
}