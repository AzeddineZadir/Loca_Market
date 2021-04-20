package com.example.loca_market.ui.seller.productDetails;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.webkit.MimeTypeMap;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.loca_market.R;
import com.example.loca_market.data.models.Product;
import com.example.loca_market.databinding.FragmentProductDetailsBinding;
import com.example.loca_market.ui.seller.addProduct.AddProductViewModel;

public class ProductDetailsFragment extends Fragment {

    private final static String  TAG ="ProductDetailsFragment" ;
    private static final int PICK_IMAGE_REQUEST = 1;
    //data bindig
    private FragmentProductDetailsBinding binding ;
    private Product product ;
    private ProductDetailsViewModel productDetailsViewModel ;
    private ImageView iv_details_product ;
    private AutoCompleteTextView actv_details_product_category ;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // récupération de largumnt passer par le fragment product list
        String productUid = ProductDetailsFragmentArgs.fromBundle(getArguments()).getProductUid();




        // Obtain the ViewModel component.
        productDetailsViewModel = new ViewModelProvider(this).get(ProductDetailsViewModel.class);

        // inflate the databinded view
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_details, container, false);

        // get the bindede view
        View view = binding.getRoot();

        productDetailsViewModel.init(productUid);


        // data binding
        binding.setProductDetailsViewModel(productDetailsViewModel);
        binding.setLifecycleOwner(this);
        // image view evvent listener
        iv_details_product = (ImageView) view.findViewById(R.id.iv_details_product);
        iv_details_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();

            }
        });

        // AutoCompleteTextView de la category
        actv_details_product_category = (AutoCompleteTextView) view.findViewById(R.id.actv_details_product_category);
        return  view ;
    }


    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getActivity().getApplicationContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }



}
