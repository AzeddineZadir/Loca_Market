package com.example.loca_market.data.repositores;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;

public class UserRepository {
    private static UserRepository instance;
    public static FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public static UserRepository getInstance(Context context) {
        if (instance == null) {
            instance = new UserRepository();

        }
        return instance;
    }

    public static void logIN(String email ,String password){

    }

    // logout the user by firbase authentification system
    public static  void logOut(){
        mAuth.signOut();
    }

    public static  void signeUp(){

    }
}
