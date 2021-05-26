package com.example.loca_market.ui.seller.updateProductDetails;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.loca_market.R;
import com.example.loca_market.data.models.Category;
import com.example.loca_market.data.models.Product;
import com.example.loca_market.databinding.FragmentProductDetailsBinding;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ProductDetailsFragment extends Fragment {

    private final static String  TAG ="ProductDetailsFragment" ;
    private static final int PICK_IMAGE_REQUEST = 1;
    //data bindig
    private FragmentProductDetailsBinding binding ;
    private Product product ;
    private ProductDetailsViewModel productDetailsViewModel ;
    private ImageView iv_details_product ;
    private AutoCompleteTextView actv_details_product_category ;
    private Uri newImage ;

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

        // observations
        observeProduct();
        observeSbconfirmation();
        observeCategories();

        return  view ;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK
                && data != null && data.getData() != null) {
            newImage = data.getData();
            Picasso.get().load(newImage).into(iv_details_product);
        }
        productDetailsViewModel.new_image = newImage ;
        productDetailsViewModel.new_imge_extention = getFileExtension(newImage);
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

    private void observeSbconfirmation(){
        productDetailsViewModel.sbConfirmation.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean result) {
                if (result== true){
                    Snackbar snackbar =   Snackbar.make(binding.getRoot(),"produit correctement mis a joure ", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            }
        });
    }

    private  void observeProduct(){
        productDetailsViewModel.product.observe(getViewLifecycleOwner(), new Observer<Product>() {
            @Override
            public void onChanged(Product product) {

                Glide.with(getContext()).load(product.getImageUrl()).into(iv_details_product);

            }
        });
    }

    private void  observeCategories(){
        productDetailsViewModel.getCategoryData().observe(getViewLifecycleOwner(), new Observer<ArrayList<Category>>() {
            @Override
            public void onChanged(ArrayList<Category> categoryArrayList) {
                Log.e(TAG, "onChanged categories: " + categoryArrayList.size()  );

                ArrayList<String>stringArrayList=  getCategoriesNameList(categoryArrayList);
                setCategoryAdapter(stringArrayList);

            }
        });
    }

    private ArrayList<String> getCategoriesNameList(ArrayList<Category>categoryArrayList){
        ArrayList<String>stringArrayList = new ArrayList<>();
        for (Category category:categoryArrayList) {
            stringArrayList.add(category.getName());
        }
        return  stringArrayList;
    }
    private void setCategoryAdapter(ArrayList<String>stringArrayList){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, stringArrayList);
        actv_details_product_category.setAdapter(adapter);
    }
}
