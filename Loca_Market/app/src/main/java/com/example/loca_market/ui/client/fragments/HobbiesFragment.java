package com.example.loca_market.ui.client.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.loca_market.R;
import com.example.loca_market.data.models.Category;
import com.example.loca_market.data.models.ProductCart;
import com.example.loca_market.ui.client.Activities.ProductCartActivity;
import com.example.loca_market.ui.client.Activities.ProductDetailsActivity;
import com.example.loca_market.ui.client.adapter.CategorieAdapter;
import com.example.loca_market.ui.client.adapter.HobbiesAdapter;
import com.example.loca_market.ui.client.adapter.SearchStoreAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class HobbiesFragment extends Fragment {

    FirebaseFirestore mStore;
    FirebaseAuth mAuth;
    List<Category> hobbies;
    RecyclerView hobbiesRecyclerView;

    HobbiesAdapter hobbiesAdapter;

    public HobbiesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_hobbies, container, false);
        mStore =FirebaseFirestore.getInstance();
        mAuth =FirebaseAuth.getInstance();
        hobbies =new ArrayList<>();
        getAllHobbies();
        hobbiesAdapter= new HobbiesAdapter(getContext(),hobbies);
        hobbiesRecyclerView = view.findViewById(R.id.hobbies_recycler);
        hobbiesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        hobbiesRecyclerView.setAdapter(hobbiesAdapter);
        return view;
    }

    private void getAllHobbies() {
        mStore.collection("categories")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Category category=document.toObject(Category.class);
                                hobbies.add(category);
                                hobbiesAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Log.w("Home Fragment", "Error getting documents.", task.getException());
                        }
                    }
                });
    }


}