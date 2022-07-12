package com.example.onlineshop.utils.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.CardCategoryBinding;
import com.example.onlineshop.model.Category;
import com.example.onlineshop.view.category.ProductsGroupCategoryFragmentDirections;


import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    List<Category> categoriesList = new ArrayList<>();

    public void setCategoriesList(List<Category> categoriesList) {
        this.categoriesList = categoriesList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardCategoryBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext())
                , R.layout.card_category
                , parent
                , false);

        return new CategoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categoriesList.get(position);

        holder.binding.setModel(category);

    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        CardCategoryBinding binding;

        public CategoryViewHolder(CardCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener((view -> Navigation.findNavController(view).navigate(
                    ProductsGroupCategoryFragmentDirections
                            .actionProductsGroupCategoryFragmentToProductListFragment(
                                    categoriesList.get(getAdapterPosition()).getName(), ""))));
        }
    }
}
