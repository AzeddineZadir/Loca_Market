package com.example.loca_market.ui.client.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.loca_market.R;
import com.example.loca_market.data.models.Order;
import com.example.loca_market.data.models.ProductCart;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Client_confirm_orderActivity extends AppCompatActivity {

    private EditText firstNameEditText, lastNameEditText, phoneEditText, addressEditText, cityEditText;
    private Button confirmOrderBtn;
    private FirebaseFirestore mStore;
    private FirebaseAuth mAuth;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_confirm_order);
        // tootlbar managment
        mToolbar=findViewById(R.id.confirm_order_toolbar);
        setSupportActionBar(mToolbar);
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Nouvelle Commande");
        firstNameEditText = findViewById(R.id.ed_shippment_firstName);
        lastNameEditText = findViewById(R.id.ed_shippment_lastName);
        phoneEditText = findViewById(R.id.ed_shippment_phone_number);
        addressEditText = findViewById(R.id.ed_shippment_address);
        cityEditText = findViewById(R.id.ed_shippment_city);
        confirmOrderBtn =findViewById(R.id.b_confirm_order);

        mStore =FirebaseFirestore.getInstance();
        mAuth =FirebaseAuth.getInstance();

        confirmOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Check();
            }
        });

    }

    private void Check()
    {
        if (TextUtils.isEmpty(firstNameEditText.getText().toString()))
        {
            Toast.makeText(this, "Insérez votre nom SVP.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(lastNameEditText.getText().toString()))
        {
            Toast.makeText(this, "Insérez votre prénom SVP.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phoneEditText.getText().toString()))
        {
            Toast.makeText(this, "Inserez votre numéro de téléphone SVP", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(addressEditText.getText().toString()))
        {
            Toast.makeText(this, "Insérez votre adresse SVP", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(cityEditText.getText().toString()))
        {
            Toast.makeText(this, "Inserez la ville SVP", Toast.LENGTH_SHORT).show();
        }
        else
        {
            ConfirmOrder();
        }
    }

    private void ConfirmOrder() {

      String saveCurrentDate, saveCurrentTime;

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        saveCurrentDate = currentDate.format(calForDate.getTime());
        String dateTime[] =saveCurrentDate.split(" ");
        String dayMonthYear[]=dateTime[0].split("-");
        saveCurrentDate=dayMonthYear[1]+"/"+dayMonthYear[0]+"/"+dayMonthYear[2];
        String hourMinSec[]=dateTime[1].split(":");
        saveCurrentTime = hourMinSec[0]+":"+hourMinSec[1];
        String totalAmount = getIntent().getStringExtra("totalAmount");
        List<ProductCart>productsOrdered =(ArrayList<ProductCart>) getIntent().getSerializableExtra("itemsList");

        Order order =new Order();
        DocumentReference orderRef = mStore.collection("orders").document();
        String oId = orderRef.getId();
        order.setOrderId(oId);

        for (ProductCart productCart:productsOrdered) {
            productCart.setOrderId(oId);
        }
        order.setLastName(lastNameEditText.getText().toString());
        order.setFirstName(firstNameEditText.getText().toString());
        order.setPhone(phoneEditText.getText().toString());
        order.setAddress(addressEditText.getText().toString());
        order.setCity(cityEditText.getText().toString());
        order.setState("En cour");
        order.setDate(saveCurrentDate);
        order.setTime(saveCurrentTime);
        order.setClientId(mAuth.getCurrentUser().getUid());
        order.setTotalAmount(totalAmount);
        order.setProductsOrdred(productsOrdered);


        mStore.collection("orders").add(order).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                deleteCart();
                Intent intent=new Intent(Client_confirm_orderActivity.this,ClientOrderConfirmedActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    private void deleteCart() {
        mStore.collection("users").document(mAuth.getCurrentUser().getUid())
                .collection("cart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult()!=null){
                        for(DocumentChange doc :task.getResult().getDocumentChanges()){
                            String documentId = doc.getDocument().getId();
                            mStore.collection("users").document(mAuth.getCurrentUser().getUid())
                                    .collection("cart").document(documentId).delete();
                        }
                    }

                }
            }
        });
    }
}