package com.example.loca_market.ui.client.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.example.loca_market.R;
import com.example.loca_market.data.models.Category;
import com.example.loca_market.data.models.Store;
import com.example.loca_market.ui.client.adapter.SearchCategoryAdapter;
import com.example.loca_market.ui.client.adapter.SearchStoreAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AllCategories extends AppCompatActivity {
    FirebaseFirestore mStore;
    FirebaseAuth mAuth;
    private Toolbar mToolbar;
    EditText et_searchCategory_text;
    List<Category> searchCategoryList;
    List<Category> categoryList;
    RecyclerView searchCategoryReceyclerView;
    SearchCategoryAdapter searchCategoryAdapter;
    RecyclerView allCategoriesRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_all_categories);
        // tootlbar managment
        mToolbar=findViewById(R.id.allCategories_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Catégories");

        /* search bar */
        mAuth =FirebaseAuth.getInstance();
        mStore=FirebaseFirestore.getInstance();
        et_searchCategory_text = findViewById(R.id.et_search_text_allCategories);
        searchCategoryList =new ArrayList<>();
        searchCategoryReceyclerView= findViewById(R.id.recycler_allCategories);
        searchCategoryReceyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchCategoryAdapter = new SearchCategoryAdapter(this, searchCategoryList);
        searchCategoryReceyclerView.setAdapter(searchCategoryAdapter);
        et_searchCategory_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().isEmpty()){
                    searchCategoryList.clear();
                    searchCategoryAdapter.notifyDataSetChanged();
                }else {
                    searchCategoryList.clear();
                    searchCategoryAdapter.notifyDataSetChanged();
                    searchCategory(s.toString());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchCategory(s.toString());
            }
        });

        // retreive all categories
        categoryList =new ArrayList<>();

        mStore.collection("categories").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot doc:task.getResult().getDocuments()){
                        Category category=doc.toObject(Category.class);
                        categoryList.add(category);
                        searchCategoryList.add(category);
                        Log.i("SearchCategory",category.getName());
                        searchCategoryAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    private void searchCategory(String text) {
        searchCategoryList.clear();
        if(!text.isEmpty()){
            for(Category category : categoryList){
                if(category.getName().toLowerCase().contains(text.toLowerCase())){
                    searchCategoryList.add(category);
                    searchCategoryAdapter.notifyDataSetChanged();
                }
            }
        }else {
            for (Category category : categoryList) {
                searchCategoryList.add(category);
            }
        }
    }
}