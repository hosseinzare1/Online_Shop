package com.example.onlineshop.utils;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class ImageSetter {


    @BindingAdapter("setImage")
    public static void setImage(ImageView view, String imageURL) {


        Glide.with(view.getContext())
                .load(imageURL)
                .into(view);

    }

}
