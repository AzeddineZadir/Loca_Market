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
import com.example.loca_market.ui.seller.adapters.SellerStoreProductLineAdapter;

import java.util.List;

public class SelectedStoreProductLinarAdapter extends RecyclerView.Adapter<SelectedStoreProductLinarAdapter.ViewHolder> {
    Context context;
    List<Product> productsList;

    public SelectedStoreProductLinarAdapter(Context applicationContext, List<Product> productsList) {
        this.context = applicationContext;
        this.productsList = productsList;
    }

    @NonNull
    @Override
    public SelectedStoreProductLinarAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.products_list_item,parent,false);
        return new SelectedStoreProductLinarAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedStoreProductLinarAdapter.ViewHolder holder, int position) {
        Product currentProduct = productsList.get(position);
        holder.tv_product_name.setText(currentProduct.getName());
        Float newPrice = currentProduct.getPrice()-(currentProduct.getPrice()*currentProduct.getPercentage()/100);
        holder.tv_product_description.setText(newPrice+" €");

        if(currentProduct.getPercentage()!= 0){
            holder.tv_product_offer_percentage_line.setText("- " +currentProduct.getPercentage()+" %");
        }else{
            holder.tv_product_offer_percentage_line.setVisibility(View.INVISIBLE);
        }



        Glide.with(context).load(currentProduct.getImageUrl()).into(holder.iv_item_product);
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

    public class ViewHolder extends RecyclerView.ViewHolder  {
        private TextView tv_product_name;
        private TextView tv_product_description;
        private TextView  tv_product_offer_percentage_line ;
        private ImageView iv_item_product;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_product_name = itemView.findViewById(R.id.tv_item_prodcut_name);
            tv_product_description = itemView.findViewById(R.id.tv_item_product_description);
            tv_product_offer_percentage_line = itemView.findViewById(R.id.tv_product_offer_percentage_line);
            iv_item_product = itemView.findViewById(R.id.iv_item_product);


        }


    }
}
