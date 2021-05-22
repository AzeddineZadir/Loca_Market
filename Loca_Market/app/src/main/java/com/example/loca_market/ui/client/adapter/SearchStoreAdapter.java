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
import com.example.loca_market.data.models.Store;

import java.util.List;

public class SearchStoreAdapter extends RecyclerView.Adapter<SearchStoreAdapter.ViewHolder> {
    private Context context;
    private List<Store>storeList;

    public SearchStoreAdapter(Context context, List<Store> storeList) {
        this.context = context;
        this.storeList = storeList;
    }

    @NonNull
    @Override
    public SearchStoreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.client_single_selller_card_view,parent,false);
        return new SearchStoreAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchStoreAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(storeList.get(position).getImageUrl()).into(holder.storeImg);
        holder.sellerName.setText(storeList.get(position).getSellerName());
        holder.storeName.setText(storeList.get(position).getName());
        holder.storeCategory.setText(storeList.get(position).getCategory());
        holder.seeStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // faire la liaison avec le magasin
             //   Intent intent = new Intent(context, .class);
              //  context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return storeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView storeImg;
        private TextView sellerName;
        private TextView storeName;
        private  TextView storeCategory;
        private TextView seeStore;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            storeImg = itemView.findViewById(R.id.i_shop_image);
            sellerName =itemView.findViewById(R.id.t_seller_UserName);
            storeName = itemView.findViewById(R.id.t_seller_store_name);
            storeCategory = itemView.findViewById(R.id.t_seller_store_name);
            seeStore =itemView.findViewById(R.id.t_see_store);

        }
    }
}
