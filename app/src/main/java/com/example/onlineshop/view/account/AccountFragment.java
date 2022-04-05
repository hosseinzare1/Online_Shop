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

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.FragmentAccountBinding;
import com.example.onlineshop.model.Account;
import com.example.onlineshop.viewmodel.CommodityActivityViewModelFactory;
import com.example.onlineshop.viewmodel.MainActivityViewModel;
import com.example.onlineshop.viewmodel.MainActivityViewModelFactory;


public class AccountFragment extends Fragment {

    private static final String TAG = "account";
    FragmentAccountBinding binding;
    MainActivityViewModel viewModel;

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false);


        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(getActivity(),new MainActivityViewModelFactory(getActivity().getApplication())).get(MainActivityViewModel.class);


        SharedPreferences sharedPreferences
                = getActivity().getSharedPreferences(getString(R.string.logged_in_number_file), Context.MODE_PRIVATE);

        String number = sharedPreferences.getString(getString(R.string.logged_in_number_KEY), null);

        AccountFragmentEventListener eventListener = new AccountFragmentEventListener();

        binding.setEventListener(eventListener);
        Log.i(TAG, "onViewCreated: " + number);

        viewModel.getAccountDetails(number).observe(getActivity(), new Observer<Account>() {
            @Override
            public void onChanged(Account account) {
                binding.setModel(account);
                Log.i(TAG, "onViewCreated: " + account.getName());
            }
        });


    }


   public class AccountFragmentEventListener {
        public void editBtnListener(View view, Account account) {

            Navigation.findNavController(view).navigate(
                    AccountFragmentDirections.actionAccountFragmentToEditAccountFragment(account)
            );

        }


    }
}