package com.example.onlineshop.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.FragmentHomeBinding;
import com.example.onlineshop.utils.adapters.HorizontalProductsAdapter;
import com.example.onlineshop.utils.adapters.ImageSliderAdapter;
import com.example.onlineshop.view.commodity.CommodityActivity;
import com.example.onlineshop.viewmodel.CommodityActivityViewModelFactory;
import com.example.onlineshop.viewmodel.MainActivityViewModel;
import com.example.onlineshop.viewmodel.MainActivityViewModelFactory;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;


public class HomeFragment extends Fragment implements HorizontalProductsAdapter.OnClickListener {

    public static final String TAG = "HomeFragment";

    WormDotsIndicator indicator;

    ImageSliderAdapter imageSliderAdapter = new ImageSliderAdapter();
    HorizontalProductsAdapter discountsListAdapter = new HorizontalProductsAdapter();
    HorizontalProductsAdapter bestsellingListAdapter = new HorizontalProductsAdapter();
    HorizontalProductsAdapter historyListAdapter = new HorizontalProductsAdapter();
    MainActivityViewModel viewModel;
    FragmentHomeBinding binding;

    public HomeFragment() {
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this,new MainActivityViewModelFactory(getActivity().getApplication())).get(MainActivityViewModel.class);


        binding.newsViewPager.setAdapter(imageSliderAdapter);
        binding.newsViewPagerIndicator.setViewPager(binding.newsViewPager);

        binding.discountsRecyclerView.setAdapter(discountsListAdapter);
        binding.discountsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        discountsListAdapter.setOnClickListener(id -> {
            Intent intent = new Intent(getContext(), CommodityActivity.class);

            intent.putExtra("id", id);

            startActivity(intent);

        });

        binding.bestsellingRecyclerView.setAdapter(bestsellingListAdapter);
        binding.bestsellingRecyclerView
                .setLayoutManager(new GridLayoutManager(getContext(), 1, RecyclerView.HORIZONTAL, false));
        bestsellingListAdapter.setOnClickListener(id -> {
//    Intent intent = new Intent(getContext(), CommodityActivity.class);
//
//    intent.putExtra("id", id);
//
//    startActivity(intent);
//
//
            onProductClickListener(id);

        });

        binding.historyRecyclerView.setAdapter(historyListAdapter);
        binding.historyRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1, RecyclerView.HORIZONTAL, false));
        historyListAdapter.setOnClickListener(id -> {
//            Intent intent = new Intent(getContext(), CommodityActivity.class);
//
//            intent.putExtra("id", id);
//
//            startActivity(intent);
            onProductClickListener(id);

        });
        //TODO change images resource
        viewModel.getImages(1).observe(getViewLifecycleOwner(), images -> imageSliderAdapter.setImages(images));

        viewModel.getAllItems().observe(getViewLifecycleOwner(), homeItems -> {
            discountsListAdapter.setItems(homeItems);
            Log.i(TAG, "onChanged 1 :" + homeItems.get(2).getName());

        });
        viewModel.getAllItems().observe(getViewLifecycleOwner(), homeItems -> {
            bestsellingListAdapter.setItems(homeItems);
            Log.i(TAG, "onChanged 2 :" + homeItems.get(2).getName());

        });
        viewModel.getAllItems().observe(getViewLifecycleOwner(), homeItems -> {
            historyListAdapter.setItems(homeItems);
            Log.i(TAG, "onChanged 3 :" + homeItems.get(2).getName());

        });


    }


    @Override
    public void onProductClickListener(int id) {
        Intent intent = new Intent(getContext(), CommodityActivity.class);

        intent.putExtra("id", id);

        startActivity(intent);

    }
}