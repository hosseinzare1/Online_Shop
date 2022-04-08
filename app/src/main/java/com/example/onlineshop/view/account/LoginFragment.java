package com.example.onlineshop.view.account;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.FragmentLoginBinding;
import com.example.onlineshop.model.User;
import com.example.onlineshop.view.MainActivity;
import com.example.onlineshop.view.account.Login_Signup_Activity;
import com.example.onlineshop.viewmodel.LoginSignupViewModel;
import com.example.onlineshop.viewmodel.LoginSignupViewModelFactory;


public class LoginFragment extends Fragment {

    FragmentLoginBinding binding;
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
//        User model = new User();
        LoginSignupViewModel viewModel = new ViewModelProvider(this,new LoginSignupViewModelFactory(getActivity().getApplication())).get(LoginSignupViewModel.class);


        binding.setEventListener(loginFragmentEventListener);
//        binding.setModel(model);
        binding.setViewModel(viewModel);

        viewModel.signInUserMutableLiveData.observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                Log.i(TAG, "onChanged: "+user.getNumber()+"  "+user.getPassword());

                viewModel.login(user.getNumber(), user.getPassword()).observe((LifecycleOwner) context, new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        showMessage(String.valueOf(integer), user);
                    }
                });
            }
        });

    }

    public void showMessage(String code, User user) {

        String message;

        switch (code) {
            case "212":
                message = "ورود موفق بود، خوش آمدید";
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();

                SharedPreferences sharedPreferences =
                        context.getSharedPreferences(context.getString(R.string.logged_in_number_file), Context.MODE_PRIVATE);

                sharedPreferences.edit().putString(context.getString(R.string.logged_in_number_KEY), user.getNumber()).apply();
                sharedPreferences.edit().putString(context.getString(R.string.logged_in_name_KEY), user.getName()).apply();




                context.startActivity(new Intent(context, MainActivity.class));
                ((Login_Signup_Activity) context).finish();
                break;
            case "213":
                message = "حسابی با این شماره وجود ندارد.";
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();

                break;

            case "214":
                message = "رمز عبور اشتباه است.";
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();

                break;
            case "220":
                message = "داده ها نامعتبر هستند";
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();

                break;
            default:
                message = "Unexpected value: " + code;
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }

    }

    public static class LoginFragmentEventListener {
//
//        Context context;
//        View view;

//        public LoginFragmentEventListener(Context context) {
//            this.context = context;
//        }

        public void LoginToSignupFragment(View view) {
            Navigation.findNavController(view).navigate(LoginFragmentDirections.actionLoginFragmentToSignupFragment());
//            this.view = view;
        }

//        public void LoginBtn(View view, LoginSignupViewModel viewModel, User model) {
//            this.view = view;
//            viewModel.login(model.getNumber(), model.getPassword()).observe((LifecycleOwner) context, new Observer<Integer>() {
//                @Override
//                public void onChanged(Integer integer) {
//                    showMessage(String.valueOf(integer), model.getNumber());
//                }
//            });
//
//
//        }


    }


}
