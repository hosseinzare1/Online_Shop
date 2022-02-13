package com.example.onlineshop.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.onlineshop.model.HomeItem;
import com.example.onlineshop.model.Image;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.utils.Repository;

import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class MainActivityViewModel extends ViewModel {
CompositeDisposable disposable = new CompositeDisposable();

    public LiveData<List<HomeItem>> getAllItems(){
        LiveData<List<HomeItem>> liveData = new MutableLiveData<>();
       return Repository.getInstance().getAll(disposable);
    }


    public LiveData<Product> getDetails(int id){
        LiveData<Product> liveData = new MutableLiveData<>();
       return Repository.getInstance().getDetails(id,disposable);
    }


    public LiveData<List<Image>> getImages(int id){
        LiveData<List<Image>> liveData = new MutableLiveData<>();
       return Repository.getInstance().getImages(id,disposable);
    }




    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
