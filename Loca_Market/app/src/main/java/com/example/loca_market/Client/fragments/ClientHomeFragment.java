package com.example.loca_market.Client.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.loca_market.Client.adapter.BestSellsAdapter;
import com.example.loca_market.Client.adapter.CategorieAdapter;
import com.example.loca_market.Client.adapter.NewProductAdapter;
import com.example.loca_market.Models.Category;
import com.example.loca_market.Models.Product;
import com.example.loca_market.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ClientHomeFragment extends Fragment {
    private FirebaseFirestore mstore;

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
     //   mNewProductsRecyclerView.setBackgroundColor(R.color.red_600);
        mNewProductsRecyclerView.setAdapter(mNewProductsAdapter);



        mstore.collection("Categories")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("Home Fragment", document.getId() + " => " + document.getData());
                                Category category=document.toObject(Category.class);
                                mCategoryList.add(category);
                                mCategoryAdapter.notifyDataSetChanged();

                            }
                        } else {
                            Log.w("Home Fragment", "Error getting documents.", task.getException());
                        }
                    }
                });



        mstore.collection("Products")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("Home Fragment", document.getId() + " => " + document.getData());
                                if(document.getData().get("bestSell").equals(false)) {
                                    Product product = document.toObject(Product.class);
                                    mNewProductsList.add(product);
                                    mNewProductsAdapter.notifyDataSetChanged();
                                }
                            }
                        } else {
                            Log.w("Home Fragment", "Error getting documents.", task.getException());
                        }
                    }
                });

        // for BestSells
        mBestSellsList =new ArrayList<>();
        mBestSellsAdapter = new BestSellsAdapter(getContext(),mBestSellsList);
        mBestSellsRecyclerView= view.findViewById(R.id.bestSellsRecycler);
        mBestSellsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
   //     mBestSellsRecyclerView.setBackgroundColor(R.color.red_600);
        mBestSellsRecyclerView.setAdapter(mBestSellsAdapter);

        mstore.collection("Products")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("Home Fragment Best Sell", document.getId() + " => " + document.getData());
                                if(document.getData().get("bestSell").equals(true)){
                                    Product product2=document.toObject(Product.class);
                                    mBestSellsList.add(product2);
                                    mBestSellsAdapter.notifyDataSetChanged();
                                }

                            }
                        } else {
                            Log.w("Home Fragment", "Error getting documents.", task.getException());
                        }
                    }
                });

        return view;
    }
}