package com.example.onlineshop.utils;


import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.IdRes;
//import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;

import com.example.onlineshop.R;


public class ErrorObserver {

    MutableLiveData<Integer> errorLiveData;
    //    Fragment fragment;
    NavController navController;
    Activity activity;
    int errorLayoutID;
    private final String TAG = "ErrorObserver";

    public ErrorObserver(Activity activity, NavController navController, MutableLiveData<Integer> errorLiveData, @IdRes int errorLayoutID) {
        this.errorLiveData = errorLiveData;
//        this.fragment = fragment;
        this.navController = navController;
        this.activity = activity;
        this.errorLayoutID = errorLayoutID;
        observeErrors();

    }

    public void setNoConnectionError() {
        errorLiveData.setValue(R.string.no_error);
    }

    public void observeErrors() {
        errorLiveData.observe((LifecycleOwner) activity, integer -> {


            if (integer != R.string.no_error) {
                Bundle bundle = new Bundle();
                bundle.putString(activity.getString(R.string.error_message_key), activity.getString(integer));

                if (integer == R.string.internet_connection_error) {
                    navController.navigate(errorLayoutID, bundle);
                } else if (integer == R.string.server_connection_error) {
                    navController.navigate(errorLayoutID, bundle);
                }
            }
        });
    }

}
