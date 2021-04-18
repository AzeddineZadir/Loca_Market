package com.example.loca_market.ui.seller.addProduct;

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
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.loca_market.R;
import com.example.loca_market.data.models.Category;
import com.example.loca_market.data.models.Product;
import com.example.loca_market.databinding.FragmentAddProductBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AddProductFragment extends Fragment {

    private final static String  TAG ="AddProductFragment" ;
    private static final int PICK_IMAGE_REQUEST = 1;
    // data binding
    private FragmentAddProductBinding binding  ;
    private Product product ;
    private AddProductViewModel addProductViewModel;
    private Uri mImageUri;
    private ImageView iv_product ;
    private AutoCompleteTextView actv_product_category ;
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
        addProductViewModel = new ViewModelProvider(this).get(AddProductViewModel.class);
        addProductViewModel.init();
        // data binding
        binding.setProductToAddViewModel(addProductViewModel);
        // image view evvent listener
        iv_product = (ImageView) view.findViewById(R.id.iv_product);
        iv_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            openFileChooser();

            }
        });

        // AutoCompleteTextView de la category
        actv_product_category = (AutoCompleteTextView) view.findViewById(R.id.actv_product_category);



        // observations
        observeNavigationToManagementFragment(view);

        observeProducts();
        observeCategories();
        return view;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).into(iv_product);
        }
        addProductViewModel.imageUri = mImageUri ;
        addProductViewModel.image_ext = getFileExtension(mImageUri);


    }

    // observations
    private void observeNavigationToManagementFragment(View view){
        addProductViewModel.navigationToManageFragment.observe(getViewLifecycleOwner(),result -> {
            if (result == true ){
                NavDirections action = AddProductFragmentDirections.actionAddProductFragmentToManageFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });

        addProductViewModel.endGoToManagmentFragment();
    }

    private void  observeProducts(){
        addProductViewModel.getProductData().observe(getViewLifecycleOwner(), new Observer<ArrayList<Product>>() {
            @Override
            public void onChanged(ArrayList<Product> products) {
                Log.e(TAG, "onChanged: " + products.size()  );
            }
        });
    }

    private void  observeCategories(){
        addProductViewModel.getCategoryData().observe(getViewLifecycleOwner(), new Observer<ArrayList<Category>>() {
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
        actv_product_category.setAdapter(adapter);
    }
}
