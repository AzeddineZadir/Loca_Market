package com.example.loca_market.ui.seller.productDetails;

import android.net.Uri;
import android.view.View;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.loca_market.data.models.Category;
import com.example.loca_market.data.models.Product;
import com.example.loca_market.data.repositores.ProductRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class ProductDetailsViewModel  extends ViewModel {
    private ProductRepository productRepository;
    public MutableLiveData<Product> product = new MutableLiveData<>() ;



    public ProductDetailsViewModel() {
        productRepository = ProductRepository.getInstance();
        product.setValue(new Product());

         ;
    }

    public void init(String productUid) {
        getProductDetails(productUid);
        if ( product!= null) {
            return ;

        }

    }

    public MutableLiveData<Product> getProductDetails(String productUid){


        product=productRepository.getProductByUid(productUid) ;

        return  product ;
    }
}
