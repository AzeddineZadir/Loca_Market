package com.example.loca_market.Client.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.loca_market.Models.Category;
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
        View view = LayoutInflater.from(context).inflate(R.layout.single_category_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategorieAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(mCategoryList.get(position).getImg_url()).into(holder.mTypeImg);
    }

    @Override
    public int getItemCount() {
        return mCategoryList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView mTypeImg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTypeImg =itemView.findViewById(R.id.category_img);
        }
    }
}
