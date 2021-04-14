package com.example.loca_market.Client;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.loca_market.R;

public class ProductActivity extends AppCompatActivity {
    private String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        type=getIntent().getStringExtra("Category");
        Toast.makeText(this,"l'interface du produit de la catégorie  "+type+" est en cours de réalisation",Toast.LENGTH_LONG).show();
    }
}