package com.example.loca_market.ui.seller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.loca_market.R;

public class ManageOffersFragment extends Fragment {

    CardView card_add_offer,card_update_offer,card_delete_offer;

    public ManageOffersFragment(){
        super(R.layout.fragment_manage_offers);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_offers, container, false);

        card_add_offer = (CardView)view.findViewById(R.id.card_add_offer);
        card_update_offer = (CardView)view.findViewById(R.id.card_update_offer);
        card_delete_offer = (CardView)view.findViewById(R.id.card_delete_offer);

        // gérer la navigations vers les differents fragment

        card_add_offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action = ManageOffersFragmentDirections.actionManageOffersFragmentToAddOfferFragment();
                Navigation.findNavController(view).navigate(action);

            }
        });

        card_update_offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action = ManageOffersFragmentDirections.actionManageOffersFragmentToUpdateOffersListFragment();
                Navigation.findNavController(view).navigate(action);

            }
        });

        card_delete_offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action = ManageOffersFragmentDirections.actionManageOffersFragmentToDeleteOfferListFragment();
                Navigation.findNavController(view).navigate(action);

            }
        });


        return view;
    }
}
