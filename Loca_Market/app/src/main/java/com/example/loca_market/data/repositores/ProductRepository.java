package com.example.loca_market.data.repositores;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.loca_market.data.models.Product;
import com.example.loca_market.data.models.ProductImage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    public static final String TAG = "ProductRepository";
    private static final String PRODUCTS = "products";
    private static ProductRepository instance;
    private ArrayList<Product> productArrayList = new ArrayList<>();

    private static final FirebaseFirestore fdb = FirebaseFirestore.getInstance();
    private static final CollectionReference productRef = fdb.collection(PRODUCTS);
    // delaration de l'instence de storage
    private static final FirebaseStorage storage = FirebaseStorage.getInstance();
    //construction d'une référance
    private static final StorageReference storageRef = storage.getReference("products_imges");



    public static ProductRepository getInstance(Context context) {
        if (instance == null) {
            instance = new ProductRepository();

        }
        return instance;
    }


    public static void addProduct(Product product, Uri mImageUri, String image_name, String image_extention) {


        DocumentReference new_product_uid = productRef.document();
        productRef.document(new_product_uid.getId()).set(product).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.e(TAG + "ADD", "onSuccess: ");
                // si le produits a été ajouter correctement
                // on upload son image

                uploadProductImage(mImageUri, image_name, image_extention, new_product_uid);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG + "ADD", "onFailure: ", e);
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


    public static void uploadProductImage(Uri mImageUri, String image_name, String image_extention, DocumentReference reference) {
        StorageReference imageStorageReference = storageRef.child(image_name + "." + image_extention);


        imageStorageReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.e(TAG + "UPL ", "onSuccess: ");
                // on crrer l'ojet image a ajouter dans notre base de donée
                // on récupére lur de l'image que on viens d'ajouter
                // String image_url = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString() ;
                String image_url = taskSnapshot.getStorage().getDownloadUrl().toString();
                // on instencie l'objet a ajouter dans la base
                ProductImage productImageToAdd = new ProductImage(image_name, image_url);
                reference.update("imageUrl", image_url).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e(TAG, "onSuccess: added image url in database  ");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: added image url in database ");
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG + "UPL", "onFailure: ", e);
            }
        });
    }


    //  navigation


}
