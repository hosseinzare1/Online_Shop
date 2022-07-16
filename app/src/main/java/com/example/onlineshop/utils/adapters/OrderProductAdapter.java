package com.example.onlineshop.utils.adapters;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.CardProductOrderBinding;
import com.example.onlineshop.model.CartProduct;

import java.util.ArrayList;
import java.util.List;

public class OrderProductAdapter extends RecyclerView.Adapter<OrderProductAdapter.OrderProductViewHolder> {

    private static final String TAG = "OrderProductAdapter";
    List<CartProduct> items = new ArrayList<>();

    public interface ClickListener {
        void onItemClick(int id);
    }

    ClickListener clickListener;

    public void setOnClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setItems(List<CartProduct> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardProductOrderBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.card_product_order, parent, false);

        return new OrderProductViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderProductViewHolder holder, int position) {
        holder.binding.setModel(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class OrderProductViewHolder extends RecyclerView.ViewHolder {
        CardProductOrderBinding binding;

        public OrderProductViewHolder(@NonNull CardProductOrderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(view -> clickListener.onItemClick(items.get(getAdapterPosition()).getId()));
        }
    }

}
