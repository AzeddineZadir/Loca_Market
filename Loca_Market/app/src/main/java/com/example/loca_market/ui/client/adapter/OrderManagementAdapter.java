package com.example.loca_market.ui.client.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loca_market.R;
import com.example.loca_market.data.models.Order;
import com.example.loca_market.data.models.ProductCart;
import com.example.loca_market.ui.client.Activities.DetailOrderActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class OrderManagementAdapter extends RecyclerView.Adapter<OrderManagementAdapter.ViewHolder> {
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private Context context;
    private List<Order> orderListe;

    public OrderManagementAdapter(Context context, List<Order> orderListe) {
        this.context = context;
        this.orderListe = orderListe;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.client_order_management_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.orderTitle.setText("Commande " + (position + 1));
        holder.orderDate.setText("Date : " + orderListe.get(position).getDate() + " à " + orderListe.get(position).getTime());
        holder.orderState.setText("Etat : "+ orderListe.get(position).getState());

        holder.orderTotalPrice.setText("Prix Total : " + orderListe.get(position).getTotalAmount() + " €");


        if ( orderListe.get(position).getState().trim().equals("Accépté")) {
            holder.removeOrder.setVisibility(View.INVISIBLE);
        } else {
            holder.removeOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    firebaseFirestore.collection("orders").document(orderListe.get(position).getOrderId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                orderListe.remove(orderListe.get(position));
                                notifyDataSetChanged();
                            } else {
                                Toast.makeText(holder.itemView.getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            });
        }
        holder.detailOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailOrderActivity.class);
                intent.putExtra("orderDetail", orderListe.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return orderListe.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderTitle;
        TextView orderDate;
        TextView orderState;
        TextView orderTotalPrice;
        TextView removeOrder;
        TextView detailOrder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderTitle = itemView.findViewById(R.id.t_order_title);
            orderDate = itemView.findViewById(R.id.t_order_date);
            orderState = itemView.findViewById(R.id.t_order_state);
            orderTotalPrice = itemView.findViewById(R.id.t_order_total_price);
            removeOrder = itemView.findViewById(R.id.t_b_aboart_order);
            detailOrder = itemView.findViewById(R.id.t_b_order_details);
        }
    }


}
