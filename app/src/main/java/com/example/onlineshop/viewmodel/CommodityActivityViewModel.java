package com.example.onlineshop.viewmodel;

import android.content.Context;

import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.onlineshop.model.Attribute;
import com.example.onlineshop.model.CartItemModel;
import com.example.onlineshop.model.Comment;
import com.example.onlineshop.model.Image;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.utils.InputValidator;
import com.example.onlineshop.utils.Repository;

import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class CommodityActivityViewModel extends ViewModel {

    private Context context;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    Repository repository;

    public MutableLiveData<Integer> selectedProductLiveData = new MutableLiveData<>();

    public MutableLiveData<Integer> getErrorLiveData() {
        return repository.getErrorLiveData();
    }

    public CommodityActivityViewModel(Context context) {
        this.context = context;
        repository = Repository.getInstance(context);
    }

    public LiveData<List<CartItemModel>> getCartItems() {
        return repository.getCartItems();
    }

    public String getUserName() {
        return repository.getUserName(context);
    }

    public String getUserNumber() {
        return repository.getUserNumber(context);
    }

    public void addHistoryItem(Product product) {
        repository.addHistoryItem(product);
    }

    public LiveData<Product> getProduct(int id) {

        return repository.getProduct(id, compositeDisposable);

    }

    public LiveData<List<Product>> getSameProducts(int id) {
        return repository.getSameProducts(id, compositeDisposable);
    }

    public LiveData<List<Image>> getImages(int id) {
        return repository.getImages(id, compositeDisposable);
    }

    public LiveData<List<Comment>> getComments(int id) {

        return repository.getComments(id, compositeDisposable);
    }


    public LiveData<List<Attribute>> getAttributes(int id) {

        return repository.getProductAttributes(id, compositeDisposable);
    }


    public void clearCommentForm() {
        comment_rating.setValue(0.0f);
        comment_title.setValue("");
        comment_text.setValue("");
    }

    public MutableLiveData<String> comment_title = new MutableLiveData<>();
    public MutableLiveData<String> comment_text = new MutableLiveData<>();
    public MutableLiveData<Float> comment_rating = new MutableLiveData<>();
    public ObservableArrayList<InputValidator.InputErrors> commentFormErrors = new ObservableArrayList<>();


    public boolean isCommentFormValid() {
        commentFormErrors.clear();

        InputValidator.titleValidation(comment_title.getValue(),commentFormErrors);
        InputValidator.textValidation(comment_text.getValue(),commentFormErrors);
        InputValidator.ratingValidation(comment_rating.getValue(),commentFormErrors);
//
//        //title validation
//        if (comment_title.getValue() != null) {
//            if (comment_title.getValue().length() < 3)
//                commentFormErrors.add(CommentFormErrors.TITLE_INVALID);
//        } else commentFormErrors.add(CommentFormErrors.TITLE_INVALID);
//
//        //text validation
//        if (comment_text.getValue() != null) {
//            if (comment_text.getValue().length() < 3)
//                commentFormErrors.add(CommentFormErrors.TEXT_INVALID);
//        } else commentFormErrors.add(CommentFormErrors.TEXT_INVALID);
//
//
//        //rating validation -- show snackBar if not valid.
//        if (comment_rating.getValue() != null) {
//            if (comment_rating.getValue() > 5 | comment_rating.getValue() < 1)
//                commentFormErrors.add(CommentFormErrors.RATING_INVALID);
//        } else commentFormErrors.add(CommentFormErrors.RATING_INVALID);

        return commentFormErrors.isEmpty();
    }

    public LiveData<String> submitComment(Comment comment) {
        return repository.submitComment(comment, compositeDisposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();

    }

}
