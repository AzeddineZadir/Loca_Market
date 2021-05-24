package com.example.loca_market.ui.seller.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loca_market.R;
import com.example.loca_market.data.models.Order;

import java.util.ArrayList;
import java.util.List;

public class SellerOrdersAdapter  extends RecyclerView.Adapter<SellerOrdersAdapter.OrderHolder> {
private List<Order> orders= new ArrayList<>();
private Context context;
private SellerOrdersAdapter.OnOrderItemListener onOrderItemListener ;


public SellerOrdersAdapter(Context  context , SellerOrdersAdapter.OnOrderItemListener onOrderItemListener) {
        this.context=context ;
        this.onOrderItemListener = onOrderItemListener ;
        }

@NonNull
@Override
public SellerOrdersAdapter.OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
        .inflate(R.layout.seller_orders_list_item,parent,false);
        return new SellerOrdersAdapter.OrderHolder(itemView,onOrderItemListener);
        }

@Override
public void onBindViewHolder(@NonNull SellerOrdersAdapter.OrderHolder holder, int position) {
        Order currentOrder = orders.get(position);
        holder.tv_item_client_info.setText(currentOrder.getFirstName()+" "+currentOrder.getLastName());
        holder.tv_item_order_date.setText(currentOrder.getDate());
        holder.tv_item_order_satus.setText(currentOrder.getState());
        holder.tv_item_order_amoun.setText(currentOrder.getTotalAmount()+" €");

        }

@Override
public int getItemCount() {
        return orders.size();
        }


public void setProducts(List<Order> orders){
        this.orders = new ArrayList<>();
        this.orders = orders ;
        notifyDataSetChanged();
        }

class OrderHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView tv_item_client_info ;
    private TextView tv_item_order_date;
    private TextView tv_item_order_satus;
    private TextView tv_item_order_amoun;
    SellerOrdersAdapter.OnOrderItemListener onOrderItemListener ;
    public OrderHolder(@NonNull View itemView , SellerOrdersAdapter.OnOrderItemListener onOrderItemListener) {
        super(itemView);
        tv_item_client_info= itemView.findViewById(R.id.tv_item_client_info);
        tv_item_order_date= itemView.findViewById(R.id.tv_item_order_date);
        tv_item_order_satus= itemView.findViewById(R.id.tv_item_order_satus);
        tv_item_order_amoun= itemView.findViewById(R.id.tv_item_order_amoun);


        this.onOrderItemListener = onOrderItemListener ;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onOrderItemListener.onOrderClick(getAdapterPosition());
    }
}
public interface OnOrderItemListener{
    void onOrderClick (int position);
}
}


