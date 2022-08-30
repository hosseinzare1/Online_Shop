package com.example.onlineshop.utils.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.CardCartBinding;
import com.example.onlineshop.model.CartProduct;

import java.util.ArrayList;
import java.util.List;

public class CartProductAdapter extends RecyclerView.Adapter<CartProductAdapter.CartHolder> {
    CardCartBinding binding;
    List<CartProduct> cartProducts = new ArrayList<>();


    OnCartProductData onCartProductData;

    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.card_cart, parent, false);
        return new CartHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull CartHolder holder, int position) {
        CartProduct model = cartProducts.get(position);
        holder.bind(model);
        holder.setIsRecyclable(false);
    }


    @Override
    public int getItemCount() {
        return cartProducts.size();
    }

    public void clearItems() {
        List<CartProduct> emptyList = new ArrayList<>();
        setCartItemModels(emptyList);
//        cartProducts.clear();
//        notifyDataSetChanged();
    }


    public class CartHolder extends RecyclerView.ViewHolder {

        public void bind(CartProduct product) {
            binding.setModel(product);
        }

        public CartHolder(@NonNull CardCartBinding binding) {
            super(binding.getRoot());

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

    public void setCartItemModels(List<CartProduct> data) {
        DiffCallBack diffCallBack = new DiffCallBack(cartProducts, data);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallBack);
        this.cartProducts = data;
        diffResult.dispatchUpdatesTo(this);
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

    class DiffCallBack extends DiffUtil.Callback {

        List<CartProduct> oldItems = new ArrayList<>();
        List<CartProduct> newItems = new ArrayList<>();

        public DiffCallBack(List<CartProduct> oldItems, List<CartProduct> newItems) {
            this.oldItems = oldItems;
            this.newItems = newItems;
        }

        @Override
        public int getOldListSize() {
            return oldItems.size();
        }

        @Override
        public int getNewListSize() {
            return newItems.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return (oldItems.get(oldItemPosition) == newItems.get(newItemPosition));
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return (oldItems.get(oldItemPosition).quantity == newItems.get(newItemPosition).quantity);
        }
    }


}
