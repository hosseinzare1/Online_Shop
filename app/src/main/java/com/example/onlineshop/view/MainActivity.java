package com.example.onlineshop.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    NavHostFragment navHostFragment;
    NavController navController;

    private final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.Main_fragmentContainerView);
        navController = navHostFragment.getNavController();

//        setSupportActionBar(binding.mainToolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        binding.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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


    //search
/**
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
 **/
}