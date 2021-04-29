package com.example.loca_market.ui.client.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.loca_market.R;
import com.example.loca_market.data.models.Product;
import com.example.loca_market.data.models.ProductCart;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class CartProductAdapter extends RecyclerView.Adapter<CartProductAdapter.ViewHolder> {
    List<ProductCart> productCartList;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    ProductCartRemoved productCartRemoved;

    public CartProductAdapter(List<ProductCart> productCartList, ProductCartRemoved productCartRemoved) {
        this.productCartList = productCartList;
        this.productCartRemoved = productCartRemoved;
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth =FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.client_single_cart_product,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.productCartName.setText(productCartList.get(position).getProduct().getName());
        holder.productCartQuantity.setText(""+productCartList.get(position).getQuantity());
        holder.productCartPrice.setText("prix :  "+productCartList.get(position).getProduct().getPrice()+" €");
        Glide.with(holder.itemView.getContext()).load(productCartList.get(position).getProduct().getImageUrl()).into(holder.productCartImage);
        holder.removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseFirestore.collection("users").document(firebaseAuth.getCurrentUser().getUid())
                        .collection("cart").document(productCartList.get(position).getDocId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            productCartList.remove(productCartList.get(position));
                            notifyDataSetChanged();
                            productCartRemoved.onProductRemoved(productCartList);
                        }else{
                            Toast.makeText(holder.itemView.getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return productCartList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productCartImage;
        TextView productCartName;
        TextView productCartQuantity;
        TextView productCartPrice;
        TextView removeItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productCartImage = itemView.findViewById(R.id.i_cart_image);
            productCartName = itemView.findViewById(R.id.t_product_cart_name);
            productCartQuantity =itemView.findViewById(R.id.t_product_cart_quantity);
            productCartPrice = itemView.findViewById(R.id.t_product_cart_price);
            removeItem = itemView.findViewById(R.id.t_remove_product_cart);
        }
    }
    public interface ProductCartRemoved{
        public void onProductRemoved(List<ProductCart> productCartList);
    }
}
