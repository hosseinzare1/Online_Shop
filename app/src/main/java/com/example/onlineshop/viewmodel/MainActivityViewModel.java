package com.example.onlineshop.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.onlineshop.model.Account;
import com.example.onlineshop.model.CartItemModel;
import com.example.onlineshop.model.Category;
import com.example.onlineshop.model.Group;
import com.example.onlineshop.model.Image;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.utils.Repository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class MainActivityViewModel extends ViewModel {
    private static final String TAG = "viewmodel";
    CompositeDisposable disposable = new CompositeDisposable();
//
//    List<CartItemModel> cartItemsList = new ArrayList<>();
//    MutableLiveData<List<CartItemModel>> cartItemsLiveData = new MutableLiveData<>();
//
//    MutableLiveData<String> totalPriceLiveData = new MutableLiveData<>();

    Context context;

    Repository repository;


    public MainActivityViewModel(Context context) {
        this.context = context;
        repository = new Repository(context);
    }


    public LiveData<List<Product>> searchProducts(String search_text) {
        return repository.searchProducts(search_text);

    }

    public LiveData<List<Category>> getCategories(int groupID) {
        return repository.getCategories(groupID);
    }

    public LiveData<List<Group>> getGroups() {
        return repository.getGroups();
    }


    public void addCartItem(CartItemModel item) {

        repository.addCartItem(item);

//        //TODO disable add button if exist

        Log.i(TAG, "addCartItem: " + item.getName());

    }

    public void increaseItemCount(CartItemModel cartItemModel) {
        repository.increaseItemCount(cartItemModel);
    }

    public void decreaseItemCount(CartItemModel cartItemModel) {
        repository.decreaseItemCount(cartItemModel);
    }

    public void deleteFromCart(CartItemModel cartItemModel) {
        repository.deleteCartItem(cartItemModel);
    }

    public LiveData<List<CartItemModel>> getCartItems() {
        Log.i(TAG, "getCartItems: called ");

        return repository.getCartItems();


    }

    public LiveData<Double> getTotalPrice() {

        MutableLiveData<Double> totalPriceLiveData = new MutableLiveData<>();

        repository.getCartItems().observe((LifecycleOwner) context, new Observer<List<CartItemModel>>() {
            @Override
            public void onChanged(List<CartItemModel> cartItemModels) {

                Double totalPrice = 0.0;
                for (CartItemModel item : cartItemModels
                ) {
                    totalPrice += item.getCount() * item.getPrice();

                }

                totalPriceLiveData.setValue(totalPrice);
            }
        });


        return totalPriceLiveData;
    }


    public LiveData<Account> updateAccount(Account account) {

        return repository.updateAccount(account);
    }

    public LiveData<Account> getAccountDetails(String number) {
        LiveData<Account> liveData = new MutableLiveData<>();

        liveData = repository.getAccountDetails(number, disposable);

        //        Log.i(TAG, "getAccountDetails: "+liveData.getValue().getName());

        return liveData;

    }

    public LiveData<List<Product>> getAllItems() {
        LiveData<List<Product>> liveData = new MutableLiveData<>();
        return repository.getAll(disposable);
    }

    public LiveData<List<Product>> getProductsByCategory(int id) {
        LiveData<List<Product>> liveData = new MutableLiveData<>();
        return repository.getProductsByCategory(id, disposable);
    }


    public LiveData<Product> getDetails(int id) {
        LiveData<Product> liveData = new MutableLiveData<>();
        return repository.getProduct(id, disposable);
    }


    public LiveData<List<Image>> getImages(int id) {
        LiveData<List<Image>> liveData = new MutableLiveData<>();
        return repository.getImages(id, disposable);
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
