package com.example.onlineshop.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.FragmentOrderDetailsBinding;
import com.example.onlineshop.utils.adapters.OrderProductAdapter;
import com.example.onlineshop.view.commodity.CommodityActivity;
import com.example.onlineshop.viewmodel.MainActivityViewModel;
import com.example.onlineshop.viewmodel.MainActivityViewModelFactory;

public class OrderDetailsFragment extends Fragment {

    FragmentOrderDetailsBinding binding;
    MainActivityViewModel viewModel;
    OrderDetailsFragmentArgs args;

    String TAG = "OrderDetailsFragment";

    public OrderDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order_details, container, false);
        if (getActivity() != null)
            viewModel = new ViewModelProvider(getActivity(), new MainActivityViewModelFactory(getActivity())).get(MainActivityViewModel.class);
        args = OrderDetailsFragmentArgs.fromBundle(getArguments());
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
            Log.i(TAG, "onViewCreated: "+order.toString());
            binding.setModel(order);
            adapter.setItems(order.getOrder_items());
            Log.i(TAG, "onViewCreated: "+order.getOrder_items().get(0).toString());
        });

        adapter.setOnClickListener(id -> {
            Intent intent = new Intent(getContext(), CommodityActivity.class);

            intent.putExtra("id", id);

            startActivity(intent);
        });


    }
}