package com.example.onlineshop.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlineshop.R;

import com.example.onlineshop.databinding.FragmentProductListBinding;
import com.example.onlineshop.model.HomeItem;
import com.example.onlineshop.utils.adapters.ProductsListAdapter;
import com.example.onlineshop.viewmodel.MainActivityViewModel;

import java.util.List;


public class ProductListFragment extends Fragment {

    FragmentProductListBinding binding;
    MainActivityViewModel viewModel;
    ProductsListAdapter productsListAdapter;

    public ProductListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_list, container, false);
        productsListAdapter = new ProductsListAdapter();

        binding.HomeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.HomeRecyclerView.setAdapter(productsListAdapter);

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(MainActivityViewModel.class);

        viewModel.getAllItems().observe(getActivity(), homeItems -> productsListAdapter.setProducts(homeItems));


    }
}