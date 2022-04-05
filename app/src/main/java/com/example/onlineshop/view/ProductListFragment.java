package com.example.onlineshop.view;

import android.content.Intent;
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
import com.example.onlineshop.model.Product;
import com.example.onlineshop.utils.adapters.ProductsListAdapter;
import com.example.onlineshop.view.commodity.CommodityActivity;
import com.example.onlineshop.viewmodel.CommodityActivityViewModelFactory;
import com.example.onlineshop.viewmodel.MainActivityViewModel;
import com.example.onlineshop.viewmodel.MainActivityViewModelFactory;

import java.util.List;


public class ProductListFragment extends Fragment {

    FragmentProductListBinding binding;
    MainActivityViewModel viewModel;
    ProductsListAdapter productsListAdapter;
    ProductListFragmentArgs args;
    private final String TAG = "ProductListFragment";

    public ProductListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_list, container, false);
        productsListAdapter = new ProductsListAdapter();
        productsListAdapter.setOnClickListener(new ProductsListAdapter.OnClickListener() {
            @Override
            public void OnItemClickListener(int id) {

                Intent intent = new Intent(getContext(), CommodityActivity.class);

                intent.putExtra("id", id);

                startActivity(intent);

            }
        });
        binding.HomeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.HomeRecyclerView.setAdapter(productsListAdapter);

        args = ProductListFragmentArgs.fromBundle(getArguments());

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity(),new MainActivityViewModelFactory(getActivity().getApplication())).get(MainActivityViewModel.class);


        //if id==0 , -> search

        Log.i(TAG, "onViewCreated: id:"+args.getCategoryID());
        Log.i(TAG, "onViewCreated: text:"+getArguments().getString("searchText"));

        if (args.getCategoryID() == 0) {
            String text =getArguments().getString("searchText");

            viewModel.searchProducts(text).observe(getActivity(), new Observer<List<Product>>() {
                @Override
                public void onChanged(List<Product> products) {
                    productsListAdapter.setProducts(
                            products

                    );
                    for (Product p:products
                         ) {
                        Log.i(TAG, "for: "+p.getName());
                    }
                }
            });

            viewModel.getAllItems().observe(getActivity(), homeItems -> productsListAdapter.setProducts(homeItems));
        } else {
            viewModel.getProductsByCategory(args.getCategoryID()).observe(getActivity(), homeItems -> productsListAdapter.setProducts(homeItems));
        }


    }
//
//    @Override
//    public void OnItemClickListener(int id) {
//
//    }
}