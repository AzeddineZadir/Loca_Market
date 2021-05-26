package com.example.loca_market.ui.client.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loca_market.R;
import com.example.loca_market.data.models.Category;
import com.example.loca_market.data.models.Store;

import java.util.List;

public class SearchCategoryAdapter extends RecyclerView.Adapter<SearchCategoryAdapter.ViewHolder>{

    private Context context;
    private List<Category> categoryList;

    public SearchCategoryAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public SearchCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.client_all_categories_single_category_item,parent,false);
        return new SearchCategoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchCategoryAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView categoryImg;
        private TextView categoryName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryImg = itemView.findViewById(R.id.i_category_image);
            categoryName=itemView.findViewById(R.id.t_category_Name);
        }
    }
}
