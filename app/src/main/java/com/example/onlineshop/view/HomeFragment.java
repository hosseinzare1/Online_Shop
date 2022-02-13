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
import com.example.onlineshop.databinding.FragmentHomeBinding;
import com.example.onlineshop.model.HomeItem;
import com.example.onlineshop.utils.adapters.HomeAdapter;
import com.example.onlineshop.viewmodel.MainActivityViewModel;

import java.util.List;


public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    MainActivityViewModel viewModel;
    HomeAdapter homeAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        homeAdapter = new HomeAdapter();

        binding.HomeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.HomeRecyclerView.setAdapter(homeAdapter);

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(MainActivityViewModel.class);
        viewModel.getAllItems().observe(getActivity(), new Observer<List<HomeItem>>() {
            @Override
            public void onChanged(List<HomeItem> homeItems) {
                homeAdapter.setProducts(homeItems);
            }
        });


    }
}