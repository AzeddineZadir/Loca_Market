package com.example.loca_market.ui.seller.OrdersList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loca_market.R;
import com.example.loca_market.data.models.Order;
import com.example.loca_market.data.models.ProductCart;
import com.example.loca_market.ui.seller.adapters.SellerOrdersAdapter;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class OrdersFragment extends Fragment implements  SellerOrdersAdapter.OnOrderItemListener{

    private static final String TAG ="OrdersFragment" ;
    private OrdersFragmentViewModel ordersFragmentViewModel;
    private RecyclerView rv_seller_orders ;
    private SellerOrdersAdapter sellerOrdersAdapter ;
    private List<Order> orderList ;

    public FirebaseUser currentuser ;
    public OrdersFragment(){

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        currentuser= FirebaseAuth.getInstance().getCurrentUser();
        rv_seller_orders = view.findViewById(R.id.rv_seller_orders);
        orderList = new ArrayList<>();

        sellerOrdersAdapter = new SellerOrdersAdapter(getContext(),this);
        rv_seller_orders.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_seller_orders.setAdapter(sellerOrdersAdapter);
        ordersFragmentViewModel = new ViewModelProvider(requireActivity()).get(OrdersFragmentViewModel.class);

        observeOrders();



        return view;
    }


    private void observeOrders(){
        ordersFragmentViewModel.getOrders().observe(getViewLifecycleOwner(), new Observer<ArrayList<Order>>() {
            @Override
            public void onChanged(ArrayList<Order> orders) {
                orderList.clear();
                orderList =filterSellerOrders(orders);

                sellerOrdersAdapter.setProducts(orderList);
            }
        });
    }

    @Override
    public void onOrderClick(int position) {

        Order orderToValidate = orderList.get(position);
        if (!orderToValidate.getState().equals("Accépté")) {
            Snackbar snackbar = Snackbar.make(getView(), "voulez vous valider la commande de  " + orderToValidate.getFirstName() + " " + orderToValidate.getLastName(), BaseTransientBottomBar.LENGTH_LONG)
                    .setAction("OUI", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ordersFragmentViewModel.validateOrder(orderToValidate.getOrderId()).observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                                @Override
                                public void onChanged(Boolean aBoolean) {
                                    if (aBoolean == true) {
                                        Snackbar.make(getView(), "le produit a été supprimé correctement", BaseTransientBottomBar.LENGTH_LONG).show();
                                       observeOrders();

                                    } else {
                                        Snackbar.make(getView(), "un probleme lors de la suppression ", BaseTransientBottomBar.LENGTH_LONG).show();
                                    }
                                }
                            });


                        }
                    });
            snackbar.show();
        }
    }

    private  ArrayList<ProductCart> filterProductOrdersBySeller(ArrayList<Order> orders, String seller_id){
        ArrayList<ProductCart> productCarts = new ArrayList<>();
        for (Order order:orders) {
            for (ProductCart product :order.getProductsOrdred()) {
                if(product.getProduct().getProductOwner().equals(seller_id)){
                    productCarts.add(product);
                }
            }
        }

        return  productCarts;
    }
    private ArrayList<Order> filterSellerOrders( ArrayList<Order>  orders){
        ArrayList<Order> filterdOrders = new ArrayList<>();
        boolean toSeller =false ;

        for (Order order:orders) {
            for (ProductCart productCart : order.getProductsOrdred() ){
                if (productCart.getProduct().getProductOwner().equals(currentuser.getUid())){
                    filterdOrders.add(order);
                    break;
                }
            }
        }



        return filterdOrders ;
    }
}
