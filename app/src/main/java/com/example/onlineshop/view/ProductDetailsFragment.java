package com.example.onlineshop.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.FragmentProductDetailsBinding;
import com.example.onlineshop.model.CartProduct;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.utils.adapters.ImageSliderAdapter;
import com.example.onlineshop.viewmodel.MainActivityViewModel;
import com.example.onlineshop.viewmodel.MainActivityViewModelFactory;

public class ProductDetailsFragment extends Fragment {
    FragmentProductDetailsBinding binding;
//    ProductDetailsFragmentArgs args;
    MainActivityViewModel viewModel;
    ProductDetailsEventListener eventListener;
    ImageSliderAdapter adapter = new ImageSliderAdapter();

    public String TAG = "ProductDetailsFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_details, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(getActivity() != null)  viewModel = new ViewModelProvider(getActivity(),new MainActivityViewModelFactory(getActivity())).get(MainActivityViewModel.class);
        eventListener = new ProductDetailsEventListener();

        binding.setEventListener(eventListener);
        binding.setViewModel(viewModel);
        binding.detailsViewPager.setAdapter(adapter);


    }

    public static class ProductDetailsEventListener {
        public void onAddToCart(View view,Product model, MainActivityViewModel viewModel, String imageUrl) {
            viewModel.addCartItem(
                    new CartProduct(model.getId(),
                            model.getName(),
                            imageUrl,
                            model.getPrice(),
                            1,
                            model.getDiscount()));
        }

    }

}
