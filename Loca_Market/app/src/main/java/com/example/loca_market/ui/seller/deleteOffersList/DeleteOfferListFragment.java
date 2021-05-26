package com.example.loca_market.ui.seller.deleteOffersList;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loca_market.R;
import com.example.loca_market.data.models.Offer;
import com.example.loca_market.data.models.Product;
import com.example.loca_market.data.repositores.OfferRepository;
import com.example.loca_market.data.repositores.ProductRepository;
import com.example.loca_market.ui.seller.adapters.DeleteOfferAdapter;
import com.example.loca_market.ui.seller.adapters.DeleteProductAdapter;
import com.example.loca_market.ui.seller.deleteProductList.DeleteProductListViewModel;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class DeleteOfferListFragment extends Fragment implements DeleteOfferAdapter.OnOfferItemListener {
    private static final String TAG = "DeleteOfferListFragment";
    private RecyclerView rv_seller_delete_offer_list;
    private DeleteOfferAdapter deleteOfferAdapter;
    private DeleteOfferListViewModel deleteOfferListViewModel;
    private OfferRepository offerRepository;

    private List<Offer> offersList;
    private ImageView iv_drope_offert;


    public FirebaseUser currentuser;

    public DeleteOfferListFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delete_offer_list, container, false);
        Log.d(TAG, " in DeleteOfferListFragment  ");
        rv_seller_delete_offer_list = view.findViewById(R.id.rv_seller_delete_offer_list);
        offersList = new ArrayList<>();
        deleteOfferAdapter = new DeleteOfferAdapter(getContext(),this);
        rv_seller_delete_offer_list.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_seller_delete_offer_list.setAdapter(deleteOfferAdapter);


        // view model
        deleteOfferListViewModel = new ViewModelProvider(requireActivity()).get(DeleteOfferListViewModel.class);
        observeOffersBySeller();

        return view;
    }



    private void observeOffersBySeller() {
        deleteOfferListViewModel.getOfferBySellerUid().observe(getViewLifecycleOwner(), new Observer<ArrayList<Offer>>() {
            @Override
            public void onChanged(ArrayList<Offer> offers) {
                offersList=offers ;
                deleteOfferAdapter.setOffers(offers);
            }
        });
    }

    @Override
    public void onOfferClick(int position) {


    }

    @Override
    public void onDropOfferButtonClick(int position) {
        String offertoDelet = offersList.get(position).getOfferId();
        Log.e(TAG, "onDropProductButtonClick:");
        Snackbar snackbar = Snackbar.make(getView(), "voulez vous vraiment supprimer le produits " + offersList.get(position).getOfferTitel(), BaseTransientBottomBar.LENGTH_LONG)
                .setAction("OUI", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteOfferListViewModel.dropOffer(offertoDelet).observe(getViewLifecycleOwner(), new Observer<Boolean>() {
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
        observeOffersBySeller();

    }
}
