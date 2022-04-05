package com.example.onlineshop.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.onlineshop.R;
import com.example.onlineshop.model.Account;
import com.example.onlineshop.model.Attribute;
import com.example.onlineshop.model.CartItemModel;
import com.example.onlineshop.model.Category;
import com.example.onlineshop.model.Comment;
import com.example.onlineshop.model.Group;
import com.example.onlineshop.model.Image;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.model.User;
import com.google.gson.JsonObject;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;

public class Repository {

    private static Repository repository = null;
    public static final String TAG = "repository";
    private final Context context;

    private static AppDatabase databaseInstance;

    public Repository(Context context) {
        this.context = context;
        databaseInstance = AppDatabase.getInstance(context);

    }

    //Context context;
    public static final Repository getInstance(Context context) {
        if (repository == null) {
            repository = new Repository(context);
        }
        return repository;

    }

    public LiveData<List<CartItemModel>> getCartItems() {


        return databaseInstance.itemCartDao().getItems();


    }

    public void addCartItems(CartItemModel itemModel) {

        databaseInstance.itemCartDao().insertItem(itemModel);


    }

    public void updateCartItems(CartItemModel itemModel) {

        databaseInstance.itemCartDao().updateItem(itemModel);

    }

    public void deleteCartItem(CartItemModel itemModel) {

        databaseInstance.itemCartDao().deleteItem(itemModel);

    }


//    public static final Repository getInstance(Context context) {
//        if (repository == null) {
//            repository = new Repository();
//        }
//        return repository;
//    }

    public String getUserNumber(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.logged_in_number_file), Context.MODE_PRIVATE);
        return sharedPreferences.getString(context.getString(R.string.logged_in_number_KEY), "0");
    }

    public String getUserName(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.logged_in_number_file), Context.MODE_PRIVATE);
        return sharedPreferences.getString(context.getString(R.string.logged_in_name_KEY), "0");
    }


    public LiveData<List<Product>> searchProducts(String search_text) {
        MutableLiveData<List<Product>> liveData = new MutableLiveData<>();
        RetrofitInstance.getAPI().searchProducts(search_text).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Product>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<Product> products) {
                        liveData.setValue(products);
//                        Log.i(TAG, "onSuccess: "+products.get(0).getName());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i(TAG, "onError: " + e.getMessage());
                    }
                });
        return liveData;

    }

    public LiveData<List<Group>> getGroups() {
        MutableLiveData<List<Group>> liveData = new MutableLiveData<>();
        RetrofitInstance.getAPI().getGroups().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Group>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<Group> groups) {
                        liveData.setValue(groups);
//                        Log.i(TAG, "onSuccess: g1 " + groups.get(1).getName());

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
        return liveData;
    }

    public LiveData<List<Category>> getCategories(int groupID) {
        MutableLiveData<List<Category>> liveData = new MutableLiveData<>();
        RetrofitInstance.getAPI().getCategorys(groupID).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Category>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<Category> categories) {
                        liveData.setValue(categories);
//                        Log.i(TAG, "onSuccess: g1 " + categories.get(1).getName());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
        return liveData;
    }

    public LiveData<Account> updateAccount(Account account) {
        MutableLiveData<Account> liveData = new MutableLiveData<>();
        RetrofitInstance.getAPI().updateAccount(account.getNumber(), account.getName(), account.getNumber(), account.getAddress(), account.getEmail(), account.getPassword())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Account>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Account account) {
                        liveData.setValue(account);
//                        Log.i(TAG, "onSuccess: " + account.getName());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
//                        Log.i(TAG, "onError: " + e.getMessage() + "*****" + e.toString());
                    }
                });


        return liveData;
    }

    public LiveData<Account> getAccountDetails(String number, CompositeDisposable disposable) {
        MutableLiveData<Account> liveData = new MutableLiveData<>();

        RetrofitInstance.getAPI().getAccountDetails(number)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Account>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Account account) {
//                        Log.i(TAG, "onSuccess: " + account.getName());
                        liveData.setValue(account);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i(TAG, "onError: " + e.getMessage());
                    }
                });
        return liveData;

    }

    public LiveData<List<Comment>> getComments(int id, CompositeDisposable disposable) {
        MutableLiveData<List<Comment>> liveData = new MutableLiveData<>();
        RetrofitInstance.getAPI().getComments(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Comment>>() {
                               @Override
                               public void onSubscribe(@NonNull Disposable d) {

                               }

                               @Override
                               public void onSuccess(@NonNull List<Comment> comments) {
                                   liveData.setValue(comments);
                               }

                               @Override
                               public void onError(@NonNull Throwable e) {

                               }
                           }
                );
        return liveData;
    }

    public LiveData<String> submitComment(Comment comment, CompositeDisposable disposable) {
        MutableLiveData<String> liveData = new MutableLiveData<>();
        RetrofitInstance.getAPI().submitComment(comment).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull String string) {
                        liveData.setValue(string);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });

        return liveData;
    }

    public LiveData<List<Image>> getImages(int id, CompositeDisposable disposable) {
        MutableLiveData<List<Image>> liveData = new MutableLiveData<>();
        RetrofitInstance.getAPI().getImages(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Image>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull List<Image> images) {
                        liveData.setValue(images);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });

        return liveData;


    }

    public LiveData<Product> getProduct(int id, CompositeDisposable disposable) {
        MutableLiveData<Product> liveData = new MutableLiveData<>();

        Log.i(TAG, "getDetails: log ok");

        RetrofitInstance.getAPI().getProduct(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Product>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Product product) {
//                        Log.i(TAG, "onSuccess: " + product.getName());
                        liveData.setValue(product);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i(TAG, "onError: " + e.getMessage());
                    }
                });

        return liveData;

    }

    public LiveData<List<Attribute>> getProductAttributes(int id, CompositeDisposable disposable) {
        MutableLiveData<List<Attribute>> liveData = new MutableLiveData<>();

        RetrofitInstance.getAPI().getProductAttributes(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Attribute>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<Attribute> attributes) {
                        liveData.setValue(attributes);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });

        return liveData;

    }

    public LiveData<List<Product>> getAll(CompositeDisposable disposable) {
        MutableLiveData<List<Product>> productsList = new MutableLiveData<>();

        RetrofitInstance.getAPI().getAllItems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Product>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull List<Product> homeItems) {
                        productsList.setValue(homeItems);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i(TAG, "onError: " + e.getMessage());
                    }
                });
        return productsList;

    }

    public LiveData<List<Product>> getProductsByCategory(int id, CompositeDisposable disposable) {
        MutableLiveData<List<Product>> productsList = new MutableLiveData<>();

        RetrofitInstance.getAPI().getProductsByCategory(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Product>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull List<Product> homeItems) {
                        productsList.setValue(homeItems);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i(TAG, "onError: " + e.getMessage());
                    }
                });
        return productsList;

    }

    public LiveData<Integer> login(String number, String password, CompositeDisposable disposable) {
        MutableLiveData<Integer> loginLiveData = new MutableLiveData<>();


        RetrofitInstance.getAPI().login(number, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<JsonObject>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<JsonObject> jsonObjectResponse) {
                        loginLiveData.setValue(jsonObjectResponse.code());

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });


        return loginLiveData;


    }

    public LiveData<Integer> signup(String number, String password, String name, CompositeDisposable disposable) {
        MutableLiveData<Integer> signupLiveData = new MutableLiveData<>();
        RetrofitInstance.getAPI().signup(name, number, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<JsonObject>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i(TAG, "onSubscribe: " + d.toString());
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<JsonObject> jsonObjectResponse) {
                        signupLiveData.setValue(jsonObjectResponse.code());
//                        Log.i(TAG, "onSuccess: " + jsonObjectResponse.code());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i(TAG, "onError: " + e.getMessage());
                    }
                });

        return signupLiveData;
    }


}
