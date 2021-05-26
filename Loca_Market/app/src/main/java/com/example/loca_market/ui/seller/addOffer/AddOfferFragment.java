package com.example.loca_market.ui.seller.addOffer;

import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.util.Pair;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.loca_market.R;
import com.example.loca_market.ui.seller.addProduct.AddProductViewModel;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddOfferFragment extends Fragment {
    private TextInputLayout tif_offer_titel,tif_percentage,tif_offer_product,tif_offer_begin_date,tif_offer_end_date;
    private Button b_offer_begin_date,b_add_offer;

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







        return view;
    }

}
