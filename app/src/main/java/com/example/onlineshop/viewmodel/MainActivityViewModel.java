package com.example.onlineshop.viewmodel;

import android.content.Context;
import android.util.Log;

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
import com.example.onlineshop.model.Order;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.utils.Repository;

import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class MainActivityViewModel extends ViewModel {
    private static final String TAG = "viewmodel";
    CompositeDisposable disposable = new CompositeDisposable();

//    List<CartItemModel> cartItemsList = new ArrayList<>();
//    MutableLiveData<List<CartItemModel>> cartItemsLiveData = new MutableLiveData<>();
//
//    MutableLiveData<String> totalPriceLiveData = new MutableLiveData<>();

    Context context;

    Repository repository;


    public String getUserNumber() {
        return repository.getUserNumber(context);
    }

    public MainActivityViewModel(Context context) {
        this.context = context;
        repository = Repository.getInstance(context);
    }

    public MutableLiveData<Integer> getError() {
        return repository.getErrorLiveData();
    }

    public LiveData<String> submitOrder(Order order) {
        return repository.submitOrder(order, disposable);
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

    public LiveData<Long> getTotalPrice() {

        MutableLiveData<Long> totalPriceLiveData = new MutableLiveData<>();

        repository.getCartItems().observe((LifecycleOwner) context, new Observer<List<CartItemModel>>() {
            @Override
            public void onChanged(List<CartItemModel> cartItemModels) {

                long totalPrice = 0;
                for (CartItemModel item : cartItemModels
                ) {
                    totalPrice += item.getQuantity() * item.getPrice();

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

        return repository.getAccountDetails(number, disposable);

    }

    public List<Product> getHistory() {
        return repository.getHistory();

    }

    public LiveData<List<Product>> getAllItems() {
        return repository.getAll(disposable);
    }

    public LiveData<List<Product>> getSpecialDiscounts() {
        return repository.getSpecialDiscounts(disposable);
    }

    public LiveData<List<Product>> getBestSelling() {
        return repository.getBestselling(disposable);
    }

    public LiveData<List<Product>> getProductsByCategory(String category) {
        return repository.getProductsByCategory(category, disposable);
    }


    public LiveData<Product> getDetails(int id) {
        return repository.getProduct(id, disposable);
    }


    public LiveData<List<Image>> getImages(int id) {
        return repository.getImages(id, disposable);
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }

    public LiveData<List<Product>> getProductsByGroup(String group) {
        return repository.getProductsByGroup(group, disposable);
    }
}
