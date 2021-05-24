package com.example.loca_market.ui.seller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.loca_market.R;
import com.example.loca_market.ui.userAuth.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.content.Context.MODE_PRIVATE;

public class ProfilFragment extends Fragment {
    Button b_logout;
    FirebaseAuth mAuth;
    FirebaseFirestore fdb;
    FirebaseUser user ;
    String username ,email ;
    public static final String  USER_SHARED_PREFS ="userSharedPrefs";
    public static final String  EMAIL ="email";
    public static final String  PWD ="pwd";
    public static final String  ROLE ="role";
    public ProfilFragment(){
        super(R.layout.fragment_profil);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profil, container, false);
        b_logout = (Button) view.findViewById(R.id.b_logout);
        mAuth = FirebaseAuth.getInstance();
        b_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // se décconecter
                mAuth.signOut();
                wipeUserPref();
                Toast.makeText(getActivity(), "you just signed out ", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent1);


            }
        });
        return view;
    }


    public void wipeUserPref(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(USER_SHARED_PREFS,MODE_PRIVATE);
        sharedPreferences.edit().putString(EMAIL,"").apply();
        sharedPreferences.edit().putString(PWD,"").apply();
        sharedPreferences.edit().putString(ROLE,"").apply();
    }
}
