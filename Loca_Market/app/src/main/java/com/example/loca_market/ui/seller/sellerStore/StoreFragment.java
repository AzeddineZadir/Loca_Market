package com.example.loca_market.ui.seller.sellerStore;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.example.loca_market.R;
import com.example.loca_market.data.models.Category;
import com.example.loca_market.data.models.Product;
import com.example.loca_market.data.models.Store;
import com.example.loca_market.data.repositores.UserRepository;
import com.example.loca_market.databinding.FragmentStoreBinding;
import com.example.loca_market.ui.seller.adapters.SellerStoreProductLineAdapter;
import com.example.loca_market.ui.seller.adapters.SellerStoreProductStaggeredAdapter;
import com.example.loca_market.ui.userAuth.LoginActivity;

import java.util.ArrayList;
import java.util.List;

public class StoreFragment extends Fragment implements SellerStoreProductLineAdapter.OnProductItemListener, SellerStoreProductStaggeredAdapter.OnProductItemListener {

    private final static String TAG = "StoreFragment";
    private FragmentStoreBinding binding;
    private StoreFragmentViewModel storeFragmentViewModel;
    ImageView iv_store_banner;
    private Toolbar toolbar_seller_store;
    private RecyclerView rv_linear_products, rv_grid_products;
    private List<Product> productList;
    private SellerStoreProductLineAdapter sellerStoreProductLineAdapter;
    private SellerStoreProductStaggeredAdapter sellerStoreProductStaggeredAdapter;
    private Store myStore;
    private TextView tv_store_name;
    private Button b_logout;


    public StoreFragment() {
        super(R.layout.fragment_store);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        storeFragmentViewModel = new ViewModelProvider(this).get(StoreFragmentViewModel.class);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_store, container, false);
        // get the bindede view
        View view = binding.getRoot();

        storeFragmentViewModel.init();
        binding.setStoreFragmentViewModel(storeFragmentViewModel);
        binding.setLifecycleOwner(this);

        toolbar_seller_store = (Toolbar) view.findViewById(R.id.toolbar_seller_store);

        b_logout = (Button) view.findViewById(R.id.b_store_logout);
        iv_store_banner = (ImageView) view.findViewById(R.id.iv_store_banner);

        productList = new ArrayList<>();


        b_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserRepository.logOut();
                Toast.makeText(getContext(), "you just signed out ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                intent.putExtra("role", "seller");
                startActivity(intent);
            }
        });
        b_logout.setVisibility(View.INVISIBLE);
        // observations
        observeStore();
        observeProductsBySeller();

        // recycler view liniear
        rv_linear_products = view.findViewById(R.id.rv_linear_products);
        sellerStoreProductLineAdapter = new SellerStoreProductLineAdapter(getContext(), this);
        rv_linear_products.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_linear_products.setAdapter(sellerStoreProductLineAdapter);

        // recycler view grid

        rv_grid_products = view.findViewById(R.id.rv_grid_products);
        sellerStoreProductStaggeredAdapter = new SellerStoreProductStaggeredAdapter(getContext(), this);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        rv_grid_products.setLayoutManager(staggeredGridLayoutManager);
        rv_grid_products.setAdapter(sellerStoreProductStaggeredAdapter);

        return view;
    }

    private void observeStore() {
        storeFragmentViewModel.storeMutableLiveData.observe(getViewLifecycleOwner(), new Observer<Store>() {
            @Override
            public void onChanged(Store store) {
                if (store.getName() != null) {

                    Glide.with(getContext()).load(store.getImageUrl()).into(iv_store_banner);
                    Window window = getActivity().getWindow();
                    // clear FLAG_TRANSLUCENT_STATUS flag:
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    // finally change the color
                    window.setStatusBarColor(store.getColor());
                    toolbar_seller_store.setBackgroundColor(store.getColor());
                    toolbar_seller_store.setTitle(store.getName());
                    if (store.isGrildView()) {

                        rv_linear_products.setVisibility(View.INVISIBLE);
                        rv_grid_products.setVisibility(View.VISIBLE);
                    } else {
                        rv_grid_products.setVisibility(View.INVISIBLE);
                        rv_linear_products.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    private void observeProductsBySeller() {
        storeFragmentViewModel.getProductBySellerUid().observe(getViewLifecycleOwner(), new Observer<ArrayList<Product>>() {
            @Override
            public void onChanged(ArrayList<Product> products) {
                productList = new ArrayList<>();
                productList = products;

                sellerStoreProductStaggeredAdapter.setProducts(products);
                sellerStoreProductLineAdapter.setProducts(products);


            }
        });
    }








    @Override
    public void onProductClick(int position) {

    }
}
