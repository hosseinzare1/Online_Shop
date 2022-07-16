package com.example.onlineshop.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.onlineshop.R;
import com.example.onlineshop.model.Account;
import com.example.onlineshop.model.Attribute;
import com.example.onlineshop.model.CartProduct;
import com.example.onlineshop.model.Category;
import com.example.onlineshop.model.Comment;
import com.example.onlineshop.model.Group;
import com.example.onlineshop.model.Image;
import com.example.onlineshop.model.Order;
import com.example.onlineshop.model.Product;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NonNls;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
    public static final String TAG = "Repository";

    private static AppDatabase databaseInstance;

    public Repository(Context context) {
        databaseInstance = AppDatabase.getInstance(context);


    }

    MutableLiveData<Integer> errorLiveData = new MutableLiveData<>(R.string.no_error);

    public MutableLiveData<Integer> getErrorLiveData() {
        return errorLiveData;
    }

    public void addError(Throwable e) {

        if (e instanceof java.net.ConnectException | e instanceof java.net.SocketTimeoutException) {
            //If we can connect to other sites(for example google) then the problem is from the server
            NetworkStatus.hasInternetConnection().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<Boolean>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onSuccess(@NonNull Boolean aBoolean) {
                            int error = aBoolean ? (R.string.server_connection_error) : (R.string.internet_connection_error);
                            if (!errorLiveData.getValue().equals(error))
                                errorLiveData.setValue(error);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                        }
                    });
        }
    }

    public static Repository getInstance(Context context) {
        if (repository == null) {
            repository = new Repository(context);
        }
        return repository;

    }

    public LiveData<List<Order>> getOrders(String number, CompositeDisposable disposable) {
        MutableLiveData<List<Order>> ordersLiveData = new MutableLiveData<>();
        RetrofitInstance.getAPI().getOrders(number).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .map(orders -> {
                    List<Order> convertedOrders = new ArrayList<>();

                    for (@NonNls Order order : orders
                    ) {

                        switch (order.getState()) {
                            case "1":
                                order.setState("ثبت شده");
                                break;
                            case "2":
                                order.setState("در حال پردازش");
                                break;
                            case "3":
                                order.setState("تحویل به اداره پست");
                                break;
                            default:
                                order.setState("نامشخص");
                        }

                        convertedOrders.add(order);
                    }


                    return convertedOrders;
                }).subscribe(
                        new SingleObserver<List<Order>>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                disposable.add(d);
                            }

                            @Override
                            public void onSuccess(@NonNull List<Order> orders) {
                                ordersLiveData.setValue(orders);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {

                            }
                        }
                );
        return ordersLiveData;
    }

    public LiveData<Order> getOrder(String order_id, CompositeDisposable disposable) {
        MutableLiveData<Order> orderLiveData = new MutableLiveData<>();
        RetrofitInstance.getAPI().getOrder(order_id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .map((@NonNls Order order) -> {
                    switch (order.getState()) {
                        case "1":
                            order.setState("ثبت شده");
                            break;
                        case "2":
                            order.setState("در حال پردازش");
                            break;
                        case "3":
                            order.setState("تحویل به اداره پست");
                            break;
                        default:
                            order.setState("نامشخص");
                    }
                    return order;
                }).subscribe(
                        new SingleObserver<Order>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                disposable.add(d);
                            }

                            @Override
                            public void onSuccess(@NonNull Order order) {
                                orderLiveData.setValue(order);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {

                            }
                        }
                );
        return orderLiveData;
    }

    public LiveData<List<Comment>> getUserComments(String user_number, CompositeDisposable disposable) {
        MutableLiveData<List<Comment>> liveData = new MutableLiveData<>();
        RetrofitInstance.getAPI().getUserComments(user_number)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Comment>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull List<Comment> comments) {
                        liveData.setValue(comments);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });

        return liveData;
    }

    public LiveData<String> submitComment(Comment comment, CompositeDisposable disposable) {
        MutableLiveData<String> liveData = new MutableLiveData<>();
        RetrofitInstance.getAPI().submitComment(comment).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<JsonObject>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<JsonObject> jsonObjectResponse) {
                        liveData.setValue(String.valueOf(jsonObjectResponse.code()));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    }
                });

        return liveData;
    }

    public LiveData<String> submitOrder(Order order, CompositeDisposable disposable) {
        MutableLiveData<String> liveData = new MutableLiveData<>();
        RetrofitInstance.getAPI().submitOrder(order).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<JsonObject>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);

                    }

                    @Override
                    public void onSuccess(@NonNull JsonObject jsonObject) {
                        liveData.setValue(jsonObject.toString());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        addError(e);


                    }
                });
        return liveData;
    }

    public List<Product> getHistory() {
        List<Product> productList = databaseInstance.historyDAO().getItems();
        Collections.reverse(productList);
        return productList;
    }

    public void addHistoryItem(Product product) {

        databaseInstance.historyDAO().deleteItem(product.getName());

        if (databaseInstance.historyDAO().getItems().size() > 9) {
            databaseInstance.historyDAO().deleteFirstItem();
        }

        databaseInstance.historyDAO().insertItem(product);


    }


    public LiveData<List<CartProduct>> getCartItems() {
        return databaseInstance.itemCartDao().getItems();
    }

    public boolean isItemExist(CartProduct model) {
        List<String> items = databaseInstance.itemCartDao().isItemExist(model.getName());
        return items.size() > 0;
    }


    public void addCartItem(CartProduct itemModel) {

        if (!isItemExist(itemModel)) {
            databaseInstance.itemCartDao().insertItem(itemModel);

        }


    }


    public void deleteCartItem(CartProduct itemModel) {
        databaseInstance.itemCartDao().deleteItem(itemModel.getName());
    }

    public void RemoveAllCartItems() {
        databaseInstance.itemCartDao().removeAllCartItems();
    }


    public void increaseItemCount(CartProduct model) {
        databaseInstance.itemCartDao().increaseItemCount(model.getName());

    }

    public void decreaseItemCount(CartProduct model) {
        databaseInstance.itemCartDao().decreaseItemCount(model.getName());

    }


    public String getUserName(Context context) {
        SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences(context.getString(R.string.logged_in_shared_preferences), Context.MODE_PRIVATE);
        return sharedPreferences.getString(context.getString(R.string.logged_in_name_KEY), context.getString(R.string.default_name));
    }

    public String getUserNumber(Context context) {
        SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences(context.getString(R.string.logged_in_shared_preferences), Context.MODE_PRIVATE);
        return sharedPreferences.getString(context.getString(R.string.logged_in_number_KEY), context.getString(R.string.default_name));
    }

    public String getUserAddress(Context context) {
        SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences(context.getString(R.string.logged_in_shared_preferences), Context.MODE_PRIVATE);
        return sharedPreferences.getString(context.getString(R.string.logged_in_address_KEY), context.getString(R.string.default_address));
    }

    public String getUserEmail(Context context) {
        SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences(context.getString(R.string.logged_in_shared_preferences), Context.MODE_PRIVATE);
        return sharedPreferences.getString(context.getString(R.string.logged_in_email_KEY), context.getString(R.string.default_email));
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
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        addError(e);
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

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        addError(e);

                    }
                });
        return liveData;
    }

    public LiveData<List<Category>> getCategories(int groupID) {
        MutableLiveData<List<Category>> liveData = new MutableLiveData<>();
        RetrofitInstance.getAPI().getCategories(groupID).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Category>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<Category> categories) {
                        liveData.setValue(categories);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        addError(e);

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
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        addError(e);
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
                        liveData.setValue(account);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        addError(e);
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
                                   disposable.add(d);
                               }

                               @Override
                               public void onSuccess(@NonNull List<Comment> comments) {
                                   liveData.setValue(comments);
                               }

                               @Override
                               public void onError(@NonNull Throwable e) {
                                   addError(e);

                               }
                           }
                );
        return liveData;
    }

    public LiveData<Integer> delete_comment(int id, CompositeDisposable disposable) {
        MutableLiveData<Integer> status = new MutableLiveData<>();
        RetrofitInstance.getAPI().deleteComment(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<JsonObject>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<JsonObject> jsonObjectResponse) {
                        status.setValue(jsonObjectResponse.code());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
        return status;
    }

    public LiveData<Integer> edit_comment(Comment comment, CompositeDisposable disposable) {
        MutableLiveData<Integer> status = new MutableLiveData<>();
        RetrofitInstance.getAPI().editComment(comment, comment.getId()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<JsonObject>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<JsonObject> jsonObjectResponse) {
                        status.setValue(jsonObjectResponse.code());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
        return status;
    }

    public LiveData<List<Image>> getNewsImages(CompositeDisposable disposable) {
        MutableLiveData<List<Image>> liveData = new MutableLiveData<>();

        RetrofitInstance.getAPI().getNewsImages().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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
                        addError(e);

                    }
                });

        return liveData;


    }

    public LiveData<List<Product>> getSameProducts(int id, CompositeDisposable disposable) {
        MutableLiveData<List<Product>> liveData = new MutableLiveData<>();

        RetrofitInstance.getAPI().getSameProducts(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Product>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);

                    }

                    @Override
                    public void onSuccess(@NonNull List<Product> products) {
                        liveData.setValue(products);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        addError(e);

                    }
                });


        return liveData;


    }

    public LiveData<List<Product>> getBestselling(CompositeDisposable disposable) {
        MutableLiveData<List<Product>> liveData = new MutableLiveData<>();

        RetrofitInstance.getAPI().getBestselling().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Product>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull List<Product> products) {
                        liveData.setValue(products);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        addError(e);
                    }
                });

        return liveData;
    }

    public LiveData<List<Product>> getSpecialDiscounts(CompositeDisposable disposable) {
        MutableLiveData<List<Product>> liveData = new MutableLiveData<>();

        RetrofitInstance.getAPI().GetSpecialDiscounts().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Product>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);

                    }

                    @Override
                    public void onSuccess(@NonNull List<Product> products) {
                        liveData.setValue(products);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        addError(e);

                    }
                });

        return liveData;
    }

    public LiveData<Product> getProduct(int id, CompositeDisposable disposable) {
        MutableLiveData<Product> liveData = new MutableLiveData<>();


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
                        liveData.setValue(product);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        addError(e);
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
                        disposable.add(d);

                    }

                    @Override
                    public void onSuccess(@NonNull List<Attribute> attributes) {
                        liveData.setValue(attributes);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        addError(e);

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
                        addError(e);
                    }
                });
        return productsList;

    }

    public LiveData<List<Product>> getProductsByCategory(String category, CompositeDisposable disposable) {
        MutableLiveData<List<Product>> productsList = new MutableLiveData<>();

        RetrofitInstance.getAPI().getProductsByCategory(category)
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
                        addError(e);
                    }
                });
        return productsList;

    }

    public LiveData<Response<JsonObject>> login(String number, String password, CompositeDisposable disposable) {
        MutableLiveData<Response<JsonObject>> loginLiveData = new MutableLiveData<>();


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
                        loginLiveData.setValue(jsonObjectResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        addError(e);
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
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<JsonObject> jsonObjectResponse) {
                        signupLiveData.setValue(jsonObjectResponse.code());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        addError(e);
                    }
                });

        return signupLiveData;
    }


    public LiveData<List<Product>> getProductsByGroup(String group, CompositeDisposable disposable) {
        MutableLiveData<List<Product>> liveData = new MutableLiveData<>();

        RetrofitInstance.getAPI().getProductsByGroup(group).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Product>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull List<Product> products) {
                        liveData.setValue(products);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        addError(e);
                    }
                });

        return liveData;

    }
}
