package com.example.loca_market.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.loca_market.R;

public class StoreFragment  extends Fragment {

    TextView tv_text  ;
    public StoreFragment (){
        super(R.layout.fragment_store);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store, container, false);

        tv_text = (TextView)view.findViewById(R.id.tv_text);
        tv_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "zaaaaaaaaaaaaaaaaaaaa", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
