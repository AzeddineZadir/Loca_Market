package com.example.loca_market.ui.seller.adapters;

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
import com.example.loca_market.ui.client.adapter.ProductSearchRecyclerAdapter;

import java.util.List;

public class SellerAddOfferSearchProductAdapter extends RecyclerView.Adapter<SellerAddOfferSearchProductAdapter.ViewHolder>{
    private Context context;
    private List<Product> products;
    private SellerAddOfferSearchProductAdapter.OnProductItemListener onProductItemListener ;

    public SellerAddOfferSearchProductAdapter(Context context, List<Product> products, SellerAddOfferSearchProductAdapter.OnProductItemListener onProductItemListener) {
        this.context = context;
        this.products = products;
        this.onProductItemListener = onProductItemListener ;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.client_single_product_search,parent,false);
        return new SellerAddOfferSearchProductAdapter.ViewHolder(view,onProductItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.productName.setText(products.get(position).getName());
        holder.productPrice.setText(products.get(position).getPrice()+"€");
        Glide.with(context).load(products.get(position).getImageUrl()).into(holder.productImg);
        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // to be updated as needed by azeddine
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView productImg;
        private TextView productPrice;
        private  TextView productName;
        SellerAddOfferSearchProductAdapter.OnProductItemListener onProductItemListener ;
        public ViewHolder(@NonNull View itemView, SellerAddOfferSearchProductAdapter.OnProductItemListener onProductItemListener) {
            super(itemView);
            productImg = itemView.findViewById(R.id.i_search_product_image);
            productPrice =itemView.findViewById(R.id.t_search_product_price);
            productName=itemView.findViewById(R.id.t_search_product_name);
            this.onProductItemListener = onProductItemListener ;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onProductItemListener.onProductClick(getAdapterPosition());
        }
    }

    public interface OnProductItemListener{
        void onProductClick (int position);
    }
}
