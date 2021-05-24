package com.example.loca_market.ui.client.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.loca_market.R;
import com.example.loca_market.data.models.Category;
import com.example.loca_market.data.models.Order;
import com.example.loca_market.data.models.Product;
import com.example.loca_market.data.models.User;
import com.example.loca_market.ui.client.Activities.OrdersManagement;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;


public class PersonnalInformationsFragment extends Fragment {

    private FirebaseFirestore mStore;
    FirebaseAuth mAuth;
    EditText edFirstName, edLastName, edUserName, edPhoneNumber;
    User currentUser =null;

    public PersonnalInformationsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  =inflater.inflate(R.layout.fragment_personnal_informations, container, false);
        edFirstName =view.findViewById(R.id.et_firstName_personnalInformation);
        edLastName =view.findViewById(R.id.et_lastName_personnalInformation);
        edUserName =view.findViewById(R.id.et_username_personal_information);
        edPhoneNumber = view.findViewById(R.id.et_phoneNumber_personal_information);
        mStore =FirebaseFirestore.getInstance();
        mAuth=FirebaseAuth.getInstance();
        insertData();

        return view;
    }

    private void insertData() {
        mStore.collection("users").document(mAuth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                 currentUser = documentSnapshot.toObject(User.class);
                 if(currentUser != null){
                     if(currentUser.getFirstName()!=null && !currentUser.getFirstName().isEmpty())
                         edFirstName.setText(currentUser.getFirstName());
                     if(currentUser.getLastName()!=null && !currentUser.getLastName().isEmpty())
                         edLastName.setText(currentUser.getLastName());
                     if(currentUser.getUsername()!=null && !currentUser.getUsername().isEmpty())
                         edUserName.setText(currentUser.getUsername());
                     if(currentUser.getPhoneNumber()!=null && !currentUser.getPhoneNumber().isEmpty())
                         edPhoneNumber.setText(currentUser.getPhoneNumber());
                 }
            }
        });

    }

    public void saveData(){

        Map<String,Object> mapInfo = new HashMap<>();
        if(edFirstName!=null && !edFirstName.getText().toString().trim().isEmpty() ){
            mapInfo.put("firstName",edFirstName.getText().toString().trim());
        }
        if(edLastName!=null && !edLastName.getText().toString().trim().isEmpty()){
            mapInfo.put("lastName",edLastName.getText().toString().trim());
        }
        if(edUserName!=null && !edUserName.getText().toString().trim().isEmpty()){
            mapInfo.put("username",edUserName.getText().toString().trim());
        }
        if (edPhoneNumber!=null && !edPhoneNumber.getText().toString().trim().isEmpty()){
            mapInfo.put("phoneNumber",edPhoneNumber.getText().toString().trim());
        }
        if (!mapInfo.isEmpty())
        mStore.collection("users").document(mAuth.getCurrentUser().getUid()).update(mapInfo);
    }
}