package com.example.onlineshop.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.ActivityMainBinding;
import com.example.onlineshop.viewmodel.MainActivityViewModel;
import com.example.onlineshop.viewmodel.MainActivityViewModelFactory;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    NavHostFragment navHostFragment;
    NavController navController;
    MainActivityViewModel viewModel;
    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.Main_fragmentContainerView);
        navController = navHostFragment.getNavController();
        viewModel = new ViewModelProvider(this, new MainActivityViewModelFactory(this)).get(MainActivityViewModel.class);


        observeErrors();

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            setNoConnectionError();
            switch (item.getItemId()) {
                case R.id.homeFragment:
                    navController.navigate(R.id.homeFragment);
                    break;
                case R.id.productsGroupCategoryFragment:
                    navController.navigate(R.id.productsGroupCategoryFragment);
                    break;
                case R.id.cartFragment:
                    navController.navigate(R.id.cartFragment);
                    break;
                case R.id.accountFragment:
                    navController.navigate(R.id.accountFragment);
                    break;
            }

            return true;
        });


    }
    public void setNoConnectionError() {
        viewModel.getError().setValue(R.string.no_error);
    }

    public void observeErrors() {
       viewModel.getError().observe(this, integer -> {
            if (integer != R.string.no_error) {
                Bundle bundle = new Bundle();
                bundle.putString(getString(R.string.error_message_key), this.getString(integer));

                if (integer == R.string.internet_connection_error) {
                    navController.navigate(R.id.errorFragment_main, bundle);
                } else if (integer == R.string.server_connection_error) {
                    navController.navigate(R.id.errorFragment_main, bundle);
                }
            }
        });
    }


}