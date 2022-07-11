package com.example.onlineshop.view.category;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlineshop.R;

import com.example.onlineshop.databinding.FragmentProductListBinding;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.utils.adapters.ProductsVerticalAdapter;
import com.example.onlineshop.view.commodity.CommodityActivity;
import com.example.onlineshop.viewmodel.MainActivityViewModel;
import com.example.onlineshop.viewmodel.MainActivityViewModelFactory;

import java.util.List;


public class ProductListFragment extends Fragment {

    FragmentProductListBinding binding;
    MainActivityViewModel viewModel;
    ProductsVerticalAdapter productsVerticalAdapter;
    ProductListFragmentArgs args;
    private final String TAG = "ProductListFragment";

    public ProductListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_list, container, false);
        productsVerticalAdapter = new ProductsVerticalAdapter();
        productsVerticalAdapter.setOnClickListener(id -> {

            Intent intent = new Intent(getContext(), CommodityActivity.class);

            intent.putExtra("id", id);

            startActivity(intent);

        });
        binding.HomeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.HomeRecyclerView.setAdapter(productsVerticalAdapter);

        args = ProductListFragmentArgs.fromBundle(getArguments());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null)
            viewModel = new ViewModelProvider(getActivity(), new MainActivityViewModelFactory(getActivity())).get(MainActivityViewModel.class);

        LiveData<List<Product>> liveData;

        if (!args.getCategory().equals("")) {
            liveData = viewModel.getProductsByCategory(args.getCategory());
        } else {
            liveData = viewModel.getProductsByGroup(args.getGroup());
        }

        liveData.observe(getViewLifecycleOwner(), products -> productsVerticalAdapter.setProducts(products));

        //if id==0 , -> search


    }
}