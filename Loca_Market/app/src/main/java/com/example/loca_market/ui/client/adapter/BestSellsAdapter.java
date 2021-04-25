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

public class BestSellsAdapter extends RecyclerView.Adapter<BestSellsAdapter.ViewHolder>{
    private Context context;
    private List<Product> mBestSellsList;

    public BestSellsAdapter(Context context, List<Product> mBestSellsList) {
        this.context = context;
        this.mBestSellsList = mBestSellsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_product_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.mBestSellsName.setText(mBestSellsList.get(position).getName().trim());
            holder.mBestSellsPrice.setText(mBestSellsList.get(position).getPrice()+"€");
            Glide.with(context).load(mBestSellsList.get(position).getimageUrl()).into(holder.mBestSellsImg);
    }

    @Override
    public int getItemCount() {
       return mBestSellsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mBestSellsImg;
        private TextView mBestSellsPrice;
        private  TextView mBestSellsName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mBestSellsImg = itemView.findViewById(R.id.i_ProductImg);
            mBestSellsPrice=itemView.findViewById(R.id.t_ProductPrice);
            mBestSellsName=itemView.findViewById(R.id.t_ProductName);

        }
    }
}
