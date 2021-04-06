package com.example.loca_market;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.loca_market.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class SellerHomeActivity extends AppCompatActivity {
    private final String  TAG ="SellerHomeActivity";
    TextView tv_username_home , tv_email_home ;
    Button b_log_out;
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

        b_log_out = (Button) findViewById(R.id.b_log_out);
        tv_username_home = (TextView) findViewById(R.id.tv_username_home) ;
        tv_email_home = (TextView) findViewById(R.id.tv_email_home) ;

        user = FirebaseAuth.getInstance().getCurrentUser();
        Log.d("theUID",user.getUid());
        DocumentReference docRef = fdb.collection("Users").document(user.getUid());


        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User current_user = documentSnapshot.toObject(User.class);
                Log.d("theUID",current_user.getEmail());
                Log.d("theUID",current_user.getUsername());
                // Name, email address, and profile photo Url
                username = current_user.getUsername();
                email = current_user.getEmail();
                tv_username_home.setText(username);
                tv_email_home.setText(email);


            }
        });






    }

    public void click_b_log_out(View view) {
        // se décconecter
        mAuth.signOut();
        Toast.makeText(this, "you just signed out ", Toast.LENGTH_SHORT).show();
        Intent intent1 = new Intent(this, SellerLoginActivity.class);
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