package com.example.onlineshop.viewmodel;

import android.content.Context;

import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.onlineshop.utils.InputValidator;
import com.example.onlineshop.utils.Repository;
import com.google.gson.JsonObject;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import retrofit2.Response;

public class LoginSignupViewModel extends ViewModel {

    CompositeDisposable disposable = new CompositeDisposable();


    //TODO reset values when switch between fragments ?
    public MutableLiveData<String> name = new MutableLiveData<>();
    public MutableLiveData<String> number = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();

    public ObservableArrayList<InputValidator.InputErrors> formErrors = new ObservableArrayList<>();

    String TAG = "LoginSignupViewModel";

    public boolean isSigningFormValid() {
        formErrors.clear();

        InputValidator.phoneNumberValidation(number.getValue(), formErrors);
        InputValidator.passwordValidation(password.getValue(), formErrors);

        return formErrors.isEmpty();
    }

    public boolean isSignupFormValid() {
        formErrors.clear();

        InputValidator.nameValidation(name.getValue(), formErrors);
        InputValidator.phoneNumberValidation(number.getValue(), formErrors);
        InputValidator.passwordValidation(password.getValue(), formErrors);

        return formErrors.isEmpty();
    }


    Repository repository;


    public LoginSignupViewModel(Context context) {

        repository = Repository.getInstance(context);
    }

    public MutableLiveData<Integer> getErrorLiveData() {
        return repository.getErrorLiveData();
    }

    public LiveData<Response<JsonObject>> login() {
        return repository.login(number.getValue(), password.getValue(), disposable);
    }

    public LiveData<Integer> signup() {
        return repository.signup(number.getValue(), password.getValue(), name.getValue(), disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
