package com.example.loca_market.ui.addProduct;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.loca_market.data.models.Product;
import com.example.loca_market.data.repositores.ProductRepository;
import com.example.loca_market.ui.MainActivity;
import com.example.loca_market.ui.ManageFragmentDirections;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;
import java.util.ArrayList;

public class AddProductViewModel extends ViewModel {

    public static final  String TAG = "AddProductViewModel" ;
    private MutableLiveData<ArrayList<Product>> productLiveData;
    private ProductRepository productRepository ;

    public MutableLiveData<Product> product = new MutableLiveData<Product>() ;
    public Uri imageUri ;
    public String image_ext ,image_name ;

    // navigation variables
    MutableLiveData<Boolean> navigationToManageFragment = new MutableLiveData<Boolean>();

    public AddProductViewModel() {
        product.setValue(new Product());
    }

    public void init(Context context){
        if (productLiveData != null){

            return ;
        }
    }

    public void  addProduct () {

        Log.e(TAG, "addProduct: "+product.getValue().getName() );
        Log.e(TAG, "addProduct: "+product.getValue().getBrand() );
        Log.e(TAG, "addProduct: "+product.getValue().getCategorie() );
        image_name = product.getValue().getName()+"_"+product.getValue().getBrand();
        ProductRepository.addProduct(product.getValue(),imageUri,image_name,image_ext);
        goToManagmentFragment();
    }

    public LiveData<ArrayList<Product>> getProductData(){
        return productLiveData ;
    }




    public  void goToManagmentFragment(){
        navigationToManageFragment .setValue(true); ;
    }

    public void endGoToManagmentFragment(){
        navigationToManageFragment .setValue(false); ;
    }


}
