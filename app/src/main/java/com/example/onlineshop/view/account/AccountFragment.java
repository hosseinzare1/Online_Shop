package com.example.onlineshop.view.account;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.onlineshop.databinding.FragmentAccountBinding;
import com.example.onlineshop.utils.adapters.AccountButtonAdapter;
import com.example.onlineshop.utils.adapters.ProductHorizontalAdapter;
import com.example.onlineshop.view.commodity.ProductActivity;
import com.example.onlineshop.viewmodel.MainActivityViewModel;
import com.example.onlineshop.viewmodel.MainActivityViewModelFactory;

import org.jetbrains.annotations.NonNls;

import java.util.ArrayList;
import java.util.List;


public class AccountFragment extends Fragment {

    private static final String TAG = "AccountFragment";
    FragmentAccountBinding binding;
    MainActivityViewModel viewModel;

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false);


        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getActivity() != null) {
            viewModel = new ViewModelProvider(getActivity(), new MainActivityViewModelFactory(getActivity())).get(MainActivityViewModel.class);
            SharedPreferences sharedPreferences
                    = getActivity().getSharedPreferences(getString(R.string.logged_in_shared_preferences), Context.MODE_PRIVATE);
            String number = sharedPreferences.getString(getString(R.string.logged_in_number_KEY), null);
            viewModel.getAccountDetails(number).observe(getActivity(), account -> binding.setModel(account));
        } else {
            Navigation.findNavController(view).popBackStack();
        }


        @NonNls List<String> texts = new ArrayList<>();
        List<Integer> icons = new ArrayList<>();

        texts.add(getString(R.string.order_history));
        texts.add(getString(R.string.account_details));
        texts.add(getString(R.string.submitted_comments));
//        texts.add("لیست علاقه مندی ها");
        icons.add(R.drawable.ic_baseline_shopping_cart_24);
        icons.add(R.drawable.ic_baseline_account_circle_24);
        icons.add(R.drawable.ic_baseline_comment_24);
        AccountButtonAdapter accountButtonAdapter = new AccountButtonAdapter();
        accountButtonAdapter.setData(icons, texts);
        binding.buttonsRecyclerView.setAdapter(accountButtonAdapter);
        binding.buttonsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL,true));

        ProductHorizontalAdapter historyAdapter = new ProductHorizontalAdapter();
        binding.historyRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        binding.historyRecyclerView.setAdapter(historyAdapter);


        if (viewModel.getHistory().size() > 0) {
            binding.historyLayout.setVisibility(View.VISIBLE);
            historyAdapter.setItems(viewModel.getHistory());
        } else {
            binding.historyLayout.setVisibility(View.GONE);
        }

        historyAdapter.setOnClickListener(new ProductHorizontalAdapter.OnClickListener() {
            @Override
            public void onProductClickListener(int id) {

                Intent intent = new Intent(getContext(), ProductActivity.class);

                intent.putExtra("id", id);

                startActivity(intent);

            }
        });

        accountButtonAdapter.setOnClickListener(itemText -> {
            switch (itemText) {
                case "تاریخچه سفارشات":

                    Navigation.findNavController(view).navigate(AccountFragmentDirections.actionAccountFragmentToOrderHistoryListFragment());
                    break;
                case "مشخصات حساب کاربری":
                    Navigation.findNavController(view).navigate(AccountFragmentDirections.actionAccountFragmentToEditAccountFragment());

                    break;
                case "نظرات ارسال شده":
                    Navigation.findNavController(view).navigate(AccountFragmentDirections.actionAccountFragmentToSubmittedCommentsFragment());

                    break;


            }
        });

    }


}