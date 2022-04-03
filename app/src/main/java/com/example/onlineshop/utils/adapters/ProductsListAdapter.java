package com.example.onlineshop.utils.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.ItemCardBinding;
import com.example.onlineshop.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductsListAdapter extends RecyclerView.Adapter<ProductsListAdapter.Holder> {

    ItemCardBinding binding;
    List<Product> homeItems = new ArrayList<>();

    public interface OnClickListener {
        public void OnItemClickListener(int id);
    }

    OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }


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


    public void setProducts(List<Product> homeItems) {
        this.homeItems = homeItems;
        notifyDataSetChanged();
    }

    public class Holder extends RecyclerView.ViewHolder {

        ItemCardBinding binding;

        public Holder(@NonNull ItemCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

//            binding.getRoot().setOnClickListener(view ->
//                    Navigation.findNavController(view).navigate(ProductListFragmentDirections.
//                    actionProductListFragmentToProductDetailsFragment(homeItems.get(getAdapterPosition()).getId())));

            binding.getRoot().setOnClickListener(view -> onClickListener.OnItemClickListener(
                    Integer.parseInt(homeItems.get(getAdapterPosition()).getId())));


        }
    }
}
