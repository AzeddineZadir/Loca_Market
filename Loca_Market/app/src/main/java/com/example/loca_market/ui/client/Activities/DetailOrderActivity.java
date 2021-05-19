package com.example.loca_market.ui.client.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.loca_market.R;
import com.example.loca_market.data.models.Order;
import com.example.loca_market.data.models.ProductCart;
import com.example.loca_market.ui.client.adapter.CartProductAdapter;
import com.example.loca_market.ui.client.adapter.OrderProductAdapter;

import java.util.List;

public class DetailOrderActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    TextView orderDateTime;
    TextView orderState;
    TextView orderTotalAmount;
    TextView orderReceiverLastname;
    TextView orderReceiverFirstname;
    TextView orderReceiverAddress;
    TextView orderReceiverCity;
    TextView orderReceiverPhoneNumber;
    Order curentOrder;

    RecyclerView productsOrdredRecyclerView;
    OrderProductAdapter productsOrdredAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);
        // tootlbar managment
        mToolbar=findViewById(R.id.order_detail_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Détail Commande");
        curentOrder = (Order) getIntent().getSerializableExtra("orderDetail");

        orderDateTime = findViewById(R.id.t_order_detail_date);
        orderState= findViewById(R.id.t_order_detail_state);
        orderTotalAmount = findViewById(R.id.t_order_detail_totalAmount);
        orderReceiverLastname = findViewById(R.id.t_order_detail_receiver_lastname);
        orderReceiverFirstname =findViewById(R.id.t_order_detail_receiver_firstname);
        orderReceiverAddress =findViewById(R.id.t_order_detail_receiver_address);
        orderReceiverCity = findViewById(R.id.t_order_detail_receiver_city);
        orderReceiverPhoneNumber = findViewById(R.id.t_order_detail_receiver_phone);

        orderDateTime.setText(curentOrder.getDate()+" à "+curentOrder.getTime());
        orderState.setText(curentOrder.getState());
        orderTotalAmount.setText(curentOrder.getTotalAmount());
        orderReceiverLastname.setText(curentOrder.getLastName());
        orderReceiverFirstname.setText(curentOrder.getFirstName());
        orderReceiverAddress.setText(curentOrder.getAddress());
        orderReceiverCity.setText(curentOrder.getCity());
        orderReceiverPhoneNumber.setText(curentOrder.getPhone());

        productsOrdredRecyclerView =findViewById(R.id.order_detail_container);
        productsOrdredRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        productsOrdredRecyclerView.setHasFixedSize(true);
        productsOrdredAdapter = new OrderProductAdapter(this,curentOrder.getProductsOrdred());
        productsOrdredRecyclerView.setAdapter(productsOrdredAdapter);

    }
}