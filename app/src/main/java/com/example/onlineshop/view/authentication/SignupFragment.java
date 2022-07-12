package com.example.onlineshop.view.authentication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.example.onlineshop.databinding.FragmentSignupBinding;
import com.example.onlineshop.model.User;
import com.example.onlineshop.view.MainActivity;
import com.example.onlineshop.viewmodel.LoginSignupViewModel;
import com.example.onlineshop.viewmodel.LoginSignupViewModelFactory;

public class SignupFragment extends Fragment {

    FragmentSignupBinding binding;
    LoginSignupViewModel viewModel;
    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SignupFragmentEventListener signupFragmentEventListener = new SignupFragmentEventListener();
        this.context = getActivity();
        if (getActivity() != null) {
            viewModel = new ViewModelProvider(getActivity(), new LoginSignupViewModelFactory(getActivity())).get(LoginSignupViewModel.class);
        }
        binding.setEventListener(signupFragmentEventListener);
        binding.setViewModel(viewModel);


    }


    public void showMessage(String code, User user) {

        String message;
        switch (code) {
            case "210":
                message = getString(R.string.number_already_exist_message);
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();

                break;
            case "211":
                message = getString(R.string.successful_signup_message);
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();

                SharedPreferences sharedPreferences =
                        context.getApplicationContext().getSharedPreferences(context.getString(R.string.logged_in_shared_preferences), Context.MODE_PRIVATE);

                sharedPreferences.edit().putString(context.getString(R.string.logged_in_number_KEY), user.getNumber()).apply();
                sharedPreferences.edit().putString(context.getString(R.string.logged_in_name_KEY), user.getName()).apply();


                context.startActivity(new Intent(context, MainActivity.class));
                ((Login_Signup_Activity) context).finish();
                break;
            case "220":
                message = getString(R.string.invalid_data_message);
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();

                break;
            default:
                message = getString(R.string.unexpected_value)+ code;
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }


    }

    public class SignupFragmentEventListener {

        public void onSignupClick(View view, LoginSignupViewModel viewModel) {
            if (viewModel.isSignupFormValid()) {
                viewModel.signup().observe((LifecycleOwner) context,
                        integer -> showMessage(String.valueOf(integer), new User(viewModel.number.getValue()
                                , viewModel.password.getValue(), viewModel.name.getValue())));
            }


        }


        public void SignupToLoginFragment(View view) {
            viewModel.clearFields();
            Navigation.findNavController(view).navigate(SignupFragmentDirections.actionSignupFragmentToLoginFragment());
        }


    }

}
