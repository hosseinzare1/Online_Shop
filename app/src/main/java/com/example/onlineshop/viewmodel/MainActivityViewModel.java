package com.example.onlineshop.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.onlineshop.R;
import com.example.onlineshop.model.Account;
import com.example.onlineshop.model.HomeItem;
import com.example.onlineshop.model.Image;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.utils.Repository;

import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class MainActivityViewModel extends ViewModel {
    private static final String TAG = "viewmodel";
    CompositeDisposable disposable = new CompositeDisposable();




    public LiveData<Account> updateAccount(Account account){

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
