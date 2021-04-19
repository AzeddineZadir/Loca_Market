package com.example.loca_market.ui.seller.ProductList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.loca_market.data.models.Product;
import com.example.loca_market.data.repositores.ProductRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class ProductListViewModel extends ViewModel {

    public static final String TAG = "ProductListViewModel";
    private MutableLiveData<ArrayList<Product>> productLiveData ;
    private ProductRepository productRepository;
    public FirebaseUser currentuser ;

    public ProductListViewModel(){
        productLiveData = new MutableLiveData<>();
        productRepository = ProductRepository.getInstance();
        currentuser= FirebaseAuth.getInstance().getCurrentUser();
        productLiveData = productRepository.getProductsBySeller(currentuser.getUid());
    }

    public void init(){

        if (productLiveData != null) {

            return;
        }
    }

    public LiveData<ArrayList<Product>> getProductBySellerUid(){
        productLiveData = productRepository.getProductsBySeller(currentuser.getUid());
        return productLiveData;
    }

}
