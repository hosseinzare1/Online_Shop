package com.example.onlineshop.utils.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.CartItemCardBinding;
import com.example.onlineshop.model.CartItemModel;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder> {
    CartItemCardBinding binding;
    List<CartItemModel> cartItemModels = new ArrayList<>();


    OnCartProductData onCartProductData;

    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.cart_item_card, parent, false);
        return new CartHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull CartHolder holder, int position) {
        CartItemModel model = cartItemModels.get(position);
        binding.setModel(model);
    }


    @Override
    public int getItemCount() {
        return cartItemModels.size();
    }


    public class CartHolder extends RecyclerView.ViewHolder {
        CartItemCardBinding binding;

        public CartHolder(@NonNull CartItemCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.cartItemImageView.setOnClickListener(view ->
                            onCartProductData.onProductClickListener(cartItemModels.get(getAdapterPosition()).getId())
//                    Navigation.findNavController(view).navigate(
//                            CartFragmentDirections.actionCartFragmentToCommodityMainFragment(cartItemModels.get(getAdapterPosition()).getId())
//            )
            );

            binding.cartItemTitle.setOnClickListener(view ->
                            onCartProductData.onProductClickListener(cartItemModels.get(getAdapterPosition()).getId())

//                    Navigation.findNavController(view).navigate(
//                    CartFragmentDirections.actionCartFragmentToCommodityMainFragment(cartItemModels.get(getAdapterPosition()).getId())
//            )
            );


            binding.addButton.setOnClickListener(view -> {
                if (onCartProductData != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    onCartProductData.onAddClickListener(cartItemModels.get(getAdapterPosition()));
                }
            });

            binding.minesButton.setOnClickListener(view -> {
                if (onCartProductData != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    onCartProductData.onReduceClickListener(cartItemModels.get(getAdapterPosition()));
                }
            });

            binding.deleteButton.setOnClickListener(view -> {
                if (onCartProductData != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    onCartProductData.onDeleteClickListener(cartItemModels.get(getAdapterPosition()));
                }
            });

        }
    }

    public void setCartItemModels(List<CartItemModel> cartItemModels) {
        this.cartItemModels = cartItemModels;

        notifyDataSetChanged();
    }

    public interface OnCartProductData {
        void onAddClickListener(CartItemModel item);

        void onReduceClickListener(CartItemModel item);

        void onDeleteClickListener(CartItemModel item);

        void onProductClickListener(int id);
    }

    public void setOnCartProductData(OnCartProductData onCartProductData) {
        this.onCartProductData = onCartProductData;
    }
}
