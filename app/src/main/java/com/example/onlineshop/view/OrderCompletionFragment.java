package com.example.onlineshop.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.FragmentOrderCompletionBinding;
import com.example.onlineshop.model.Order;
import com.example.onlineshop.utils.Utility;
import com.example.onlineshop.viewmodel.MainActivityViewModel;
import com.example.onlineshop.viewmodel.MainActivityViewModelFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class OrderCompletionFragment extends Fragment {

    public OrderCompletionFragment() {
        // Required empty public constructor
    }

    FragmentOrderCompletionBinding binding;
    MainActivityViewModel viewModel;
    OrderCompletionFragmentArgs args;
    Order order;
    public static final String TAG = "OrderCompletionFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order_completion, container, false);
        binding.setEventListener(new EventListener());
        viewModel = new ViewModelProvider(this, new MainActivityViewModelFactory(getActivity())).get(MainActivityViewModel.class);
        args = OrderCompletionFragmentArgs.fromBundle(getArguments());
        order = args.getOrder();

        viewModel.getTotalPrice().observe(getViewLifecycleOwner(), aLong -> binding.setTotalPrice(aLong));
        viewModel.getTotalPriceWithDiscount().observe(getViewLifecycleOwner(), aLong -> binding.setTotalPriceWithDiscount(aLong));
        viewModel.getTotalDiscount().observe(getViewLifecycleOwner(), aLong -> binding.setTotalDiscount(aLong));
        binding.setItemCount(order.getOrder_items().size());

        order.setUser(viewModel.getUserNumber());
        order.setSubmit_date(Utility.getCurrentShamsidate());
        order.setSubmit_time(new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date()));

        viewModel.getTotalPriceWithDiscount().observe(getViewLifecycleOwner(), order::setTotalPrice);

        order.setTransferee_name(viewModel.getUserName());
        Log.i(TAG, "onCreateView: "+viewModel.getUserName());
        order.setTransferee_number(viewModel.getUserNumber());
        order.setTransferee_address(viewModel.getUserAddress());

        binding.setOrder(order);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    public class EventListener{
        public void onPayClickListener(View view,Order order){
            Log.i(TAG, "onPayClickListener: "+order.toString());

            viewModel.submitOrder(order).observe(getViewLifecycleOwner(), new Observer<String>() {
                        @Override
                        public void onChanged(String s) {
                            Log.i(TAG, "onChanged: " + s);
                        }
                    }
            );
        }


    }
}