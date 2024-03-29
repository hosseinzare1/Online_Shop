package com.example.onlineshop.view.commodity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.ActivityProductBinding;
import com.example.onlineshop.viewmodel.CommodityActivityViewModel;
import com.example.onlineshop.viewmodel.CommodityActivityViewModelFactory;

public class ProductActivity extends AppCompatActivity {

    ActivityProductBinding binding;
    NavHostFragment navHostFragment;
    NavController navController;
    Bundle bundle;
    int id;
    String TAG = "ProductActivity";
    CommodityActivityViewModel viewModel;
//    ErrorObserver errorObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product);
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.commodityNavHost);
        navController = navHostFragment.getNavController();
        viewModel = new ViewModelProvider(this, new CommodityActivityViewModelFactory(this)).get(CommodityActivityViewModel.class);
        bundle = getIntent().getExtras();

        observeErrors();
        navController.setGraph(R.navigation.commodity_activity_navigation, bundle);

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
                    navController.navigate(R.id.errorFragment_commodity, bundle);
                } else if (integer == R.string.server_connection_error) {
                    navController.navigate(R.id.errorFragment_commodity, bundle);
                }
            }
        });
    }
}