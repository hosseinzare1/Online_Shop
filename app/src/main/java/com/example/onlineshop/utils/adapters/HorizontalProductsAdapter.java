package com.example.onlineshop.utils.adapters;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.HorizontalItemCardBinding;
import com.example.onlineshop.model.Product;

import java.util.ArrayList;
import java.util.List;

public class HorizontalProductsAdapter extends RecyclerView.Adapter<HorizontalProductsAdapter.HorizontalViewHolder> {

    List<Product> homeItems = new ArrayList<>();

    public interface OnClickListener {
        public void onProductClickListener(int id);
    }

    OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public HorizontalProductsAdapter.HorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        HorizontalItemCardBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.horizontal_item_card, parent, false);


        HorizontalViewHolder viewHolder = new HorizontalViewHolder(binding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalProductsAdapter.HorizontalViewHolder holder, int position) {
        holder.binding.setModel(homeItems.get(position));
    }

    public void setItems(List<Product> items) {
        this.homeItems = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return homeItems.size();
    }

    public class HorizontalViewHolder extends RecyclerView.ViewHolder {

        HorizontalItemCardBinding binding;

        public HorizontalViewHolder(@NonNull HorizontalItemCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            if (onClickListener != null) {
                binding.getRoot().setOnClickListener((view -> onClickListener.onProductClickListener(Integer.parseInt(binding.getModel().getId()))));
            }
            binding.originalPriceHorizontalItem.setPaintFlags(
                    binding.originalPriceHorizontalItem.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }


    }
}
