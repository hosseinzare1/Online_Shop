package com.example.onlineshop.view;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.ActivityMainBinding;
import com.example.onlineshop.viewmodel.MainActivityViewModel;
import com.example.onlineshop.viewmodel.MainActivityViewModelFactory;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    NavHostFragment navHostFragment;
    NavController navController;
    MainActivityViewModel viewModel;
    private final String TAG = "MainActivity2";
//    ErrorObserver errorObserver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.Main_fragmentContainerView);
        navController = navHostFragment.getNavController();
        viewModel = new ViewModelProvider(this, new MainActivityViewModelFactory(this)).get(MainActivityViewModel.class);


//        setSupportActionBar(binding.mainToolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        Objects.requireNonNull(getSupportActionBar()).setTitle("");

        observeErrors();

        binding.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
            }
        });


    }
    public void setNoConnectionError() {
        viewModel.getError().setValue(R.string.no_error);
    }

    public void observeErrors() {
       viewModel.getError().observe((LifecycleOwner)this, integer -> {
//            Log.i(TAG, "observeErrors:changed " + activity.getString(integer));
            Log.i(TAG, "****live observer***");
            Log.i(TAG, "observeErrors livedata :"+viewModel.getError().toString());
            Log.i(TAG, "****live***");

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


    //search
/*
 //    @Override
 //    public boolean onCreateOptionsMenu(Menu menu) {
 //        MenuInflater menuInflater = getMenuInflater();
 //        menuInflater.inflate(R.menu.toolbar_searchview, menu);
 //
 //
 //        SearchView searchView = (SearchView) menu.findItem(R.id.toolbar_searchView).getActionView();
 //        menu.findItem(R.id.toolbar_searchView).expandActionView();
 //        searchView.setQueryHint("جستوجو");
 //        searchView.setIconifiedByDefault(false);
 //
 //
 //        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
 //            @Override
 //            public boolean onQueryTextSubmit(String s) {
 ////                Toast.makeText(getApplicationContext(), s+"onQueryTextSubmit()", Toast.LENGTH_SHORT).show();
 //                Bundle bundle = new Bundle();
 //                bundle.putString("searchText",s);
 //                bundle.putInt("categoryID",0);
 ////                Log.i(TAG, "onQueryTextSubmit: "+);
 //                navController.navigate(R.id.productListFragment,bundle);
 //                Log.i(TAG, "onQueryTextSubmit: "+s);
 //                return false;
 //            }
 //
 //            @Override
 //            public boolean onQueryTextChange(String s) {
 //                Toast.makeText(getApplicationContext(), s+"onQueryTextChange()", Toast.LENGTH_SHORT).show();
 //
 //                return false;
 //            }
 //        });
 //
 //
 //        return true;
 //    }
 */
}