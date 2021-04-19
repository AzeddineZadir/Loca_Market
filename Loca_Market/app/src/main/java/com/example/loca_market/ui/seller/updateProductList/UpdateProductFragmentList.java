package com.example.loca_market.ui.seller.updateProductList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loca_market.R;
import com.example.loca_market.data.models.Product;
import com.example.loca_market.ui.seller.adapters.ProductAdapter;

import java.util.ArrayList;
import java.util.List;

public class UpdateProductFragmentList extends Fragment {
    private UpdateProductListViewModel updateProductListViewModel ;
    private RecyclerView rv_sellers_product_list;
    private ProductAdapter productAdapter;
    private List<Product> productList;

    public UpdateProductFragmentList() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_product_list, container, false);

        rv_sellers_product_list = view.findViewById(R.id.rv_sellers_product_list);
        productList = new ArrayList<>();
        productAdapter = new ProductAdapter(getContext());
        rv_sellers_product_list.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_sellers_product_list.setAdapter(productAdapter);
        // view model
        updateProductListViewModel = new ViewModelProvider(requireActivity()).get(UpdateProductListViewModel.class);
        observeProductsBySeller();
        return view;
    }

    private void observeProductsBySeller() {
        updateProductListViewModel.getProductBySellerUid().observe(getViewLifecycleOwner(), new Observer<ArrayList<Product>>() {
            @Override
            public void onChanged(ArrayList<Product> products) {
                productAdapter.setProducts(products);
            }
        });
    }
}
