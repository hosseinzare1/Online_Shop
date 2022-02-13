package com.example.onlineshop.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.onlineshop.model.User;
import com.example.onlineshop.utils.Repository;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class LoginSignupViewModel extends ViewModel {
    CompositeDisposable disposable = new CompositeDisposable();

    public MutableLiveData<String> number = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    public MutableLiveData<String> name = new MutableLiveData<>();


    public MutableLiveData<User> signInUserMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<User> singUpUserMutableLiveData = new MutableLiveData<>();



    public void onSignInClicked() {
        User user = new User(number.getValue(), password.getValue());
        signInUserMutableLiveData.setValue(user);

    }

    public void onSignUpClicked() {
        User user = new User(number.getValue(), password.getValue(), name.getValue());
        singUpUserMutableLiveData.setValue(user);

    }

    public LiveData<Integer> login(String number, String password) {
        return Repository.getInstance().login(number, password, disposable);
    }

    public LiveData<Integer> signup(String number, String password, String name) {
        return Repository.getInstance().signup(number, password, name, disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
