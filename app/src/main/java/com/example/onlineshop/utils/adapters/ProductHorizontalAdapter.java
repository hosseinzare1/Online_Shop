package com.example.onlineshop.utils.adapters;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.CardProductHorizontalBinding;
import com.example.onlineshop.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductHorizontalAdapter extends RecyclerView.Adapter<ProductHorizontalAdapter.HorizontalViewHolder> {

    List<Product> homeItems = new ArrayList<>();

    public interface OnClickListener {
        void onProductClickListener(int id);
    }

    OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ProductHorizontalAdapter.HorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        CardProductHorizontalBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.card_product_horizontal, parent, false);


        return new HorizontalViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHorizontalAdapter.HorizontalViewHolder holder, int position) {
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

        CardProductHorizontalBinding binding;

        public HorizontalViewHolder(@NonNull CardProductHorizontalBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            if (onClickListener != null) {
                binding.getRoot().setOnClickListener((view -> onClickListener.onProductClickListener(binding.getModel().getId())));
            }
            binding.originalPriceHorizontalItem.setPaintFlags(
                    binding.originalPriceHorizontalItem.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }


    }
}
