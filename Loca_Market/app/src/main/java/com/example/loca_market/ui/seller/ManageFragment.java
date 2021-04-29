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

public class ManageFragment  extends Fragment {

    CardView card_manage_product,card_manage_store;

    public ManageFragment(){
        super(R.layout.fragment_manage);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage, container, false);

        card_manage_product = (CardView)view.findViewById(R.id.card_manage_product);
        card_manage_store = (CardView)view.findViewById(R.id.card_manage_store);

        // gérer la navigations vers les differents fragment

        card_manage_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action = ManageFragmentDirections.actionManageFragmentToManageProductFragment();
                Navigation.findNavController(view).navigate(action);

            }
        });

        card_manage_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action = ManageFragmentDirections.actionManageFragmentToManageStoreFragment();
                Navigation.findNavController(view).navigate(action);

            }
        });




        return view;
    }
}
