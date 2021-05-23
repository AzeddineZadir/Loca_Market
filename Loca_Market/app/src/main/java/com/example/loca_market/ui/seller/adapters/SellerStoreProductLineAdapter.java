package com.example.loca_market.ui.seller.adapters;

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

import java.util.ArrayList;
import java.util.List;

public class SellerStoreProductLineAdapter extends RecyclerView.Adapter<SellerStoreProductLineAdapter.ProductHolder> {

    private List<Product> products = new ArrayList<>();
    private Context context;
    private SellerStoreProductLineAdapter.OnProductItemListener onProductItemListener;

    public SellerStoreProductLineAdapter(Context context, SellerStoreProductLineAdapter.OnProductItemListener onProductItemListener) {
        this.context = context;
        this.onProductItemListener = onProductItemListener;

    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.products_list_item,parent,false);
        return new SellerStoreProductLineAdapter.ProductHolder(itemView,onProductItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        Product currentProduct = products.get(position);
        holder.tv_product_name.setText(currentProduct.getName());
        holder.tv_product_description.setText(currentProduct.getPrice()+" €");
        Glide.with(context).load(currentProduct.getImageUrl()).into(holder.iv_item_product);
    }


    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setProducts(List<Product> products){
        this.products = products ;
        notifyDataSetChanged();
    }

    class ProductHolder extends RecyclerView.ViewHolder implements View.OnClickListener , View.OnLongClickListener {
        private TextView tv_product_name;
        private TextView tv_product_description;
        private ImageView iv_item_product;
        OnProductItemListener onProductItemListener;

        public ProductHolder(@NonNull View itemView, SellerStoreProductLineAdapter.OnProductItemListener onProductItemListener) {
            super(itemView);
            tv_product_name = itemView.findViewById(R.id.tv_item_prodcut_name);
            tv_product_description = itemView.findViewById(R.id.tv_item_product_description);
            iv_item_product = itemView.findViewById(R.id.iv_item_product);
            this.onProductItemListener = onProductItemListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onProductItemListener.onProductClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            onProductItemListener.onProductLongClick(getAdapterPosition());

            return true;
        }
    }

    public interface OnProductItemListener {
        void onProductClick(int position);
        void onProductLongClick(int position);
    }

}
