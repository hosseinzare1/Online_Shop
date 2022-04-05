package com.example.onlineshop.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class LoginSignupViewModelFactory implements ViewModelProvider.Factory {

Context context ;

    public LoginSignupViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> aClass) {
        return (T) new LoginSignupViewModel(context);
    }
}
