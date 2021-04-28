package com.example.loca_market.ui.client.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.loca_market.R;
import com.example.loca_market.data.models.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProductDetailsActivity extends AppCompatActivity {

    private Button addToCartButton;
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


        addToCartButton = (Button)findViewById(R.id.b_add_pd_to_cart_btn);
        b_return =(Button)findViewById(R.id.b_return);
        numberButton = (ElegantNumberButton)findViewById(R.id.b_number_btn);
        productImage = (ImageView)findViewById(R.id.i_product_image_details);
        productName =(TextView) findViewById(R.id.t_product_name_details);
        productBrand = (TextView)findViewById(R.id.t_product_brand_details);
        productDescription = (TextView)findViewById(R.id.t_product_description_details);
        productPrice = (TextView) findViewById(R.id.t_product_price_details);

        mStore =FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        
        getProductDetails(product);
        b_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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