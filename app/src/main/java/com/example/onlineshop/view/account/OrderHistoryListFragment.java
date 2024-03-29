package com.example.onlineshop.view.account;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.FragmentOrderHistoryListBinding;
import com.example.onlineshop.utils.adapters.OrderHistoryAdapter;
import com.example.onlineshop.viewmodel.MainActivityViewModel;
import com.example.onlineshop.viewmodel.MainActivityViewModelFactory;

public class OrderHistoryListFragment extends Fragment {

    FragmentOrderHistoryListBinding binding;
    MainActivityViewModel viewModel;

    public OrderHistoryListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order_history_list, container, false);
        if (getActivity() != null)
            viewModel = new ViewModelProvider(getActivity(), new MainActivityViewModelFactory(getActivity())).get(MainActivityViewModel.class);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        OrderHistoryAdapter adapter = new OrderHistoryAdapter();
        viewModel.getOrders(viewModel.getUserNumber()).observe(getViewLifecycleOwner(), adapter::setOrders);

        binding.OrderHistoryListRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        binding.OrderHistoryListRecyclerView.setAdapter(adapter);


    }
}