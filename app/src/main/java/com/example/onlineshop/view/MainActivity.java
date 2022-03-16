package com.example.onlineshop.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    NavHostFragment navHostFragment;
    NavController navController;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.Main_fragmentContainerView);
        navController = navHostFragment.getNavController();

        setSupportActionBar(binding.mainToolbar);
//        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        binding.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_home:
                        navController.navigate(R.id.productListFragment);
                        break;
                    case R.id.item_categories:
                        navController.navigate(R.id.productsCategoryFragment);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
menuInflater.inflate(R.menu.toolbar_searchview,menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.toolbar_searchView).getActionView();
        searchView.setQueryHint("جستوجو");
        searchView.setIconifiedByDefault(false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();

                return false;
            }
        });

        return true;
    }
}