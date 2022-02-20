package com.example.onlineshop.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.onlineshop.R;
import com.example.onlineshop.model.Account;
import com.example.onlineshop.model.CartItemModel;
import com.example.onlineshop.model.HomeItem;
import com.example.onlineshop.model.Image;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.utils.Repository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class MainActivityViewModel extends ViewModel {
    private static final String TAG = "viewmodel";
    CompositeDisposable disposable = new CompositeDisposable();

    List<CartItemModel> cartItemsList = new ArrayList<>();
    MutableLiveData<List<CartItemModel>> cartItemsLiveData = new MutableLiveData<>();

    MutableLiveData<String> totalPriceLiveData = new MutableLiveData<>();

    public MutableLiveData<String> getTotalPrice() {
        return totalPriceLiveData;
    }

    public void setTotalPriceLiveData() {
        int totalPrice=0;
        for (CartItemModel model: cartItemsList
             ) {
            totalPrice+=Integer.parseInt(model.getPrice())*Integer.parseInt(model.getCount());
        }
        totalPriceLiveData.setValue(String.valueOf(totalPrice));
    }

    public void addItemCount(int position){
        cartItemsList.get(position).setCount(String.valueOf(
                Integer.parseInt(cartItemsList.get(position).getCount())+1)
                );
        cartItemsLiveData.setValue(cartItemsList);

        setTotalPriceLiveData();
    }
    public void reduceItemCount(int position){
        if(Integer.parseInt(cartItemsList.get(position).getCount())>1){

            cartItemsList.get(position).setCount(String.valueOf(
                    Integer.parseInt(cartItemsList.get(position).getCount())-1)
            );
            cartItemsLiveData.setValue(cartItemsList);
        }

        setTotalPriceLiveData();

    }
    public void deleteFromCart(int position){
        cartItemsList.remove(position);
        cartItemsLiveData.setValue(cartItemsList);

        setTotalPriceLiveData();
    }


    public LiveData<List<CartItemModel>> getCartItems() {
        return cartItemsLiveData;
    }

    public void addCartItem(CartItemModel item) {

        //TODO disable add button if exist
        if (isProductExist(item)) {

        } else {
            cartItemsList.add(item);
            cartItemsLiveData.setValue(cartItemsList);
        }

        setTotalPriceLiveData();

    }

    public boolean isProductExist(CartItemModel itemModel) {
        boolean isExist = false;

        for (CartItemModel model : cartItemsList
        ) {
            if (model.getName().equals(itemModel.getName())) {
                isExist = true;
                break;

            }
        }

        return isExist;
    }

    public LiveData<Account> updateAccount(Account account) {

        return Repository.getInstance().updateAccount(account);
    }

    public LiveData<Account> getAccountDetails(String number) {
        LiveData<Account> liveData = new MutableLiveData<>();

        liveData = Repository.getInstance().getAccountDetails(number, disposable);

        //        Log.i(TAG, "getAccountDetails: "+liveData.getValue().getName());

        return liveData;

    }

    public LiveData<List<HomeItem>> getAllItems() {
        LiveData<List<HomeItem>> liveData = new MutableLiveData<>();
        return Repository.getInstance().getAll(disposable);
    }


    public LiveData<Product> getDetails(int id) {
        LiveData<Product> liveData = new MutableLiveData<>();
        return Repository.getInstance().getDetails(id, disposable);
    }


    public LiveData<List<Image>> getImages(int id) {
        LiveData<List<Image>> liveData = new MutableLiveData<>();
        return Repository.getInstance().getImages(id, disposable);
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
