package com.example.loca_market.ui.seller.addProduct;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.loca_market.data.models.Category;
import com.example.loca_market.data.models.Product;
import com.example.loca_market.data.repositores.ProductRepository;

import java.util.ArrayList;

public class AddProductViewModel extends ViewModel {

    public static final String TAG = "AddProductViewModel";
    private MutableLiveData<ArrayList<Product>> productLiveData = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Category>> categoryLiveData = new MutableLiveData<>();
    private ProductRepository productRepository;

    public MutableLiveData<Product> product = new MutableLiveData<Product>();
    public Uri imageUri;
    public String image_ext, image_name;

    // navigation variables
    MutableLiveData<Boolean> navigationToManageFragment = new MutableLiveData<Boolean>();

    public AddProductViewModel() {
        product.setValue(new Product());
    }

    public void init() {
        if (productLiveData != null) {
            productRepository = ProductRepository.getInstance();
            return;
        }
    }

    public void addProduct() {
        Log.e(TAG, "addProduct: " + product.getValue().getName());
        Log.e(TAG, "addProduct: " + product.getValue().getBrand());
        Log.e(TAG, "addProduct: " + product.getValue().getCategory());
        image_name = product.getValue().getName() + "_" + product.getValue().getBrand();
        ProductRepository.addProduct(product.getValue(), imageUri, image_name, image_ext);
        goToManagmentFragment();
    }

    public LiveData<ArrayList<Product>> getProductData() {

        productLiveData =productRepository.getProductData();

        return productLiveData;
    }

    public LiveData<ArrayList<Category>> getCategoryData() {

        categoryLiveData = productRepository.getCategoryData();

        return categoryLiveData;
    }


    public void goToManagmentFragment() {
        navigationToManageFragment.setValue(true);
        ;
    }

    public void endGoToManagmentFragment() {
        navigationToManageFragment.setValue(false);
        ;
    }


}
