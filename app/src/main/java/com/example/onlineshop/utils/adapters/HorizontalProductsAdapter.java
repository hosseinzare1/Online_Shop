package com.example.onlineshop.utils.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshop.DataBinderMapperImpl;
import com.example.onlineshop.R;
import com.example.onlineshop.databinding.HorizontalItemCardBinding;
import com.example.onlineshop.model.HomeItem;

import java.util.ArrayList;
import java.util.List;

public class HorizontalProductsAdapter extends RecyclerView.Adapter<HorizontalProductsAdapter.HorizontalViewHolder> {

List<HomeItem> homeItems = new ArrayList<>();
    @NonNull
    @Override
    public HorizontalProductsAdapter.HorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        HorizontalItemCardBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.horizontal_item_card,parent,false);


        HorizontalViewHolder viewHolder = new HorizontalViewHolder(binding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalProductsAdapter.HorizontalViewHolder holder, int position) {
            holder.binding.setModel(homeItems.get(position));
    }

    public void setItems(List<HomeItem> items){
        this.homeItems = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return homeItems.size();
    }

    public class HorizontalViewHolder extends RecyclerView.ViewHolder{

        HorizontalItemCardBinding binding;

        public HorizontalViewHolder(@NonNull HorizontalItemCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
