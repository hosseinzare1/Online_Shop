package com.example.onlineshop.utils.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.CardAttributeBinding;
import com.example.onlineshop.model.Attribute;

import java.util.ArrayList;
import java.util.List;

public class AttributeAdapter extends RecyclerView.Adapter<AttributeAdapter.AttributesViewHolder> {

    private List<Attribute> attributes = new ArrayList<>();
String TAG = "AttributeAdapter";
    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public AttributesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardAttributeBinding binding;

        binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.card_attribute,
                parent,
                false);

        return new AttributesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AttributesViewHolder holder, int position) {
        holder.binding.setModel(attributes.get(position));
    }

    @Override
    public int getItemCount() {
        return attributes.size();
    }

    public static class AttributesViewHolder extends RecyclerView.ViewHolder {
        CardAttributeBinding binding;

        public AttributesViewHolder(@NonNull CardAttributeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
