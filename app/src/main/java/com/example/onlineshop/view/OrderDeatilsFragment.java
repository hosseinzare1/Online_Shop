package com.example.onlineshop.view;

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
import com.example.onlineshop.databinding.FragmentOrderDeatilsBinding;
import com.example.onlineshop.model.Order;
import com.example.onlineshop.utils.adapters.OrderProductAdapter;
import com.example.onlineshop.viewmodel.MainActivityViewModel;
import com.example.onlineshop.viewmodel.MainActivityViewModelFactory;

public class OrderDeatilsFragment extends Fragment {

    FragmentOrderDeatilsBinding binding;
    MainActivityViewModel viewModel;
    OrderDeatilsFragmentArgs args;

    String TAG = "OrderDeatilsFragment";

    public OrderDeatilsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order_deatils, container, false);
        viewModel = new ViewModelProvider(getActivity(), new MainActivityViewModelFactory(getActivity())).get(MainActivityViewModel.class);
        args = OrderDeatilsFragmentArgs.fromBundle(getArguments());
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        OrderProductAdapter adapter = new OrderProductAdapter();
        binding.orderProductsRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        binding.orderProductsRecyclerView.setAdapter(adapter);

        viewModel.getOrder(args.getOrderId()).observe(getViewLifecycleOwner(), order -> {
            binding.setModel(order);
            adapter.setItems(order.getOrder_items());
            Log.i(TAG, "onViewCreated: "+order.getOrder_items());
        });


    }
}