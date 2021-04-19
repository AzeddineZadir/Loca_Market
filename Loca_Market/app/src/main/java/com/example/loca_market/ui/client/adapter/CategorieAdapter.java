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
import com.example.loca_market.ui.client.Activities.ProductsOfCategoryActivity;
import com.example.loca_market.data.models.Category;
import com.example.loca_market.R;

import java.util.List;

public class CategorieAdapter extends RecyclerView.Adapter<CategorieAdapter.ViewHolder> {
    private Context context;
    private List<Category> mCategoryList;
    public CategorieAdapter(Context context, List<Category> mCategoryList) {
        this.context =context;
        this.mCategoryList=mCategoryList;
    }

    @NonNull
    @Override
    public CategorieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.client_home_single_category_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategorieAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(mCategoryList.get(position).getImageUrl()).into(holder.mCategoryImg);
        holder.mCategoryText.setText(mCategoryList.get(position).getName());
        holder.mCategoryImg.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context, ProductsOfCategoryActivity.class);
                intent.putExtra("Category",mCategoryList.get(position).getName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCategoryList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView mCategoryImg;
        private TextView mCategoryText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mCategoryImg =itemView.findViewById(R.id.category_img);
            mCategoryText=itemView.findViewById(R.id.t_Category);
        }
    }
}
