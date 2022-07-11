package com.example.onlineshop.utils.adapters;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.ItemOrderProductBinding;
import com.example.onlineshop.model.CartItemModel;

import java.util.ArrayList;
import java.util.List;

public class OrderProductAdapter extends RecyclerView.Adapter<OrderProductAdapter.OrderProductViewHolder> {

    private static final String TAG = "OrderProductAdapter";
    List<CartItemModel> items = new ArrayList<>();

    public interface ClickListener {
        void onItemClick(int id);
    }

    ClickListener clickListener;

    public void setOnClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setItems(List<CartItemModel> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemOrderProductBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_order_product, parent, false);

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
        ItemOrderProductBinding binding;

        public OrderProductViewHolder(@NonNull ItemOrderProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(view -> clickListener.onItemClick(items.get(getAdapterPosition()).getId()));
        }
    }

}
