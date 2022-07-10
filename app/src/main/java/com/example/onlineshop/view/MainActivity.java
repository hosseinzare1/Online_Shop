package com.example.onlineshop.view;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

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
                case R.id.item_home:
                    navController.navigate(R.id.homeFragment);
                    break;
                case R.id.item_categories:
                    navController.navigate(R.id.productsGroupCategoryFragment);
                    break;
                case R.id.item_cart:
                    navController.navigate(R.id.cartFragment);
                    break;
                case R.id.item_account:
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
                bundle.putString("message", this.getString(integer));

                if (integer == R.string.internet_connection_error) {
                    navController.navigate(R.id.errorFragment_main, bundle);
                } else if (integer == R.string.server_connection_error) {
                    navController.navigate(R.id.errorFragment_main, bundle);
                }
            }
        });
    }


}