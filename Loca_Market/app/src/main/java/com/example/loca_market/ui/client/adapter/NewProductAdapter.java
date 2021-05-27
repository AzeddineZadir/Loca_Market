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

public class NewProductAdapter extends RecyclerView.Adapter<NewProductAdapter.ViewHolder> {
    private Context context;
    private List<Product> mProductList;

    public NewProductAdapter(Context context, List<Product> mProductList) {
        this.context = context;
        this.mProductList = mProductList;
    }

    @NonNull
    @Override
    public NewProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.client_home_single_product_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewProductAdapter.ViewHolder holder, int position) {
        Product currentProduct = mProductList.get(position);
        holder.mNewProductName.setText(mProductList.get(position).getName());
        Float newPrice = currentProduct.getPrice()-(currentProduct.getPrice()*currentProduct.getPercentage()/100);
        holder.mNewProductPrice.setText(newPrice+"€");

        if(currentProduct.getPercentage()!=0){
            holder.tv_product_offer_precentage.setText("- " +currentProduct.getPercentage()+" %");
        }else{
            holder.tv_product_offer_precentage.setVisibility(View.INVISIBLE);
        }


        Glide.with(context).load(mProductList.get(position).getImageUrl()).into(holder.mNewProductImg);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailsActivity.class);
                intent.putExtra("productDetail",mProductList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProductList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView mNewProductImg;
        private TextView mNewProductPrice;
        private  TextView mNewProductName;
        private  TextView tv_product_offer_precentage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
          mNewProductImg = itemView.findViewById(R.id.i_ProductImg);
          mNewProductPrice =itemView.findViewById(R.id.t_ProductPrice);
          mNewProductName=itemView.findViewById(R.id.t_ProductName);
            tv_product_offer_precentage=itemView.findViewById(R.id.tv_product_offer_precentage);
        }
    }
}
