package com.example.loca_market.ui.seller.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loca_market.R;
import com.example.loca_market.data.models.Offer;

import java.util.ArrayList;
import java.util.List;

public class DeleteOfferAdapter extends RecyclerView.Adapter<DeleteOfferAdapter.OfferHolder> {
    private List<Offer> offers = new ArrayList<>();
    private final Context context;
    private final DeleteOfferAdapter.OnOfferItemListener onOfferItemListener;


    public DeleteOfferAdapter(Context context, DeleteOfferAdapter.OnOfferItemListener onOfferItemListener) {
        this.context = context;
        this.onOfferItemListener = onOfferItemListener;
    }

    @NonNull
    @Override
    public DeleteOfferAdapter.OfferHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.offer_delete_list_item, parent, false);
        return new DeleteOfferAdapter.OfferHolder(itemView, onOfferItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DeleteOfferAdapter.OfferHolder holder, int position) {
        Offer currentOffer = offers.get(position);
        holder.tv_item_offer_titel.setText(currentOffer.getOfferTitel());
        holder.tv_item_offer_date.setText(currentOffer.getBeginDate() + "-" + currentOffer.getEndDate());
        holder.tv_item_offer_percentage.setText(currentOffer.getPercentage() + " %");
        holder.tv_item_product_name.setText(currentOffer.getOfferProduct().getName());


    }

    @Override
    public int getItemCount() {
        return offers.size();
    }


    public void setOffers(List<Offer> offers) {

        this.offers = offers;
        notifyDataSetChanged();
    }

    class OfferHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView tv_item_offer_titel;
        private final TextView tv_item_offer_date;
        private final TextView tv_item_offer_percentage;
        private final TextView tv_item_product_name;
        private final ImageView iv_drop_offer;

        DeleteOfferAdapter.OnOfferItemListener onOfferItemListener;

        public OfferHolder(@NonNull View itemView, DeleteOfferAdapter.OnOfferItemListener onOfferItemListener) {
            super(itemView);
            tv_item_offer_titel = itemView.findViewById(R.id.tv_item_offer_titel);
            tv_item_offer_date = itemView.findViewById(R.id.tv_item_offer_date);
            tv_item_offer_percentage = itemView.findViewById(R.id.tv_item_offer_percentage);
            tv_item_product_name = itemView.findViewById(R.id.tv_item_product_name);
            iv_drop_offer = itemView.findViewById(R.id.iv_drop_offer);

            iv_drop_offer.setOnClickListener(this);

            this.onOfferItemListener = onOfferItemListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            if (v.getId() == itemView.getId()) {
                onOfferItemListener.onOfferClick(getAdapterPosition());
            } else if (v.getId() == iv_drop_offer.getId()) {

                onOfferItemListener.onDropOfferButtonClick(getAdapterPosition());
            }


        }
    }

    public interface OnOfferItemListener {
        void onOfferClick(int position);

        void onDropOfferButtonClick(int position);
    }
}

