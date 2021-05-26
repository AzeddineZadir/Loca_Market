package com.example.loca_market.data.repositores;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.loca_market.data.models.Product;
import com.example.loca_market.data.models.Store;
import com.example.loca_market.data.models.User;
import com.example.loca_market.data.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    public static final String TAG = "UserRepository";
    private static UserRepository instance;
    public static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static final String USERS = "users";
    private static final FirebaseFirestore fdb = FirebaseFirestore.getInstance();
    private static final CollectionReference usersRef = fdb.collection(USERS);
    private static FirebaseUser currentUser ;
    public boolean updateStatue = false;

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
            currentUser= FirebaseAuth.getInstance().getCurrentUser();
        }
        return instance;
    }

    public static void logIN(String email ,String password ){

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {


                    FirebaseUser connectedUser = FirebaseAuth.getInstance().getCurrentUser();

                    DocumentReference userdocRef = fdb.collection("users").document(connectedUser.getUid());
                    userdocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            User current_user = documentSnapshot.toObject(User.class);
                            // save the user to loghim auto


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });


                } else {

                }
            }
        });
    }

    // logout the user by firbase authentification system
    public static  void logOut(){
        mAuth.signOut();
    }

    //recuperer les tous les produis de firstore grace a loadUserData et les passer au view model
    public MutableLiveData<User> getUserByUid() {

        MutableLiveData<User> data = new MutableLiveData<>();
        loadUserByUid(data);
        return data;
    }

    private void loadUserByUid(MutableLiveData<User> UserLiveData) {
        usersRef.document(currentUser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                UserLiveData.setValue(documentSnapshot.toObject(User.class));

                Log.e(TAG, "onSuccess: user details ritraved " + documentSnapshot.toObject(Product.class).getName());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure: in getting user details ");
            }
        });



    }


    public boolean updateUser (User userToUpdate) {


        usersRef.document(userToUpdate.getUid()).set(userToUpdate, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.e(TAG, "onSuccess: to update the store " + userToUpdate.getUsername());

                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(userToUpdate.getUsername())
                        .build();


                currentUser.updateProfile(profileUpdates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "User profile updated.");
                                }
                            }
                        });

                currentUser.updateEmail(userToUpdate.getEmail())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "User email address updated.");
                                }
                            }
                        });

                updateStatue=true ;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure: to update the store " + userToUpdate.getUsername());
                updateStatue=true ;
            }
        });

        return updateStatue;
    }
}
