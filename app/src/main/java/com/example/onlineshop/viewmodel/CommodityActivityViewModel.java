package com.example.onlineshop.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.onlineshop.model.Attribute;
import com.example.onlineshop.model.Comment;
import com.example.onlineshop.model.Image;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.utils.Repository;

import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import retrofit2.Response;

public class CommodityActivityViewModel extends ViewModel {

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }

    public LiveData<Product> getProduct(int id) {

        return Repository.getInstance().getProduct(id, compositeDisposable);

    }

    public LiveData<List<Image>> getImages(int id) {
        LiveData<List<Image>> liveData = new MutableLiveData<>();
        return Repository.getInstance().getImages(id, compositeDisposable);
    }

    public LiveData<List<Comment>> getComments(int id) {

        return Repository.getInstance().getComments(id, compositeDisposable);
    }

    public LiveData<String> submitComment(Comment comment) {

        return Repository.getInstance().submitComment(comment, compositeDisposable);
    }

    public LiveData<List<Attribute>> getAttributes(int id) {

        return Repository.getInstance().getProductAttributes(id, compositeDisposable);
    }

}
