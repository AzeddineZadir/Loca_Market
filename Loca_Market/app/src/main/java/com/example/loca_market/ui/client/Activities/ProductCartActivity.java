package com.example.loca_market.ui.client.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loca_market.R;
import com.example.loca_market.data.models.Product;
import com.example.loca_market.data.models.ProductCart;
import com.example.loca_market.ui.client.adapter.CartProductAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ProductCartActivity extends AppCompatActivity  implements CartProductAdapter.ProductCartRemoved {
    FirebaseFirestore mStore;
    FirebaseAuth mAuth;
    List<ProductCart> productsCartList;
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
        productsCartList=new ArrayList<>();
        cartRecyclerView =findViewById(R.id.cart_item_container);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartRecyclerView.setHasFixedSize(true);
        // button buy the product
        buyProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        // à implémenter pour la commande
            }
        });
        cartProductAdapter = new CartProductAdapter(productsCartList,this);
        cartRecyclerView.setAdapter(cartProductAdapter);
        mStore.collection("users").document(mAuth.getCurrentUser().getUid())
                .collection("cart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult()!=null){
                        for(DocumentChange doc :task.getResult().getDocumentChanges()){
                            String documentId = doc.getDocument().getId();
                            ProductCart productCart = doc.getDocument().toObject(ProductCart.class);
                            productCart.setDocId(documentId);
                            productsCartList.add(productCart);
                        }
                        calculateAmount(productsCartList);
                        cartProductAdapter.notifyDataSetChanged();
                    }

                }else{
                    Toast.makeText(ProductCartActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void calculateAmount(List<ProductCart> productsCartList) {
        double totalAmountInDouble = 0.0;
        for(ProductCart productCart:productsCartList){
            totalAmountInDouble += (productCart.getProduct().getPrice()*productCart.getQuantity());
        }
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        totalAmount.setText("TOTAL : "+df.format(totalAmountInDouble));
    }


    @Override
    public void onProductRemoved(List<ProductCart> productCartList) {
        calculateAmount(productsCartList);
    }
}