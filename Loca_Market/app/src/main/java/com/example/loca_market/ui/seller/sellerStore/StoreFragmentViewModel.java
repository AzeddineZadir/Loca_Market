package com.example.loca_market.ui.seller.sellerStore;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.loca_market.data.models.Category;
import com.example.loca_market.data.models.Product;
import com.example.loca_market.data.models.Store;
import com.example.loca_market.data.repositores.ProductRepository;
import com.example.loca_market.data.repositores.StoreRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class StoreFragmentViewModel extends ViewModel {
    private StoreRepository storeRepository ;
    public MutableLiveData<Store> storeMutableLiveData = new MutableLiveData<>();
    public Uri store_image_uri ;
    public String store_image_extention , store_image_name ;
    MutableLiveData<Boolean>  sbConfirmation = new  MutableLiveData<>();
    private MutableLiveData<ArrayList<Product>> productLiveData ;
    public FirebaseUser currentuser ;
    private ProductRepository productRepository;



    public StoreFragmentViewModel() {
        storeRepository = StoreRepository.getInstance();
        currentuser = FirebaseAuth.getInstance().getCurrentUser();
        storeMutableLiveData.setValue(new Store());
        productLiveData = new MutableLiveData<>();
        productRepository = ProductRepository.getInstance();
        productLiveData = productRepository.getProductsBySeller(currentuser.getUid());
    }

    public void init(){
        getStoreDetails();
        if (storeMutableLiveData != null) {
            return;
        }
    }

    public MutableLiveData<Store> getStoreDetails(){

        storeMutableLiveData= storeRepository.getStoreBySellerUid(currentuser.getUid());

        return storeMutableLiveData ;
    }
    public LiveData<ArrayList<Product>> getProductBySellerUid(){
        productLiveData = productRepository.getProductsBySeller(currentuser.getUid());
        return productLiveData;
    }




}
