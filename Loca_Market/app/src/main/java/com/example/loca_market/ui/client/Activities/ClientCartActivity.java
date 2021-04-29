package com.example.loca_market.ui.client.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.loca_market.R;
import com.example.loca_market.data.models.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ClientCartActivity extends AppCompatActivity  implements CartProductAdapter.ItemRemoved {
    FirebaseFirestore mStore;
    FirebaseAuth mAuth;
    List<Product> productsList;
    RecyclerView cartRecyclerView;
    CartProductAdapter cartProductAdapter;
    Button buyProductButton;
    TextView totalAmount;
    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_cart);
        // tootlbar managment
        mToolbar=findViewById(R.id.cart_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        buyProductButton= findViewById(R.id.cart_product_buy_now);
        totalAmount =findViewById(R.id.cart_total_amount);
        productsList=new ArrayList<>();
        cartRecyclerView =findViewById(R.id.cart_item_container);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartRecyclerView.setHasFixedSize(true);
        // button buy the product
        buyProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void onProductRemoved(List<Product> itemsList) {

    }
}