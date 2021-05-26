package com.example.loca_market.ui.seller.addOffer;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loca_market.R;
import com.example.loca_market.data.models.Product;
import com.example.loca_market.ui.seller.adapters.SellerAddOfferSearchProductAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddOfferFragment extends Fragment {
    private TextInputLayout tif_offer_titel,tif_percentage,tif_offer_product,tif_offer_begin_date,tif_offer_end_date;
    private Button b_offer_begin_date,b_add_offer;
    private FirebaseFirestore firebaseFirestore;
    private List<Product> productsListSearch;
    private List<Product>allProducts;
    private RecyclerView productSearchRecyclerView;
    private SellerAddOfferSearchProductAdapter productSearchAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_offer, container, false);
        // construction du  date picker
        MaterialDatePicker.Builder<Pair<Long, Long>> builder =MaterialDatePicker.Builder.dateRangePicker() ;
        builder.setTitleText("la date du debut ");
        MaterialDatePicker materialDatePickerBegin = builder.build();



        tif_offer_titel= (TextInputLayout)view.findViewById(R.id.tif_offer_titel);
        tif_percentage= (TextInputLayout)view.findViewById(R.id.tif_percentage);
        tif_offer_product= (TextInputLayout)view.findViewById(R.id.tif_offer_product);

        b_offer_begin_date= (Button)view.findViewById(R.id.b_offer_begin_date) ;
        b_add_offer= (Button)view.findViewById(R.id.b_add_offer) ;
        // afficher le date picker de la date de debut
        b_offer_begin_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePickerBegin.show(getParentFragmentManager(),"date debut ");

            }
        });

        materialDatePickerBegin.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onPositiveButtonClick(Object selection) {

                Pair selectedDates = (Pair) materialDatePickerBegin.getSelection();
//              then obtain the startDate & endDate from the range
                final Pair<Date, Date> rangeDate = new Pair<>(new Date((Long) selectedDates.first), new Date((Long) selectedDates.second));
//              assigned variables
                Date startDate = rangeDate.first;
                Date endDate = rangeDate.second;
//              Format the dates in ur desired display mode
                SimpleDateFormat simpleFormat = new SimpleDateFormat("dd MMM yyyy");
//              Display it by setText
                b_offer_begin_date.setText(simpleFormat.format(startDate) +" : "+ simpleFormat.format(endDate));

            }
        });

        // get all products
        firebaseFirestore=FirebaseFirestore.getInstance();
        allProducts = new ArrayList<>();
        firebaseFirestore.collection("products").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful() && task.getResult()!=null){
                    for(DocumentSnapshot doc:task.getResult().getDocuments()){
                        Product product=doc.toObject(Product.class);
                        allProducts.add(product);


                    }
                }
            }
        } );
        // search products
        tif_offer_product= view.findViewById(R.id.tif_offer_product);
        productsListSearch =new ArrayList<>();
        productSearchRecyclerView = view.findViewById(R.id.offer_product_search_recycler);
        productSearchRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        productSearchAdapter = new SellerAddOfferSearchProductAdapter(getContext(), productsListSearch);
        productSearchRecyclerView.setAdapter(productSearchAdapter);

        tif_offer_product.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().isEmpty()){
                    productsListSearch.clear();
                    productSearchAdapter.notifyDataSetChanged();
                }else {
                    productsListSearch.clear();
                    searchProduct(s.toString());
                    productSearchAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }

    private void searchProduct(String text) {
        productsListSearch.clear();
        if(!text.isEmpty()){
            for(Product product : allProducts){
                if(product.getName().toLowerCase().contains(text.toLowerCase())){
                    productsListSearch.add(product);
                    productSearchAdapter.notifyDataSetChanged();
                }
            }
        }

    }

}
