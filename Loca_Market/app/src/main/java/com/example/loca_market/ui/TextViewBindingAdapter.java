package com.example.loca_market.ui;

import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;

public class TextViewBindingAdapter {

    @BindingAdapter("android:text")
    public static void setText(TextView view, Float value) {
        if (value == null)
            return;

        view.setText(String.valueOf(value));
    }

    @InverseBindingAdapter(attribute = "android:text", event = "android:textAttrChanged")
    public static Float getTextString(TextView view) {
        Float f = new Float(0);
        if (!view.getText().toString().isEmpty()){
            return Float.valueOf(view.getText().toString());
        }else{
            return f;
        }
    }
}
