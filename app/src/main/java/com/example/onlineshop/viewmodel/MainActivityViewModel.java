package com.example.onlineshop.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.onlineshop.model.Account;
import com.example.onlineshop.model.CartItemModel;
import com.example.onlineshop.model.Category;
import com.example.onlineshop.model.Comment;
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

    public String getUserName() {
        return repository.getUserName(context);
    }

    public String getUserNumber() {
        return repository.getUserNumber(context);
    }

    public String getUserAddress() {
        return repository.getUserAddress(context);
    }

    public MainActivityViewModel(Context context) {
        this.context = context;
        repository = Repository.getInstance(context);
    }

    public MutableLiveData<Integer> getError() {
        return repository.getErrorLiveData();
    }


    public LiveData<List<Order>> getOrders(String number) {

        return repository.getOrders(number, disposable);

    }

    public LiveData<List<Comment>> getUserComments(String user_number) {

        return repository.getUserComments(user_number, disposable);
    }

    public LiveData<Integer> deleteComment(int id) {
        return repository.delete_comment(id, disposable);
    }

    public LiveData<Integer> editComment(Comment comment) {
        return repository.edit_comment(comment, disposable);
    }


    public void clearCommentForm() {
        comment_rating.setValue(0.0f);
        comment_title.setValue("");
        comment_text.setValue("");
    }

    public MutableLiveData<String> comment_title = new MutableLiveData<>();
    public MutableLiveData<String> comment_text = new MutableLiveData<>();
    public MutableLiveData<Float> comment_rating = new MutableLiveData<>();
    public ObservableArrayList<CommodityActivityViewModel.CommentFormErrors> commentFormErrors = new ObservableArrayList<>();

    public enum CommentFormErrors {
        RATING_INVALID,
        TITLE_INVALID,
        TEXT_INVALID
    }

    public boolean isCommentFormValid() {
        commentFormErrors.clear();
        //title validation
        if (comment_title.getValue() != null) {
            if (comment_title.getValue().length() < 3)
                commentFormErrors.add(CommodityActivityViewModel.CommentFormErrors.TITLE_INVALID);
        } else commentFormErrors.add(CommodityActivityViewModel.CommentFormErrors.TITLE_INVALID);

        //text validation
        if (comment_text.getValue() != null) {
            if (comment_text.getValue().length() < 3)
                commentFormErrors.add(CommodityActivityViewModel.CommentFormErrors.TEXT_INVALID);
        } else commentFormErrors.add(CommodityActivityViewModel.CommentFormErrors.TEXT_INVALID);


        //rating validation -- show snackBar if not valid.
        if (comment_rating.getValue() != null) {
            if (comment_rating.getValue() > 5 | comment_rating.getValue() < 1)
                commentFormErrors.add(CommodityActivityViewModel.CommentFormErrors.RATING_INVALID);
        } else commentFormErrors.add(CommodityActivityViewModel.CommentFormErrors.RATING_INVALID);

        return commentFormErrors.isEmpty();
    }

    public LiveData<Order> getOrder(String order_id) {

        return repository.getOrder(order_id, disposable);

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

    public void RemoveAllCartItems() {
        repository.RemoveAllCartItems();
    }

    public LiveData<List<CartItemModel>> getCartItems() {
        Log.i(TAG, "getCartItems: called ");

        return repository.getCartItems();


    }

    public MutableLiveData<Long> getTotalPrice() {

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

    public MutableLiveData<Long> getTotalPriceWithDiscount() {

        MutableLiveData<Long> totalPriceLiveData = new MutableLiveData<>();

        repository.getCartItems().observe((LifecycleOwner) context, new Observer<List<CartItemModel>>() {
            @Override
            public void onChanged(List<CartItemModel> cartItemModels) {

                long totalPriceWithDiscount = 0;
                for (CartItemModel item : cartItemModels
                ) {
                    totalPriceWithDiscount += item.getQuantity() * (item.getPrice() * (100 - item.getDiscount()) / 100);

                }

                totalPriceLiveData.setValue(totalPriceWithDiscount);
            }
        });


        return totalPriceLiveData;
    }

    public MutableLiveData<Long> getTotalDiscount() {

        MutableLiveData<Long> totalPriceLiveData = new MutableLiveData<>();

        repository.getCartItems().observe((LifecycleOwner) context, new Observer<List<CartItemModel>>() {
            @Override
            public void onChanged(List<CartItemModel> cartItemModels) {

                long totalDiscount = 0;
                for (CartItemModel item : cartItemModels
                ) {
                    totalDiscount += item.getQuantity() * (item.getPrice() * (item.getDiscount()) / 100);

                }

                totalPriceLiveData.setValue(totalDiscount);
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
