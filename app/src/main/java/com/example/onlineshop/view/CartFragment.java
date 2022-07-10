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
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.FragmentCartBinding;
import com.example.onlineshop.model.CartItemModel;
import com.example.onlineshop.model.Order;
import com.example.onlineshop.utils.adapters.CartAdapter;
import com.example.onlineshop.view.commodity.CommodityActivity;
import com.example.onlineshop.viewmodel.MainActivityViewModel;
import com.example.onlineshop.viewmodel.MainActivityViewModelFactory;


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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null)
            viewModel = new ViewModelProvider(getActivity(), new MainActivityViewModelFactory(getActivity())).get(MainActivityViewModel.class);
        // Inflate the layout for this fragment
        recyclerView = binding.cartRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(cartAdapter);
        binding.setEventListener(new CartFragmentEventListener());

        Order order = new Order();
        binding.setViewModel(viewModel);

        viewModel.getCartItems().observe(getViewLifecycleOwner(), cartItemModels -> {
            if (cartItemModels.size() > 0) {
                cartAdapter.setCartItemModels(cartItemModels);
                order.setOrder_items(cartItemModels);
                binding.setOrder(order);
                binding.setItemCount(cartItemModels.size());
            } else {
                Navigation.findNavController(view).navigate(CartFragmentDirections.actionCartFragmentToEmptyCartFragment());
            }

        });

        viewModel.getTotalPrice().observe(getViewLifecycleOwner(), aLong -> binding.setTotalPrice(aLong));

        viewModel.getTotalPriceWithDiscount().observe(getViewLifecycleOwner(), aLong -> binding.setTotalPriceWithDiscount(aLong));
        viewModel.getTotalDiscount().observe(getViewLifecycleOwner(), aLong -> binding.setTotalDiscount(aLong));

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

    @Override
    public void onProductClickListener(int id) {
        Intent intent = new Intent(getContext(), CommodityActivity.class);

        intent.putExtra("id", id);

        startActivity(intent);
    }


    public class CartFragmentEventListener {
        public void onPayClickListener(View view, Order order, MainActivityViewModel viewModel) {

            Navigation.findNavController(view)
                    .navigate(CartFragmentDirections.actionCartFragmentToOrderCompletionFragment(order));


        }

        public void onRemoveAll(View view) {
            viewModel.RemoveAllCartItems();
        }

    }
}