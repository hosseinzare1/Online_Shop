package com.example.onlineshop.viewmodel;

import android.content.Context;

import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.onlineshop.model.Account;
import com.example.onlineshop.model.CartProduct;
import com.example.onlineshop.model.Category;
import com.example.onlineshop.model.Comment;
import com.example.onlineshop.model.Group;
import com.example.onlineshop.model.Image;
import com.example.onlineshop.model.Order;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.utils.InputValidator;
import com.example.onlineshop.utils.Repository;

import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class MainActivityViewModel extends ViewModel {
    private static final String TAG = "MainActivityViewModel";
    CompositeDisposable disposable = new CompositeDisposable();

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

    public ObservableArrayList<InputValidator.InputErrors> profileFormErrors = new ObservableArrayList<>();

    public boolean isProfileFormValid(Account account) {
        profileFormErrors.clear();
        InputValidator.nameValidation(account.getName(), profileFormErrors);
        InputValidator.phoneNumberValidation(account.getNumber(), profileFormErrors);
        InputValidator.passwordValidation(account.getPassword(), profileFormErrors);
        return profileFormErrors.isEmpty();
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

        InputValidator.titleValidation(comment_title.getValue(), commentFormErrors);
        InputValidator.textValidation(comment_text.getValue(), commentFormErrors);
        InputValidator.ratingValidation(comment_rating.getValue(), commentFormErrors);

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


    public void addCartItem(CartProduct item) {

        repository.addCartItem(item);


    }

    public void increaseItemCount(CartProduct cartProduct) {
        repository.increaseItemCount(cartProduct);
    }

    public void decreaseItemCount(CartProduct cartProduct) {
        repository.decreaseItemCount(cartProduct);
    }

    public void deleteFromCart(CartProduct cartProduct) {
        repository.deleteCartItem(cartProduct);
    }

    public void RemoveAllCartItems() {
        repository.RemoveAllCartItems();
    }

    public LiveData<List<CartProduct>> getCartItems() {

        return repository.getCartItems();


    }

    public MutableLiveData<Long> getTotalPrice() {

        MutableLiveData<Long> totalPriceLiveData = new MutableLiveData<>();

        repository.getCartItems().observe((LifecycleOwner) context, cartItemModels -> {

            long totalPrice = 0;
            for (CartProduct item : cartItemModels
            ) {
                totalPrice += item.getQuantity() * item.getPrice();

            }

            totalPriceLiveData.setValue(totalPrice);
        });


        return totalPriceLiveData;
    }

    public MutableLiveData<Long> getTotalPriceWithDiscount() {

        MutableLiveData<Long> totalPriceLiveData = new MutableLiveData<>();

        repository.getCartItems().observe((LifecycleOwner) context, cartItemModels -> {

            long totalPriceWithDiscount = 0;
            for (CartProduct item : cartItemModels
            ) {
                totalPriceWithDiscount += item.getQuantity() * (item.getPrice() * (100 - item.getDiscount()) / 100);

            }

            totalPriceLiveData.setValue(totalPriceWithDiscount);
        });


        return totalPriceLiveData;
    }

    public MutableLiveData<Long> getTotalDiscount() {

        MutableLiveData<Long> totalPriceLiveData = new MutableLiveData<>();

        repository.getCartItems().observe((LifecycleOwner) context, cartItemModels -> {

            long totalDiscount = 0;
            for (CartProduct item : cartItemModels
            ) {
                totalDiscount += item.getQuantity() * (item.getPrice() * (item.getDiscount()) / 100);

            }

            totalPriceLiveData.setValue(totalDiscount);
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

    public LiveData<List<Image>> getNewsImages(){
        return repository.getNewsImages(disposable);
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
