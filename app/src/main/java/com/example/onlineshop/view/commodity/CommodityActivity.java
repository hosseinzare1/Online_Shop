package com.example.onlineshop.view.commodity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.ActivityCommodityBinding;
import com.example.onlineshop.viewmodel.CommodityActivityViewModel;

public class CommodityActivity extends AppCompatActivity {

    ActivityCommodityBinding binding;
    NavHostFragment navHostFragment;
    NavController navController;
    Bundle bundle;
    int id;
    CommodityActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_commodity);
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.commodityNavHost);
        navController = navHostFragment.getNavController();

        bundle = getIntent().getExtras();

        navController.setGraph(R.navigation.commodity_activity_navigation,bundle);

        viewModel = new ViewModelProvider(this).get(CommodityActivityViewModel.class);



    }
}