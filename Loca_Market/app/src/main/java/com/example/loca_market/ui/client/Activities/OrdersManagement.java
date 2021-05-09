package com.example.loca_market.ui.client.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.loca_market.R;
import com.example.loca_market.data.models.Order;
import com.example.loca_market.data.models.ProductCart;
import com.example.loca_market.ui.client.adapter.OrderManagementAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class OrdersManagement extends AppCompatActivity {
    FirebaseFirestore mStore;
    FirebaseAuth mAuth;
    List<Order>orderList;
    RecyclerView ordersRecyclerView;
    OrderManagementAdapter orderManagementAdapter;
    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_orders_management);

        // tootlbar managment
        mToolbar=findViewById(R.id.order_management_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Commandes");

        mStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        orderList =new ArrayList<>();
        ordersRecyclerView =findViewById(R.id.order_item_container);
        ordersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ordersRecyclerView.setHasFixedSize(true);

        orderManagementAdapter =new OrderManagementAdapter(this,orderList);
        ordersRecyclerView.setAdapter(orderManagementAdapter);
        mStore.collection("orders").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult()!=null){
                        for(DocumentChange doc :task.getResult().getDocumentChanges()){
                            Order order = doc.getDocument().toObject(Order.class);
                            if(order.getClientId().trim().equals(mAuth.getCurrentUser().getUid())){
                                orderList.add(order);
                            }
                        }
                        orderManagementAdapter.notifyDataSetChanged();
                    }

                }else{
                    Toast.makeText(OrdersManagement.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}