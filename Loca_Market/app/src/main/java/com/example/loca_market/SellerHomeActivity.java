package com.example.loca_market;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class SellerHomeActivity extends AppCompatActivity {

    Button b_log_out ;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_home);
        mAuth = FirebaseAuth.getInstance();
        b_log_out = (Button)findViewById(R.id.b_log_out);

    }

    public void click_b_log_out(View view) {
        // se décconecter
        mAuth.signOut();
        Toast.makeText(this, "you just signed out ", Toast.LENGTH_SHORT).show();
        Intent intent1 =new Intent(this,SellerLoginActivity.class);
        startActivity(intent1);

    }
}