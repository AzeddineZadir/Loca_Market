package com.example.loca_market.ui.client.adapter;

import android.content.Context;
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

import java.util.List;

public class ProductSearchRecyclerAdapter extends RecyclerView.Adapter<ProductSearchRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<Product> mProductList;

    public ProductSearchRecyclerAdapter(Context context, List<Product> mProductList) {
        this.context = context;
        this.mProductList = mProductList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // à modifier
        View view = LayoutInflater.from(context).inflate(R.layout.client_single_product_search,parent,false);
        return new ProductSearchRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mNewProductName.setText(mProductList.get(position).getName());
        holder.mNewProductPrice.setText(mProductList.get(position).getPrice()+"€");
        Glide.with(context).load(mProductList.get(position).getimageUrl()).into(holder.mNewProductImg);
    }

    @Override
    public int getItemCount() {
        return mProductList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView mNewProductImg;
        private TextView mNewProductPrice;
        private  TextView mNewProductName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mNewProductImg = itemView.findViewById(R.id.i_search_product_image);
            mNewProductPrice =itemView.findViewById(R.id.t_search_product_price);
            mNewProductName=itemView.findViewById(R.id.t_search_product_name);
        }
    }
}
