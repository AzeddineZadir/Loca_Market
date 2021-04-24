package com.example.loca_market.ui.client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import com.example.loca_market.ui.client.adapter.ProductSearchRecyclerAdapter;
import com.example.loca_market.ui.client.fragments.ClientHomeFragment;
import com.example.loca_market.ui.userAuth.LoginActivity;
import com.google.android.gms.tasks.Task;
import com.example.loca_market.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ClientHomeActivity extends AppCompatActivity {
    private Fragment clientHomeFragment;
    private EditText et_search_text;
    private Toolbar mToolbar;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mStore;
    private List<Product> mProductsList;
    private RecyclerView mProductRecyclerView;
    private ProductSearchRecyclerAdapter productSearchRecyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_home);
        clientHomeFragment =new ClientHomeFragment();
        loadFragment(clientHomeFragment);
        mAuth =FirebaseAuth.getInstance();
        mToolbar=findViewById(R.id.toolbar_client_home);
        setSupportActionBar(mToolbar);
        et_search_text = findViewById(R.id.et_search_text_client_home);
        mStore=FirebaseFirestore.getInstance();
        mProductsList=new ArrayList<>();
        mProductRecyclerView = findViewById(R.id.search_recycler);
        mProductRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        productSearchRecyclerAdapter = new ProductSearchRecyclerAdapter(this,mProductsList);
        mProductRecyclerView.setAdapter(productSearchRecyclerAdapter);

        et_search_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().isEmpty()){
                    mProductsList.clear();
                    productSearchRecyclerAdapter.notifyDataSetChanged();
                }else {
                    mProductsList.clear();
                    productSearchRecyclerAdapter.notifyDataSetChanged();
                    searchItem(s.toString());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    private void searchItem(String text) {
        if(!text.isEmpty()){
            mStore.collection("Products").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful() && task.getResult()!=null){
                        for(DocumentSnapshot doc:task.getResult().getDocuments()){
                            Log.i("ClientHomeActivity",text);
                            Product product=doc.toObject(Product.class);
                            if(product.getName().toLowerCase().contains(text.toLowerCase())){
                                mProductsList.add(product);
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.client_main_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.b_Logout_main_menu){
            mAuth.signOut();
            Intent intent=new Intent(ClientHomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return true;
    }
}