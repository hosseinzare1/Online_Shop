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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.FragmentCartBinding;
import com.example.onlineshop.databinding.ItemCardCommentBinding;
import com.example.onlineshop.model.CartItemModel;
import com.example.onlineshop.utils.adapters.CartAdapter;
import com.example.onlineshop.viewmodel.CommodityActivityViewModelFactory;
import com.example.onlineshop.viewmodel.MainActivityViewModel;
import com.example.onlineshop.viewmodel.MainActivityViewModelFactory;

import java.util.List;


public class CartFragment extends Fragment implements CartAdapter.OnCartProductData {
    private static final String TAG = "CartFragment";

    FragmentCartBinding binding;
    MainActivityViewModel viewModel;
    CartAdapter cartAdapter = new CartAdapter();
    RecyclerView recyclerView;


    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(getActivity(), new MainActivityViewModelFactory(getActivity())).get(MainActivityViewModel.class);
        // Inflate the layout for this fragment
        recyclerView = binding.cartRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(cartAdapter);
        viewModel.getCartItems().observe(getViewLifecycleOwner(), new Observer<List<CartItemModel>>() {
            @Override
            public void onChanged(List<CartItemModel> cartItemModels) {
                cartAdapter.setCartItemModels(cartItemModels);

            }
        });

        viewModel.getTotalPrice().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                binding.cartPriceTextView.setText(String.valueOf(aDouble));
            }
        });

        cartAdapter.setOnCartProductData(this);
    }

    @Override
    public void onAddClickListener(CartItemModel cartItemModel) {
        viewModel.increaseItemCount(cartItemModel);
    }

    @Override
    public void onReduceClickListener(CartItemModel cartItemModel) {
        viewModel.decreaseItemCount(cartItemModel);
    }

    @Override
    public void onDeleteClickListener(CartItemModel cartItemModel) {
        viewModel.deleteFromCart(cartItemModel);
    }


    public class CartFragmentEventListener {


    }
}