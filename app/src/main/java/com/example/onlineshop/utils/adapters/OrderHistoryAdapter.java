package com.example.onlineshop.utils.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.CardOrderBinding;
import com.example.onlineshop.model.Order;
import com.example.onlineshop.view.account.OrderHistoryListFragmentDirections;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryViewHolder> {


    List<Order> orders = new ArrayList<>();

    public void setOrders(List<Order> orders) {
        this.orders = orders;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardOrderBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.card_order, parent, false);
        return new OrderHistoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryViewHolder holder, int position) {
        holder.binding.setModel(orders.get(position));
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class OrderHistoryViewHolder extends RecyclerView.ViewHolder {
        CardOrderBinding binding;

        public OrderHistoryViewHolder(@NonNull CardOrderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnClickListener(view -> Navigation.findNavController(view)
                    .navigate(OrderHistoryListFragmentDirections.actionOrderHistoryListFragmentToOrderDetailsFragment(String.valueOf(orders.get(getAdapterPosition()).getId()))));
        }
    }
}
