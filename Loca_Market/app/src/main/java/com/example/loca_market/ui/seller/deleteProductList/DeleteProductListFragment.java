package com.example.loca_market.ui.seller.deleteProductList;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loca_market.R;
import com.example.loca_market.data.models.Product;
import com.example.loca_market.data.repositores.ProductRepository;
import com.example.loca_market.ui.seller.adapters.DeleteProductAdapter;
import com.example.loca_market.ui.seller.adapters.UpdateProductAdapter;
import com.example.loca_market.ui.seller.updateProductList.UpdateProductListFragmentDirections;
import com.example.loca_market.ui.seller.updateProductList.UpdateProductListViewModel;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class DeleteProductListFragment  extends Fragment implements DeleteProductAdapter.OnProductItemListener {
    private static final String TAG = "DeleteProductList";
    private RecyclerView rv_sellers_product_list;
    private DeleteProductAdapter deleteProductAdapter;
    private DeleteProductListViewModel deleteProductListViewModel;
    private ProductRepository productRepository;
    private List<Product> productList;
    private ImageView iv_drope_product;


    public FirebaseUser currentuser;

    public DeleteProductListFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delete_product_list, container, false);
        Log.d(TAG, " in DeleteProductListFragment  ");
        rv_sellers_product_list = view.findViewById(R.id.rv_sellers_delete_product_list);
        productList = new ArrayList<>();
        deleteProductAdapter = new DeleteProductAdapter(getContext(), this);
        rv_sellers_product_list.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_sellers_product_list.setAdapter(deleteProductAdapter);

        // view model
        deleteProductListViewModel = new ViewModelProvider(requireActivity()).get(DeleteProductListViewModel.class);
        observeProductsBySeller();
        return view;
    }

    private void observeProductsBySeller() {
        deleteProductListViewModel.getProductBySellerUid().observe(getViewLifecycleOwner(), new Observer<ArrayList<Product>>() {
            @Override
            public void onChanged(ArrayList<Product> products) {
                productList = products;

                deleteProductAdapter.setProducts(products);

            }
        });
    }

    @Override
    public void onProductClick(int position) {

        Log.d(TAG, "onProductClick: in position = " + position);
        String productUid = productList.get(position).getPid();

    }

    @Override
    public void onDropProductButtonClick(int position) {

        String productToDeleteUid = productList.get(position).getPid();
        Log.e(TAG, "onDropProductButtonClick:");
        Snackbar snackbar = Snackbar.make(getView(), "voulez vous vraiment supprimer le produits " + productList.get(position).getName(), BaseTransientBottomBar.LENGTH_LONG)
                .setAction("OUI", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteProductListViewModel.dropProduct(productToDeleteUid).observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                            @Override
                            public void onChanged(Boolean aBoolean) {
                                if (aBoolean == true) {
                                    Snackbar.make(getView(), "le produit a été supprimé correctement", BaseTransientBottomBar.LENGTH_LONG).show();


                                } else {
                                    Snackbar.make(getView(), "un probleme lors de la suppression ", BaseTransientBottomBar.LENGTH_LONG).show();
                                }
                            }
                        });


                    }
                });
        snackbar.show();
    }

}
