package com.example.loca_market.ui.seller.OrdersList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.loca_market.data.models.Order;
import com.example.loca_market.data.models.Product;
import com.example.loca_market.data.models.ProductCart;
import com.example.loca_market.data.repositores.OrdersRepository;
import com.example.loca_market.data.repositores.ProductRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class OrdersFragmentViewModel extends ViewModel {

    public static final String TAG = "OrdersFragmentViewModel";
    private MutableLiveData<ArrayList<Order>> ordersLiveData ;
    private OrdersRepository ordersRepository;
    public FirebaseUser currentuser ;
    private MutableLiveData<Boolean> validateStatuLiveData ;


    public OrdersFragmentViewModel() {
        ordersLiveData = new MutableLiveData<>();
        ordersRepository = OrdersRepository.getInstance();
        currentuser= FirebaseAuth.getInstance().getCurrentUser();
        ordersLiveData = ordersRepository.getOrdersData();
    }

    public void init(){

        if (ordersLiveData != null) {

            return;
        }
    }

    public LiveData<ArrayList<Order>> getOrders(){

        ordersLiveData = ordersRepository.getOrdersData();
        return ordersLiveData ;

    }

    public LiveData<Boolean> validateOrder(String order_id){

        validateStatuLiveData = ordersRepository.validateProductByUid(order_id);
        return validateStatuLiveData;
    }

}
