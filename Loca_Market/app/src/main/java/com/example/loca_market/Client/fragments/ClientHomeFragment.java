package com.example.loca_market.Client.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.loca_market.Client.adapter.CategorieAdapter;
import com.example.loca_market.Models.Category;
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
    private List<Category> mCategoryList;
    private CategorieAdapter mCategoryAdapter;
    private RecyclerView mCategoryRecyclerView;
    public ClientHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  =inflater.inflate(R.layout.fragment_client_home, container, false);
        mstore=FirebaseFirestore.getInstance();
        // get the data
        mCategoryList =new ArrayList<>();
        mCategoryAdapter = new CategorieAdapter(getContext(),mCategoryList);
        mCategoryRecyclerView = view.findViewById(R.id.category_recycler);
        mCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));

        mCategoryRecyclerView.setAdapter(mCategoryAdapter);
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


        return view;
    }
}