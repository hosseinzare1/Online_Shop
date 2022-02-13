package com.example.onlineshop.utils.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.ImageSliderItemBinding;
import com.example.onlineshop.model.Image;

import java.util.ArrayList;
import java.util.List;

public class ImageSliderAdapter extends PagerAdapter {
    List<Image> images = new ArrayList<>();

    public ImageSliderAdapter(List<Image> images) {
        this.images = images;
    }

    public ImageSliderAdapter() {
    }

    public void setImages(List<Image> images){

        this.images=images;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return object == view;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageSliderItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(container.getContext()), R.layout.image_slider_item, container, false);

        binding.setModel(images.get(position));
        container.addView(binding.getRoot());

        return binding.getRoot();


    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
