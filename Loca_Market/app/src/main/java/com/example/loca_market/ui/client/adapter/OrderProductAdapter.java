package com.example.loca_market.ui.client.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.loca_market.R;
import com.example.loca_market.data.models.ProductCart;

import java.util.List;

public class OrderProductAdapter extends RecyclerView.Adapter<OrderProductAdapter.ViewHolder>{

    private Context context;
    private List<ProductCart> productsList;

    public OrderProductAdapter(Context context, List<ProductCart> productsList) {
        this.context = context;
        this.productsList = productsList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.client_single_product_order_detail,parent,false);
        return new OrderProductAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.productName.setText(productsList.get(position).getProduct().getName());
        holder.quatityOrdred.setText("Quantité : "+ productsList.get(position).getQuantity());
        holder.productPrice.setText("prix :  "+ productsList.get(position).getProduct().getPrice()+" €");
        Glide.with(holder.itemView.getContext()).load(productsList.get(position).getProduct().getImageUrl()).into(holder.productImg);
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView productImg;
        private  TextView productName;
        private TextView productPrice;
        private  TextView quatityOrdred;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImg=itemView.findViewById(R.id.i_productOrdred_image);
            productPrice=itemView.findViewById(R.id.t_productOrdred_price);
            productName =itemView.findViewById(R.id.t_productOrdred_name);
            quatityOrdred =itemView.findViewById(R.id.t_productOrdred_quantity);
        }
    }
}
