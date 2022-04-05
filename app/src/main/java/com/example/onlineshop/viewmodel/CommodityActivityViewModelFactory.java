package com.example.onlineshop.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class CommodityActivityViewModelFactory implements ViewModelProvider.Factory {

    Context context;

    public CommodityActivityViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> aClass) {
        return (T) new CommodityActivityViewModel(context);
    }
}
