package com.example.onlineshop.utils.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.ItemCardBinding;
import com.example.onlineshop.model.HomeItem;
import com.example.onlineshop.view.ProductListFragmentDirections;

import java.util.ArrayList;
import java.util.List;

public class ProductsListAdapter extends RecyclerView.Adapter<ProductsListAdapter.Holder> {

    ItemCardBinding binding;
    List<HomeItem> homeItems = new ArrayList<>();

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_card, parent, false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.binding.setModel(homeItems.get(position));

    }

    @Override
    public int getItemCount() {
        return homeItems.size();
    }


    public void setProducts(List<HomeItem> homeItems) {
        this.homeItems = homeItems;
        notifyDataSetChanged();
    }

    public class Holder extends RecyclerView.ViewHolder {

        ItemCardBinding binding;

        public Holder(@NonNull ItemCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Navigation.findNavController(view).navigate(ProductListFragmentDirections.
                            actionProductListFragmentToProductDetailsFragment(homeItems.get(getAdapterPosition()).getId()));
                }
            });

        }
    }
}
