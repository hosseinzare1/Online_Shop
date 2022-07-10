package com.example.onlineshop.utils.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.ItemCardSpecificationBinding;
import com.example.onlineshop.model.Attribute;

import java.util.ArrayList;
import java.util.List;

public class AttributesAdapter extends RecyclerView.Adapter<AttributesAdapter.AttributesViewHolder> {

    private List<Attribute> attributes = new ArrayList<>();
String TAG = "AttributesAdapter";
    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
        notifyDataSetChanged();
        Log.i(TAG, "setAttributes: ");
    }


    @NonNull
    @Override
    public AttributesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCardSpecificationBinding binding;

        binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_card_specification,
                parent,
                false);

        return new AttributesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AttributesViewHolder holder, int position) {
        holder.binding.setModel(attributes.get(position));
        Log.i(TAG, "onBindViewHolder: "+attributes.get(position));
    }

    @Override
    public int getItemCount() {
        return attributes.size();
    }

    public class AttributesViewHolder extends RecyclerView.ViewHolder {
        ItemCardSpecificationBinding binding;

        public AttributesViewHolder(@NonNull ItemCardSpecificationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
