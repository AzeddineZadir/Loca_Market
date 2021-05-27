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

public class BestSellsAdapter extends RecyclerView.Adapter<BestSellsAdapter.ViewHolder> {
    private final Context context;
    private final List<Product> mBestSellsList;

    public BestSellsAdapter(Context context, List<Product> mBestSellsList) {
        this.context = context;
        this.mBestSellsList = mBestSellsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.client_home_single_product_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product currentProduct = mBestSellsList.get(position);
        holder.mBestSellsName.setText(mBestSellsList.get(position).getName().trim());


        Float newPrice = currentProduct.getPrice() - (currentProduct.getPrice() * currentProduct.getPercentage() / 100);
        holder.mBestSellsPrice.setText(newPrice + " €");
        if (currentProduct.getPercentage() != 0) {
            holder.tv_product_offer_precentage.setText("- " + currentProduct.getPercentage() + " %");
        } else {
            holder.tv_product_offer_precentage.setVisibility(View.INVISIBLE);
        }


        Glide.with(context).load(mBestSellsList.get(position).getImageUrl()).into(holder.mBestSellsImg);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailsActivity.class);
                intent.putExtra("productDetail", mBestSellsList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBestSellsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mBestSellsImg;
        private final TextView mBestSellsPrice;
        private final TextView tv_product_offer_precentage;
        private final TextView mBestSellsName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mBestSellsImg = itemView.findViewById(R.id.i_ProductImg);
            mBestSellsPrice = itemView.findViewById(R.id.t_ProductPrice);
            tv_product_offer_precentage = itemView.findViewById(R.id.tv_product_offer_precentage);


            mBestSellsName = itemView.findViewById(R.id.t_ProductName);

        }
    }
}
