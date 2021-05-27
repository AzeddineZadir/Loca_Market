package com.example.loca_market.ui.client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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

import com.example.loca_market.data.models.Product;
import com.example.loca_market.ui.client.Activities.AllCategories;
import com.example.loca_market.ui.client.Activities.ClientSettingsActivity;
import com.example.loca_market.ui.client.Activities.ClientSearchSellerActivity;
import com.example.loca_market.ui.client.Activities.OrdersManagement;
import com.example.loca_market.ui.client.Activities.ProductCartActivity;
import com.example.loca_market.ui.client.Activities.ProductsOfCategoryActivity;
import com.example.loca_market.ui.client.adapter.ProductSearchRecyclerAdapter;
import com.example.loca_market.ui.client.fragments.ClientHomeFragment;
import com.example.loca_market.ui.userAuth.LoginActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.example.loca_market.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;

public class ClientHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private final String TAG = "ClientHomeActivity";
    private Fragment clientHomeFragment;
    private EditText et_search_text;
    private Toolbar mToolbar;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mStore;
    private List<Product> mProductslistSearch;
    private RecyclerView mProductSearchRecyclerView;
    private ProductSearchRecyclerAdapter productSearchRecyclerAdapter;
    FirebaseUser user;
    // navigation drawer
    private DrawerLayout mDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_home);
        clientHomeFragment =new ClientHomeFragment();
        loadFragment(clientHomeFragment);
        mAuth =FirebaseAuth.getInstance();
        mStore=FirebaseFirestore.getInstance();
        mToolbar=findViewById(R.id.toolbar_client_home);
        setSupportActionBar(mToolbar);
        // navigation drawer
        mDrawer=findViewById(R.id.drawer_layout_home_activity);
        user = FirebaseAuth.getInstance().getCurrentUser();
        DocumentReference docRef = mStore.collection("users").document(user.getUid());
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // creat toketn
        addTokenToUser(docRef);

        // search bar
        et_search_text = findViewById(R.id.et_search_text_client_home);

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

            }
        });


    }

    private void searchProduct(String text) {
        if(!text.isEmpty()){
            mStore.collection("products").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful() && task.getResult()!=null){
                        for(DocumentSnapshot doc:task.getResult().getDocuments()){
                            Product product=doc.toObject(Product.class);
                            if(product.getName().toLowerCase().contains(text.toLowerCase())){
                                mProductslistSearch.add(product);
                                productSearchRecyclerAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                }
            } );
        }

    }

    private void loadFragment(Fragment clientHomeFragment) {
        FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.client_home_container,clientHomeFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void addTokenToUser( DocumentReference docRef) {
        String token = FirebaseInstanceId.getInstance().getToken();

        docRef.update("notifToken", token).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                Log.e(TAG, "onSuccess: update token " + user.getDisplayName());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Log.e(TAG, "onfaile: update token " + user.getDisplayName());

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout_home_activity);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.client_home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_homme)
        {
            Intent intent = new Intent(ClientHomeActivity.this, ClientHomeActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_cart)
        {
            Intent intent = new Intent(ClientHomeActivity.this, ProductCartActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_orders)
        {
                Intent intent = new Intent(ClientHomeActivity.this, OrdersManagement.class);
                startActivity(intent);
        }
       else if (id == R.id.nav_sellers)
        {
            Intent intent = new Intent(ClientHomeActivity.this, ClientSearchSellerActivity.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_settings)
        {
            Intent intent = new Intent(ClientHomeActivity.this, ClientSettingsActivity.class);
            startActivity(intent);

        }
       else if (id == R.id.nav_categories)
        {
            Intent intent = new Intent(ClientHomeActivity.this, AllCategories.class);
            startActivity(intent);
        }

        else if (id == R.id.nav_logout)
        {

                 mAuth.signOut();
                 Intent intent=new Intent(ClientHomeActivity.this, LoginActivity.class);
                 startActivity(intent);
                 finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout_home_activity);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}