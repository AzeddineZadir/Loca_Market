package com.example.loca_market.data.repositores;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.loca_market.data.models.Product;
import com.example.loca_market.data.models.Store;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class StoreRepository {

    public static final String TAG = "StoreRepository";
    private static final String STORES = "stores";
    private static StoreRepository instance ;
    private static final FirebaseFirestore fdb = FirebaseFirestore.getInstance();
    private static final CollectionReference storeRef = fdb.collection(STORES);
    private static final FirebaseStorage storage = FirebaseStorage.getInstance();
    //construction d'une référance
    private static final StorageReference storageRef = storage.getReference("stores_imges");
    public boolean updateStatue;

    public static StoreRepository getInstance(){
        if (instance == null){
            instance = new StoreRepository() ;
        }
        return instance  ;
    }

    //modififer un magasin
    public static  void addStore(Store store){
        storeRef.document(store.getSid()).set(store).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.e(TAG, "onSuccess: adding store" );
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure:  adding store" ,e);
            }
        });
    }

    // récupérer la boutique d'un vendeur grace a son id
    public MutableLiveData<Store> getStoreBySellerUid(String sellerUid){
        MutableLiveData<Store> data = new MutableLiveData<>();
        loadStoreByUid(data, sellerUid);
        return data;
    }

    private void loadStoreByUid(MutableLiveData<Store> storeMutableLiveData , String sellerUid){
        storeRef.document(sellerUid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                storeMutableLiveData.setValue(documentSnapshot.toObject(Store.class));

                Log.e(TAG, "onSuccess: store recived" + documentSnapshot.toObject(Product.class).getName());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure: in getting store");
            }
        });

    }

    public boolean updateStore(Store store, Uri mImageUri, String image_name, String image_extention) {

        DocumentReference reference = storeRef.document(store.getSid());
        storeRef.document(store.getSid()).set(store, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.e(TAG, "onSuccess: to update the store " + store.getName());
                if (mImageUri != null) {
                    uploadStoreImage(mImageUri, image_name, image_extention, reference);
                }

                updateStatue = true;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure: to update the store " + store.getName());

            }
        });

        return updateStatue;
    }

    private void uploadStoreImage(Uri mImageUri, String image_name, String image_extention, DocumentReference reference) {
        StorageReference imageStorageReference = storageRef.child(image_name + "." + image_extention);
        UploadTask uploadTask = imageStorageReference.putFile(mImageUri);
        // Dabord récupérer le URI de l'image que on viens de uploade
        // puis transformer URI en  string
        // ensuite mettre ajoure le produis concérné
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return imageStorageReference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                // si on arrive a avoir le lins de limage
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    String image_url = downloadUri.toString();
                    Log.e(TAG, "onComplete: get uri + " + image_url);
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

                } else {
                    Log.e(TAG, "onFailure: to get the URI ");
                }
            }
        });


    }

}
