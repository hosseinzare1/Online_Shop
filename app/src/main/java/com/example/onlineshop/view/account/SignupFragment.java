package com.example.onlineshop.view.account;

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
import androidx.lifecycle.Observer;
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
//        User user = new User();
        this.context = getActivity();

        viewModel = new ViewModelProvider(getActivity(),new LoginSignupViewModelFactory(getActivity())).get(LoginSignupViewModel.class);

//
//        binding.setModel(user);
        binding.setEventListener(signupFragmentEventListener);
        binding.setViewModel(viewModel);

        viewModel.singUpUserMutableLiveData.observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                viewModel.signup(user.getNumber(), user.getPassword(),user.getName()).observe((LifecycleOwner) context, new Observer<Integer>() {
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
            case "210":
                message = "این شماره قبلا ثبت شده.";
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();

                break;
            case "211":
                message = "ثبت نام موفق بود، خوش آمدید";
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();

                SharedPreferences sharedPreferences =
                        context.getSharedPreferences(context.getString(R.string.logged_in_shared_preferences), Context.MODE_PRIVATE);

                sharedPreferences.edit().putString(context.getString(R.string.logged_in_number_KEY), user.getNumber()).apply();
                sharedPreferences.edit().putString(context.getString(R.string.logged_in_name_KEY), user.getName()).apply();


                context.startActivity(new Intent(context, MainActivity.class));
                ((Login_Signup_Activity) context).finish();
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

    public static class SignupFragmentEventListener {



//        public void SignupBtn(View view,LoginSignupViewModel viewModel , User model){
//            this.view = view;
//
//            viewModel.signup(model.getNumber(),model.getPassword(),model.getName()).observe((LifecycleOwner) context, new Observer<Integer>() {
//                @Override
//                public void onChanged(Integer integer) {
//                 showMessage(String.valueOf(integer),model.getNumber());
//                }
//            });


        public void SignupToLoginFragment(View view) {
            Navigation.findNavController(view).navigate(SignupFragmentDirections.actionSignupFragmentToLoginFragment());

        }


    }

}
