package com.example.loca_market.ui.seller.sellerStore;

import android.net.Uri;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.loca_market.data.models.Store;
import com.example.loca_market.data.repositores.StoreRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StoreFragmentViewModel extends ViewModel {
    private StoreRepository storeRepository ;
    public MutableLiveData<Store> storeMutableLiveData = new MutableLiveData<>();
    public Uri store_image_uri ;
    public String store_image_extention , store_image_name ;
    MutableLiveData<Boolean>  sbConfirmation = new  MutableLiveData<>();
    public FirebaseUser currentuser ;

    public StoreFragmentViewModel() {
        storeRepository = StoreRepository.getInstance();
        currentuser = FirebaseAuth.getInstance().getCurrentUser();
        storeMutableLiveData.setValue(new Store());
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
}
