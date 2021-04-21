package com.example.loca_market.ui.seller.deleteProductList;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.loca_market.data.models.Product;
import com.example.loca_market.data.repositores.ProductRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class DeleteProductListViewModel  extends ViewModel {
    private MutableLiveData<ArrayList<Product>> productLiveData ;
    private ProductRepository productRepository;
    public FirebaseUser currentuser ;
    //UI VARIABLES
    MutableLiveData<Boolean> sbConfirmation = new  MutableLiveData<>();

    public DeleteProductListViewModel() {
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

    public boolean dropProduct(String productUid){

        return productRepository.deleteProductByUid(productUid);
    }



}
