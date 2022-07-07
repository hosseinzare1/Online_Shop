package com.example.onlineshop.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.onlineshop.utils.Repository;
import com.google.gson.JsonObject;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import retrofit2.Response;

public class LoginSignupViewModel extends ViewModel {

    public enum FormError {
        MISSING_NAME,
        INVALID_NUMBER,
        INVALID_PASSWORD,
    }

    CompositeDisposable disposable = new CompositeDisposable();


    //TODO reset values when switch bitwen fragments ?
    public MutableLiveData<String> name = new MutableLiveData<>();
    public MutableLiveData<String> number = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();

    public ObservableArrayList<FormError> formErrors = new ObservableArrayList<>();

    String TAG = "LoginSignupViewModel";

    public boolean isSigningFormValid() {
        formErrors.clear();

        //phone validation
        if (number.getValue() != null) {
            String numberValue = number.getValue().trim();
            if (numberValue.startsWith("+98")) {
                numberValue = numberValue.substring(3);
            } else if (numberValue.startsWith("0")) {
                numberValue = numberValue.substring(1);
            }
            Log.i(TAG, "isLoginFormValid: ");
            if (numberValue.length() != 10) {
                formErrors.add(FormError.INVALID_NUMBER);
            }
        } else {
            formErrors.add(FormError.INVALID_NUMBER);
        }


        //password validation
        if (password.getValue() != null) {
            if (password.getValue().length() < 6) formErrors.add(FormError.INVALID_PASSWORD);
        } else formErrors.add(FormError.INVALID_PASSWORD);

        return formErrors.isEmpty();
    }

    public boolean isSignupFormValid() {
        formErrors.clear();

        //name validation
        if (name.getValue() != null) {
            if (name.getValue().length() < 3) {
                formErrors.add(FormError.MISSING_NAME);
            }
        } else {
            formErrors.add(FormError.MISSING_NAME);
        }

        //phone validation
        if (number.getValue() != null) {
            String numberValue = number.getValue().trim();
            if (numberValue.startsWith("+98")) {
                numberValue = numberValue.substring(3);
            } else if (numberValue.startsWith("0")) {
                numberValue = numberValue.substring(1);
            }
            if (numberValue.length() != 10) {
                formErrors.add(FormError.INVALID_NUMBER);
            }
        } else {
            formErrors.add(FormError.INVALID_NUMBER);
        }


        //password validation
        if (password.getValue() != null) {
            if (password.getValue().length() < 6) formErrors.add(FormError.INVALID_PASSWORD);
        } else {
            formErrors.add(FormError.INVALID_PASSWORD);
        }


        return formErrors.isEmpty();
    }


    Repository repository;

//    Context context;

    public LoginSignupViewModel(Context context) {
//        this.context = context;
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
