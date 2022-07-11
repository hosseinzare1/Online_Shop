package com.example.onlineshop.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.FragmentOrderCompletionBinding;
import com.example.onlineshop.model.Order;
import com.example.onlineshop.utils.Utility;
import com.example.onlineshop.viewmodel.MainActivityViewModel;
import com.example.onlineshop.viewmodel.MainActivityViewModelFactory;
import com.google.android.material.snackbar.Snackbar;

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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
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

        viewModel.getTotalPriceWithDiscount().observe(getViewLifecycleOwner(), order::setTotalPrice);

        order.setTransferee_name(viewModel.getUserName());
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
            order.setSubmit_date(Utility.getCurrentSolarHijri());
            order.setSubmit_time(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date()));


            // TODO The payment method is implemented in this section

            viewModel.submitOrder(order).observe(getViewLifecycleOwner(), s -> {

                Snackbar.make(view,"سفارش شما با موفقیت ثبت شد."+"برای پیگیری سفارش به صفحه پروفایل خود مراجعه نمایید."
                        ,Snackbar.LENGTH_LONG).show();

                viewModel.RemoveAllCartItems();

                Navigation.findNavController(view).popBackStack();
            }
            );


        }


    }
}