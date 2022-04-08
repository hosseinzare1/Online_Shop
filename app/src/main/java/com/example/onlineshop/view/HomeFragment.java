package com.example.onlineshop.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.FragmentHomeBinding;
import com.example.onlineshop.utils.adapters.HorizontalProductsAdapter;
import com.example.onlineshop.utils.adapters.ImageSliderAdapter;
import com.example.onlineshop.view.commodity.CommodityActivity;
import com.example.onlineshop.viewmodel.MainActivityViewModel;
import com.example.onlineshop.viewmodel.MainActivityViewModelFactory;


public class HomeFragment extends Fragment implements HorizontalProductsAdapter.OnClickListener {

    public static final String TAG = "HomeFragment";


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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this, new MainActivityViewModelFactory(getActivity())).get(MainActivityViewModel.class);


        binding.newsViewPager.setAdapter(imageSliderAdapter);
        binding.newsViewPagerIndicator.setViewPager(binding.newsViewPager);

        binding.discountsRecyclerView.setAdapter(discountsListAdapter);
        binding.discountsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1, RecyclerView.HORIZONTAL, true));
        discountsListAdapter.setOnClickListener(this);

        binding.bestsellingRecyclerView.setAdapter(bestsellingListAdapter);
        binding.bestsellingRecyclerView
                .setLayoutManager(new GridLayoutManager(getContext(), 1, RecyclerView.HORIZONTAL, true));
        bestsellingListAdapter.setOnClickListener(this);

        binding.historyRecyclerView.setAdapter(historyListAdapter);
        binding.historyRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1, RecyclerView.HORIZONTAL, true));
        historyListAdapter.setOnClickListener(this);

        //TODO change images resource
        viewModel.getImages(1).observe(getViewLifecycleOwner(), images -> imageSliderAdapter.setImages(images));
        viewModel.getSpecialDiscounts().observe(getViewLifecycleOwner(), homeItems -> discountsListAdapter.setItems(homeItems));
        viewModel.getBestSelling().observe(getViewLifecycleOwner(), homeItems -> bestsellingListAdapter.setItems(homeItems));

        historyListAdapter.setItems(viewModel.getHistory());


    }


    @Override
    public void onProductClickListener(int id) {
        startActivity(
                new Intent(getContext(), CommodityActivity.class)
                        .putExtra("id", id)
        );
    }
}