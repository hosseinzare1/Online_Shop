package com.example.onlineshop.view.account;

import android.content.Context;
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

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.FragmentEditAccountBinding;
import com.example.onlineshop.model.Account;
import com.example.onlineshop.viewmodel.MainActivityViewModel;
import com.example.onlineshop.viewmodel.MainActivityViewModelFactory;

public class EditAccountFragment extends Fragment {

    FragmentEditAccountBinding binding;

    EditAccountEventListener eventListener;
    MainActivityViewModel viewModel;
    public static final String TAG = "EditFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_account, container, false);
        eventListener = new EditAccountEventListener();
        if (getActivity() != null)
            viewModel = new ViewModelProvider(getActivity(), new MainActivityViewModelFactory(getActivity())).get(MainActivityViewModel.class);

        viewModel.getAccountDetails(viewModel.getUserNumber()).observe(getViewLifecycleOwner(), account -> binding.setModel(account));

        binding.setViewModel(viewModel);
        binding.setEventListener(eventListener);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    public class EditAccountEventListener {

        public void onSaveClick(View view, Account account, MainActivityViewModel viewModel) {

            if(viewModel.isProfileFormValid(account)){

                SharedPreferences sharedPreferences = getActivity().getApplicationContext()
                        .getSharedPreferences(getString(R.string.logged_in_shared_preferences), Context.MODE_PRIVATE);

            viewModel.updateAccount(account).observe(getViewLifecycleOwner(),newAccount ->{

                        sharedPreferences.edit().putString(getString(R.string.logged_in_name_KEY),newAccount.getName()).apply();
                        sharedPreferences.edit().putString(getString(R.string.logged_in_number_KEY),newAccount.getNumber()).apply();
                        sharedPreferences.edit().putString(getString(R.string.logged_in_address_KEY),newAccount.getAddress()).apply();
                        sharedPreferences.edit().putString(getString(R.string.logged_in_email_KEY),newAccount.getEmail()).apply();

                    }
                    );





            Navigation.findNavController(view).navigate(EditAccountFragmentDirections.actionEditAccountFragmentToAccountFragment());
            }


        }

        public void onCancelClick(View view) {
            Navigation.findNavController(view).navigate(EditAccountFragmentDirections.actionEditAccountFragmentToAccountFragment());
        }


    }
}
