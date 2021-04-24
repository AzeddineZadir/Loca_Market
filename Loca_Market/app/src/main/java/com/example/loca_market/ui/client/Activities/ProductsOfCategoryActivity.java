package com.example.loca_market.ui.client.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.loca_market.R;
import com.example.loca_market.data.models.Product;
import com.example.loca_market.ui.client.adapter.ProductSearchRecyclerAdapter;
import com.example.loca_market.ui.client.adapter.ProductsOfCategoryAdapter;
import com.example.loca_market.ui.userAuth.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductsOfCategoryActivity extends AppCompatActivity {

    private String categoryName;

    private FirebaseFirestore mStore;
    public List<Product> mProductsList;
    private RecyclerView productRecyclerView;
    ProductsOfCategoryAdapter productrecyclerAdapter;
    private Toolbar mToolbar;
    EditText et_search_text;
    private List<Product> mProductslistSearch;
    private RecyclerView mProductSearchRecyclerView;
    private ProductSearchRecyclerAdapter productSearchRecyclerAdapter;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_products_of_category);
        categoryName=getIntent().getStringExtra("Category");
      /* Toolbar */
        mToolbar=findViewById(R.id.toolbar_products_of_category);
        setSupportActionBar(mToolbar);
        /* search bar */
        mAuth =FirebaseAuth.getInstance();
        mStore=FirebaseFirestore.getInstance();
        et_search_text = findViewById(R.id.et_search_text_products_of_category);
        mProductslistSearch =new ArrayList<>();
        mProductSearchRecyclerView = findViewById(R.id.search_recycler);
        mProductSearchRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        productSearchRecyclerAdapter = new ProductSearchRecyclerAdapter(this, mProductslistSearch);
        mProductSearchRecyclerView.setAdapter(productSearchRecyclerAdapter);
        et_search_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().isEmpty()){
                    mProductslistSearch.clear();
                    productSearchRecyclerAdapter.notifyDataSetChanged();
                }else {
                    mProductslistSearch.clear();
                    productSearchRecyclerAdapter.notifyDataSetChanged();
                    searchProduct(s.toString());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchProduct(s.toString());
            }
        });

        /* retrieve products of category */
        mStore=FirebaseFirestore.getInstance();
        mProductsList =new ArrayList<>();
        productRecyclerView =findViewById(R.id.recycler_products_of_category);
        productRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        productrecyclerAdapter=new ProductsOfCategoryAdapter(this, mProductsList);
        productRecyclerView.setAdapter(productrecyclerAdapter);
        if(categoryName!=null){
            mProductsList.clear();
            mStore.collection("Products").whereEqualTo("category",categoryName).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(DocumentSnapshot doc:task.getResult().getDocuments()){

                            Product product=doc.toObject(Product.class);
                            mProductsList.add(product);
                            productrecyclerAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }

    }
    /* search bar */
    private void searchProduct(String text) {
        mProductslistSearch.clear();
        if(!text.isEmpty()){
            for(Product product : mProductsList){
                if(product.getName().toLowerCase().contains(text.toLowerCase())){
                    mProductslistSearch.add(product);
                    productSearchRecyclerAdapter.notifyDataSetChanged();
                }
            }
        }

    }
    /*  Main menu */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.client_main_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.b_Logout_main_menu){
            mAuth.signOut();
            Intent intent=new Intent(ProductsOfCategoryActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return true;
    }

}