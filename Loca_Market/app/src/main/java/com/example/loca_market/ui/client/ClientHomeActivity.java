package com.example.loca_market.ui.client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.example.loca_market.ui.client.fragments.ClientHomeFragment;
import com.google.android.gms.tasks.Task;
import com.example.loca_market.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class ClientHomeActivity extends AppCompatActivity {
    private Fragment clientHomeFragment;
    private EditText et_search_text;
    private FirebaseFirestore mStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_home);
        clientHomeFragment =new ClientHomeFragment();
        loadFragment(clientHomeFragment);
        et_search_text = findViewById(R.id.et_search_text);
        mStore=FirebaseFirestore.getInstance();
        et_search_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                    searchItem(s.toString());
            }
        });

    }

    private void searchItem(String text) {
    mStore.collection("Products").whereGreaterThanOrEqualTo("name",text).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if(task.isSuccessful() && task.getResult()!=null){
                for(DocumentSnapshot doc:task.getResult().getDocuments()){
                    Log.i("ClientHomeActivity","on complete "+ doc.getData());
                   /* Items items=doc.toObject(Items.class);
                    mItemsList.add(items);
                    itemsRecyclerAdapter.notifyDataSetChanged();

                    */
                }
            }
        }
    } );
    }

    private void loadFragment(Fragment clientHomeFragment) {
        FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.client_home_container,clientHomeFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}