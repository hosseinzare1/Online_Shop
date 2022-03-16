package com.example.onlineshop.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.FragmentProductListBinding;
import com.example.onlineshop.databinding.FragmentProductsCategoryBinding;
import com.example.onlineshop.model.Category;
import com.example.onlineshop.model.Group;
import com.example.onlineshop.utils.adapters.GroupsAdapter;
import com.example.onlineshop.viewmodel.MainActivityViewModel;

import java.util.List;

public class ProductsCategoryFragment extends Fragment {

    private static final String TAG = "ProductListFragment";
    FragmentProductsCategoryBinding binding;
    MainActivityViewModel viewModel;
    GroupsAdapter adapter;

    public ProductsCategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_products_category, container, false);
        this.viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        adapter = new GroupsAdapter(viewModel, getContext());
        binding.groupsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.groupsRecyclerView.setAdapter(adapter);
        adapter.getItemCount();

        viewModel.getGroups().observe(this, new Observer<List<Group>>() {
            @Override
            public void onChanged(List<Group> groups) {
                adapter.setGroups(groups);
                Log.i(TAG, "onChanged: " + groups.get(1).getName());
            }
        });

        viewModel.getCategories(1).observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                Log.i(TAG, "onChanged: cat : " + categories.get(1).getName());
            }
        });

        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


    }
}