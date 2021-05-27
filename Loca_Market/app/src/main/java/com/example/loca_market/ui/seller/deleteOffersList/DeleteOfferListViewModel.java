package com.example.loca_market.ui.seller.deleteOffersList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.loca_market.data.models.Offer;
import com.example.loca_market.data.repositores.OfferRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class DeleteOfferListViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Offer>> offersLiveData;
    private MutableLiveData<Boolean> dropStatuLiveData;
    private final OfferRepository offerRepository;
    public FirebaseUser currentuser;


    public DeleteOfferListViewModel() {
        offersLiveData = new MutableLiveData<>();
        dropStatuLiveData = new MutableLiveData<>();
        offerRepository = OfferRepository.getInstance();
        currentuser = FirebaseAuth.getInstance().getCurrentUser();
        offersLiveData = offerRepository.getOffersBySeller();

    }

    public void init() {

        if (offersLiveData != null) {

            return;
        }
    }

    public LiveData<ArrayList<Offer>> getOfferBySellerUid() {
        offersLiveData = offerRepository.getOffersBySeller();
        return offersLiveData;
    }


    public LiveData<Boolean> dropOffer(Offer offer) {

        dropStatuLiveData = offerRepository.deleteOfferByid(offer);

        return dropStatuLiveData;
    }


}
