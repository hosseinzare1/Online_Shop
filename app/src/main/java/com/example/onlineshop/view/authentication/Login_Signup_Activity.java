package com.example.onlineshop.view.authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.ActivityLoginSignupBinding;
import com.example.onlineshop.view.MainActivity;
import com.example.onlineshop.viewmodel.LoginSignupViewModel;
import com.example.onlineshop.viewmodel.LoginSignupViewModelFactory;

public class Login_Signup_Activity extends AppCompatActivity {

    ActivityLoginSignupBinding binding;
    private static final String TAG = "LoginSignup";

    LoginSignupViewModel viewModel;

    NavHostFragment navHostFragment;
    NavController navController;

//    ErrorObserver errorObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.logged_in_shared_preferences), MODE_PRIVATE);

        if (sharedPreferences.getString(getString(R.string.logged_in_number_KEY), null) == null) {
            binding = DataBindingUtil.setContentView(this, R.layout.activity_login_signup);
            viewModel = new ViewModelProvider(this, new LoginSignupViewModelFactory(this)).get(LoginSignupViewModel.class);
            navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.login_signup_NavHost);
            navController = navHostFragment.getNavController();
//            errorObserver = new ErrorObserver(this, navController, viewModel.getErrorLiveData(), R.id.errorFragment_main);


            observeErrors();

        } else {
            startActivity(new Intent(this, MainActivity.class));
        }


    }

    public void setNoConnectionError() {
        viewModel.getErrorLiveData().setValue(R.string.no_error);
    }

    public void observeErrors() {
        viewModel.getErrorLiveData().observe(this, integer -> {
            if (integer != R.string.no_error) {
                Bundle bundle = new Bundle();
                bundle.putString(getString(R.string.error_message_key), this.getString(integer));
                if (integer == R.string.internet_connection_error) {
                    navController.navigate(R.id.errorFragment_login_signup, bundle);
                } else if (integer == R.string.server_connection_error) {
                    navController.navigate(R.id.errorFragment_login_signup, bundle);
                }
            }
        });
    }

}