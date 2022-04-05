package com.example.onlineshop.viewmodel;

import android.content.Context;

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

    private final Context context;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    Repository repository;

    public CommodityActivityViewModel(Context context) {
        this.context = context;
        repository = new Repository(context);
    }



    public LiveData<Product> getProduct(int id) {

        return repository.getProduct(id, compositeDisposable);

    }

    public LiveData<List<Image>> getImages(int id) {
        LiveData<List<Image>> liveData = new MutableLiveData<>();
        return repository.getImages(id, compositeDisposable);
    }

    public LiveData<List<Comment>> getComments(int id) {

        return repository.getComments(id, compositeDisposable);
    }

    public LiveData<String> submitComment(Comment comment) {

        return repository.submitComment(comment, compositeDisposable);
    }

    public LiveData<List<Attribute>> getAttributes(int id) {

        return repository.getProductAttributes(id, compositeDisposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();

    }

}
