package com.example.onlineshop.utils.adapters;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.VerticalItemCardBinding;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.view.ProductListFragmentDirections;

import java.util.ArrayList;
import java.util.List;

public class ProductsListAdapter extends RecyclerView.Adapter<ProductsListAdapter.Holder> {

    VerticalItemCardBinding binding;
    List<Product> homeItems = new ArrayList<>();

    public interface OnClickListener {
        void OnItemClickListener(int id);
    }

    OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.vertical_item_card, parent, false);
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

        VerticalItemCardBinding binding;

        public Holder(@NonNull VerticalItemCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnClickListener(view ->
                    Navigation.findNavController(view).navigate(ProductListFragmentDirections
                            .actionProductListFragmentToCommodityMainFragment(homeItems.get(getAdapterPosition()).getId())));

            binding.getRoot().setOnClickListener(view -> onClickListener.OnItemClickListener(
                    homeItems.get(getAdapterPosition()).getId()));

            binding.originalPriceItemCard.setPaintFlags(
                    binding.originalPriceItemCard.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG
            );

        }
    }
}
