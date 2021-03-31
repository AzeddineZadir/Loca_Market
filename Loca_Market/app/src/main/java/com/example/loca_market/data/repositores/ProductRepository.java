package com.example.loca_market.data.repositores;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.loca_market.data.models.Product;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    public static final  String TAG = "ProductRepository" ;
    private static final String PRODUCTS ="products" ;
    private static ProductRepository instance;
    private ArrayList<Product> productArrayList = new ArrayList<>();
    private static  final FirebaseFirestore fdb = FirebaseFirestore.getInstance();
    private static final CollectionReference productRef = fdb.collection(PRODUCTS);


    public static ProductRepository getInstance(Context context) {
        if (instance == null) {
            instance = new ProductRepository();
        }
        return instance;
    }



    public static  void addProduct (Product product){


        DocumentReference new_product_uid = productRef.document();
        productRef.document(new_product_uid.getId()).set(product).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.e(TAG+"ADD", "onSuccess: " );
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG+"ADD", "onFailure: ",e );
            }
        });



    }

    public MutableLiveData<ArrayList<Product>> getProductData() {

        loadProductData();

        MutableLiveData<ArrayList<Product>> data = new MutableLiveData<>();
        data.setValue(productArrayList);
        return data;
    }


    private void loadProductData() {

        productRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> documentSnapshotList = queryDocumentSnapshots.getDocuments();

                    for (DocumentSnapshot documentSnapshot : documentSnapshotList) {
                        productArrayList.add(documentSnapshot.toObject(Product.class));
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure: ", e);
            }
        });

    }

}
