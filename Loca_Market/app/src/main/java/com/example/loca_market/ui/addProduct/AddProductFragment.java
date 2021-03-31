package com.example.loca_market.ui.addProduct;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.loca_market.R;
import com.example.loca_market.data.models.Product;
import com.example.loca_market.databinding.FragmentAddProductBinding;

public class AddProductFragment extends Fragment {

    private final static String  TAG ="AddProductFragment" ;
    // data binding
    FragmentAddProductBinding binding  ;
    private Product product ;
    private ProductViewModel productViewModel ;


    public AddProductFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // inflate the databinded view
        binding =DataBindingUtil.inflate(inflater,R.layout.fragment_add_product, container, false);
        // get the bindede view
        View view = binding.getRoot();

        //here data must be an instance of the class MarsDataProvider


        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        binding.setProductToAdd(productViewModel);





        return view;
    }
}
