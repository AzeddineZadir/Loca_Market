package com.example.loca_market.data.repositores;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.loca_market.data.models.Order;
import com.example.loca_market.data.models.Order;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class OrdersRepository {
    public static final String TAG = "OrdersRepository";
    private static final String ORDERS = "orders";
    private static final FirebaseFirestore fdb = FirebaseFirestore.getInstance();
    private static final CollectionReference ordersRef = fdb.collection(ORDERS);
    private static OrdersRepository instance ;

    public static OrdersRepository getInstance() {
        if (instance == null) {
            instance = new OrdersRepository();
        }
        return instance;
    }

    //recuperer les tous les produis de firstore grace a loadOrderDaa et les passer au view model
    public MutableLiveData<ArrayList<Order>> getOrdersData() {

        MutableLiveData<ArrayList<Order>> data = new MutableLiveData<>();
        loadOrdersData(data);
        return data;
    }

    private void loadOrdersData(MutableLiveData<ArrayList<Order>> ordersLiveData) {

        ArrayList<Order> ordersArrayList = new ArrayList<>();

        ordersRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                if (!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> documentSnapshotList = queryDocumentSnapshots.getDocuments();

                    for (DocumentSnapshot documentSnapshot : documentSnapshotList) {
                        ordersArrayList.add(documentSnapshot.toObject(Order.class));
                    }
                    ordersLiveData.setValue(ordersArrayList);

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure: ", e);
            }
        });


    }


    public MutableLiveData<Boolean> validateOrderByid(String order_id){
        MutableLiveData<Boolean > data = new MutableLiveData<>();
        validate(data,order_id);
        return  data;



    }

    private void validate(MutableLiveData<Boolean> validateStatus ,  String order_id){

        ordersRef.document(order_id).update("state","Accépté").addOnSuccessListener(new OnSuccessListener<Void>() {


            @Override
            public void onSuccess(Void aVoid) {
                validateStatus.setValue(true) ;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                validateStatus.setValue(false) ;
            }
        });

    }

    public MutableLiveData<Boolean> deleteOrderByid(String order_id){
        MutableLiveData<Boolean > data = new MutableLiveData<>();
        delete(data,order_id);
        return  data;



    }

    private void delete(MutableLiveData<Boolean> dropStatus ,  String order_id){
        ordersRef.document(order_id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {


            @Override
            public void onSuccess(Void aVoid) {
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
