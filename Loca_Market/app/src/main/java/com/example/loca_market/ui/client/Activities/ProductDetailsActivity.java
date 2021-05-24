package com.example.loca_market.ui.client.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.loca_market.R;
import com.example.loca_market.data.models.Product;
import com.example.loca_market.data.models.ProductCart;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDetailsActivity extends AppCompatActivity {

    private Button b_addToCartButton;
    private Button b_return;
    private ImageView productImage;
    private ElegantNumberButton numberButton;
    private TextView productPrice, productDescription, productName, productBrand;
    private Product product;

    FirebaseFirestore mStore;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        product= (Product) getIntent().getSerializableExtra("productDetail");
        mStore =FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        b_addToCartButton = (Button)findViewById(R.id.b_add_pd_to_cart_btn);
        b_return =(Button)findViewById(R.id.b_return);
        numberButton = (ElegantNumberButton)findViewById(R.id.b_number_btn);
        productImage = (ImageView)findViewById(R.id.i_product_image_details);
        productName =(TextView) findViewById(R.id.t_product_name_details);
        productBrand = (TextView)findViewById(R.id.t_product_brand_details);
        productDescription = (TextView)findViewById(R.id.t_product_description_details);
        productPrice = (TextView) findViewById(R.id.t_product_price_details);
        numberButton =(ElegantNumberButton)findViewById(R.id.b_number_btn);
        final String[] strQty = {"1"};
        numberButton.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                strQty[0] = numberButton.getNumber();
            }
        });
        getProductDetails(product);
        b_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        b_addToCartButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ProductCart productCart = new ProductCart(product,Integer.parseInt(strQty[0]),"");
               addProductToCart(productCart);
            }
        });
    }

    private void addProductToCart(ProductCart productCart) {
        List<ProductCart>productsCartList = new ArrayList<>();
      int tempQuatity[]={0};
     boolean[] updated = {false};
        mStore.collection("users").document(mAuth.getCurrentUser().getUid())
                .collection("cart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if(task.getResult()!=null) {
                        for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                            ProductCart productCart2 = doc.toObject(ProductCart.class);
                            DocumentReference docRef = doc.getReference();
                            Log.i("ProductDetail", docRef.getId());
                            if (productCart.getProduct().getName().equals(productCart2.getProduct().getName())) {
                                tempQuatity[0] = productCart2.getQuantity() + productCart.getQuantity();
                                Map<String, Object> updates = new HashMap<>();
                                updates.put("quantity", tempQuatity[0]);
                                docRef.update(updates);
                                Toast.makeText(ProductDetailsActivity.this, "Produit mis à jour dans le panier", Toast.LENGTH_SHORT).show();
                                updated[0] = true;
                            }
                        }
                    }
                    if(!updated[0]){
                        mStore.collection("users").document(mAuth.getCurrentUser().getUid())
                                .collection("cart").add(productCart).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                Toast.makeText(ProductDetailsActivity.this, "Produit ajouter au panier ", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                }
            }
        });


    }

    private void getProductDetails(Product product) {
        Glide.with(getApplicationContext()).load(product.getImageUrl()).into(productImage);
        productName.setText(product.getName());
        productBrand.setText(product.getBrand());
        productPrice.setText(""+product.getPrice()+" €");
        productDescription.setText(product.getDescription());
    }
}