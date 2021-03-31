package com.example.loca_market.ui.addProduct;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.loca_market.data.models.Product;
import com.example.loca_market.data.repositores.ProductRepository;

import java.util.ArrayList;

public class ProductViewModel extends ViewModel {
    public static final  String TAG = "ProductViewModel" ;
    private MutableLiveData<ArrayList<Product>> productLiveData;
    private ProductRepository productRepository ;

    public MutableLiveData<Product> product = new MutableLiveData<Product>() ;


    public ProductViewModel() {
        product.setValue(new Product());
    }

    public void init(Context context){
        if (productLiveData != null){

            return ;
        }
    }

    public void  addProduct () {

        Log.e(TAG, "addProduct: "+product.getValue().getName() );
        Log.e(TAG, "addProduct: "+product.getValue().getName() );
        Log.e(TAG, "addProduct: "+product.getValue().getBrand() );
        Log.e(TAG, "addProduct: "+product.getValue().getCategorie() );
        productRepository.addProduct(product.getValue());
    }
    public LiveData<ArrayList<Product>> getProductData(){
        return productLiveData ;
    }

}
