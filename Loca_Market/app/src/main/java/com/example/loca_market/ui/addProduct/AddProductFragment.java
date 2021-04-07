package com.example.loca_market.ui.addProduct;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.loca_market.R;
import com.example.loca_market.data.models.Product;
import com.example.loca_market.data.repositores.ProductRepository;
import com.example.loca_market.databinding.FragmentAddProductBinding;
import com.google.android.play.core.internal.ad;
import com.squareup.picasso.Picasso;

public class AddProductFragment extends Fragment {

    private final static String  TAG ="AddProductFragment" ;
    private static final int PICK_IMAGE_REQUEST = 1;
    // data binding
    FragmentAddProductBinding binding  ;
    private Product product ;
    private AddProductViewModel addProductViewModel;
    private Uri mImageUri;

    ImageView iv_product ;

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

        binding.setProductToAdd(addProductViewModel);

        iv_product = (ImageView) view.findViewById(R.id.iv_product);

        iv_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            openFileChooser();

            }
        });



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



}
