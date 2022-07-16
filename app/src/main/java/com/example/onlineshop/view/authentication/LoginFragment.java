package com.example.onlineshop.view.authentication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.onlineshop.R;

import com.example.onlineshop.databinding.FragmentLoginBinding;

import com.example.onlineshop.model.User;
import com.example.onlineshop.view.MainActivity;
import com.example.onlineshop.viewmodel.LoginSignupViewModel;
import com.example.onlineshop.viewmodel.LoginSignupViewModelFactory;
import com.google.gson.Gson;


public class LoginFragment extends Fragment {

    FragmentLoginBinding binding;
    LoginSignupViewModel viewModel;
    Context context;
    public static final String TAG = "LoginFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.context = getActivity();
        LoginFragmentEventListener loginFragmentEventListener = new LoginFragmentEventListener();

        if (getActivity() != null)
            viewModel = new ViewModelProvider(getActivity(), new LoginSignupViewModelFactory(getActivity())).get(LoginSignupViewModel.class);

        binding.setEventListener(loginFragmentEventListener);
        binding.setViewModel(viewModel);


        initializeTextChangeListeners();
    }

    void initializeTextChangeListeners() {
        binding.loginNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!viewModel.formErrors.isEmpty()) {
                    viewModel.isSigningFormValid();
                }
            }
        });
        binding.loginPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!viewModel.formErrors.isEmpty()) {
                    viewModel.isSigningFormValid();
                }
            }
        });
    }

    public void showMessage(String code, User user) {

        String message;

        switch (code) {
            case "212":
                message = getString(R.string.successful_login_message);
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();

                SharedPreferences sharedPreferences =
                        context.getApplicationContext().getSharedPreferences(context.getString(R.string.logged_in_shared_preferences), Context.MODE_PRIVATE);

                sharedPreferences.edit().putString(context.getString(R.string.logged_in_number_KEY), user.getNumber()).apply();
                sharedPreferences.edit().putString(context.getString(R.string.logged_in_name_KEY), user.getName()).apply();

                context.startActivity(new Intent(context, MainActivity.class));
                ((Login_Signup_Activity) context).finish();
                break;
            case "213":
                message = getString(R.string.no_account_message);
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();

                break;

            case "214":
                message = getString(R.string.invalid_password_message);
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();

                break;
            case "220":
                message = getString(R.string.invalid_data_message);
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();

                break;
            default:
                message = getString(R.string.unexpected_value) + code;
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }

    }

    public class LoginFragmentEventListener {

        public void onSignInClick(View view, LoginSignupViewModel viewModel) {

            if (viewModel.isSigningFormValid())
                viewModel.login().observe((LifecycleOwner) context,
                        response -> showMessage(String.valueOf(response.code()),
                                new Gson().fromJson(response.body(),
                                        User.class)));
        }

        public void LoginToSignupFragment(View view) {
            viewModel.clearFields();
            Navigation.findNavController(view).navigate(LoginFragmentDirections.actionLoginFragmentToSignupFragment());

        }


    }


}
