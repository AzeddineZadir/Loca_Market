package com.example.loca_market.ui.Client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.loca_market.ui.Client.fragments.ClientHomeFragment;
import com.example.loca_market.R;

public class ClientHomeActivity extends AppCompatActivity {
    Fragment clientHomeFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_home);
        clientHomeFragment =new ClientHomeFragment();
        loadFragment(clientHomeFragment);
    }
    private void loadFragment(Fragment clientHomeFragment) {
        FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.client_home_container,clientHomeFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}