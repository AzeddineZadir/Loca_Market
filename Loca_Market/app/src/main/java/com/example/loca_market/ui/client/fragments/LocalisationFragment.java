package com.example.loca_market.ui.client.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.loca_market.R;
import com.example.loca_market.data.models.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class LocalisationFragment extends Fragment {

    private FirebaseFirestore mStore;
    FirebaseAuth mAuth;
    EditText et_address, et_city, et_postalCode, et_country;
    User currentUser =null;
    public LocalisationFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_localisation, container, false);
        et_address = view.findViewById(R.id.et_address_localisation);
        et_city=view.findViewById(R.id.et_localisation_city);
        et_postalCode = view.findViewById(R.id.et_postal_code_localisation);
        et_country = view.findViewById(R.id.et_country_localisation);
        mStore =FirebaseFirestore.getInstance();
        mAuth =FirebaseAuth.getInstance();
        insertData();
        return  view;
    }

    private void insertData() {
        mStore.collection("users").document(mAuth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                currentUser = documentSnapshot.toObject(User.class);
                if(currentUser != null){
                    if(currentUser.getAddress()!=null && !currentUser.getAddress().isEmpty())
                        et_address.setText(currentUser.getAddress());
                    if(currentUser.getCity()!=null && !currentUser.getCity().isEmpty())
                        et_city.setText(currentUser.getCity());
                    if(currentUser.getPostalCode()!=null && !currentUser.getPostalCode().isEmpty())
                        et_postalCode.setText(currentUser.getPostalCode());
                    if(currentUser.getCountry()!=null && !currentUser.getCountry().isEmpty())
                        et_country.setText(currentUser.getCountry());
                }
            }
        });
    }

    public void saveData(){

        Map<String,Object> mapInfo = new HashMap<>();
        if(et_address!=null && !et_address.getText().toString().trim().isEmpty() ){
            mapInfo.put("address",et_address.getText().toString().trim());
        }
        if(et_city!=null && !et_city.getText().toString().trim().isEmpty()){
            mapInfo.put("city",et_city.getText().toString().trim());
        }
        if(et_postalCode!=null && !et_postalCode.getText().toString().trim().isEmpty()){
            mapInfo.put("postalCode",et_postalCode.getText().toString().trim());
        }
        if (et_country!=null && !et_country.getText().toString().trim().isEmpty()){
            mapInfo.put("country",et_country.getText().toString().trim());
        }
        if(!mapInfo.isEmpty())
        mStore.collection("users").document(mAuth.getCurrentUser().getUid()).update(mapInfo);
    }
}