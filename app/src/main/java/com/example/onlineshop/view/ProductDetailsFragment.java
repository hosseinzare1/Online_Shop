package com.example.onlineshop.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.FragmentProductDetailsBinding;
import com.example.onlineshop.model.CartItemModel;
import com.example.onlineshop.model.Image;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.utils.adapters.ImageSliderAdapter;
import com.example.onlineshop.viewmodel.CommodityActivityViewModelFactory;
import com.example.onlineshop.viewmodel.MainActivityViewModel;
import com.example.onlineshop.viewmodel.MainActivityViewModelFactory;

import java.util.List;

public class ProductDetailsFragment extends Fragment {
    FragmentProductDetailsBinding binding;
    ProductDetailsFragmentArgs args;
    MainActivityViewModel viewModel;
    ProductDetailsEventListener eventListener;
    ImageSliderAdapter adapter = new ImageSliderAdapter();

    public static final String TAG = "ProductDetailsFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_details, container, false);
        args = ProductDetailsFragmentArgs.fromBundle(getArguments());

//        binding.setImageUrl();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(getActivity(),new MainActivityViewModelFactory(getActivity().getApplication())).get(MainActivityViewModel.class);
        eventListener = new ProductDetailsEventListener();

        binding.setEventListener(eventListener);
        binding.setViewModel(viewModel);
        binding.detailsViewPager.setAdapter(adapter);
        //TODO convert int to string
        viewModel.getDetails(Integer.parseInt(args.getId())).observe(getViewLifecycleOwner(), new Observer<Product>() {
            @Override
            public void onChanged(Product product) {
                binding.setModel(product);
            }
        });

        //TODO convert int to string
        viewModel.getImages(Integer.parseInt(args.getId())).observe(getViewLifecycleOwner(), new Observer<List<Image>>() {
            @Override
            public void onChanged(List<Image> images) {
                adapter.setImages(images);
                binding.setImageUrl(images.get(0).getImageUrl());
            }
        });

    }

    public class ProductDetailsEventListener {
        public void onAddToCart(View view, Product model, MainActivityViewModel viewModel, String imageUrl) {
            Log.i(TAG, "onAddToCart 1 : "+model.getName());
            Log.i(TAG, "onAddToCart 2 : "+model.getPrice());
            viewModel.addCartItem(new CartItemModel(0,model.getName(),imageUrl,model.getPrice(),"1"));
        }

    }

}
