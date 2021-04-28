package com.example.loca_market.ui.client.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.loca_market.R;
import com.example.loca_market.data.models.Product;
import com.example.loca_market.ui.client.Activities.ProductDetailsActivity;

import java.util.List;

public class ProductsOfCategoryAdapter extends RecyclerView.Adapter<ProductsOfCategoryAdapter.ViewHolder> {
    Context context;
    List<Product>productsList;

    public ProductsOfCategoryAdapter(Context applicationContext, List<Product> productsList) {
        this.context = applicationContext;
        this.productsList = productsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.client_single_product_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mProductName.setText(productsList.get(position).getName());
        holder.mProductPrice.setText(productsList.get(position).getPrice()+"€");
        Glide.with(context).load(productsList.get(position).getImageUrl()).into(holder.mProductImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailsActivity.class);
                intent.putExtra("productDetail",productsList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mProductImage;
        private TextView mProductPrice;
        private TextView mProductName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mProductImage=itemView.findViewById(R.id.Product_Of_Category_image);
            mProductPrice=itemView.findViewById(R.id.t_Product_Of_Category_Price);
            mProductName=itemView.findViewById(R.id.t_Product_Of_Category_Name);
        }
    }
}
