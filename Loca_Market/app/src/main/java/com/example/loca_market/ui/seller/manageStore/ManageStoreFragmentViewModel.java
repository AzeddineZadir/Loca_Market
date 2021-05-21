package com.example.loca_market.ui.seller.manageStore;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.loca_market.data.models.Category;
import com.example.loca_market.data.models.Store;
import com.example.loca_market.data.repositores.ProductRepository;
import com.example.loca_market.data.repositores.StoreRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class ManageStoreFragmentViewModel  extends ViewModel {
    private StoreRepository storeRepository ;
    public MutableLiveData<Store> storeMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Category>> categoryLiveData = new MutableLiveData<>();
    private ProductRepository productRepository;
    public Uri store_image_uri ;
    public String store_image_extention , store_image_name ;
    MutableLiveData<Boolean>  sbConfirmation = new  MutableLiveData<>();
    public FirebaseUser currentuser ;
    public ManageStoreFragmentViewModel() {
        storeRepository = StoreRepository.getInstance();
        currentuser = FirebaseAuth.getInstance().getCurrentUser();
        storeMutableLiveData.setValue(new Store());

    }

    public void init(){
        getStoreDetails();
        if (storeMutableLiveData != null) {
            productRepository = ProductRepository.getInstance();
            return;
        }
    }

    public MutableLiveData<Store> getStoreDetails(){

        storeMutableLiveData= storeRepository.getStoreBySellerUid(currentuser.getUid());

        return storeMutableLiveData ;
    }

    public boolean updateStore(){
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        store_image_name = ts;
        boolean  result =  storeRepository.updateStore(storeMutableLiveData.getValue(),store_image_uri,store_image_name,store_image_extention) ;
        if (result){
            showConfirmationSb();
        }
        return result ;
    }
    public LiveData<ArrayList<Category>> getCategoryData() {

        categoryLiveData = productRepository.getCategoryData();

        return categoryLiveData;
    }

    public void showConfirmationSb(){
        sbConfirmation.setValue(true);
    }

}
