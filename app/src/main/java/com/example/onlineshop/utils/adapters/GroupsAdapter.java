package com.example.onlineshop.utils.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.GroupItemBinding;
import com.example.onlineshop.model.Category;
import com.example.onlineshop.model.Group;
import com.example.onlineshop.view.ProductsGroupCategoryFragmentDirections;
import com.example.onlineshop.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.GroupViewHolder> {

    public void setGroups(List<Group> groups) {
        this.groups = groups;
        notifyDataSetChanged();
    }

    private static final String TAG = "GroupsAdapter";

    List<Group> groups = new ArrayList<>();
    MainActivityViewModel viewModel;
    Context context;

    public GroupsAdapter(MainActivityViewModel viewModel, Context context) {
        this.viewModel = viewModel;
        this.context = context;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        GroupItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.group_item
                , parent
                , false);
        return new GroupViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        Group group = groups.get(position);

        CategoryAdapter adapter = new CategoryAdapter();
        holder.binding.categorysRecyclerView.setAdapter(adapter);
        holder.binding.categorysRecyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, true));
        holder.binding.setModel(group);
        viewModel.getCategories(Integer.parseInt(group.getId())).observe((LifecycleOwner) context, new Observer<List<Category>>() {

            @Override
            public void onChanged(List<Category> categories) {
                adapter.setCategoriesList(categories);
                Log.i(TAG, "onChanged: " + categories.get(1).getName());
            }
        });


    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    public class GroupViewHolder extends RecyclerView.ViewHolder {
        GroupItemBinding binding;

        public GroupViewHolder(@NonNull GroupItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.groupItemCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Navigation.findNavController(view).navigate(ProductsGroupCategoryFragmentDirections.
                            actionProductsGroupCategoryFragmentToProductListFragment(
                                    groups.get(getAdapterPosition()).getName(), ""

                            ));
                }
            });

            binding.groupItemsCount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Navigation.findNavController(view).navigate(ProductsGroupCategoryFragmentDirections.actionProductsGroupCategoryFragmentToProductListFragment(
                            "",
                            groups.get(getAdapterPosition()).getName()
                    ));
                }
            });


        }
    }
}
