package com.example.loca_market.data.repositores;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.example.loca_market.data.models.Category;
import com.example.loca_market.data.models.Product;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    public static final String TAG = "ProductRepository";
    private static final String PRODUCTS = "products";
    private static final String CATEGORIES = "categories";
    private static ProductRepository instance;
    private static final FirebaseFirestore fdb = FirebaseFirestore.getInstance();
    private static final CollectionReference productRef = fdb.collection(PRODUCTS);
    private static final CollectionReference categoryRef = fdb.collection(CATEGORIES);
    // delaration de l'instence de storage
    private static final FirebaseStorage storage = FirebaseStorage.getInstance();
    //construction d'une référance
    private static final StorageReference storageRef = storage.getReference("products_imges");


    public static ProductRepository getInstance() {
        if (instance == null) {
            instance = new ProductRepository();
        }
        return instance;
    }
    //ajouter un produit a la collection products toute en uploadent l'image de ce dernier dans le cloud
    public static void addProduct(Product product, Uri mImageUri, String image_name, String image_extention) {

        DocumentReference new_product_uid = productRef.document();
        productRef.document(new_product_uid.getId()).set(product).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.e(TAG + "ADD", "onSuccess: ");
                // si le produits a été ajouter correctement
                // on upload son image
                if (mImageUri!= null) {
                    uploadProductImage(mImageUri, image_name, image_extention, new_product_uid);
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG + "ADD", "onFailure: ", e);
            }
        });


    }

    //recuperer les tous les produis de firstore grace a loadProductData et les passer au view model
    public MutableLiveData<ArrayList<Product>> getProductData() {

        MutableLiveData<ArrayList<Product>> data = new MutableLiveData<>();
        loadProductData(data);
        return data;
    }

    //recuperer tous les produits de la collections products de firstore et les mettres dans un mutable live data
    private void loadProductData(MutableLiveData<ArrayList<Product>> productLiveData) {

        ArrayList<Product>productArrayList = new ArrayList<>();

        productRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                if (!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> documentSnapshotList = queryDocumentSnapshots.getDocuments();

                    for (DocumentSnapshot documentSnapshot : documentSnapshotList) {
                        productArrayList.add(documentSnapshot.toObject(Product.class));
                    }
                    productLiveData.setValue(productArrayList);

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure: ", e);
            }
        });


    }

    //recuperer les toutes les categories de firstore grace a loadCategoryData et les passer au view model
    public MutableLiveData<ArrayList<Category>> getCategoryData() {
        MutableLiveData<ArrayList<Category>> data = new MutableLiveData<>();
        loadCategoryData(data);
        return data;

    }

    //recuperer toutes les catégories de firstore et les mettres dans un mutable live data
    private void loadCategoryData(MutableLiveData<ArrayList<Category>> categoryLiveData) {
        ArrayList<Category>categoryArrayList = new ArrayList<>();

        categoryRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                if (!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> documentSnapshotList = queryDocumentSnapshots.getDocuments();

                    for (DocumentSnapshot documentSnapshot : documentSnapshotList) {
                        categoryArrayList.add(documentSnapshot.toObject(Category.class));
                    }
                    categoryLiveData.setValue(categoryArrayList);

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure: ", e);
            }
        });

    }

    //uploader l'image d'un produis dans le cloud de firebase
    public static void uploadProductImage(Uri mImageUri, String image_name, String image_extention, DocumentReference reference) {
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
                    Log.e(TAG, "onComplete: get uri + "+image_url );
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


        /*if (mImageUri != null) {
            imageStorageReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.e(TAG + "UPL ", "onSuccess: ");
                    // on crrer l'ojet image a ajouter dans notre base de donée
                    // on récupére lur de l'image que on viens d'ajouter
                    // String image_url = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString() ;
                    String image_url = imageStorageReference.getDownloadUrl().toString();

                    // on instencie l'objet a ajouter dans la base

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

        }*/
    }

    //recuperer tous les produis d'un vendeur seulement
    public MutableLiveData<ArrayList<Product>>getProductsBySeller(String sellerUid){
        MutableLiveData<ArrayList<Product>> data = new MutableLiveData<>();
        loadProductsBySeller(data,sellerUid);
        return data;
    }

    // recuperer tous les produits d'un vendeur de firestore et les mettre dans un mutable live data
    private void loadProductsBySeller(MutableLiveData<ArrayList<Product>> productLiveData,String sellerUid) {
        ArrayList<Product>productArrayList = new ArrayList<>();

        productRef.whereEqualTo("productOwner",sellerUid).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w(TAG, "Listen failed.", error);
                    return;
                }
                if (!value.isEmpty()) {
                    List<DocumentSnapshot> documentSnapshotList = value.getDocuments();

                    for (DocumentSnapshot documentSnapshot : documentSnapshotList) {
                        productArrayList.add(documentSnapshot.toObject(Product.class));
                    }
                    productLiveData.setValue(productArrayList);

                }
            }
        });


    }


}
