package com.example.loca_market.data.repositores;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.example.loca_market.data.models.Offer;
import com.example.loca_market.data.models.Offer;
import com.example.loca_market.data.models.Product;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class OfferRepository {
    public static final String TAG = "OfferRepository";
    private static final String OFFERS = "offers";
    private static final FirebaseFirestore fdb = FirebaseFirestore.getInstance();
    private static final CollectionReference offersRef = fdb.collection(OFFERS);
    private static OfferRepository instance ;
    private static FirebaseUser currentUser ;

    public static OfferRepository getInstance() {
        if (instance == null) {
            instance = new OfferRepository();
            currentUser= FirebaseAuth.getInstance().getCurrentUser();

        }
        return instance;
    }

    public MutableLiveData<ArrayList<Offer>> getOffersBySeller() {
        MutableLiveData<ArrayList<Offer>> data = new MutableLiveData<>();
        loadOffersBySeller(data);
        return data;
    }

    // recuperer tous les produits d'un vendeur de firestore et les mettre dans un mutable live data
    private void loadOffersBySeller(MutableLiveData<ArrayList<Offer>> offerLiveData) {


        offersRef.whereEqualTo("sellerId", currentUser.getUid()).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                ArrayList<Offer> offerArrayList = new ArrayList<>();
                if (error != null) {
                    Log.w(TAG, "Listen failed.", error);
                    return;
                }
                if (!value.isEmpty()) {
                    List<DocumentSnapshot> documentSnapshotList = value.getDocuments();

                    for (DocumentSnapshot documentSnapshot : documentSnapshotList) {
                        offerArrayList.add(documentSnapshot.toObject(Offer.class));
                    }

                    offerLiveData.setValue(offerArrayList);

                }
            }
        });


    }



    public MutableLiveData<Boolean> deleteOfferByid(Offer offer){
        MutableLiveData<Boolean > data = new MutableLiveData<>();
        delete(data,offer);
        return  data;



    }

    private void delete(MutableLiveData<Boolean> dropStatus ,  Offer offer){
        offersRef.document(offer.getOfferId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // update the product
                Product productToUp = offer.getOfferProduct();

                fdb.collection("products").document(offer.getProductOfferId()).update("percentage",Float.parseFloat("0")).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e(TAG, "onSuccess: to update the product " + productToUp.getName());

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: to update the product " +e.getMessage());

                    }
                });

                dropStatus.setValue(true) ;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dropStatus.setValue(false) ;
            }
        });

    }
}
