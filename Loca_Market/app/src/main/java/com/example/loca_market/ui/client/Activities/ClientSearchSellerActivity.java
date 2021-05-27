package com.example.loca_market.ui.client.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.example.loca_market.R;
import com.example.loca_market.data.models.Product;
import com.example.loca_market.data.models.Store;
import com.example.loca_market.ui.client.adapter.ProductSearchRecyclerAdapter;
import com.example.loca_market.ui.client.adapter.ProductsOfCategoryAdapter;
import com.example.loca_market.ui.client.adapter.SearchStoreAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ClientSearchSellerActivity extends AppCompatActivity {

    FirebaseFirestore mStore;
    FirebaseAuth mAuth;

    private Toolbar mToolbar;
    EditText et_search_text;
    List <Store> searchStoreList;
    List<Store> storeList;
    RecyclerView searchStoreReceyclerView;
    SearchStoreAdapter searchStoreAdapter;
    RecyclerView allStoresRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_serach_seller);
        // tootlbar managment
        mToolbar=findViewById(R.id.clientSearchSeller_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Chercher Commerçant");

        /* search bar */
        mAuth =FirebaseAuth.getInstance();
        mStore=FirebaseFirestore.getInstance();
        et_search_text = findViewById(R.id.et_search_text_ClientSearchSeller);
        searchStoreList =new ArrayList<>();
        searchStoreReceyclerView= findViewById(R.id.search_recycler_clientSearchSeller);
        searchStoreReceyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchStoreAdapter = new SearchStoreAdapter(this, searchStoreList);
        searchStoreReceyclerView.setAdapter(searchStoreAdapter);
        et_search_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().isEmpty()){
                    searchStoreList.clear();
                    searchStoreAdapter.notifyDataSetChanged();
                }else {
                    searchStoreList.clear();
                    searchStoreAdapter.notifyDataSetChanged();
                    searchStore(s.toString());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchStore(s.toString());
            }
        });

        // retreive all stores
        mStore=FirebaseFirestore.getInstance();
        storeList =new ArrayList<>();

        mStore.collection("stores").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot doc:task.getResult().getDocuments()){
                        Store store=doc.toObject(Store.class);
                        storeList.add(store);
                        searchStoreList.add(store);
                        Log.i("SearchSeller",store.getName());
                        searchStoreAdapter.notifyDataSetChanged();
                    }
                }
            }
        });


    }

    private void searchStore(String text) {
        searchStoreList.clear();
        if(!text.isEmpty()){
            for(Store store : storeList){
                if(store.getName().toLowerCase().contains(text.toLowerCase())){
                    searchStoreList.add(store);
                    searchStoreAdapter.notifyDataSetChanged();
                }
            }
        }else {
            for (Store store : storeList) {
                searchStoreList.add(store);
            }
        }
    }
}