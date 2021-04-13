package com.example.loca_market.ui.seller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.loca_market.R;
import com.example.loca_market.data.models.User;
import com.example.loca_market.ui.userAuth.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SellerHomeActivity extends AppCompatActivity {
    private final String  TAG ="SellerHomeActivity";

    FirebaseAuth mAuth;
    FirebaseFirestore fdb;
    FirebaseUser user ;
    String username ,email ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_home);

        mAuth = FirebaseAuth.getInstance();
        fdb = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        BottomNavigationView bottom_navigation_menu = findViewById(R.id.bottom_navigation_menu) ;


       /* NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_container);
*/
        FragmentManager fragmentManager = getSupportFragmentManager();
        NavHostFragment navHostFragment =
                (NavHostFragment) fragmentManager.findFragmentById(R.id.nav_host_fragment_container);
        NavController navController = navHostFragment.getNavController();

        NavigationUI.setupWithNavController(bottom_navigation_menu, navController);

        Log.d("theUID",user.getUid());
        DocumentReference docRef = fdb.collection("users").document(user.getUid());

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User current_user = documentSnapshot.toObject(User.class);
                if (current_user != null){
                    Log.d("theUID",current_user.getEmail());
                    Log.d("theUID",current_user.getUsername());
                    // Name, email address, and profile photo Url
                    username = current_user.getUsername();
                    email = current_user.getEmail();

                }




            }
        });







    }



    public void click_b_log_out(View view) {
        // se décconecter
        mAuth.signOut();
        Toast.makeText(this, "you just signed out ", Toast.LENGTH_SHORT).show();
        Intent intent1 = new Intent(this, LoginActivity.class);
        startActivity(intent1);

    }


    public void click_email_verification(View view) {
        if (!user.isEmailVerified()) {
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(SellerHomeActivity.this, "vifification Email Sent", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}