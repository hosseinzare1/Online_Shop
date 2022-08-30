package com.example.onlineshop.utils;

import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputLayout;

public class ImageSetter {

static String TAG = "ImageSetter";
    @BindingAdapter("setImage")
    public static void setImage(ImageView view, String imageURL) {
        Glide.with(view.getContext())
                .load(imageURL)
                .into(view);

        Log.i(TAG, "setImage: "+imageURL);
    }


    @BindingAdapter("app:errorText")
    public static void setErrorMessage(TextInputLayout view, String errorMessage) {
        view.setError(errorMessage);
    }

}
