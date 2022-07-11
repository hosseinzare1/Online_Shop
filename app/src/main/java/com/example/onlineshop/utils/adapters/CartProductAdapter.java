package com.example.onlineshop.utils.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.CartItemCardBinding;
import com.example.onlineshop.model.CartProduct;

import java.util.ArrayList;
import java.util.List;

public class CartProductAdapter extends RecyclerView.Adapter<CartProductAdapter.CartHolder> {
    CartItemCardBinding binding;
    List<CartProduct> cartProducts = new ArrayList<>();


    OnCartProductData onCartProductData;

    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.cart_item_card, parent, false);
        return new CartHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull CartHolder holder, int position) {
        CartProduct model = cartProducts.get(position);
        binding.setModel(model);
    }


    @Override
    public int getItemCount() {
        return cartProducts.size();
    }


    public class CartHolder extends RecyclerView.ViewHolder {
        CartItemCardBinding binding;

        public CartHolder(@NonNull CartItemCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.cartItemImageView.setOnClickListener(view ->
                            onCartProductData.onProductClickListener(cartProducts.get(getAdapterPosition()).getId())
//                    Navigation.findNavController(view).navigate(
//                            CartFragmentDirections.actionCartFragmentToCommodityMainFragment(cartProducts.get(getAdapterPosition()).getId())
//            )
            );

            binding.cartItemTitle.setOnClickListener(view ->
                            onCartProductData.onProductClickListener(cartProducts.get(getAdapterPosition()).getId())

//                    Navigation.findNavController(view).navigate(
//                    CartFragmentDirections.actionCartFragmentToCommodityMainFragment(cartProducts.get(getAdapterPosition()).getId())
//            )
            );


            binding.addButton.setOnClickListener(view -> {
                if (onCartProductData != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    onCartProductData.onAddClickListener(cartProducts.get(getAdapterPosition()));
                }
            });

            binding.minesButton.setOnClickListener(view -> {
                if (onCartProductData != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    onCartProductData.onReduceClickListener(cartProducts.get(getAdapterPosition()));
                }
            });

            binding.deleteButton.setOnClickListener(view -> {
                if (onCartProductData != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    onCartProductData.onDeleteClickListener(cartProducts.get(getAdapterPosition()));
                }
            });

        }
    }

    public void setCartItemModels(List<CartProduct> cartProducts) {
        this.cartProducts = cartProducts;

        notifyDataSetChanged();
    }

    public interface OnCartProductData {
        void onAddClickListener(CartProduct item);

        void onReduceClickListener(CartProduct item);

        void onDeleteClickListener(CartProduct item);

        void onProductClickListener(int id);
    }

    public void setOnCartProductData(OnCartProductData onCartProductData) {
        this.onCartProductData = onCartProductData;
    }
}
