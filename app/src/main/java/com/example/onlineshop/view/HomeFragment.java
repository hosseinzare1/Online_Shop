package com.example.onlineshop.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
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
import com.example.onlineshop.model.HomeItem;
import com.example.onlineshop.model.Image;
import com.example.onlineshop.utils.adapters.HorizontalProductsAdapter;
import com.example.onlineshop.utils.adapters.ImageSliderAdapter;
import com.example.onlineshop.viewmodel.MainActivityViewModel;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import java.util.List;


public class HomeFragment extends Fragment {

    public static final String TAG = "HomeFragment";

    WormDotsIndicator indicator ;

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
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);


        binding.newsViewPager.setAdapter(imageSliderAdapter);
        binding.newsViewPagerIndicator.setViewPager(binding.newsViewPager);

        binding.discountsRecyclerView.setAdapter(discountsListAdapter);
        binding.discountsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL,false));

        binding.bestsellingRecyclerView.setAdapter(bestsellingListAdapter);
        binding.bestsellingRecyclerView
                .setLayoutManager(new GridLayoutManager(getContext(),1,RecyclerView.HORIZONTAL,false));


        binding.historyRecyclerView.setAdapter(historyListAdapter);
        binding.historyRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),1,RecyclerView.HORIZONTAL,false));

        //TODO change images resource
        viewModel.getImages(1).observe(this, images -> imageSliderAdapter.setImages(images));

        viewModel.getAllItems().observe(this, homeItems -> {
            discountsListAdapter.setItems(homeItems);
            Log.i(TAG, "onChanged 1 :" + homeItems.get(2).getName());

        });
        viewModel.getAllItems().observe(this, homeItems -> {
            bestsellingListAdapter.setItems(homeItems);
            Log.i(TAG, "onChanged 2 :" + homeItems.get(2).getName());

        });
        viewModel.getAllItems().observe(this, homeItems -> {
            historyListAdapter.setItems(homeItems);
            Log.i(TAG, "onChanged 3 :" + homeItems.get(2).getName());

        });



    }


}