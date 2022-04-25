package com.example.onlineshop.view.account;

import android.content.Context;
import android.content.SharedPreferences;
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
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.FragmentAccountBinding;
import com.example.onlineshop.model.Account;
import com.example.onlineshop.utils.adapters.ButtonAdapter;
import com.example.onlineshop.viewmodel.MainActivityViewModel;
import com.example.onlineshop.viewmodel.MainActivityViewModelFactory;

import java.util.ArrayList;
import java.util.List;


public class AccountFragment extends Fragment {

    private static final String TAG = "account";
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

        viewModel = new ViewModelProvider(getActivity(), new MainActivityViewModelFactory(getActivity())).get(MainActivityViewModel.class);


        SharedPreferences sharedPreferences
                = getActivity().getSharedPreferences(getString(R.string.logged_in_shared_preferences), Context.MODE_PRIVATE);

        String number = sharedPreferences.getString(getString(R.string.logged_in_number_KEY), null);

        AccountFragmentEventListener eventListener = new AccountFragmentEventListener();

        binding.setEventListener(eventListener);

        viewModel.getAccountDetails(number).observe(getActivity(), new Observer<Account>() {
            @Override
            public void onChanged(Account account) {
                binding.setModel(account);
            }
        });


        List<String> texts = new ArrayList<>();
        List<Integer> icons = new ArrayList<>();

        texts.add("تاریخچه سفارشات");
        texts.add("مشخصات حساب کاربری");
        texts.add("نظرات ارسال شده");
//        texts.add("لیست علاقه مندی ها");
        icons.add(R.drawable.ic_baseline_shopping_cart_24);
        icons.add(R.drawable.ic_baseline_account_circle_24);
        icons.add(R.drawable.ic_baseline_comment_24);
        ButtonAdapter buttonAdapter = new ButtonAdapter();
        buttonAdapter.setData(icons, texts);
        binding.buttonsRecyclerView.setAdapter(buttonAdapter);
        binding.buttonsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


    public class AccountFragmentEventListener {
        public void editBtnListener(View view, Account account) {

            Navigation.findNavController(view).navigate(
                    AccountFragmentDirections.actionAccountFragmentToEditAccountFragment(account)
            );

        }


    }
}