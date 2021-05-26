package com.example.loca_market.ui.client.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.example.loca_market.R;
import com.example.loca_market.data.models.Product;
import com.example.loca_market.data.models.Store;
import com.example.loca_market.ui.client.adapter.SelectedStoreProductGrideAdapter;
import com.example.loca_market.ui.client.adapter.SelectedStoreProductLinarAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SellectedStoreActivity extends AppCompatActivity {

    public static final String TAG = "SellectedStoreActivity";
    private static final String PRODUCTS = "products";
    private static final FirebaseFirestore fdb = FirebaseFirestore.getInstance();
    private static final CollectionReference productRef = fdb.collection(PRODUCTS);

    private static final String STORES = "stores";
    private static final CollectionReference storeRef = fdb.collection(STORES);

    FirebaseAuth mAuth;
    FirebaseUser user;

    private androidx.appcompat.widget.Toolbar toolbar_selected_store;
    private ImageView iv_sellected_store_banner;
    private RecyclerView rv_linear_products_sellected, rv_grid_products_sellected;
    private ArrayList<Product> productList;


    private SelectedStoreProductLinarAdapter selectedStoreProductLinarAdapter;
    private SelectedStoreProductGrideAdapter selectedStoreProductGrideAdapter;
    private Store myStore;
    private TextView tv_store_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sellected_store);
        Intent intent = getIntent();
        productList = new ArrayList<>();
        String store_id = intent.getStringExtra("store_id");

        toolbar_selected_store = (Toolbar) findViewById(R.id.toolbar_selected_store);
        iv_sellected_store_banner = (ImageView) findViewById(R.id.iv_sellected_store_banner);

        rv_grid_products_sellected = findViewById(R.id.rv_grid_products_sellected);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        rv_grid_products_sellected.setLayoutManager(staggeredGridLayoutManager);
        rv_grid_products_sellected.setHasFixedSize(true);
        selectedStoreProductGrideAdapter = new SelectedStoreProductGrideAdapter(this, productList);
        rv_grid_products_sellected.setAdapter(selectedStoreProductGrideAdapter);

        // linare rv
        rv_linear_products_sellected = findViewById(R.id.rv_linear_products_sellected);
        rv_linear_products_sellected.setLayoutManager(new LinearLayoutManager(this));
        rv_linear_products_sellected.setHasFixedSize(true);
        selectedStoreProductLinarAdapter = new SelectedStoreProductLinarAdapter(this ,productList);
        rv_linear_products_sellected.setAdapter(selectedStoreProductLinarAdapter);


        storeRef.document(store_id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                myStore = documentSnapshot.toObject(Store.class);
                toolbar_selected_store.setTitle(myStore.getName());
                Glide.with(getApplicationContext()).load(myStore.getImageUrl()).into(iv_sellected_store_banner);
                Window window = getWindow();
                // clear FLAG_TRANSLUCENT_STATUS flag:
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                // finally change the color
                window.setStatusBarColor(myStore.getColor());
                toolbar_selected_store.setBackgroundColor(myStore.getColor());

                Log.e(TAG, "onSuccess: store recived" + documentSnapshot.toObject(Product.class).getName());

                //load product

                productRef.whereEqualTo("productOwner", store_id).addSnapshotListener(new EventListener<QuerySnapshot>() {
                    ArrayList<Product> productArrayList = new ArrayList<>();

                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.w(TAG, "Listen failed.", error);
                            return;
                        }
                        if (!value.isEmpty()) {
                            List<DocumentSnapshot> documentSnapshotList = value.getDocuments();

                            for (DocumentSnapshot documentSnapshot : documentSnapshotList) {

                                Product product=documentSnapshot.toObject(Product.class);
                                productList.add(product);
                                selectedStoreProductGrideAdapter.notifyDataSetChanged();
                                selectedStoreProductLinarAdapter.notifyDataSetChanged();

                            }
                            if (myStore.isGrildView()) {

                                rv_grid_products_sellected.setVisibility(View.VISIBLE);
                                rv_linear_products_sellected.setVisibility(View.INVISIBLE);

                            } else {
                                rv_grid_products_sellected.setVisibility(View.INVISIBLE);
                                rv_linear_products_sellected.setVisibility(View.VISIBLE);

                            }

                        }
                    }
                });


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure: in getting store");
            }
        });

    }







}
