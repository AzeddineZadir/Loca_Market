package com.example.loca_market.ui.seller;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.loca_market.R;
import com.example.loca_market.data.repositores.UserRepository;
import com.example.loca_market.ui.userAuth.LoginActivity;

public class StoreFragment  extends Fragment {

    TextView tv_text  ;
    Button b_logout ;
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
        b_logout = (Button)view.findViewById(R.id.b_store_logout) ;

        tv_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "zaaaaaaaaaaaaaaaaaaaa", Toast.LENGTH_SHORT).show();
            }
        });

        b_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserRepository.logOut();
                Toast.makeText(getContext(), "you just signed out ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                intent.putExtra("role","seller");
                startActivity(intent);
            }
        });
        return view;
    }
}
