package com.example.loca_market.ui.client.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loca_market.R;
import com.example.loca_market.data.models.Order;
import com.example.loca_market.ui.client.fragments.HobbiesFragment;
import com.example.loca_market.ui.client.fragments.LocalisationFragment;
import com.example.loca_market.ui.client.fragments.PersonnalInformationsFragment;

public class ClientSettingsActivity extends AppCompatActivity implements View.OnClickListener{

    private Toolbar mToolbar;
    TextView personalInformation, localisation, hobbies,tSave;
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
        getSupportActionBar().setTitle("Préférences");

        // menu
        personalInformation =findViewById(R.id.t_personalInformationSettings);
        localisation = findViewById(R.id.t_localisationSettings);
        hobbies=findViewById(R.id.t_hobbiesSettings);
        tSave =findViewById(R.id.t_saveSettings);

        // gestion des fragments
        personalInformation.setOnClickListener(this);
        localisation.setOnClickListener(this);
        hobbies.setOnClickListener(this);

        //enregistrement des informations
        tSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                personnalInfoFragment.saveData();

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
                fragment =new PersonnalInformationsFragment();
                break;
            }
            case R.id.t_localisationSettings:{
                fragment =new LocalisationFragment();
                break;
            }
            case  R.id.t_hobbiesSettings:{
                fragment =new HobbiesFragment();
                break;
            }
        }
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_idSettings,fragment);
        transaction.commit();
    }
}