package com.example.loca_market.ui.client.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loca_market.R;
import com.example.loca_market.data.models.Order;
import com.example.loca_market.ui.client.ClientHomeActivity;
import com.example.loca_market.ui.client.fragments.HobbiesFragment;
import com.example.loca_market.ui.client.fragments.LocalisationFragment;
import com.example.loca_market.ui.client.fragments.PersonnalInformationsFragment;

public class ClientSettingsActivity extends AppCompatActivity implements View.OnClickListener{

    private Toolbar mToolbar;
    TextView personalInformation, localisation, hobbies;
    Button b_save;
    FragmentTransaction transaction;
    PersonnalInformationsFragment personnalInfoFragment=null;
    LocalisationFragment localisationFragment=null;
    HobbiesFragment hobbiesFragment=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_settings);
        // tootlbar managment
        mToolbar=findViewById(R.id.clientSettings_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Configurations");

        // menu
        personalInformation =findViewById(R.id.t_personalInformationSettings);
        localisation = findViewById(R.id.t_localisationSettings);
        hobbies=findViewById(R.id.t_hobbiesSettings);
        b_save =findViewById(R.id.b_saveSettings);

        // gestion des fragments
        personalInformation.setOnClickListener(this);
        localisation.setOnClickListener(this);
        hobbies.setOnClickListener(this);

        //enregistrement des informations
        b_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                personnalInfoFragment.saveData();
                localisationFragment.saveData();
                Intent intent=new Intent(ClientSettingsActivity.this,ClientHomeActivity.class);
                startActivity(intent);
            }
        });
        // initaialisation des fragments
        personnalInfoFragment =new PersonnalInformationsFragment();
        localisationFragment = new LocalisationFragment();
        hobbiesFragment = new HobbiesFragment();

        // inflate du fragment initiale
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container_idSettings,personnalInfoFragment,"personnalInfo");
        transaction.commit();




    }

    @Override
    public void onClick(View v) {
        Fragment fragment =null;
        switch (v.getId()){
            case R.id.t_personalInformationSettings :{
                fragment = personnalInfoFragment ;
                break;
            }
            case R.id.t_localisationSettings:{
                fragment = localisationFragment;

                break;
            }
            case  R.id.t_hobbiesSettings:{
                fragment = hobbiesFragment;
                break;
            }
        }
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_idSettings,fragment);
        transaction.commit();
    }
}