package com.example.onlineshop.utils;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputLayout;

public class ImageSetter {


    @BindingAdapter("setImage")
    public static void setImage(ImageView view, String imageURL) {


        Glide.with(view.getContext())
                .load(imageURL)
                .into(view);

    }


    @BindingAdapter("app:errorText")
    public static void setErrorMessage(TextInputLayout view, String errorMessage) {
        view.setError(errorMessage);
    }

}
