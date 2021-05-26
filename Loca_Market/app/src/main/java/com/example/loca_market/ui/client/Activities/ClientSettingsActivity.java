package com.example.loca_market.ui.client.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.loca_market.R;
import com.example.loca_market.data.models.Order;
import com.example.loca_market.data.models.User;
import com.example.loca_market.ui.client.ClientHomeActivity;
import com.example.loca_market.ui.client.fragments.HobbiesFragment;
import com.example.loca_market.ui.client.fragments.LocalisationFragment;
import com.example.loca_market.ui.client.fragments.PersonnalInformationsFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ClientSettingsActivity extends AppCompatActivity implements View.OnClickListener{

    private Toolbar mToolbar;
    TextView personalInformation, localisation, hobbies;
    Button b_save;
    FragmentTransaction transaction;
    PersonnalInformationsFragment personnalInfoFragment=null;
    LocalisationFragment localisationFragment=null;
    HobbiesFragment hobbiesFragment=null;

    //Profile Picture
    private ImageView profilePic;
    public Uri imageUri=null;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseFirestore mStore;
    private FirebaseAuth mAuth;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_settings);
        // tootlbar managment
        mToolbar=findViewById(R.id.clientSettings_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Configurations");

        mStore =FirebaseFirestore.getInstance();
        mAuth =FirebaseAuth.getInstance();
        getCurrentUser();

        // Profile picture
        profilePic =findViewById(R.id.i_profilePicture);
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture();
            }
        });
        firebaseStorage =FirebaseStorage.getInstance();
        storageReference =firebaseStorage.getReference();

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
                saveProfilePicture();
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

    private void getCurrentUser() {
            mStore.collection("users").document(mAuth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                 currentUser = documentSnapshot.toObject(User.class);
                 if(currentUser!=null){
                     if (currentUser.getProfilePicURL()!=null && !currentUser.getProfilePicURL().isEmpty())
                         Glide.with(getApplicationContext()).load(currentUser.getProfilePicURL()).into(profilePic);
                 }

                }
            });
    }

    private void saveProfilePicture() {
        Map<String,Object> mapInfo = new HashMap<>();
        if(imageUri!=null ) {
            Log.i("ClientSettings",imageUri.toString());
            mapInfo.put("profilePicURL", imageUri.toString());
            mStore.collection("users").document(mAuth.getCurrentUser().getUid()).update(mapInfo);
        }
    }

    private void choosePicture() {
        Intent intent =new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            imageUri = data.getData();
            profilePic.setImageURI(imageUri);
            uploadPicture();
        }
    }

    private void uploadPicture() {
        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Chargement...");
        progressDialog.show();
        final String randomKey = UUID.randomUUID().toString();
        String profilePicName ="profilePicture";
        StorageReference profilePicRef = storageReference.child("profile_images/"+profilePicName);
        profilePicRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                progressDialog.dismiss();
                Snackbar.make(findViewById(android.R.id.content),"Image téléchargée avec succès.",Snackbar.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Téléchargement impossible",Toast.LENGTH_LONG).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progressPercent = (100.00 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                progressDialog.setMessage("Progrés : "+progressPercent +" %");
            }
        });

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