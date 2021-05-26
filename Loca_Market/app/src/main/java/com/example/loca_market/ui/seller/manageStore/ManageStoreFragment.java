package com.example.loca_market.ui.seller.manageStore;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.loca_market.R;
import com.example.loca_market.data.models.Category;
import com.example.loca_market.data.models.Product;
import com.example.loca_market.data.models.Store;
import com.example.loca_market.databinding.FragmentManageStoreBinding;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import petrov.kristiyan.colorpicker.ColorPicker;


public class ManageStoreFragment extends Fragment {

    private final static String  TAG ="ManageStoreFragment" ;
    private static final int PICK_IMAGE_REQUEST = 1;
    // data binding
    private FragmentManageStoreBinding binding ;
    private ManageStoreFragmentViewModel manageStoreFragmentViewModel ;
    private ImageView iv_store_cover ;
    private AutoCompleteTextView actv_store_category ;
    private Uri newImage ;
    Button b_color_choser ;
    CheckBox cb_lineair , cb_grid ;


    public ManageStoreFragment(){
        super(R.layout.fragment_manage_store);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        manageStoreFragmentViewModel =  new ViewModelProvider(this).get(ManageStoreFragmentViewModel.class);
        // inflate the databinded view
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_manage_store, container, false);

        // get the bindede view
        View view = binding.getRoot();

        manageStoreFragmentViewModel.init();

        // data binding
        binding.setManageStoreFragmentViewModel(manageStoreFragmentViewModel);
        binding.setLifecycleOwner(this);
        // image view evvent listener
        iv_store_cover = (ImageView) view.findViewById(R.id.iv_store_cover);

        iv_store_cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();

            }
        });

        // AutoCompleteTextView de la category
        actv_store_category = (AutoCompleteTextView) view.findViewById(R.id.actv_store_category);


        b_color_choser = (Button)view.findViewById(R.id.b_color_choser) ;
        b_color_choser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorPicker colorPicker=new ColorPicker(getActivity());
                colorPicker.setRoundColorButton(true);
                colorPicker.show();
                colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                    @Override
                    public void onChooseColor(int position, int color) {

                        b_color_choser.setBackgroundColor(color);

                        manageStoreFragmentViewModel.storeMutableLiveData.getValue().setColor(color);

                    }

                    @Override
                    public void onCancel() {

                    }
                });


            }
        });

        // les checkbox

        cb_lineair = (CheckBox)view.findViewById(R.id.cb_lineair);

        cb_lineair.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){cb_grid.setVisibility(View.INVISIBLE);}
                if (!isChecked){cb_grid.setVisibility(View.VISIBLE);}
                manageStoreFragmentViewModel.storeMutableLiveData.getValue().setGrildView(false);
            }
        });
        cb_grid = (CheckBox)view.findViewById(R.id.cb_grid);
        cb_grid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){cb_lineair.setVisibility(View.INVISIBLE);}
                if (!isChecked){cb_lineair.setVisibility(View.VISIBLE);}
                manageStoreFragmentViewModel.storeMutableLiveData.getValue().setGrildView(true);
            }
        });


        // observations
        observeStore();
        observeCategories();
        observeSbconfirmation();

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
            newImage = data.getData();
            Picasso.get().load(newImage).into(iv_store_cover);
        }
        manageStoreFragmentViewModel.store_image_uri = newImage ;
        manageStoreFragmentViewModel.store_image_extention = getFileExtension(newImage);
    }

    private  void observeStore(){
        manageStoreFragmentViewModel.storeMutableLiveData.observe(getViewLifecycleOwner(), new Observer<Store>() {
            @Override
            public void onChanged(Store store) {
                Glide.with(getContext()).load(store.getImageUrl()).into(iv_store_cover);
                b_color_choser.setBackgroundColor(store.getColor());
                if (store.isGrildView()){
                    cb_grid.setVisibility(View.VISIBLE);
                    cb_grid.setChecked(true);
                    cb_lineair.setVisibility(View.INVISIBLE);
                    cb_lineair.setChecked(false);
                }else {
                    cb_lineair.setVisibility(View.VISIBLE);
                    cb_lineair.setChecked(true);
                    cb_grid.setVisibility(View.INVISIBLE);
                    cb_grid.setChecked(false);
                }
            }
        });
    }

    private void observeSbconfirmation(){
        manageStoreFragmentViewModel.sbConfirmation.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean result) {
                if (result== true){
                    Snackbar snackbar =   Snackbar.make(binding.getRoot(),"boutique  mise a joure ", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            }
        });
    }

    private void  observeCategories(){
        manageStoreFragmentViewModel.getCategoryData().observe(getViewLifecycleOwner(), new Observer<ArrayList<Category>>() {
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
        actv_store_category.setAdapter(adapter);
    }
}
