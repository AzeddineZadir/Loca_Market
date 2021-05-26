package com.example.loca_market.ui.client.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;

import com.example.loca_market.R;
import com.example.loca_market.data.models.Category;
import com.example.loca_market.data.models.Store;
import com.example.loca_market.ui.client.adapter.SearchCategoryAdapter;
import com.example.loca_market.ui.client.adapter.SearchStoreAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

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
    }
}