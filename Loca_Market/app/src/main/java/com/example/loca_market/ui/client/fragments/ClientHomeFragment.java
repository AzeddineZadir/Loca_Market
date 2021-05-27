package com.example.loca_market.ui.client.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.loca_market.data.models.Product;
import com.example.loca_market.ui.client.Activities.AllCategories;
import com.example.loca_market.ui.client.adapter.BestSellsAdapter;
import com.example.loca_market.ui.client.adapter.CategorieAdapter;
import com.example.loca_market.ui.client.adapter.NewProductAdapter;
import com.example.loca_market.data.models.Category;

import com.example.loca_market.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ClientHomeFragment extends Fragment {
    private FirebaseFirestore mstore;
    FirebaseAuth mAuth;

    // for categories
    private List<Category> mCategoryList;
    private CategorieAdapter mCategoryAdapter;
    private RecyclerView mCategoryRecyclerView;

    // for new Products
    private List<Product>mNewProductsList;
    private NewProductAdapter mNewProductsAdapter;
    private RecyclerView mNewProductsRecyclerView;

    // for best sells
    private List<Product>mBestSellsList;
    private BestSellsAdapter mBestSellsAdapter;
    private RecyclerView mBestSellsRecyclerView;

    // View All catégories
    TextView tViewAllCategories;

    public ClientHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  =inflater.inflate(R.layout.fragment_client_home, container, false);
        mstore=FirebaseFirestore.getInstance();
        mAuth=FirebaseAuth.getInstance();
        tViewAllCategories = view.findViewById(R.id.t_viewAllCategories);
        // get the data

        // for categories
        mCategoryList =new ArrayList<>();
        mCategoryAdapter = new CategorieAdapter(getContext(),mCategoryList);
        mCategoryRecyclerView = view.findViewById(R.id.category_recycler);
        mCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        mCategoryRecyclerView.setAdapter(mCategoryAdapter);
        // for NewProducts
        mNewProductsList =new ArrayList<>();
        mNewProductsAdapter = new NewProductAdapter(getContext(),mNewProductsList);
        mNewProductsRecyclerView= view.findViewById(R.id.newProductRecycler);
        mNewProductsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        mNewProductsRecyclerView.setAdapter(mNewProductsAdapter);

        // for BestSells
        mBestSellsList =new ArrayList<>();
        mBestSellsAdapter = new BestSellsAdapter(getContext(),mBestSellsList);
        mBestSellsRecyclerView= view.findViewById(R.id.bestSellsRecycler);
        mBestSellsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        mBestSellsRecyclerView.setAdapter(mBestSellsAdapter);



     // get categories
        mstore.collection("users").document(mAuth.getCurrentUser().getUid())
                .collection("categories").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot doc:task.getResult().getDocuments()){
                        Category category=doc.toObject(Category.class);
                        mCategoryList.add(category);
                        mCategoryAdapter.notifyDataSetChanged();
                    }
                }
                if(!mCategoryList.isEmpty()){
                    getProducts();
                }else {
                    mstore.collection("categories").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task2) {
                            if(task2.isSuccessful()){
                                for(DocumentSnapshot doc:task2.getResult().getDocuments()){
                                    Category category=doc.toObject(Category.class);
                                    mCategoryList.add(category);
                                    mCategoryAdapter.notifyDataSetChanged();
                                }
                                getProducts();
                            }
                        }
                    });
                }

            }
        });


        // click buton for all categories
        tViewAllCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), AllCategories.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void getProducts() {
        mstore.collection("products")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                    Product product = document.toObject(Product.class);
                                    for(Category c : mCategoryList){
                                        if(product.getCategory()!=null && product.getCategory().trim().equals(c.getName().trim())){
                                            if (!product.isBestSell()) {
                                                mNewProductsList.add(product);
                                                mNewProductsAdapter.notifyDataSetChanged();
                                            }else{
                                                mBestSellsList.add(product);
                                                mBestSellsAdapter.notifyDataSetChanged();
                                            }
                                        }
                                    }
                            }

                        } else {
                            Log.w("Home Fragment", "Error getting documents.", task.getException());
                        }
                    }
                });
    }


}